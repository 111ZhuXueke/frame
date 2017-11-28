package org.num.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

/**
 * 实现类
 * @author zxk
 */
public class ConnectionPool implements IConnectionPool {

    //连接池属性配置
    private DBbean dBbean;
    //连接池活动状态,默认false
    private boolean isActive = false;
    //记录创建的总的连接数
    private int contActive = 0;

    //空闲连接
    private List<Connection> freeConnection = new Vector<Connection>();
    //活动连接
    private List<Connection> activeConnection = new Vector<Connection>();
    //线程和连接的绑定，保证事务的统一执行
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();

    public ConnectionPool(DBbean dBbean){
        super();
        this.dBbean = dBbean;
        init();
        checkPool();
    }

    //初始化
    public void init(){
        try {
            Class.forName(dBbean.getDriverName());
            for (int i = 0; i < dBbean.getMinConnections(); i++){
                Connection conn = null;
                conn = newConnection();
                if(conn != null){
                    freeConnection.add(conn);
                    contActive++;
                }
            }
            isActive = true;
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //获得新连接
    private synchronized Connection newConnection() throws ClassNotFoundException, SQLException{
        Connection conn = null;
        if(dBbean != null){
            Class.forName(dBbean.getDriverName());
            conn = DriverManager.getConnection(dBbean.getUrl(),dBbean.getUserName(),dBbean.getPassword());
        }
        return conn;
    }
    //连接是否可用
    private boolean isValid(Connection conn){
        try{
            if(conn == null || conn.isClosed()){
                return false;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }
    public synchronized  Connection getConnection() {
        Connection conn = null;
        try{
            //判断是否超过最大连接数限制
            if(contActive < this.dBbean.getMaxActiveConnections()){
                if(freeConnection.size() > 0){
                    conn = freeConnection.get(0);
                    if(conn != null){
                        threadLocal.set(conn);
                    }
                    freeConnection.remove(0);
                }else{
                    conn = newConnection();
                }
            }else{
                //继续获得连接，直到从新获得连接
                wait(this.dBbean.getConnTimeOut());
                conn = getConnection();
            }
            if(isValid(conn)){
                activeConnection.add(conn);
                contActive++;
            }
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return conn;
    }

    public Connection getCurrentConnection() {
        //默认线程里面取
        Connection conn =  threadLocal.get();
        if(!isValid(conn)){
            conn = getConnection();
        }
        return conn;
    }

    //释放连接
    public synchronized void releaseConn(Connection conn) throws SQLException {
        if(isValid(conn) && !(freeConnection.size() > dBbean.getMaxActiveConnections())){
            freeConnection.add(conn);
            activeConnection.remove(conn);
            contActive--;
            threadLocal.remove();
            //唤醒所有等待的线程，去抢连接
            notifyAll();
        }
    }

    public synchronized void destroy() {
        try{
            for(Connection conn : freeConnection){
                if(isValid(conn)){
                    conn.close();
                }
            }
            for(Connection conn : activeConnection){
                if(isValid(conn)){
                    conn.close();
                }
            }
            isActive = false;
            contActive = 0;
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean isActive() {
        return this.isActive;
    }

    public void checkPool() {
        if(dBbean.isCheckPool()){
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("空线池连接数："+freeConnection.size());
                    System.out.println("活动连接数：："+activeConnection.size());
                    System.out.println("总的连接数："+contActive);
                }
            },dBbean.getLazyCheck(),dBbean.getPeriodCheck());
        }
    }
}
