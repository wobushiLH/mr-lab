package dmp.IntoHDFS;

import java.io.File;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class Copy2HDFS {

	
	private File inputFile;
	private String src;
	private String dst;
	private Configuration conf;
	
	
	public Copy2HDFS(File inputFile, String dst, Configuration conf){
		this.src = inputFile.getAbsolutePath();
		this.dst = dst;
		this.conf = conf;
	}
	
	public Copy2HDFS(String src, String dst, Configuration conf){
		this.src = src;
		this.dst = dst;
		this.conf = conf;
	}

	public boolean upload(){
		Path srcPath = new Path(src);
		Path dstPath = new Path(dst) ;  
        try{  
            FileSystem hdfs = dstPath.getFileSystem(conf) ;  
//          FileSystem hdfs = FileSystem.get( URI.create(dst), conf) ;  
            hdfs.copyFromLocalFile(false, srcPath, dstPath) ; 
        }catch(IOException ie){  
            ie.printStackTrace() ;  
            return false;  
        }  
        return true ;  
	}

	
}
