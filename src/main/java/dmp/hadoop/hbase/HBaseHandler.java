package dmp.hadoop.hbase;

import org.apache.hadoop.conf.Configuration;


public class HBaseHandler {

	
	
	private static Configuration conf;
	
	
	
	static{
		conf = HBaseUtil.getConf();
	}
	
	
	
	
	public static void write2HBase(String tableName, String rowkey, String columnFamilyName, String qualifierName, String value){
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
