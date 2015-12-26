package dmp.CookieMappingCount;

import java.io.IOException;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;



public class CMMapper extends TableMapper<Text, Text> {

	@Override
	protected void map(ImmutableBytesWritable key, Result value,
			Mapper<ImmutableBytesWritable, Result, Text, Text>.Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		String k = Bytes.toString(value.getRow());
		
		// count number of a given source cookie
		if(k.contains("miaozhen")){
			for(Cell cell:value.rawCells()){
				String qual = Bytes.toString(CellUtil.cloneQualifier(cell));
//				System.out.println(k + "\t" + qual);
				context.write(new Text("MiaoZhen"), new Text("1"));
			}
		}
		
		
		
		
//		if(k.contains("Allyes")){
//			for(Cell cell:value.rawCells()){
//				String qual = Bytes.toString(CellUtil.cloneQualifier(cell));
////				System.out.println(k + "\t" + qual);
//				context.write(new Text("Allyes"), new Text("1"));
//			}
//		}	

		// count total number
		context.write(new Text("total"), new Text("1"));
		
	
		
		
	}
}