package dmp.hadoop.hbase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

public class HBaseUtil {
	private static final String QUORUM = "10.10.10.127,10.10.10.128,10.10.10.129";
	private static final String CLIENTPORT = "2181";

	private static Configuration conf = null;

	public static Configuration getConf() {
		if (conf == null) {
			// System.setProperty("hadoop.home.dir", "c:/hadoop");
			conf = HBaseConfiguration.create();
			conf.set("hbase.zookeeper.quorum", QUORUM);
			conf.set("hbase.zookeeper.property.clientPort", CLIENTPORT);
			// conf.set("mapreduce.app-submission.cross-platform","true");
			// conf.addResource("core-site.xml");
			// conf.addResource("hdfs-site.xml");
			// conf.addResource("hbase-site.xml");
			// conf.addResource("mapred-site.xml");
			// conf.addResource("yarn-site.xml");

		}
		return conf;
	}

	public static HTable getHTable(String targetTableName) throws Exception {
		HTable hTable = new HTable(getConf(), targetTableName);
		return hTable;
	}

	public static ResultScanner getScanAll(HTable htable) throws Exception {
		Scan scan = new Scan();
		ResultScanner rs = htable.getScanner(scan);
		return rs;
	}

	public static void addData(HTable htable, String rowKey, String familyName, String columnName, String value)
			throws Exception {
		Put put = new Put(Bytes.toBytes(rowKey));

		put.add(Bytes.toBytes(familyName), Bytes.toBytes(columnName), Bytes.toBytes(value));
		htable.put(put);
	}

	public static String hashUserid(String userid) {
		return DigestUtils.md5Hex(userid).substring(0, 4) + "_" + userid;
	}

	static final Pattern p = Pattern.compile("^[a-z0-9]{4}_([^_]+)");

	public static String unHashUserid(String hash) {
		Matcher m = p.matcher(hash);
		if (m.find()) {
			return m.group(1);
		} else {
			return null;
		}
	}
}
