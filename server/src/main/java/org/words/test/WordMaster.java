package org.words.test;

/**
 * @Title:WordMaster.java
 * @author:eric and lily
 * @date:Mar 8, 2016  3:18:17 PM
 * @version:V1.0
 */

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.nio.charset.Charset;
import java.util.regex.Pattern;

public class WordMaster {
    private static final String UTF_8 = "UTF-8";
    private static final byte[] NL = "\n".getBytes(Charset.forName(UTF_8));
    private static final Pattern PATTERN = Pattern.compile("^.*<p>(.*)</p>.*$");
    private static final Pattern PATTERN_MP3 = Pattern.compile("(?m)^.*data-file=\"([^\"]+)\".*data-dir=\"(\\w+)\".*");

//    private static Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy2.de.signintra.com", 80));
    private static Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("sscproxy.ap.signintra.com", 80));

    private static Pattern letterPattern = Pattern.compile("<li class=\"currentpage\"><span>(.*?)</span></li>");
//    private static Pattern pagePattern = Pattern.compile("<li class=\"activepage\"><span>(.*?)</span></li>");
//    private static Pattern aPattern = Pattern.compile("<li>.*?<a href=\"http://www.*?\">(.*?)</a></li>");
//    private static Pattern wordPattern = Pattern.compile("<a href=\"http://www.*?\" title=\".*?\">(.*?)</a>");
//
//    private static int wordsNum = 0;
//    private static BufferedWriter bw = null;
//    private ObjectOutputStream oos = null;
//
//    public WordMaster() {
//    }
//
//    private void fetchWords(String letter, int page) {
//        // Get all words, write to file
//        // Get next url
//        // if exist, call fetchWords(newUrl);
//        BufferedReader br = null;
//        try {
//            String urlString = new String(
//                    "http://www.oxfordlearnersdictionaries.com/wordlist/english/oxford3000/Oxford3000_" + letter + "/?page=" + page);
//            URL url = new URL(urlString);
//            URLConnection conn = url.openConnection();
//            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//
//            String nextLetter = null;
//            String nextPage = null;
//            String line = null;
//            while ((line = br.readLine()) != null) {
//                // find the next letter
//                Matcher letterMatcher = letterPattern.matcher(line);
//                if (letterMatcher.find()) {
//                    line = br.readLine();
//                    letterMatcher = aPattern.matcher(line);
//                    if (letterMatcher.find()) {
//                        nextLetter = letterMatcher.group(1);
//                    }
//                }
//
//                // find the next page
//                Matcher pageMather = pagePattern.matcher(line);
//                if (pageMather.find()) {
//                    line = br.readLine();
//                    pageMather = aPattern.matcher(line);
//                    if (pageMather.find()) {
//                        nextPage = pageMather.group(1);
//                    }
//                }
//
//                // find all the words
//                Matcher wordMatcher = wordPattern.matcher(line);
//                if (wordMatcher.find()) {
//                    String word = wordMatcher.group(1);
//                    if (word.contains("1")) {
//                        word = word.substring(0, word.indexOf(" "));
//                    }
//                    else if (word.contains("2")) {
//                        continue;
//                    }
//                    bw.write(word);
//                    bw.newLine();
//                    bw.flush();
//                    //wordsNum++;
//                }
//            }
//
//            if (nextPage != null) {
//                fetchWords(letter, ++page);
//            }
//            else {
//                if (nextLetter != null) {
//                    fetchWords(nextLetter, 1);
//                }
//                else {
//                    //System.out.println("WordsNum:"+wordsNum);
//                    return;
//                }
//            }
//        }
//        catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//        finally {
//            try {
//                if (br != null) {
//                    br.close();
//                }
//            }
//            catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
//
//    @SuppressWarnings ("finally")
//    private int countLines(String fileName) {
//        LineNumberReader reader = null;
//        int count = 0;
//        try {
//            reader = new LineNumberReader(new FileReader(fileName));
//            String line = null;
//            while ((line = reader.readLine()) != null) {
//            }
//
//            count = reader.getLineNumber();
//        }
//        catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//        finally {
//            if (reader != null) {
//                try {
//                    reader.close();
//                }
//                catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            return count;
//        }
//    }
//
//    @SuppressWarnings ("finally")
//    private String choose(String fileName) {
//        String result = null;
//        String s = null;
//        int n = 0;
//        Random rand = new Random();
//        Scanner sc = null;
//        try {
//            sc = new Scanner(new File(fileName));
//            while (sc.hasNext()) {
//                s = sc.nextLine();
//                if (rand.nextInt(++n) == 0)
//                    result = s;
//            }
//        }
//        catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        finally {
//            if (sc != null) {
//                sc.close();
//            }
//            return result;
//        }
//    }
//
//    public void fetchMp3Url(boolean useProxy, String word){
//        BufferedReader br = null;
//        try {
//            URL url = new URL("http://www.merriam-webster.com/dictionary/" + word);
//            URLConnection urlConnection = null;
//            if (useProxy) {
//                urlConnection = url.openConnection(proxy);
//            }
//            else {
//                urlConnection = url.openConnection();
//            }
//            br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), UTF_8));
//
//            String inputLine;
//            while ((inputLine = br.readLine()) != null) {
//                if (inputLine.contains("<a class=\"play-pron\"")) {
//                    Matcher matcher = PATTERN_MP3.matcher(inputLine.trim());
//                    if (matcher.matches()) {
//                        String targetUrl = "http://media.merriam-webster.com/audio/prons/en/us/mp3/" +
//                                matcher.group(2) + "/" + matcher.group(1) + ".mp3";
//
//                        System.out.println(targetUrl);
//                        loadMp3(word + ".mp3", targetUrl);
//                        break;
//                    }
//                }
//            }
//        }
//        catch (UnsupportedEncodingException e) {
//        }
//        catch (IOException e) {
//        }
//        finally {
//            if (br != null) {
//                try {
//                    br.close();
//                }
//                catch (IOException e) {
//                }
//            }
//        }
//    }
//
//    private void loadMp3(String word, String targetUrl) {
//        BufferedInputStream bi = null;
//        BufferedOutputStream bo = null;
//        try {
//            bi = new BufferedInputStream(new URL(targetUrl).openConnection(proxy).getInputStream());
//            bo = new BufferedOutputStream(new FileOutputStream(word));
//
//            int i;
//            while((i = bi.read()) != -1){
//                bo.write(i);
//            }
//        }
//        catch (IOException e) {
//        }
//        finally {
//            if (bi != null) {
//                try {
//                    bi.close();
//                }
//                catch (IOException e) {
//                }
//            }
//            if(bo != null){
//                try {
//                    bo.close();
//                }
//                catch (IOException e) {
//                }
//            }
//        }
//    }
//
//    public void fetchSentence(boolean useProxy, Word word) {
//        BufferedReader br = null;
//        try {
//            URL url = new URL("http://www.iciba.com/" + word.getName());
//            URLConnection urlConnection = null;
//            if (useProxy) {
//                urlConnection = url.openConnection(proxy);
//            }
//            else {
//                urlConnection = url.openConnection();
//            }
//            br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), UTF_8));
//
//            String inputLine;
//            Sentence sentence = null;
//            Meaning meaning = null;
//            StringBuilder sb = null;
//            boolean trackMeaning = false;
//            boolean attention = false;
//
//            while ((inputLine = br.readLine()) != null) {
////                System.out.println(inputLine);
//
//                if (inputLine.contains("<span class='method-order'>")) {
//                    sentence = null;
//                    attention = true;
//                }
//
//                if(!attention)
//                    continue;
//
//                if (inputLine.contains("<p class='method-explain'>")) {
//                    sb = new StringBuilder();
//                    trackMeaning = true;
//                }
//
//                if (trackMeaning){
//                    if(!inputLine.contains("<ul class='method-list'>")){
//                        sb.append(inputLine.trim());
//                    }else{
//                        meaning = new Meaning(sb.toString());
//                        trackMeaning = false;
//                    }
//                    continue;
//                }
//
//                Matcher matcher = PATTERN.matcher(inputLine);
//                if (matcher.matches()) {
//                    String txt = matcher.group(1);
//                    if (!txt.isEmpty()) {
//                        if (sentence == null) {
//                            sentence = new Sentence();
//                            sentence.setEnglish(txt);
//                            meaning.addSentence(sentence);
//                        }
//                        else {
//                            sentence.setChinese(txt);
//                            word.addSentence(sentence);
//                            sentence = null;
//                        }
//                    }
//                }
//            }
//        }
//        catch (UnsupportedEncodingException e) {
//        }
//        catch (IOException e) {
//        }
//        finally {
//            if (br != null) {
//                try {
//                    br.close();
//                }
//                catch (IOException e) {
//                }
//            }
//        }
//    }
//
//    /** Call this to load word and sentences into database **/
//    void initWordsSentences() {
//        BufferedReader br = null;
//        try {
//            br = new BufferedReader(new FileReader(getClass().getResource("/ielts.txt").getFile()));
//            String line = null;
//            while ((line = br.readLine()) != null) {
//                Word word = new Word(line.trim());
//                fetchSentence(true, word);
//                HibernateUtils.getSessionFactory().getCurrentSession().beginTransaction();
//                new WordDao().save(word);
//                HibernateUtils.getSessionFactory().getCurrentSession().getTransaction().commit();
//            }
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//        finally {
//            if (br != null) {
//                try {
//                    br.close();
//                }
//                catch (IOException e) {
//                }
//            }
//        }
//    }
//
//    void downloadMp3s(){
//       BufferedReader br = null;
//        try {
//            br = new BufferedReader(new FileReader(getClass().getResource("/ielts.txt").getFile()));
//            String line = null;
//            while((line = br.readLine()) != null){
//               fetchMp3Url(true, line.trim());
//            }
//        }
//        catch (FileNotFoundException e) {
//        }
//        catch (IOException e) {
//        }
//        finally {
//            if(br != null){
//                try {
//                    br.close();
//                }
//                catch (IOException e) {
//                }
//            }
//        }
//    }
//
//    void initUser() {
//        UserTO userTO = new UserTO("Eric");
//        PlanTO planTO = new PlanTO(new Date(), 150);
//        userTO.addPlan(planTO);
//        ServiceRegistry.getServiceInstance(UserService.class).saveUser(userTO);
//    }
//
////    void initTasks() {
////        PlanTO planTO = ServiceRegistry.getServiceInstance(PlanService.class).getPlan("Eric");
////        Date date = planTO.getStartDate();
////
////        Session session = HibernateUtils.getSessionFactory().openSession();
////        Transaction transaction = session.getTransaction();
////        transaction.begin();
////
////        List<String> sentences = session.createQuery("select s.id from Sentence s").list();
////        Collections.shuffle(sentences);
////
////        transaction.commit();
////        session.close();
////
////        int num = planTO.getNumber();
////
////        for (int i = 0; i < sentences.size(); i += num) {
////            int end = i + num;
////            saveTasks(sentences, i, end > sentences.size() ? sentences.size() : end, planTO, date);
////            date = new Date(date.getTime() + 60 * 60 * 24 * 1000);
////        }
////    }
//
////    private void saveTasks(List<String> sentences, int start, int end, PlanTO planTO, Date date) {
////        Session session = HibernateUtils.getSessionFactory().openSession();
////        Transaction transaction = session.getTransaction();
////        transaction.begin();
////
////        Plan plan = (Plan) session.get(Plan.class, planTO.getId());
////
////        Task task = new Task();
////        task.setDeadLine(date);
////        plan.addTask(task);
////
////        for (int i = start; i < end; i++) {
////            Sentence sentence = (Sentence) session.load(Sentence.class, sentences.get(i));
////            task.addSentence(sentence);
////            session.save(task);
////        }
////
////        transaction.commit();
////        session.close();
////    }
//
//    public void close() {
//        try {
//            if (oos != null) {
//                oos.close();
//            }
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
