package org.num.pool;

import java.util.ArrayList;
import java.util.List;

/**
 * 初始化加载jdbc配置文件
 * @author zxk
 */
public class DBInitInfo {
    public static List<DBbean> beans = null;
    static {
        beans = new ArrayList<DBbean>();
        DBbean dbMysql = new DBbean();
        dbMysql.setdBName("mysql");
        dbMysql.setDriverName("com.mysql.jdbc.Driver");
        dbMysql.setUrl("jdbc:mysql://127.0.0.1:3306/keycount");
        dbMysql.setUserName("root");
        dbMysql.setPassword("1234");
        dbMysql.setMinConnections(5);
        dbMysql.setMaxConnections(100);
        dbMysql.setPoolName("numPool");
        beans.add(dbMysql);

        DBbean dbMysql2 = new DBbean();
        dbMysql2.setdBName("mysql");
        dbMysql2.setDriverName("com.mysql.jdbc.Driver");
        dbMysql2.setUrl("jdbc:mysql://127.0.0.1:3306/jurisdiction");
        dbMysql2.setUserName("root");
        dbMysql2.setPassword("1234");
        dbMysql2.setMinConnections(5);
        dbMysql2.setMaxConnections(100);
        dbMysql2.setPoolName("jurisdictionPool");
        beans.add(dbMysql2);
    }
}
