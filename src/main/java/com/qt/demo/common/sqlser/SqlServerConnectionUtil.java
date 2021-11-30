package com.qt.demo.common.sqlser;

import java.sql.*;

public class SqlServerConnectionUtil {
    private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String URL = "jdbc:sqlserver://10.10.16.105:1433;databasename=Test;";
    private static final String DATABASE_NAME = "Test";
    private static final String USER_NAME = "sa";
    private static final String PASSWORD = "Perfma888.";


    public static void getConnection(){
        Connection conn =null;
        ResultSet rs = null;
        Statement stmt = null;
        try{
            Class.forName( DRIVER );
            conn = DriverManager.getConnection(URL,USER_NAME,PASSWORD);
            //新建一个查询
            stmt = conn.createStatement();
            //执行查询-->>返回一个结果集
            rs = stmt.executeQuery("select * from Student");

            while ( rs.next() ) {
                int k = rs.getInt("id");//这是查找数据库的id号
                String v = rs.getString("Name");//这是查找数据库的trade_name列有什么值
                System.out.println("结果是:ID"+k+"　　trade_name  :"+v);
            }

        }catch(ClassNotFoundException e){
            System.out.println("驱动问题"+e.getMessage());
        }catch(SQLException e){
            System.out.println("发生异常:"+e.getMessage());
        }finally {
            try {
                if(rs != null) {
                    rs.close();
                }
                if(stmt != null) {
                    stmt.close();
                }
                if(conn != null) {

                        conn.close();

                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
