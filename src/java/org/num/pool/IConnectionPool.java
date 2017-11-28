package org.num.pool;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 连接池接口
 * @author zxk
 */
public interface IConnectionPool {
    /**
     * 获取连接
     * @return
     */
    Connection getConnection();

    /**
     * 获取当前连接
     * @return
     */
    Connection getCurrentConnection();

    /**
     * 回收连接
     * @param conn
     * @throws SQLException
     */
    void releaseConn(Connection conn) throws SQLException;

    /**
     * 销毁清空
     */
    void destroy();

    /**
     * 连接池是否为活动状态
     * @return
     */
    boolean isActive();

    /**
     * 定时器，检查连接池
     */
    void checkPool();
}
