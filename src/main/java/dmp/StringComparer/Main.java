package dmp.StringComparer;

import dmp.HTMLContentParsing.HTMLContentParser;

public class Main {

	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HTMLContentParser parser = new HTMLContentParser();

		String template = parser.getContent("http://www.kuqin.com/shuoit/20140809/341560.html");
		String test =  parser.getContent("http://www.kuqin.com/shuoit/20140809/341560.html").substring(0, 20)
						+ "AAAAAAAAAAAAAAAAAAAA"
						+ parser.getContent("http://www.kuqin.com/shuoit/20140809/341560.html").substring(30, 50)
						+ "BBBBBBBBBBBBBB"
						+ "BBBBBBBBBBBBasdfasdgasdgBB";
		
		
//		System.out.println(template);
//		System.out.println("----------");
//		System.out.println(test);
//		System.out.println("----------");
	
		
		
		StringsComparer comparer = new StringsComparer("abcccbbccvcvcbcc", "abccccccccc");
		
		System.out.println("Recall Ratio: " + comparer.getRecallRatio());
		System.out.println("Precision Ratio: " + comparer.getPrecisionRatio());
		System.out.println("F-measure Value: " + comparer.getFmeasureValue());
	}

}
