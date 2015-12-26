package dmp.CookieMappingCount;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class CMReducer extends Reducer<Text, Text, Text, Text>{

	@Override
	protected void reduce(Text key, Iterable<Text> values,
			Reducer<Text, Text, Text, Text>.Context context) throws IOException,
			InterruptedException {
		// TODO Auto-generated method stub
		long sum = 0;
		for(Text t:values){
			long num = Long.valueOf(t.toString());
			sum = sum + num;
		}		
		context.write(key, new Text(String.valueOf(sum)));
	}
}