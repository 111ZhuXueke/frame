package org.num.pool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * 连接管理类
 * @author zxk
 */
public class ConnectionManager {
    public HashMap<String,IConnectionPool> pools = new HashMap<String, IConnectionPool>();
    public HashMap<String,String> dbeans = new HashMap<String, String>();
    private ConnectionManager(){
        init();
    }

    //单例
    public static ConnectionManager getInstance(){
        return Singtonle.instance;
    }

    private static class Singtonle{
        private static ConnectionManager instance = new ConnectionManager();
    }

    /**
     * 初始化所有连接池
     */
    public void init(){
        for(int i = 0; i < DBInitInfo.beans.size(); i++){
            DBbean dBbean = DBInitInfo.beans.get(i);
            ConnectionPool pool = new ConnectionPool(dBbean);
            if(pool != null){
                dbeans.put(dBbean.getPoolName(),dBbean.getdBName());
                pools.put(dBbean.getPoolName(),pool);
            }
        }
    }

    /**
     * 根据连接池名称获取连接
     * @param poolName
     * @return
     */
    public Connection getConnection(String poolName){
        Connection conn = null;
        if(pools.size() >0 && pools.containsKey(poolName)){
            conn = pools.get(poolName).getConnection();
        }
        return conn;
    }

    /**
     * 关闭连接
     * @param poolName
     * @param conn
     */
    public void close(String poolName, Connection conn){
        IConnectionPool pool = getPool(poolName);
        try{
            if(pool != null){
                pool.releaseConn(conn);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * 获得连接池
     * @param poolName
     * @return
     */
    public IConnectionPool getPool(String poolName){
        IConnectionPool pool = null;
        if(pools.size() > 0){
            pool = pools.get(poolName);
        }
        return pool;
    }

    /**
     * 销毁连接池
     * @param poolName
     */
    public void destory(String poolName){
        IConnectionPool pool = getPool(poolName);
        if(pool != null){
            pool.destroy();
        }
    }
}
