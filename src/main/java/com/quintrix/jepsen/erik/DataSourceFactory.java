package com.quintrix.jepsen.erik;

import javax.sql.DataSource;

import com.mysql.cj.jdbc.MysqlDataSource;

public class DataSourceFactory {
	public static DataSource getMySQLDataSource() {
		MysqlDataSource mysqlDS;
		mysqlDS = null;
		try {
			mysqlDS = new MysqlDataSource();
			mysqlDS.setUrl("jdbc:mysql://localhost");
			mysqlDS.setUser("DBAdmin");
			mysqlDS.setPassword("admin");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mysqlDS;
	}
}
