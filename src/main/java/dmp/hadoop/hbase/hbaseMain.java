package dmp.hadoop.hbase;

public class hbaseMain {
	
	private static String tableName = "test_temp_deletable";
	
	private static String[] columnFamily = {"cf1", "cf2", "cf3", "cf4"};
	
	public static void main(String[] args) {
		HBaseHandler hbaseHandler = new HBaseHandler();
		
		
		hbaseHandler.isTableExists(tableName);
		

		
//		hbaseHandler.createTable(tableName, columnFamily);
//		hbaseHandler.deleteTable(tableName);
		
		
//		hbaseHandler.addRow(tableName, "row0", "cf1", "c1", "100");
//		hbaseHandler.addRow(tableName, "row0", "cf2", "c2", "110");
//		hbaseHandler.addRow(tableName, "row0", "cf2", "c2.5", "115");
//		hbaseHandler.addRow(tableName, "row0", "cf3", "c3", "120");
//		hbaseHandler.addRow(tableName, "row0", "cf4", "c4", "130");
//		hbaseHandler.addRow(tableName, "row1", "cf1", "c11", "2354230");

//		hbaseHandler.deleteRow(tableName, "row0");
		
//		hbaseHandler.getTableWithRowkey(tableName, "9ecde68dcc0f3a386fd9746bfd88b495");
//		hbaseHandler.scanTable(tableName, 1);

		
	}
}
