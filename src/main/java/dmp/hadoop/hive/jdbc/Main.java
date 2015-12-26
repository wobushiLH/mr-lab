package dmp.hadoop.hive.jdbc;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Main {

	public static final String SHOW_TABLES = "show tables";

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String hqlCommand = "select * from dsp_bid_all_partitioned2 where dt='2015-12-01' limit 5";

		HiveJdbcClient hiveClient = new HiveJdbcClient();

		/**
		 *  Open JDBC connection
		 */
		hiveClient.openConnection();

		
		
		/**
		 *  Run a given HQL command
		 */
		hiveClient.printSqlCommand(hqlCommand);

		
		
		/**
		 *  Close JDBC connection
		 */
		hiveClient.closeConnection();

	}

}
