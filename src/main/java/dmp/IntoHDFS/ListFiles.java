package dmp.IntoHDFS;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class ListFiles {

	private String dst;
	private Configuration conf;
	
	public ListFiles(String dst, Configuration conf){
		this.dst = dst;
		this.conf = conf;
		
	}
	public void list(){
        Path dstPath = new Path(dst) ;  
        try{  
            FileSystem hdfs = dstPath.getFileSystem(conf) ;  
            FileStatus files[] = hdfs.listStatus(new Path(dst));
            System.out.println("hadoop fs -ls \t" + conf.get("fs.default.name") + dst);  
            for (FileStatus file : files) {  
                System.out.println(file.getPath());  
            }  
        }catch(IOException ie){  
            ie.printStackTrace() ;  
        }  
	}
}
