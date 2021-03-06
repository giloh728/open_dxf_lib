package open_dxf_lib;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DXF_Utils {

	public static String readFile(String path, Charset encoding){

		try	{
			byte[] encoded = Files.readAllBytes(Paths.get(path));
			return new String(encoded, encoding);
		}
		catch (IOException e) {
			return null;
		}

	}
	
	
	public static String replace_values(HashMap<String, String> values, String str){
		//Create large String from String set with spacer "|"
		StringBuilder sbStr = new StringBuilder();
		int i = 0;
		for (String s: values.keySet()) {
			i++;
			sbStr.append(s);
			if(i<values.size()) {
				sbStr.append("|");
			}
		}
		
		String patternString = "%(" + sbStr.toString() + ")%";
		Pattern pattern = Pattern.compile(patternString);
		
		Matcher matcher = pattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		while(matcher.find()) {
		    matcher.appendReplacement(sb, values.get(matcher.group(1)));
		}
		
		matcher.appendTail(sb);
		
		return sb.toString();
	}
	/**
	 * For reade any text-data in .jar as large string
	 * @param source_name
	 * @return
	 */
	public static BufferedReader read_section(String source_name){
		InputStreamReader sr = new InputStreamReader(Object.class.getClass().getResourceAsStream(source_name));
		BufferedReader br = new BufferedReader(sr);
		return br;
	}
	/*
	public static Path get_absolute_path(String source_name){
		URL textURL = Object.class.getResource(source_name);
		System.out.println(textURL);
		Path path = null;
		try {
			path = Paths.get(textURL.toURI());
			
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return path;
	}
	*/
	public static void main(String[] args){
		HashMap<String, String> values = new HashMap<String, String>();
		values.put("x1", "560.00");
		values.put("x2", "220.00");
		values.put("xt", "220.00");
		values.put("x80", "220.00");
		values.put("gtr6", "2110.00");
		String str = "ret \n"
				+ "%x1% \n"
				+ "%y1% \n"
				+ "%x2%\n"
				+ "%gtr6%\n"
				+ "\n";
		
		String str2 = replace_values(values, str);
		System.out.println(str2);
		
	}

}
