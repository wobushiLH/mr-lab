package dmp.TagValueCounter;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class TagValueReducer extends Reducer<Text,Text,Text,Text>{

	@Override
	protected void reduce(Text key, Iterable<Text> values,
			Reducer<Text, Text, Text, Text>.Context context) throws IOException,
			InterruptedException {
		// TODO Auto-generated method stub
		for(Text t:values){
			context.write(key, t);
		}
	}
}