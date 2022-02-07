package controllers;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnection {
    public Connection databaseLink;

    public Connection getConnection(){
        String databaseName ="zoo";
        String databaseUser ="root";
        String databasePassword ="";
        String url = "jdbc:mysql://localhost:3306/" + databaseName;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url,databaseUser, databasePassword);
        }catch (Exception e){
            e.printStackTrace();
        }
        return databaseLink;
    }
}
