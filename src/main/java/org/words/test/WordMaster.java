package org.words.test;

/**
 *
 * @Title:WordMaster.java
 * @author:eric and lily
 * @date:Mar 8, 2016  3:18:17 PM
 * @version:V1.0 
 */

import org.h2.server.Service;
import org.words.dao.WordDao;
import org.words.factory.ServiceRegistry;
import org.words.hbm.Sentence;
import org.words.hbm.Word;
import org.words.service.UserService;
import org.words.to.PlanTO;
import org.words.to.UserTO;
import org.words.utils.HibernateUtils;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
	
	public WordMaster() {
	}
   
	private void fetchWords(String letter, int page){
		// Get all words, write to file
		// Get next url
		// if exist, call fetchWords(newUrl);
		BufferedReader br = null;
		try {		
			String urlString = new String("http://www.oxfordlearnersdictionaries.com/wordlist/english/oxford3000/Oxford3000_"+letter+"/?page="+page);
			URL url = new URL(urlString);
			URLConnection conn = url.openConnection();
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

    private void fetchSentence(boolean useProxy, Word word) {

        BufferedReader br = null;
        try {
            URL url = new URL("http://www.iciba.com/" + word.getName());
            URLConnection urlConnection = null;
            if (useProxy) {
                urlConnection = url.openConnection(proxy);
            }
            else {
                urlConnection = url.openConnection();
            }
            br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), UTF_8));

            String inputLine;
            boolean startRecord = false;
            Sentence sentence = null;
            while ((inputLine = br.readLine()) != null) {
				if (inputLine.contains("<section class='result-collins page-section'>")) {
                    startRecord = true;
                }
                if (startRecord && inputLine.contains("</section>")) {
                    break;
                }
                if (startRecord) {
                    Matcher matcher = PATTERN.matcher(inputLine);
                    if(matcher.matches()){
                        String txt = matcher.group(1);
                        if(!txt.isEmpty()) {
                            if(sentence == null){
                                sentence = new Sentence();
                                sentence.setEnglish(txt);
                            }else{
                                sentence.setChinese(txt);
                                word.addSentence(sentence);
                                sentence = null;
                            }
                        }
                    }
                }
            }
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

	/** Call this to load word and sentences into database **/
    void initWordsSentences(){
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(getClass().getResource("/words.txt").getFile()));
            String line = null;
            while((line = br.readLine()) != null){
                Word word = new Word(line.trim());
                fetchSentence(false, word);
                HibernateUtils.getSessionFactory().getCurrentSession().beginTransaction();
                new WordDao().save(word);
                HibernateUtils.getSessionFactory().getCurrentSession().getTransaction().commit();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(br != null){
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }
    }


	void initUser(){
		UserTO userTO = new UserTO("Eric");
		PlanTO planTO = new PlanTO(new Date(), 50);
		userTO.addPlan(planTO);
		ServiceRegistry.getServiceInstance(UserService.class).saveUser(userTO);
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
