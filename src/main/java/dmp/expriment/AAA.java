package dmp.expriment;

import java.io.Serializable;

public class AAA implements Serializable {

	public static void main(String[] args) {
		
		String s1 = "abc"; 
		String s2 = "abcd"; 
		String s3 = "abcdfg"; 
		String s4 = "1bcdfg"; 
		String s5 = "cdfg"; 
		
		String a = "url-13";
		String b = "url";
		
		System.out.println(a.compareTo(b));
		System.out.println(b.compareTo(b));
		System.out.println(b.compareTo(a));
		
//		System.out.println( s1.compareTo(s2) ); // -1 (前面相等,s1长度小1) 
//		System.out.println( s1.compareTo(s3) ); // -3 (前面相等,s1长度小3) 
//		System.out.println( s1.compareTo(s4) ); // 48 ("a"的ASCII码是97,"1"的的ASCII码是49,所以返回48) 
//		System.out.println( s1.compareTo(s5) ); // -2 ("a"的ASCII码是97,"c"的ASCII码是99,所以返回-2)
	}
	
	
	
}
