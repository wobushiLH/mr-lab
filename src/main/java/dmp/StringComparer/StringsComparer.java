package dmp.StringComparer;


/**
 * The {@code StringsComparer} class provides  
 * Precision Ratio, Recall Ratio and F-measure value 
 * by calculating given Template and Test Text
 * 
 * @author  LH

 */

public class StringsComparer {
	
	// Elements number in intersection set shall be at least greater than 5 
	private final static int minimalPhraseBlockSize = 5;
	
	private double templateLength;
	private double testLength;
	private double intersectionSetLength;
	private double precisionRatio;
	private double recallRatio;
	private double FmeasureValue;
	
	public StringsComparer(String template, String test){
		this.templateLength = (double)template.length();
		this.testLength = (double)test.length();
		intersectionSetLength = (double)getIntersectionSet(template, test).length();
		
		
//		System.out.println(getIntersectionSet(template, test));
//		System.out.println("----------");
//		System.out.println(templateLength);
//		System.out.println(testLength);
//		System.out.println(intersectionSetLength);
		
		recallRatio = intersectionSetLength/ templateLength;
		precisionRatio = intersectionSetLength / testLength;
		
		if((precisionRatio + recallRatio) == 0)
			FmeasureValue = 0;
		else
			FmeasureValue = 2 * recallRatio * precisionRatio / (precisionRatio + recallRatio);
	}
	
	/**
	 * @return	F-measure value = 2 * Precision Ratio * Recall Ratio / (Precision Ratio + Recall Ratio)
	 */
	public double getFmeasureValue(){
//		return FmeasureValue;
		return Math.round(FmeasureValue * 100) * 0.001d;
	}
	/**
	 * @return	Precision Ratio = Intersection Set Size / Test Set Size
	 */	
	public double getPrecisionRatio(){
//		return precisionRatio;
		return Math.round(precisionRatio * 100) * 0.001d;
	}	
	/**
	 * @return	Recall Ratio = Intersection Set Size / Template Set Size
	 */	
	public double getRecallRatio(){
//		return recallRatio;
		return Math.round(recallRatio * 100) * 0.001d;
	}
	
	
	/**
	 * This method calls {@code compare(String template, String test)} for intersection set string value
	 *
	 * @param	template
	 * 			HTML content extracted manually
	 * 		  	
	 * @param	test
	 * 			HTML content extracted by given algorithm
	 * 
	 * @return	Intersection set of template and test content returned by {@code compare(String template, String test)}
	 */
	private String getIntersectionSet(String template, String test){
		return compare(template, test);
	}

	/**
	 * This method returns the intersection set of two input {@code String} values. 
	 * It has an assumption that the first {@code String template} value is the 
	 * correct content we intend to produce and the second {@code String test} 
	 * value is the one we actually get from our program.
	 * 
	 * @param	template
	 * 			HTML content extracted manually
	 * 		  	
	 * @param	test
	 * 			HTML content extracted by given algorithm
	 * 
	 * @return	Intersection set of template and test content
	 */
	private String compare(String template, String test){
		String firstIntersectionSet = test;
		int beginIndex = 0;
        int endIndex = test.length();
        int i = 1;
        
        if(test.length()==0)
        	return "";
        
        while (!template.contains(firstIntersectionSet))
        {
            if (endIndex == test.length())
            {
            	beginIndex = 0;
            	endIndex = test.length() - (i++);
            }
            else
            {
            	/**
            	 * Narrow the content down to see whether any substring is contained in template
            	 */
            	beginIndex++;
            	endIndex++;
            }
            
            firstIntersectionSet = test.substring(beginIndex, endIndex);
            
            // intersection set shall be at least greater than 4 words/chars 
            if(firstIntersectionSet.length() < minimalPhraseBlockSize)
            	return "";
        }
        return firstIntersectionSet + compare(template.replace(firstIntersectionSet, ""), test.replace(firstIntersectionSet, ""));
	}

}
