package dmp.hadoop.hive.jdbc;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Main {

	public static final String SHOW_TABLES = "show tables";

	public static void main(String[] args) {
		// TODO Auto-generated method stub

//		String hqlCommand = "select * from dsp_bid_all_partitioned2 where dt='2015-12-01' limit 5";
		String hqlCommand = "select get_json_object(dsp_cm_all_partitioned.json,\"$.source\"), get_json_object(dsp_cm_all_partitioned.json,\"$.hdtUID\"), get_json_object(dsp_cm_all_partitioned.json,\"$.ptUID\"), parse_url(get_json_object(dsp_bid_all_partitioned2.json,\"$.page\"),\"HOST\"), get_json_object(dsp_bid_all_partitioned2.json,\"$.nurl\") from dsp_cm_all_partitioned join dsp_bid_all_partitioned2 on get_json_object(dsp_cm_all_partitioned.json,\"$.hdtUID\") = get_json_object(dsp_bid_all_partitioned2.json,\"$.hdtcid\") where dsp_bid_all_partitioned2.dt = '2015-12-11' and dsp_cm_all_partitioned.dt = '2015-11-13' and get_json_object(dsp_cm_all_partitioned.json,\"$.source\")='miaozhen' limit 30";

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
