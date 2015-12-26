package dmp.TagValueCounter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.filecache.DistributedCache;

import dmp.CookieMappingCount.HBaseUtil;


public class TagValueMapper extends TableMapper<Text,Text>{

	private Set<String> set = new HashSet<String>();
	@Override
	protected void setup(
			Mapper<ImmutableBytesWritable, Result, Text, Text>.Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		Configuration conf = context.getConfiguration(); 
    	Path[] localCacheFiles = DistributedCache.getLocalCacheFiles(conf); 
    	if(localCacheFiles.length>0){
    		BufferedReader reader = new BufferedReader(new FileReader(localCacheFiles[0].toUri().getPath()));
			String line;
			while ((line = reader.readLine()) != null) {
				context.getCounter("aaa","111").increment(1l);
				set.add(line.trim());
			}
			reader.close();
    	}
	}
	
	@Override
	protected void map(ImmutableBytesWritable key, Result value,
			Mapper<ImmutableBytesWritable, Result, Text, Text>.Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		String row = Bytes.toString(value.getRow());
		String userid = HBaseUtil.unHashUserid(row);
		String tags = "";
		if(set.contains(userid)){
			String sex = "";
			String age = "";
			for(Cell cell:value.rawCells()){
				String qual = Bytes.toString(CellUtil.cloneQualifier(cell));
				if(qual.startsWith("t_100001")){
					sex = qual;
				}
				if(qual.startsWith("t_100002")){
					age = qual;
				}
			}
			context.getCounter("bbb","111").increment(1l);
			System.out.println(row + "\t" + sex + "\t" + age);
			context.write(new Text(row),new Text(sex + "\t" + age));
		}
	}
}
