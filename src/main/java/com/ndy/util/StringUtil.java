/*
 * PROPRIETARY/CONFIDENTIAL
 */
package com.ndy.util;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author think
 * @date 2014年7月24日
 */
public class StringUtil {
	public static String subStrAndRegex(String stext, String etext, String regex, String text, int group) {
		int sindex = text.indexOf(stext);
		if (sindex >= 0) {
			int eindex = text.indexOf(etext, sindex);
			String ctext = text.substring(sindex + stext.length(), eindex);
			return regex(regex, ctext, group);
		}
		return "";
	}
	public static String subStr(String stext, String etext, String text) {
		int sindex = text.indexOf(stext);
		if (sindex >= 0) {
			int eindex = text.indexOf(etext, sindex);
			if (eindex >= 0) {
				String ctext = text.substring(sindex + stext.length(), eindex);
				return ctext;
			}
		}
		return "";
	}

	public static String subStrIgnoreFirst(String stext, String etext, String text) {
		int sindex = text.indexOf(stext);
		if (sindex >= 0) {
			int eindex = text.indexOf(etext, sindex+stext.length());
			if (eindex >= 0) {
				String ctext = text.substring(sindex + stext.length(), eindex);
				return ctext;
			}
		}
		return "";
	}

	public static String subStrLastIgnoreFirst(String stext, String etext, String text) {
		int sindex = text.lastIndexOf(stext);
		if (sindex >= 0) {
			int eindex = text.indexOf(etext, sindex+stext.length());
			if (eindex >= 0) {
				String ctext = text.substring(sindex + stext.length(), eindex);
				return ctext;
			}
		}
		return "";
	}


	public static String regex(String regex, String text, int group) {
    	Matcher m = Pattern.compile(regex).matcher(text);
        if (m.find()) {
           return m.group(group);
           //System.out.println(text1);
        }       
        return null;
    }
	public static String formatNumber(Number bd){		
		DecimalFormat df = new DecimalFormat("0.00"); 
		return df.format(bd);
	}
	public static void main(String[] args) throws Exception {
		String str = "&#32461;&#20852;&#20013;&#23548;&#30005;&#23376;&#31185;&#25216;&#26377;&#38480;&#20844;&#21496;";
        String str1 = "绍兴中导电子科技有限公司";
		System.out.println(convertWML(str));
		System.out.println(str1.substring(0, 10));
		System.out.println(URLEncoder.encode(str1, "utf-8"));
		System.out.println(URLEncoder.encode(str1, "iso8859-1"));
	}
	public static String convertWML(String s2) {
		String regExp = "&#\\d*;"; 
		Matcher m = Pattern.compile(regExp).matcher(s2);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
		    String s = m.group(0);
		    s = s.replaceAll("(&#)|;", "");
		    char c = (char) Integer.parseInt(s);
		    m.appendReplacement(sb, Character.toString(c));
		}
		m.appendTail(sb);
		//System.out.println(sb.toString());
		return sb.toString();
	}
	
	public static String filterChinese(String chin)
    {
        chin = chin.replaceAll("[(\\u4e00-\\u9fa5)]", "");
        return chin;
    }
	
	                        /**
     * <p>
     * Title: convertUTF
     * </p>
     * <p>
     * Description: UTF转换
     * </p>
     * 
     * @author Jerry Sun
     * @param utfString
     * @return
     */
	public static String convertUTF(String utfString)
    {	
		if(utfString.indexOf("\\u") >= 0){
			StringBuilder sb = new StringBuilder();
			int i=-1;
			int pos=0;
			while((i=utfString.indexOf("\\u",pos))!=-1)
			{
				sb.append(utfString.substring(pos,i));
				if(i+5<utfString.length())
				{ 
					pos=i+6;
					sb.append((char)Integer.parseInt(utfString.substring(i+2,i+6),16));
				}
			}
			return sb.toString();
		} else {
			return utfString;
		}
    } 
	
	                        /**
     * <p>
     * Title: decodeUnicode
     * </p>
     * <p>
     * Description: Unicode编码转中文
     * </p>
     * 
     * @author Jerry Sun
     * @param theString
     * @return
     */
	public static String decodeUnicode(String theString) {
		char aChar;
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = theString.charAt(x++);
			if (aChar == '\\') {
				aChar = theString.charAt(x++);
				if (aChar == 'u') {
					// Read the xxxx
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = theString.charAt(x++);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException(
									"Malformed   \\uxxxx   encoding.");
						}
					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't')
						aChar = '\t';
					else if (aChar == 'r')
						aChar = '\r';
					else if (aChar == 'n')
						aChar = '\n';
					else if (aChar == 'f')
						aChar = '\f';
					outBuffer.append(aChar);
				}
			} else
				outBuffer.append(aChar);
		}
		return outBuffer.toString();
	} 
	public static String decodeUnicode2(final String dataStr) {  
		int start = 0;  
		int end = 0;  
		final StringBuilder buffer = new StringBuilder();  
		while (start > -1) {  
			end = dataStr.indexOf("\\u", start + 2);  
			String charStr = "";  
			if (end == -1) {  
				charStr = dataStr.substring(start + 2, dataStr.length());  
			} else {  
				charStr = dataStr.substring(start + 2, end);  
			}  
			char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。  
			buffer.append(new Character(letter).toString());  
			start = end;  
		}  
		return buffer.toString();  
	}  
	
	                        /**
     * <p>
     * Title: flowFormat
     * </p>
     * <p>
     * Description: 流量格式化，统一单位KB
     * </p>
     * 
     * @author Jerry Sun
     * @param flow_temp
     * @return
     */
	public static Double flowFormat(String flow_temp){
        try {

            BigDecimal mb2Kb = new BigDecimal("0");
            BigDecimal gb2Kb = new BigDecimal("0");
            BigDecimal kb = new BigDecimal("0");
            BigDecimal thou = new BigDecimal("1024");
            flow_temp = flow_temp.replaceAll(" ", "");
            //全角空格这么霸道...by Pat。Liu
            flow_temp = flow_temp.replaceAll("　", "");
            flow_temp = flow_temp.replaceAll("-", "");
            if (flow_temp.contains("GB") || flow_temp.contains("MB")
                    || flow_temp.contains("KB")) {
                if (flow_temp.contains("GB")) {
                    gb2Kb = new BigDecimal(flow_temp.split("GB")[0].trim())
                            .multiply(thou).multiply(thou);
                }
                if (flow_temp.contains("MB")) {
                    if (flow_temp.contains("GB"))
                        mb2Kb = new BigDecimal(
                                flow_temp.split("GB")[1].split("MB")[0].trim())
                                .multiply(thou);
                    else
                        mb2Kb = new BigDecimal(flow_temp.split("MB")[0].trim())
                                .multiply(thou);
                }
                if (flow_temp.contains("KB")) {
                    if (flow_temp.contains("MB")) {
                        kb = new BigDecimal(StringUtil.subStr("MB", "KB",
                                flow_temp).trim());
                    } else if (flow_temp.contains("GB")) {
                        kb = new BigDecimal(StringUtil.subStr("GB", "KB",
                                flow_temp).trim());
                    } else {
                        kb = new BigDecimal(flow_temp.split("KB")[0].trim());
                    }
                }
            } else {
                if (flow_temp.contains("G")) {
                    gb2Kb = new BigDecimal(flow_temp.split("G")[0].trim())
                            .multiply(thou).multiply(thou);
                }
                if (flow_temp.contains("M")) {
                    if (flow_temp.contains("G"))
                        mb2Kb = new BigDecimal(
                                flow_temp.split("G")[1].split("M")[0].trim())
                                .multiply(thou);
                    else
                        mb2Kb = new BigDecimal(flow_temp.split("M")[0].trim())
                                .multiply(thou);
                }
                if (flow_temp.contains("K")) {
                    if (flow_temp.contains("M")) {
                        kb = new BigDecimal(StringUtil.subStr("M", "K",
                                flow_temp).trim());
                    } else if (flow_temp.contains("G")) {
                        kb = new BigDecimal(StringUtil.subStr("G", "K",
                                flow_temp).trim());
                    } else {
                        kb = new BigDecimal(flow_temp.split("K")[0].trim());
                    }
                }
            }

            return gb2Kb.add(mb2Kb).add(kb).doubleValue();
        } catch (Exception e) {
            return -0.1;
        }
	}
	
    /**
     * <p>
     * Title: timeFormat
     * </p>
     * <p>
     * Description: 流量时长格式化，统一单位秒
     * </p>
     * 
     * @author Jerry Sun Modify ruili
     * @param time
     * @return
     */
	public static Long flowTimeFormat(String time){
		long allTime=0,hour2Second=0,min2Second=0,second=0;
		if(time.contains(":")) {
			second = new  Long(time.substring(6,8));
			min2Second = new Long(time.substring(3,5));
			hour2Second = new Long(time.substring(0,2));
			allTime = hour2Second*3600+min2Second*60+second;
			return allTime;
		}else {
            if (time.contains("时")) {
                if (time.contains("小时"))
                    hour2Second = Long.parseLong(time.split("小时")[0].trim()) * 60 * 60;
			else
                    hour2Second = Long.parseLong(time.split("时")[0].trim()) * 60 * 60;
		}
            if (time.contains("分")) {
                if (time.contains("时")) {
                    min2Second = Long.parseLong(StringUtil.subStr("时", "分",
                            time)) * 60;
			}
			else{
                    min2Second = Long.parseLong(time.split("分")[0].trim()) * 60;
			}
		}
            if (time.contains("秒")) {
                if (time.contains("分")) {
                    if (time.contains("分钟"))
                        second = Long.parseLong(StringUtil.subStr("分钟", "秒",
                                time).trim());
				else 
                        second = Long.parseLong(StringUtil.subStr("分", "秒",
                                time).trim());
                } else if (time.contains("时")) {
                    if (time.contains("小时"))
                        second = Long.parseLong(StringUtil.subStr("小时", "秒",
                                time).trim());
				else
                        second = Long.parseLong(StringUtil.subStr("时", "秒",
                                time).trim());
			}else{
                    second = Long.parseLong(time.split("秒")[0].trim());
			}
		}
		allTime = hour2Second + min2Second + second;
		return allTime;
		}
	}
	
	public static String filterEmoji(String str){
		if (StringUtils.isEmpty(str)) return "";
		String regexPattern = "[\uD83C-\uDBFF\uDC00-\uDFFF]+";
		return str.replaceAll(regexPattern, "");
	}
	public static String truncate(String text, int len) {
		if (text.length() > len) {
			return text.substring(0, len);
		}
		return text;
	}
		
	
}
