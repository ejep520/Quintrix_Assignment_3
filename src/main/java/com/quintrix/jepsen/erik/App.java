package com.quintrix.jepsen.erik;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import com.mysql.cj.jdbc.JdbcStatement;

/**
 * Hello world!
 *
 */
public class App {
  public static void main(String[] args) throws SQLException {
    // int recordsModified;
    DataSource ds = null;
    AcmeCorpDB db = new AcmeCorpDB();
    ds = DataSourceFactory.getMySQLDataSource();
    if (testDataSource(ds)) {
      Connection con = null;
      JdbcStatement stmt = null;
      ResultSet rs = null;
      try {
        con = ds.getConnection();
        stmt = (JdbcStatement) con.createStatement();
        rs = stmt.executeQuery("SHOW DATABASES LIKE \"quintrix\";");
        rs.next();
        if (!rs.getString(1).equals("quintrix"))
          System.err.println("Could not find the database.");
        else
          stmt.execute("USE quintrix;");
        db.CreateAddressesTable(stmt);
        db.CreateDeptTable(stmt);
        db.CreateEmployeeTable(stmt);
        db.CreateEmployee(stmt);
        db.ShowEmployee(stmt);
      } catch (SQLException e) {
        e.printStackTrace();
      } finally {
        try {
          if (rs != null)
            rs.close();
          if (stmt != null)
            stmt.close();
          if (con != null)
            con.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      // TODO Homework goes here.
    } else {
      System.out.println(
          "There was an error establishing the presence of or connection to the database.");
      System.out.println("ABEND.");
    }
  }

  private static boolean testDataSource(DataSource ds) {
    if (ds == null) {
      System.err.println("DataSource passed in for testing is null.");
      return false;
    }
    boolean result = false;
    Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      con = ds.getConnection();
      stmt = con.createStatement();
      rs = stmt.executeQuery("SHOW DATABASES");
      while (rs.next());
      result = true;
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close();
        if (stmt != null)
          stmt.close();
        if (con != null)
          con.close();
      } catch (SQLException e) {
        e.printStackTrace();
        result = false;
      }
    }
    return result;
  }
}
