package dmp.HTMLContentParsing;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * <p>
 * 在线性时间内抽取主题类（新闻、博客等）网页的正文。 采用了<b>基于行块分布函数</b>的方法，为保持通用性没有针对特定网站编写规则。
 * </p>
 */
public class HTMLContentParser {

	public  static int blocksWidth;
	public static int threshold;
	@SuppressWarnings("unused")
	private static boolean flag;

	static {
		/*
		// ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager();
		// cm.setMaxTotal(1000);
		// cm.setDefaultMaxPerRoute(100);

		HttpParams httpParams = new BasicHttpParams();
		// 设置获取连接的最大等待时间
		HttpConnectionParams.setConnectionTimeout(httpParams, 60000);
		// 设置读取超时时间
		HttpConnectionParams.setSoTimeout(httpParams, 10000);
		// 关闭旧连接检查
		// HttpConnectionParams.setStaleCheckingEnabled(httpParams, false);

		ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager();
		cm.setMaxTotal(1000);
		cm.setDefaultMaxPerRoute(500);
		httpClient = new DefaultHttpClient(cm, httpParams);

		// 请求超时
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);
		// 读取超时
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 20000);
		*/
		
		blocksWidth = 4;
		flag = false;
		/* 当待抽取的网页正文中遇到成块的新闻标题未剔除时，只要增大此阈值即可。 */
		/* 阈值增大，准确率提升，召回率下降；值变小，噪声会大，但可以保证抽到只有一句话的正文 */
		threshold = 130;
	}

	public static String getHTML0(String strURL) throws IOException {
		URL url = new URL(strURL);
		URLConnection connection = url.openConnection();
		connection.setConnectTimeout(5000);
		connection.setReadTimeout(5000);
		// connection.connect();
		BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "gbk"));
		String s = "";
		StringBuilder sb = new StringBuilder("");
		while ((s = br.readLine()) != null) {
			sb.append(s + "\n");
		}
		br.close();
		return sb.toString();
	}

	public static String getHTML1(String strURL) throws IOException {
		URL url = new URL(strURL);
		URLConnection connection = url.openConnection();
		connection.setConnectTimeout(5000);
		connection.setReadTimeout(5000);
		// connection.connect();
		InputStream is = connection.getInputStream();
		byte[] b = new byte[1024 * 1024];
		int length = 0;
		int k = 0;
		while (k != -1) {
			k = is.read(b, length, b.length - length);
			// System.out.println(" k :"+k);
			if (k != -1)
				length += k;
		}
		is.close();
		// System.out.println("length : "+ length );
		String tem = new String(b, 0, length, Charset.forName("gbk"));

		Pattern p = Pattern.compile("<meta.*charset=\"?([\\w-]+)\"?");
		Matcher m = p.matcher(tem);
		if (m.find()) {
			String encode = m.group(1);
//			 System.out.println("maches : " + encode);
			if (!encode.equals("gbk")) {
				tem = new String(b, 0, length, Charset.forName(encode));
			}
		}
		return tem;
	}

	public static String getHTML2(String strURL) throws IOException {
		URL url = new URL(strURL);
		URLConnection connection = url.openConnection();
		connection.setConnectTimeout(5000);
		connection.setReadTimeout(5000);
		InputStream is = null;
		ByteArrayOutputStream outstream = null;
		try {
			is = connection.getInputStream();
			outstream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = -1;
			while ((len = is.read(buffer)) != -1) {
				outstream.write(buffer, 0, len);
			}
		} catch (IOException e) {
			// e.printStackTrace();
			throw new IOException("read from "+strURL+"  exception",e);
		}
		finally {
			if (outstream != null)
				outstream.close();
			if (is != null)
				is.close();
		}

		String tem = outstream.toString("gbk");
		Pattern p = Pattern.compile("<meta.*charset=\"?([\\w-]+)\"?");
		Matcher m = p.matcher(tem);
		if (m.find()) {
			String encode = m.group(1);
			// System.out.println("maches : " + encode);
			if (!encode.equals("gbk")) {
				tem = outstream.toString(encode);
			}
		}
		return tem;
	}


	public static void setthreshold(int value) {
		threshold = value;
	}

	/**
	 * 抽取网页正文，不判断该网页是否是目录型。即已知传入的肯定是可以抽取正文的主题类网页。
	 * 
	 * @param _html
	 *            网页HTML字符串
	 * 
	 * @return 网页正文string
	 */
	public static String parse(String _html) {
//		System.out.println("_html: " + _html);
		return parse(_html, false);
	}

	/**
	 * 判断传入HTML，若是主题类网页，则抽取正文；否则输出<b>"unkown"</b>。
	 * 
	 * @param _html
	 *            网页HTML字符串
	 * @param _flag
	 *            true进行主题类判断, 省略此参数则默认为false
	 * 
	 * @return 网页正文string
	 */
	public static String parse(String _html, boolean _flag) {
		flag = _flag;
//		System.out.println(_html);
		String html = preProcess(_html);
//		System.out.println(html);
		return getText(html);
	}

	// eliminate all js, css... relevant data
	private static String preProcess(String html) {
		html = html.replaceAll("(?is)<!DOCTYPE.*?>", "");
		html = html.replaceAll("(?is)<!--.*?-->", ""); // remove html comment
		html = html.replaceAll("(?is)<script.*?>.*?</script>", ""); // remove
																	// javascript
		html = html.replaceAll("(?is)<style.*?>.*?</style>", ""); // remove css
		html = html.replaceAll("&.{2,5};|&#.{2,5};", " "); // remove special
															// char
		html = html.replaceAll("(?is)<.*?>", "");
		return html;
		// <!--[if !IE]>|xGv00|9900d21eb16fa4350a3001b3974a9415<![endif]-->
	}

	private static String getText(String html) {

//		System.out.println(html);
		
		// get per line in the html file
		List<String> lines = Arrays.asList(html.split("\n"));
		ArrayList<Integer> indexDistribution = new ArrayList<Integer>();
		indexDistribution.clear();

		// calculate sum of wordcunt of last number of <blockwidth> lines 
		// if 4-line-wordcount-sum > threshold then X else Y
		for (int i = 0; i < lines.size() - blocksWidth; i++) {
//			 System.err.println(i+ ": "+lines.get(i));
			int wordsNum = 0;
			for (int j = i; j < i + blocksWidth; j++) {
				lines.set(j, lines.get(j).replaceAll("\\s+", ""));
				wordsNum += lines.get(j).length();
			}
			indexDistribution.add(wordsNum);
//			 System.err.println(i + ": " + wordsNum);
		}

		int start = -1;
		int end = -1;
		boolean boolstart = false, boolend = false;
		StringBuilder text = new StringBuilder();
		text.setLength(0);

		for (int i = 0; i < indexDistribution.size() - 1; i++) {
			if (indexDistribution.get(i) > threshold && !boolstart) {
				if (indexDistribution.get(i + 1).intValue() != 0 || indexDistribution.get(i + 2).intValue() != 0
						|| indexDistribution.get(i + 3).intValue() != 0) {
					boolstart = true;
//					System.err.println("Got one start: " + indexDistribution.get(i));
					start = i;
					continue;
				}
			}
			if (boolstart) {
				if (indexDistribution.get(i).intValue() == 0 || indexDistribution.get(i + 1).intValue() == 0) {
					end = i;
//					System.err.println("Got one end: " + indexDistribution.get(i));
					boolend = true;
				}
			}
			StringBuilder tmp = new StringBuilder();
			if (boolend) {
//				 System.out.println(" start-end : "+(start+1) + "\t" + (end+1));
				for (int ii = start; ii <= end; ii++) {
					if (lines.get(ii).length() < 5)
						continue;
					tmp.append(lines.get(ii) + "\n");
				}
				String str = tmp.toString();
//				 System.out.println(str);
				if (str.contains("Copyright") || str.contains("版权所有"))
					continue;
				text.append(str);
				 boolstart = boolend = false;
//				break;
			}
		}
//		System.err.println(text.toString());
		return text.toString();
	}

	public static String getContent0(String url) {
		String content = "";
		try {
			content = getHTML2(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return parse(content);

	}
	
	public static String getContent(String url) {
		StringBuilder builder = new StringBuilder();
		try {
			
			// get doc info from given url
			Document doc =Jsoup.parse(new URL(url), 5000);
//			Elements  keywords = doc.select("meta[name=keywords]"); 
//			builder.append(keywords.attr("content")+"\n");
			Elements  des = doc.select("meta[name=Keywords]"); 
			builder.append(des.attr("content")+"\n");
			
			// doc.outerHtml: return a string of all text info in given doc
			builder.append(parse(doc.outerHtml()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();

	}

}
