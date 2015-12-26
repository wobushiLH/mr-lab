package dmp.hadoop.hive.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class HiveJdbcClient {

	private static final String HIVE_DRIVER = "org.apache.hive.jdbc.HiveDriver";
	private static final String HIVE_URL = "jdbc:hive2://10.10.10.2:10008/default";
	private Connection con;
	private Statement stmt;
	
	
	
	/**
	 * Print result of performing given hql command in following format:
	 * 
	 * Column0		Column1		Column2		...
	 * Value01		Value11		Value21		...
	 * Value02		Value12		Value22		...
	 * Value03		Value13		Value23		...
	 * 
	 * 
	 * @param queryHqlCommand
	 */
	public void printSqlCommand(String queryHqlCommand) {

		try {
			ResultSet resultSet = sendSqlCommand(queryHqlCommand);
			ResultSetMetaData metaData = resultSet.getMetaData();
			int columns = metaData.getColumnCount();

			for (int i = 1; i <= columns; i++) {
				System.out.print(metaData.getColumnName(i));
				System.out.print("\t\t");
			}
			System.out.println();

			while (resultSet.next()) {
				for (int i = 1; i <= columns; i++) {
					System.out.print(resultSet.getString(i));
					System.out.print("\t\t");
				}
				System.out.println();
			}
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
	}
	
	
	
	/**
	 * Execute Query command and return result in a ResultSet Object
	 * 
	 * @param queryHqlCommand
	 * @return
	 */
	private ResultSet sendSqlCommand(String queryHqlCommand) {
		ResultSet res = null;
		try {
			res = stmt.executeQuery(queryHqlCommand);

		} catch (SQLException e) {
			System.err.println("connection error while sending SQL command: " + e.getMessage());
		}
		return res;

	}


	
	/**
	 *  Open JDBC connection
	 */
	public void openConnection() {
		try {
			Class.forName(HIVE_DRIVER);
			con = DriverManager.getConnection(HIVE_URL, "", "");
			stmt = con.createStatement();
		} catch (ClassNotFoundException e) {
			System.err.println("driver not found");
		} catch (SQLException e) {
			System.err.println("connection error while opening connection");
		}

	}

	/**
	 *  Close JDBC connection
	 */
	public void closeConnection() {

		try {
			con.close();
			stmt.close();
		} catch (SQLException e) {
			System.err.println("connection error while closing connection:\t" + e.getMessage());
		}
	}
}
