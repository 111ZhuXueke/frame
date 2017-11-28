package org.num.dao;

import org.num.pool.BaseDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 更新类
 * @author zxk
 */
public class ExcuteUpdate {

    public static int add(String[] clumns, String tableName, String generator, Object[] parms, String poolName){
        return insertBackValue(getInsertSql(clumns, tableName), generator, tableName, parms,poolName);
    }

    public static boolean update(String[] clumns, String tableName, String condition, Object[] parms, String poolName){
        return getUpdateOrDeleteValue(getUdpdateSql(clumns,tableName,condition),parms,poolName);
    }

    public static boolean delete(String tableName, String condition, Object[] parms, String poolName){
        return getUpdateOrDeleteValue(getDeleteSql(tableName,condition),parms,poolName);
    }

    /**
     * 拼接update语句
     * @param tableName
     * @param condition
     * @return
     */
    private static String getDeleteSql(String tableName, String condition){
        StringBuffer sql = new StringBuffer("delete from " + tableName);
        sql.append(" where ");
        sql.append(condition);
        return sql.toString();
    }

    /**
     * update/delete语句返回值
     * @param sql
     * @param parms
     * @return
     */
    private static boolean getUpdateOrDeleteValue(String sql, Object[] parms, String poolName){
        boolean state = true;
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = BaseDao.getCurrentConnection(poolName);
            ps = conn.prepareStatement(sql);
            int j = 0;
            for(Object item : parms){
                ps.setObject(j+1, parms[j]);
                j++;
            }
            state = ps.execute();
            return !state;
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            BaseDao.closeConnection(poolName,conn);
        }
        return false;
    }
    /**
     * 拼接update语句
     * @param clumns
     * @param tableName
     * @param condition
     * @return
     */
    private static String getUdpdateSql(String[] clumns, String tableName, String condition) {
        StringBuffer sql = new StringBuffer("update " + tableName);
        if (clumns != null) {
            sql.append(" set ");
            int j = 0;
            int length = clumns.length;
            for (String item : clumns) {
                sql.append(clumns[j] + "=?");
                if (j != length - 1) {
                    sql.append(",");
                }
            }
            sql.append(" where ");
            sql.append(condition);
        }
        return sql.toString();
    }
    /**
     * insert语句返回值
     * @return
     */
    private static int insertBackValue(String sql, String generator, String tableName, Object[] parms, String poolName){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int result = 0;
        try{
            conn = BaseDao.getCurrentConnection(poolName);
            ps = conn.prepareStatement(sql);
            int j = 0;
            for(Object item : parms){
                ps.setObject(j+1, parms[j]);
                j++;
            }
            if(!ps.execute()){
                sql = "select max("+generator+") from " + tableName;
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                rs.first();
                result = rs.getInt(1);
            }
            return  result;
        }catch (SQLException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            BaseDao.closeConnection(poolName,conn);
        }
        return 0;
    }
    /**
     * 拼接insert语句
     * @param clumns  添加的列
     * @param tableName  表
     * @return
     */
    private static String getInsertSql(String[] clumns, String tableName) {
        StringBuffer sql = new StringBuffer("insert " + tableName);
        if (clumns != null) {
            sql.append("(");
            int i = 0;
            int length = clumns.length;
            for (String item : clumns) {
                sql.append(clumns[i]);
                if (i != length - 1) {
                    sql.append(",");
                }
                i++;
            }
            i = 0;
            sql.append(") values(");
            for(String item : clumns){
                sql.append("?");
                if(i != length-1){
                    sql.append(",");
                }
            }
            sql.append(")");
            return  sql.toString();
        }
        return null;
    }
}
