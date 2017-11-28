package org.num.pool;

import java.sql.Connection;

public class BaseDao {
    //private static IConnectionPool pool = ConnectionManager.getInstance().getPool("jurisdictionPool");
    public static Connection getConnection(String poolName){
        IConnectionPool pool = ConnectionManager.getInstance().getPool(poolName);
        Connection conn = null;
        if(pool != null){
            //conn = ConnectionManager.getInstance().getConnection("jurisdictionPool");
            conn = pool.getConnection();
        }
        return conn;
    }

    public static Connection getCurrentConnection(String poolName){
        IConnectionPool pool = ConnectionManager.getInstance().getPool(poolName);
        Connection conn = null;
        if(pool != null && pool.isActive()){
            conn = pool.getCurrentConnection();
        }
        return conn;
    }

    public static void closeConnection(String poolName, Connection conn){
        try{
            ConnectionManager.getInstance().close("jurisdictionPool",conn);
            //pool.releaseConn(conn);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
