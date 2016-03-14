/**
 * Copyright © DB Schenker. All rights reserved.
 *
 * @Title:WordCilpper.java
 * @Project:TestSession
 * @Package:com.schenker.testsession.net
 * @author:eric
 * @date:Mar 8, 2016  3:18:17 PM
 * @version:V1.0 
 */
package com.schenker.training2.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.schenker.training2.pojo.Sentence;

/**
 * @ClassName: WordCilpper
 * @author: Eric
 * @date: Mar 8, 2016 3:18:17 PM
 */
public class WordMaster {
    private static final String UTF_8 = "UTF-8";
    private static final byte[] NL = "\n".getBytes(Charset.forName(UTF_8));
    private static final Pattern PATTERN = Pattern.compile("^.*<p>(.*)</p>.*$");
    
	private static Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy2.de.signintra.com", 80));

	private static Pattern letterPattern = Pattern.compile("<li class=\"currentpage\"><span>(.*?)</span></li>");
	private static Pattern pagePattern = Pattern.compile("<li class=\"activepage\"><span>(.*?)</span></li>");
	private static Pattern aPattern = Pattern.compile("<li>.*?<a href=\"http://www.*?\">(.*?)</a></li>");
	private static Pattern wordPattern = Pattern.compile("<a href=\"http://www.*?\" title=\".*?\">(.*?)</a>");

	private static int wordsNum = 0;
	private static BufferedWriter bw = null;
    private ObjectOutputStream oos = null;
	
	public WordMaster(String fileName) {
		BufferedReader br = null;
		try { 
			//bw = new BufferedWriter(new FileWriter(fileName));
			//fetchWords("A-B", 1);
			
			oos = new ObjectOutputStream(new FileOutputStream(new File("./sentences.txt")));
			br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
			String line = null;
			line = choose(fileName);
			//for(int i = 0; i < countLines(fileName) && (line = choose(fileName)) != null; ++i) {
				fetchSentence(true, line.trim());
			//}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
	    	try {
	    		if(bw != null)		bw.close();
	    		if(br != null)		br.close();
	    	} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
   
	private void fetchWords(String letter, int page){
		// Get all words, write to file
		// Get next url
		// if exist, call fetchWords(newUrl);
		BufferedReader br = null;
		try {		
			String urlString = new String("http://www.oxfordlearnersdictionaries.com/wordlist/english/oxford3000/Oxford3000_"+letter+"/?page="+page);
			URL url = new URL(urlString);
			URLConnection conn = url.openConnection(proxy);
			br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			String nextLetter = null;
			String nextPage = null;	
			String line = null;
			while((line = br.readLine()) != null) {
				// find the next letter
				Matcher letterMatcher = letterPattern.matcher(line);
				if(letterMatcher.find()) {
					line = br.readLine();
					letterMatcher = aPattern.matcher(line);
					if(letterMatcher.find()) {
						nextLetter = letterMatcher.group(1);
					}
				} 
				
				// find the next page
				Matcher pageMather = pagePattern.matcher(line);
				if(pageMather.find()) {
					line = br.readLine();
					pageMather = aPattern.matcher(line);
					if(pageMather.find()) {
						nextPage = pageMather.group(1);
					}
				} 

				// find all the words
				Matcher wordMatcher = wordPattern.matcher(line);
				if(wordMatcher.find()) {
					String word = wordMatcher.group(1);
					if(word.contains("1")) {
						word = word.substring(0, word.indexOf(" "));
					} else if(word.contains("2")) {
						continue;
					}
					bw.write(word);
					bw.newLine();
					bw.flush();
					//wordsNum++;	
				}
			}	
			
			if(nextPage != null) {
				fetchWords(letter, ++page);
			} else {
				 if(nextLetter != null) {
					fetchWords(nextLetter, 1);
				 } else {
					//System.out.println("WordsNum:"+wordsNum);
					 return;
				 }
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(br != null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	@SuppressWarnings("finally")
	private int countLines(String fileName) {	
		LineNumberReader reader = null;
		int count = 0;
		try {	
			reader = new LineNumberReader(new FileReader(fileName));
			String line = null;
			while((line = reader.readLine()) != null) {}
			
			count = reader.getLineNumber();	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return count;
		}
	}
	
	  
	@SuppressWarnings("finally")
	private String choose(String fileName) {
	     String result = null;
	     String s = null;
	     int n = 0;
	     Random rand = new Random();
	     Scanner sc = null;
	     try {
			sc = new Scanner(new File(fileName));
			while(sc.hasNext()) {
		    	s = sc.nextLine(); 
		        if(rand.nextInt(++n) == 0)
		           result = s;         
		     }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if(sc != null) {
				sc.close();
			}
			return result;
		}     
	}

    private void fetchSentence(boolean useProxy, String word) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String fldrNm = df.format(Calendar.getInstance().getTime());
        File f = new File(fldrNm);
        if(!f.exists()){
            f.mkdir();
        }
        
        Sentence sentence = new Sentence();
    	
        BufferedReader br = null;
        try {
            URL url = new URL("http://www.iciba.com/" + word);
            URLConnection urlConnection = null;
            if (useProxy) {
                urlConnection = url.openConnection(proxy);
            }
            else {
                urlConnection = url.openConnection();
            }
            br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), UTF_8));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            
            String inputLine;
            boolean startRecord = false;
            while ((inputLine = br.readLine()) != null) {
                if (inputLine.contains("<section class='result-collins page-section'>")) {
                    startRecord = true;
//                        continue;
                }
                if (startRecord && inputLine.contains("</section>")) {
                    break;
                }
                if (startRecord) {
                    Matcher matcher = PATTERN.matcher(inputLine);
                    if(matcher.matches()){
                        byteArrayOutputStream.write(matcher.group(1).getBytes(Charset.forName(UTF_8)));
                        byteArrayOutputStream.write(NL);
                        
                        if(matcher.group(1).contains("。")) {
                        	sentence.setTranslate(matcher.group(1));
                        	oos.writeObject(sentence);
                        	sentence = new Sentence();
                        } else {
                        	sentence.setOriginal(matcher.group(1));
                        }
                         
                    }
                }
            }
            
            System.out.println(byteArrayOutputStream.toString(UTF_8));
        }
        catch (UnsupportedEncodingException e) {
        }
        catch (IOException e) {
        }
        finally{
            if(br != null){
                try {
                    br.close();
                }
                catch (IOException e) {
                }
            }
        }

    }
    
    public void close() {
    	try {
    		if(oos != null) {
    			oos.close();
    		}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
}
