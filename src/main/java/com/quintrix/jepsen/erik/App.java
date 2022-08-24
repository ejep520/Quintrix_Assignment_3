package com.quintrix.jepsen.erik;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws SQLException
    {
    	testDataSource();
    }
    private static void testDataSource() {
    	DataSource ds = null;
    	ds = DataSourceFactory.getMySQLDataSource();
    	Connection con = null;
    	Statement stmt = null;
    	ResultSet rs = null;
    	try {
    		con = ds.getConnection();
    		stmt = con.createStatement();
    		rs = stmt.executeQuery("SHOW DATABASES");
    		while (rs.next()) System.out.println(rs.getString(1));
    	} catch (SQLException e) {
    		e.printStackTrace();
    	} finally {
    		try {
    			if (rs != null) rs.close();
    			if (stmt != null) stmt.close();
    			if (con != null) con.close();
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    	}
    }
}
