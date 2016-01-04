package dmp.hadoop.hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

public class HBaseHandler {

	private Configuration conf;
	private HBaseAdmin hAdmin;

	HBaseHandler() {
		conf = HBaseUtil.getConf();
		try {
			hAdmin = new HBaseAdmin(conf);
			// HTable table = new HTable(conf, "dxp:page_kw");
			// table.setAutoFlush(false, false);
			// table.setWriteBufferSize(1048576);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Error occured due to " + e.getMessage());
		}
	}

	/**
	 * Create a hbase table with a given name and column families
	 * 
	 * @param tableName
	 * @param columnFamilys
	 */
	public void createTable(String tableName, String[] columnFamilies) {
		// 新建一个数据库管理员
		try {
			HBaseAdmin hAdmin = new HBaseAdmin(conf);
			if (hAdmin.tableExists(tableName)) {
				System.out.println("表 " + tableName + " 已存在！");
				System.exit(0);
			} else {
				// 新建一个students表的描述
				HTableDescriptor tableDesc = new HTableDescriptor(tableName);
				// 在描述里添加列族
				for (String columnFamily : columnFamilies) {
					tableDesc.addFamily(new HColumnDescriptor(columnFamily));
				}
				// 根据配置好的描述建表
				hAdmin.createTable(tableDesc);
				System.out.println("创建表 " + tableName + " 成功!");
			}
		} catch (MasterNotRunningException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ZooKeeperConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	/**
	 * Delete a table
	 * @param tableName
	 */
	public void deleteTable(String tableName) {
		// 新建一个数据库管理员
		try {
			HBaseAdmin hAdmin = new HBaseAdmin(conf);
			if (hAdmin.tableExists(tableName)) {
				// 关闭一个表
				hAdmin.disableTable(tableName);
				hAdmin.deleteTable(tableName);
				System.out.println("删除表 " + tableName + " 成功！");
			} else {
				System.out.println("删除的表 " + tableName + " 不存在！");
				System.exit(0);
			}
		} catch (MasterNotRunningException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ZooKeeperConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Check table existance in hbase
	 * @param tableName
	 */
	public void isTableExists(String tableName) {
		try {
			System.out.println("Table " + tableName + " Existance: " + hAdmin.tableExists(tableName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Error occured due to " + e.getMessage());
		}
	}

	/**
	 * Print one row with a given rowkey
	 * @param tableName
	 * @param rowkey
	 */
	public void getTableWithRowkey(String tableName, String rowkey) {
		HTable table;
		try {
			table = new HTable(conf, tableName);
			Get get = new Get(Bytes.toBytes(rowkey));
			Result result = table.get(get);
			// 输出结果,raw方法返回所有keyvalue数组
			for (KeyValue rowKV : result.raw()) {
				System.out.print("rowkey: " + new String(rowKV.getRow()) + "\t");
				System.out.print("timestamp: " + rowKV.getTimestamp() + "\t");
				System.out.print("cf: " + new String(rowKV.getFamily()) + "\t");
				System.out.print("c: " + new String(rowKV.getQualifier()) + "\t");
				System.out.println("v: " + new String(rowKV.getValue()));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Error occured due to " + e.getMessage());
		}
	}

	/**
	 * Print all rows
	 * @param tableName
	 * @param lineLimit
	 */
	public void scanTable(String tableName, int lineLimit) {
		HTable table;
		try {
			table = new HTable(conf, tableName);
			Scan scan = new Scan();
			// scan.setMaxResultsPerColumnFamily(lineLimit);
			// scan.setMaxResultSize(lineLimit);
			// scan.setBatch(lineLimit);
			ResultScanner results = table.getScanner(scan);
			for (Result result : results) {
				for (KeyValue rowKV : result.raw()) {
					System.out.print("rowkey: " + new String(rowKV.getRow()) + "\t");
					System.out.print("timestamp: " + rowKV.getTimestamp() + "\t");
					System.out.print("cf: " + new String(rowKV.getFamily()) + "\t");
					System.out.print("c: " + new String(rowKV.getQualifier()) + "\t");
					System.out.println("v: " + new String(rowKV.getValue()));
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Error occured due to " + e.getMessage());
		}
	}

	/**
	 * Add row data to table
	 * @param tableName
	 * @param row
	 * @param columnFamily
	 * @param column
	 * @param value
	 */
    public void addRow(String tableName, String row, String columnFamily, String column, String value){
		try {
			HTable table = new HTable(conf, tableName);
	        Put put = new Put(Bytes.toBytes(row));// 指定行
	        // 参数分别:列族、列、值
	        put.add(Bytes.toBytes(columnFamily), Bytes.toBytes(column), Bytes.toBytes(value));
	        table.put(put);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Error occured due to " + e.getMessage());
		}
    }
    
    public void deleteRow(String tableName, String row){
		try {
			HTable table = new HTable(conf, tableName);
	        Delete del = new Delete(Bytes.toBytes(row));
	        table.delete(del);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Error occured due to " + e.getMessage());
		}
    }

}
