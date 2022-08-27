package com.quintrix.jepsen.erik;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import com.mysql.cj.jdbc.JdbcStatement;

class AcmeCorpDB {
  private final String ADDRESS_TABLE_CREATION =
      "CREATE TABLE IF NOT EXISTS ADDRESSES " + "(addrId INTEGER NOT NULL AUTO_INCREMENT, "
          + " addr1 TINYTEXT, " + " addr2 TINYTEXT, " + " city TINYTEXT, " + " state INTEGER, "
          + " zip VARCHAR(10), " + " CONSTRAINT PK_Address PRIMARY KEY (addrId));";
  private final String DEPARTMENT_TABLE_CREATION = "CREATE TABLE IF NOT EXISTS DEPARTMENTS "
      + "(deptId INTEGER NOT NULL AUTO_INCREMENT, " + " name TINYTEXT, " + " address INTEGER, "
      + " CONSTRAINT PK_Department PRIMARY KEY (deptId), "
      + " CONSTRAINT FK_DeptAddress FOREIGN KEY (address) REFERENCES ADDRESSES(addrId));";
  private final String EMPLOYEE_TABLE_CREATION =
      "CREATE TABLE IF NOT EXISTS EMPLOYEES" + "(employeeId INTEGER NOT NULL AUTO_INCREMENT, "
          + " firstName TINYTEXT, " + " lastName TINYTEXT, " + " homeAddress INTEGER, "
          + " mailingAddress INTEGER, " + " department INTEGER, " + " managerId INTEGER, "
          + " CONSTRAINT PK_Employee PRIMARY KEY (employeeId), "
          + " CONSTRAINT FK_EmpHomeAddr FOREIGN KEY (homeAddress) REFERENCES ADDRESSES(addrId), "
          + " CONSTRAINT FK_EmpMailAddr FOREIGN KEY (mailingAddress) REFERENCES ADDRESSES(addrId), "
          + " CONSTRAINT FK_EmpDept FOREIGN KEY (department) REFERENCES DEPARTMENTS(deptId));";

  public void CreateAddressesTable(JdbcStatement stmt) throws SQLException {
    int recordsAffected = stmt.executeUpdate(ADDRESS_TABLE_CREATION);
    if (recordsAffected > 1)
      System.err.printf("%d records were affected." + System.lineSeparator(), recordsAffected);
  }

  public void CreateDeptTable(JdbcStatement stmt) throws SQLException {
    int recordsAffected = stmt.executeUpdate(DEPARTMENT_TABLE_CREATION);
    if (recordsAffected > 1)
      System.err.printf("%d records were affected" + System.lineSeparator(), recordsAffected);
  }

  public void CreateEmployeeTable(JdbcStatement stmt) throws SQLException {
    int recordsAffected = stmt.executeUpdate(EMPLOYEE_TABLE_CREATION);
    if (recordsAffected > 1)
      System.err.printf("%d records were affected" + System.lineSeparator(), recordsAffected);
  }

  public void CreateEmployee(JdbcStatement stmt) throws SQLException {
    String firstName, lastName, addr1, addr2, city, zip, addressType;
    boolean validType = false;
    int addrRef;
    ResultSet rs;
    Address.State state;
    Scanner scanner = new Scanner(System.in);
    System.out.print("What is the first name of the new employee? ");
    firstName = scanner.nextLine();
    System.out.print("What is the last name of the new employee? ");
    lastName = scanner.nextLine();
    System.out.print("What sort of address did the employee provide? ");
    addressType = scanner.nextLine().toLowerCase();
    if (addressType.startsWith("hom") || addressType.startsWith("mai"))
      validType = true;
    while (!validType) {
      System.out.println(
          "That was an invalid address type. Please specify whether this is a \"home\" or \"mailing\" address.");
      System.out.print("What sort of address did the employee provide? ");
      addressType = scanner.nextLine().toLowerCase();
      if (addressType.startsWith("hom") || addressType.startsWith("mai"))
        validType = true;
    }
    System.out.println("What was the first line of the new employee's address?");
    addr1 = scanner.nextLine();
    System.out
        .println("What was the second line of the new employee's address? (Leave blank if none.)");
    addr2 = scanner.nextLine();
    if (addr2.isEmpty())
      addr2 = null;
    System.out.print("Which city is the employee from? ");
    city = scanner.nextLine();
    state = Address.State.MINNESOTA;
    zip = "10001";
    scanner.close();
    stmt.setAttribute("addr1", addr1);
    stmt.setAttribute("addr2", addr2);
    stmt.setAttribute("city", city);
    stmt.setAttribute("state", state.ordinal());
    stmt.setAttribute("zip", zip);
    stmt.executeUpdate("INSERT INTO ADDRESSES (addr1, addr2, city, state, zip) VALUES ("
        + "mysql_query_attribute_string(), " + "mysql_query_attribute_string(), "
        + "mysql_query_attribute_string(), " + "mysql_query_attribute_string(), "
        + "mysql_query_attribute_string());");
    stmt.clearAttributes();
    rs = stmt.executeQuery("SELECT AddrId from addresses ORDER BY DESC LIMIT 1;");
    if (!rs.next()) {
      System.err.println("Unable to find the last address! Aborting.");
      System.exit(1);
    }
    addrRef = rs.getInt(1);
    rs.close();
    stmt.setAttribute("firstName", firstName);
    stmt.setAttribute("lastName", lastName);
    if (addressType.startsWith("hom")) {
      stmt.setAttribute("homeAddress", addrRef);
      stmt.executeUpdate("INSERT INTO EMPLOYEES (firstName, lastName, homeAddress) VALUES ("
          + "mysql_query_attribute_string(), " + "mysql_query_attribute_string(), "
          + "mysql_query_attribute_string());");
    } else {
      stmt.setAttribute("mailingAddress", addrRef);
      stmt.executeUpdate("INSERT INTO EMPLOYEES (firstName, lastName, mailingAddress) VALUES ("
          + "mysql_query_attribute_string(), " + "mysql_query_attribute_string(), "
          + "mysql_query_attribute_string());");
    }
  }

  public void ShowEmployee(JdbcStatement stmt) throws SQLException {
    ResultSet rs = stmt.executeQuery(
        "SELECT * from employees as emp JOIN addresses as addr on emp.homeAddress = addr.addrId;");
    while (rs.next()) {
      System.out.printf("Name: %s %s" + System.lineSeparator(), rs.getString("emp.firstName"),
          rs.getString("emp.lastName"));
      System.out.printf("Address: %s" + System.lineSeparator(), rs.getString("addr.addr1"));
      System.out.println(rs.getString("addr.addr2"));
      System.out.printf("%s, %s %s", rs.getString("addr.city"),
          Address.State.values()[rs.getInt("addr.state")], rs.getString("addr.zip"));
    }
    rs.close();
  }
  /*
   * private Address.State ProcessState(String zipCode) { String prefix = zipCode.substring(0, 2);
   * if (prefix.equals("005")) return Address.State.NEW_YORK; if (prefix.equals("006")) return
   * Address.State.PUERTO_RICO; if (prefix.equals("007")) return Address.State.PUERTO_RICO; if
   * (prefix.equals("008")) return Address.State.US_VIRGIN_ISLANDS; if (prefix.equals("009")) return
   * Address.State.PUERTO_RICO; if (prefix.startsWith("01")) return Address.State.MASSACHUSETTS; if
   * (prefix.startsWith("02")) { if (prefix.equals("028") || prefix.equals("029")) return
   * Address.State.RHODE_ISLAND; return Address.State.MASSACHUSETTS; } if (prefix.startsWith("03"))
   */
}
