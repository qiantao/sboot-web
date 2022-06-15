package com.qt.demo.common.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName:
 * @Description:
 * @author: QianTao
 * @date: 2019/07/18 10:03
 * @version: V1.0
 */
public class MyBatitsDao extends MsqlDBConnection {

    public static List<String> queryColumnInfoByTableName(List<String> tableNames,String dbSchema){
        List<String> resultList = new ArrayList<>();
        PreparedStatement pstmt =null;
        ResultSet rs =null;
        try {
            String str = "'";
            for (String tableName : tableNames) {
                str += tableName+"','";
            }
            str = str.substring(0,str.length()-2);
            con = getConnection();
            String sql = "SELECT * FROM (select A.TABLE_SCHEMA,B.TABLE_COMMENT ,A.TABLE_NAME,A.COLUMN_NAME,A.IS_NULLABLE,A.COLUMN_DEFAULT,A.DATA_TYPE,A.CHARACTER_MAXIMUM_LENGTH,A.NUMERIC_PRECISION,A.NUMERIC_SCALE,\n" +
                    "A.DATETIME_PRECISION,A.COLUMN_COMMENT,C.CONSTRAINT_NAME,C.COLUMN_NAME KEYCOLUMN \n " +
                    "from information_schema.`COLUMNS` A left join \n " +
                    "information_schema.`TABLES` B ON A.TABLE_NAME = B.TABLE_NAME\n " +
                    "left join information_schema.KEY_COLUMN_USAGE C \n" +
                    "ON A.TABLE_NAME = C.TABLE_NAME and A.COLUMN_NAME = C.COLUMN_NAME AND C.CONSTRAINT_NAME='PRIMARY' \n " +
                    "WHERE A.TABLE_NAME in ("+str+")";
            if(!"".equals(dbSchema)){
                sql += " and A.TABLE_SCHEMA = '"+dbSchema+"'";
            }
            sql +=") C order by C.TABLE_NAME " ;
            pstmt = con.prepareStatement(sql);
            int index = 1;

            rs = pstmt.executeQuery();

            while (rs.next()) {
                String tableNameColumnName = rs.getString("COLUMN_NAME") + "." + rs.getString("TABLE_NAME");
                resultList.add(tableNameColumnName);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try {
                close(con,pstmt,rs);
            }catch (SQLException e){

            }
        }
        return resultList;


    }

    public static List<String> queryColumnInfoByTableName(Connection c,List<String> tableNames, String dbSchema){
        List<String> resultList = new ArrayList<>();
        PreparedStatement pstmt =null;
        ResultSet rs =null;
        try {
            String str = "'";
            for (String tableName : tableNames) {
                str += tableName+"','";
            }
            str = str.substring(0,str.length()-2);
//            c = getConnection();
            String sql = "SELECT * FROM (select A.TABLE_SCHEMA,B.TABLE_COMMENT ,A.TABLE_NAME,A.COLUMN_NAME,A.IS_NULLABLE,A.COLUMN_DEFAULT,A.DATA_TYPE,A.CHARACTER_MAXIMUM_LENGTH,A.NUMERIC_PRECISION,A.NUMERIC_SCALE,\n" +
                    "A.DATETIME_PRECISION,A.COLUMN_COMMENT,C.CONSTRAINT_NAME,C.COLUMN_NAME KEYCOLUMN \n " +
                    "from information_schema.`COLUMNS` A left join \n " +
                    "information_schema.`TABLES` B ON A.TABLE_NAME = B.TABLE_NAME\n " +
                    "left join information_schema.KEY_COLUMN_USAGE C \n" +
                    "ON A.TABLE_NAME = C.TABLE_NAME and A.COLUMN_NAME = C.COLUMN_NAME AND C.CONSTRAINT_NAME='PRIMARY' \n " +
                    "WHERE A.TABLE_NAME in ("+str+")";
            if(!"".equals(dbSchema)){
                sql += " and A.TABLE_SCHEMA = '"+dbSchema+"'";
            }
            sql +=") C order by C.TABLE_NAME " ;
            pstmt = c.prepareStatement(sql);
            int index = 1;

            rs = pstmt.executeQuery();

            while (rs.next()) {
                String tableNameColumnName = rs.getString("COLUMN_NAME") + "." + rs.getString("TABLE_NAME");
                resultList.add(tableNameColumnName);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try {
                close(null,pstmt,rs);
            }catch (SQLException e){

            }
        }
        return resultList;


    }
}
