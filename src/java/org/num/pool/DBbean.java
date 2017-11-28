package org.num.pool;

/**
 * 连接池属性
 * @author zxk
 */
public class DBbean {
    //数据库
    private String dBName;
    //数据库驱动
    private String driverName;
    //路径
    private String url;
    //数据库连接用户名
    private String userName;
    //数据库连接密码
    private String password;
    //连接池名称
    private String poolName;
    //空闲池，最小连接数
    private int minConnections = 1;
    //空闲池，最大连接数
    private int maxConnections = 10;
    //默认连接数
    private int initConnections = 5;
    //重复获得连接的频率
    private long connTimeOut = 1000;
    //最大允许连接的连接数，与相应的数据库对应
    private int maxActiveConnections = 100;
    //连接超时时间，默认20分钟
    private long connectionTimeOut = 1000*60*20;
    //是否获得当前连接
    private boolean isCurrentConnection = true;
    //是否定时检查连接池
    private boolean isCheckPool = true;
    //延迟多少时间后开始检查
    private long lazyCheck = 1000*60*60;
    //检查频率
    private long periodCheck = 1000*60*60;

    public String getdBName() {
        return dBName;
    }

    public void setdBName(String dBName) {
        this.dBName = dBName;
    }

    public DBbean(String driverName, String url, String userName, String password, String poolName) {
        this.driverName = driverName;
        this.url = url;
        this.userName = userName;
        this.password = password;
        this.poolName = poolName;
    }

    public DBbean(){}

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPoolName() {
        return poolName;
    }

    public void setPoolName(String poolName) {
        this.poolName = poolName;
    }

    public int getMinConnections() {
        return minConnections;
    }

    public void setMinConnections(int minConnections) {
        this.minConnections = minConnections;
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }

    public int getInitConnections() {
        return initConnections;
    }

    public void setInitConnections(int initConnections) {
        this.initConnections = initConnections;
    }

    public long getConnTimeOut() {
        return connTimeOut;
    }

    public void setConnTimeOut(long connTimeOut) {
        this.connTimeOut = connTimeOut;
    }

    public int getMaxActiveConnections() {
        return maxActiveConnections;
    }

    public void setMaxActiveConnections(int maxActiveConnections) {
        this.maxActiveConnections = maxActiveConnections;
    }

    public long getConnectionTimeOut() {
        return connectionTimeOut;
    }

    public void setConnectionTimeOut(long connectionTimeOut) {
        this.connectionTimeOut = connectionTimeOut;
    }

    public boolean isCurrentConnection() {
        return isCurrentConnection;
    }

    public void setCurrentConnection(boolean currentConnection) {
        isCurrentConnection = currentConnection;
    }

    public boolean isCheckPool() {
        return isCheckPool;
    }

    public void setCheckPool(boolean checkPool) {
        isCheckPool = checkPool;
    }

    public long getLazyCheck() {
        return lazyCheck;
    }

    public void setLazyCheck(long lazyCheck) {
        this.lazyCheck = lazyCheck;
    }

    public long getPeriodCheck() {
        return periodCheck;
    }

    public void setPeriodCheck(long periodCheck) {
        this.periodCheck = periodCheck;
    }
}
