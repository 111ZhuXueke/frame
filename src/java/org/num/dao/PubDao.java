package org.num.dao;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 通用dao层
 * @author zxk
 */
public final class PubDao {
    
    public static String find(String[] clumns, String tableName, String condition, Object[] parms, int size, int rows, String poolName){
        return Find.find(clumns, tableName, condition, parms, size, rows,poolName);
    }

    public static int add(String[] clumns, String tableName, String generator, Object[] parms, String poolName){
        return ExcuteUpdate.add(clumns, tableName, generator, parms,poolName);
    }

    public static boolean update(String[] clumns, String tableName, String condition, Object[] parms, String poolName){
        return ExcuteUpdate.update(clumns, tableName, condition, parms,poolName);
    }

    public static boolean delete(String tableName, String condition, Object[] parms, String poolName){
        return ExcuteUpdate.delete(tableName, condition, parms,poolName);
    }

    public static void main(String[] arg){
        //查询示例
        String json = find(new String[]{"mid","minutes","count","wtid"},"Minutes","wtid = ?",new Object[]{6},0,5,"numPool");
        JSONArray jsonlist = JSONArray.parseArray(json);
        for (int i = 0; i < jsonlist.size(); i++){
            JSONObject obj = jsonlist.getJSONObject(i);
            System.out.println(obj.get("mid"));
        }
        //增加示例
        int ret = add(new String[]{"empname"},"emp","empid",new Object[]{"技术部"},"jurisdictionPool");
        System.out.println("insert: " + ret);
        //修改示例
        boolean state = update(new String[]{"empname"},"emp","empid = ?",new Object[]{"开发部",16},"jurisdictionPool");
        System.out.println("update: " + state);
        //删除示例
        state = delete("emp","empid = ?",new Object[]{16},"jurisdictionPool");
        System.out.println("delete: " + state);
    }
}