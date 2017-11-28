package org.num.dao;

import org.num.pool.BaseDao;
import org.num.pool.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 查询类
 * @author zxk
 */
public class Find {
    public static String find(String[] clumns, String tableName, String condition, Object[] parms, int size, int rows,String poolName){
        return Find.getStringResult(getFindSql(clumns, tableName, condition, size, rows), parms, clumns, poolName);
    }

    /**
     * 拼接sql语句
     * @param clumns  所要查询的列
     * @param tableName  所查询的表列表
     * @return
     * @param condition  条件语句
     */
    private static String getFindSql(String[] clumns, String tableName, String condition, int size, int rows){
        StringBuffer sql = new StringBuffer("select ");
        if(clumns != null){
            for (int i = 0; i< clumns.length; i++){
                sql.append(clumns[i]);
                if(i != clumns.length -1){
                    sql.append(",");
                }else{
                    sql.append(" from ");
                }
            }
            sql.append(tableName);
            if(condition != null){
                sql.append(" where " + condition);
//                if(generator != null){
//                    sql.append(" and " + generator + "in");
//                    sql.append("(select " + generator + " from " + tableName);
//                }
            }
            if(ConnectionManager.getInstance().dbeans.get("numPool").equals("mysql")){
                if(rows > 0){
                    sql.append(" Limit ");
                    sql.append(size + "," + rows);
                }
            }
            return sql.toString();
        }
        return null;
    }

    /**
     * 获取结果集
     * @param sql
     * @return
     */
    private static String getStringResult(String sql, Object[] parms, String[] clumns,String poolName){
        StringBuffer result = new StringBuffer("[");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            conn = BaseDao.getCurrentConnection(poolName);
            ps = conn.prepareStatement(sql);
            int j = 0;
            for(Object item : parms){
                ps.setObject(j+1, parms[j]);
                j++;
            }
            rs = ps.executeQuery();
            rs.last();
            int row = rs.getRow();
            if(!rs.isFirst()){
                rs.first();
                result.append(getJsonObject(rs, clumns));
                int i = 0;
                while(rs.next()){
                    if(i==0){
                        result.append(",");
                    }
                    result.append(getJsonObject(rs, clumns));
                    i = i + 1;
                    if(i != row - 1){
                        result.append(",");
                    }
                }
            }
            result.append("]");
            rs.close();
            ps.close();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            BaseDao.closeConnection(poolName,conn);
        }
        return result.toString();
    }

    /**
     * 得到json对象
     * @return
     */
    private static String getJsonObject(ResultSet rs, String[] clumns) throws SQLException{
        StringBuffer objJson = new StringBuffer("{");
        for(int i = 0; i < clumns.length; i++){
            objJson.append("\""+rs.getMetaData().getColumnLabel(i+1)+"\":\""+rs.getObject(i+1)+"\"");
            if (i != clumns.length - 1) {
                objJson.append(",");
            }
        }
        objJson.append("}");
        return objJson.toString();
    }
}
