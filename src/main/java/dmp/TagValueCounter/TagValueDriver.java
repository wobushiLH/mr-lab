package dmp.TagValueCounter;

import java.lang.reflect.Field;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.FamilyFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.mapreduce.task.JobContextImpl;
import org.apache.hadoop.security.UserGroupInformation;

import dmp.CookieMappingCount.HBaseUtil;


public class TagValueDriver {

	public static void run() throws Exception{
		//String p = "/home/zhou/mz/origin/dt=20150811/part-r-00000";
		String p = "/home/lihe/hbase/attribute/";
		Configuration conf = HBaseUtil.getConf();

		conf.setLong(HConstants.HBASE_CLIENT_SCANNER_TIMEOUT_PERIOD, 3600000); 
		conf.set("hbase.client.scanner.timeout.period", "6000000");
		conf.set("hbase.rpc.timeout", "6000000");
		conf.set("mapreduce.app-submission.cross-platform","true");
		FileSystem fs = FileSystem.get(conf);
		
		Job job = new Job(conf, "TagDriver");
		job.setJar("target/Attribute-0.0.1-SNAPSHOT.jar");
	   
	    
	    UserGroupInformation hadoop = UserGroupInformation.getBestUGI(null, "hdtreport");
	    Field ugi = JobContextImpl.class.getDeclaredField("ugi");
	    ugi.setAccessible(true);
	    ugi.set(job,hadoop);
	    
	    String output = "/home/zhou/attsresult";
	    if(fs.exists(new Path(output))){
	    	fs.delete(new Path(output));
	    }
	    FileOutputFormat.setOutputPath(job, new Path(output));
		
		
		job.addCacheFile(new Path(p).toUri());
		Scan scan = new Scan();
		Filter filter = new FamilyFilter(CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes("tag")));
		scan.setFilter(filter);
		scan.setBatch(1000);
		TableMapReduceUtil.initTableMapperJob("dxp:ck_tag", scan, TagValueMapper.class, Text.class, Text.class, job);
		job.setReducerClass(TagValueReducer.class);
		job.setNumReduceTasks(15);
	    job.setOutputFormatClass(TextOutputFormat.class);

	    job.setOutputKeyClass(Text.class);
	    job.setOutputValueClass(Text.class);

		job.waitForCompletion(true);
	}
	
	public static void main(String[] args) throws Exception{
		run();
	}
}
