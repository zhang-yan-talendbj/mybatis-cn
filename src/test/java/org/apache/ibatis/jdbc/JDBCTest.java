package org.apache.ibatis.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

//-verbose:gc -Xms20m -Xmx20m -Xmn10m -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+UseSerialGC
public class JDBCTest {
    public static void main(String[] args) throws SQLException, InterruptedException {
            read();
    }

    private static void read() throws SQLException, InterruptedException {


        long beforeTimeOffset = -1L; //创建Connection对象前时间
        long afterTimeOffset = -1L; //创建Connection对象后时间
        long executeTimeOffset = -1L; //创建Connection对象后时间
        beforeTimeOffset = new Date().getTime();
        Connection connection = getConnection();

        afterTimeOffset=new Date().getTime();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from user");

        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        executeTimeOffset=new Date().getTime();
        while (resultSet.next()) {
//            System.out.println(resultSet.getString("name"));
//            Thread.sleep(10);
        }

        System.out.println("after:\t\t" + afterTimeOffset);
        System.out.println("Create Costs:\t\t" + (afterTimeOffset - beforeTimeOffset) + " ms");
        System.out.println("Exec Costs:\t\t" + (executeTimeOffset - afterTimeOffset) + " ms");


        resultSet.close();
        preparedStatement.close();
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "password");
    }
}
