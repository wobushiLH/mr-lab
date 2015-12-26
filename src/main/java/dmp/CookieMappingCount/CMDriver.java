package dmp.CookieMappingCount;

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
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.task.JobContextImpl;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.security.UserGroupInformation;




public class CMDriver {
	
	
	// Job name
	final static String jarName = "mr-lab";
	
	// output data path
	final static String outputPath = "/home/lihe/hbase/output";
	
	// MapReduce job name
	final static String mrJobName = "CountMiaoZhen";
	
	// HBase table name for scanning
	final static String tableName = "dxp:cm";

	

	
	public static void run() throws Exception{
		Configuration conf = HBaseUtil.getConf();
		conf.setLong(HConstants.HBASE_CLIENT_SCANNER_TIMEOUT_PERIOD, 3600000); 
		conf.set("hbase.client.scanner.timeout.period", "6000000");
		conf.set("hbase.rpc.timeout", "6000000");
		conf.set("mapreduce.app-submission.cross-platform","true");
		FileSystem fs = FileSystem.get(conf);
		
		// Job Name
		Job job = new Job(conf, mrJobName);
		
		// Read jar file
		job.setJar("target/" + jarName + "-0.0.1-SNAPSHOT.jar");

	    
	    UserGroupInformation hadoop = UserGroupInformation.getBestUGI(null, "hdtreport");
	    Field ugi = JobContextImpl.class.getDeclaredField("ugi");
	    ugi.setAccessible(true);
	    ugi.set(job,hadoop);
	    
	    
	    // Output Path
	    String output = outputPath;
	    
	    if(fs.exists(new Path(output))){
	    	fs.delete(new Path(output));
	    }
	    FileOutputFormat.setOutputPath(job, new Path(output));
		
		Scan scan = new Scan();
		scan.setBatch(1000);
		TableMapReduceUtil.initTableMapperJob(tableName, scan, CMMapper.class, Text.class, Text.class, job);
		job.setReducerClass(CMReducer.class);
		job.setNumReduceTasks(1);
		job.setOutputFormatClass(TextOutputFormat.class);


	    job.setOutputKeyClass(Text.class);
	    job.setOutputValueClass(Text.class);
	    
		job.waitForCompletion(true);
	}
	
	public static void main(String[] args) throws Exception{
		run();
	}
}






















