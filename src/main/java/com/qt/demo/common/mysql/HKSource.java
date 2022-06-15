package com.qt.demo.common.mysql;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author MuYang @Perfma
 * @Date 2022/03/21 22:48
 * @version: V1.0
 */
@Slf4j
public class HKSource {

    private static List<HikariDataSource> conList = new ArrayList<>();

    public static Connection getCon(){
        if(conList.size()!=0){
            try {
                return conList.get(0).getConnection();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return initPool("277","5");
    }
    public static Connection initPool(String poolName, String maxPool) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setPoolName(poolName);
        int poolSize = Integer.parseInt(maxPool);
        dataSource.setConnectionInitSql("SELECT 1 from dual");
        dataSource.setMaximumPoolSize(poolSize);
        // dataSource.setMaxLifetime(Long.parseLong(getTimeout()));
        dataSource.setMaxLifetime(5000);
        dataSource.setAutoCommit(true);
        dataSource.setIdleTimeout(5000);
        dataSource.setConnectionTimeout(30000);
//        dataSource.setConnectionTestQuery("SELECT 1 from dual");
//        dataSource.setTransactionIsolation("-1");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://10.10.200.21:3306/perfma_xocean?tinyInt1isBit=false&connectTimeout=5000&useSSL=false");
        dataSource.setUsername("root");
        dataSource.setPassword("Perfma888.");


        conList.add(dataSource);
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException throwables) {
//            System.out.println(throwables.getMessage().contains("Communications link failure"));
            throwables.printStackTrace();
        }
        return connection;

    }

}
