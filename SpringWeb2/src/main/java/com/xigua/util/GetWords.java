package com.xigua.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class GetWords {
    final static Pattern pattern = Pattern.compile("[0-9]");//正则提取搜索结果中的数字 

  //读取文本的关键词 
  public String[] readTxt() throws IOException { 
      String[] words = new String[9000]; 
      int i = 0; 
      FileReader fr = new FileReader("H:/mojiaqin.txt"); 
      BufferedReader br = new BufferedReader(fr); 
      while(br.ready()) { 
          words[i] = br.readLine(); 
          //System.out.println(br.readLine()); 
          i++; 
      } 
      return words; 
  } 
  //将搜索结果写到文本中 
  public void writeTxt(String[] words,String[] times) throws IOException { 
      FileWriter fw = new FileWriter("H:/mojiaqin_1.txt"); 
      BufferedWriter bw = new BufferedWriter(fw); 
      for(int i = 0;i <words.length&&i<times.length;i++) { 
          if(words[i]!=null&&times[i]!=null) 
          { 
              bw.write(words[i]); 
              bw.write(","); 
              bw.write(times[i]); 
              bw.newLine(); 
          } 
          else 
              break; 
      } 
      bw.close(); 
      fw.close(); 
  } 

  //用百度搜索 
  public String  LinkBaidu(String word) throws Exception, Exception, Exception { 
      final WebClient  webclient = new WebClient(); 
      final HtmlPage htmlpage = webclient.getPage("http://www.baidu.com/"); 

      webclient.setCssEnabled(false); 
      webclient.setJavaScriptEnabled(false); 
      //System.out.println(htmlpage.getTitleText()); 
      final HtmlForm form = htmlpage.getFormByName("f"); 
      final HtmlSubmitInput button = form.getInputByValue("百度一下"); 
      final HtmlTextInput textField = form.getInputByName("wd"); 
      textField.setValueAttribute(word); 
      final HtmlPage page2 = button.click(); 
      //System.out.println(page2.asText()); 

      HtmlSpan span = (HtmlSpan) page2.getByXPath("//span[@class='nums']").get(0); 
      String str = span.asText(); 
      // pickUp(str); 
      webclient.closeAllWindows(); 
      return pickUp(str); 
  } 
  //提取数字 
  public static String pickUp(String text) { 
      Matcher matcher = pattern.matcher(text); 
      StringBuffer bf = new StringBuffer(64); 
      while (matcher.find()) { 
        bf.append(matcher.group()).append(""); 
      } 
      return bf.toString(); 
    } 
   public static void main(String[] args) throws IOException { 
       GetWords getwords = new GetWords(); 
       String[] words = getwords.readTxt(); 
       String[] times = new String[9000]; 
       String str; 
       //words.writeTxt(w); 
       try { 
           for(int i = 0;i<words.length;i++) { 
               if(words[i]!= null) { 
                   str = getwords.LinkBaidu(words[i]); 
                   times[i] = str; 
                   System.out.println(words[i]+","+str); 
               } 
               else 
                   break; 
           } 
           System.out.println("搜索完毕！"); 
           getwords.writeTxt(words,times); 
       } catch (Exception e) { 
           // TODO Auto-generated catch block 
           e.printStackTrace(); 
       } 
   } 
}
