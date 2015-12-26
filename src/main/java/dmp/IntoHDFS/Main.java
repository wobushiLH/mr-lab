package dmp.IntoHDFS;

import java.io.File;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
//		File inputFile = new File("E:\\localFolder\\hdfsTest.txt");
//		System.out.println(inputFile.getPath());
//		System.out.println(inputFile.getAbsolutePath());
		
		
		
		Configuration conf = new Configuration();
		conf.addResource(new Path("/resources/core-site.xml"));
		
		
		//	List files in given hdfs path
		ListFiles list = new ListFiles( "/home", conf);
		list.list();
		
		//	Copy given file to HDFS
//		Copy2HDFS sender = new Copy2HDFS(inputFile, "/home/lihe/hdfs/", conf);
//		sender.upload();

	}

}
