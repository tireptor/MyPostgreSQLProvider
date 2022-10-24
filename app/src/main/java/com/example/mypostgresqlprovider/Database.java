package com.example.mypostgresqlprovider;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private Connection connection;
    public static final String TAG = "Vincent";


    // For Local PostgreSQL
    private final String host = "151.80.145.240";

    private final String database = "poisson";
    private final int port = 5432;
    private final String user = "postgres";
    private final String pass = "P0stgres@123";
    private String url = "jdbc:postgresql://%s:%d/%s";
    private boolean status;

    public Database()
    {
        this.url = String.format(this.url, this.host, this.port, this.database);
        connect();
        //this.disconnect();
        Log.d(TAG,"connection status:" + status);
        System.out.println("connection status:" + status);
    }

    private void connect()
    {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try
                {
                    Class.forName("org.postgresql.Driver");
                    connection = DriverManager.getConnection(url, user, pass);
                    status = true;
                    System.out.println("connected:" + status);
                }
                catch (Exception e)
                {
                    status = false;
                    System.out.print(e.getMessage());
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try
        {
            thread.join();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            this.status = false;
        }
    }

    public ResultSet Select() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = null;
        String error;
        try {
            resultSet = statement.executeQuery("SELECT esp_nom FROM t_espece");
        }
        catch (Exception e){
           error=  e.toString();
            Log.d(TAG,"Erreur SELECT :" + error);
        }

        return resultSet;
    }

    public Connection getExtraConnection()
    {
        Connection c = null;
        try
        {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url, user, pass);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return c;
    }
}
