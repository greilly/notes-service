/**
 * Created by Greg on 11/18/2016.
 */

package com.greilly.note;
import com.greilly.note.controller.NoteController;
import com.greilly.note.service.NoteServiceImpl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import static spark.Spark.*;

public class Bootstrap {

    public static void main(String[] args) throws SQLException {
        Properties prop = readPropertiesFile();

        setIpAddress(prop.getProperty("ipaddress"));
        setPort(Integer.parseInt(prop.getProperty("port")));
        Connection conn = connectToSqlite();
        createTable(conn);
        new NoteController(new NoteServiceImpl(conn));
    }

    /**
     * Connects to the sqlite database called note.db, located at the top level.
     * @return a connection to the DB
     */
    private static Connection connectToSqlite() {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:note.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

    /**
     * Creates the DB table 'note', if it doesn't already exist.
     *
     * @param conn a connection to the DB
     * @throws SQLException
     */
    private static void createTable(Connection conn) throws SQLException {
        String sql =
                "CREATE TABLE IF NOT EXISTS note " +
                "(ID INTEGER PRIMARY KEY, " +
                "body TEXT)";
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
        stmt.close();
    }

    private static Properties readPropertiesFile() {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream("config.properties");
            prop.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return prop;
    }
}
