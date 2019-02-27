package utils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import gate.Factory;
import gate.Gate;
import gate.util.GateException;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author UPF
 */
public class SimpleHTMLExtractor {

    private static final String USER_PROJECT_DIRECTORY = System.getProperty("user.dir");
    private static final String USER_HOME_DIRECTORY = System.getProperty("user.home");

       public static String extractFromDiarioFacha(String url) throws IOException {
        String text="";
        Document doc;
        doc = (Document) Jsoup.connect(url).get();
        String title;
        System.out.println(doc.title());
        Elements els;
       
        els=doc.getElementsByTag("div");
        String txt;
        for(Element ele: els) {
             if(ele.hasAttr("itemprop")) {
                if(ele.attributes().get("itemprop").equals("articleBody")) {
                    txt=ele.text();
                    
                   text=text+ " " + txt;
                }
        }
            
        }
        return text;
    }
    
    public static String callGATE(String link) throws IOException  {
        
      String text=extractFromDiarioFacha(link);


      String summary = "";
      try {

          if(Gate.getGateHome() == null) {
              //Gate.setGateHome(new File("C:\\Users\\u124275\\Desktop\\gate-8.0-build4825-BIN"));
              //Gate.setGateHome(new File(USER_HOME_DIRECTORY + "/GATE_Developer_8.0"));
              Gate.setGateHome(new File("D:\\Program Files\\GATE_Developer_8.0"));
          }

          if(Gate.getPluginsHome() == null) {
              //Gate.setPluginsHome(new File("C:\\Users\\u124275\\Desktop\\gate-8.0-build4825-BIN\\plugins"));
              //Gate.setPluginsHome(new File(USER_HOME_DIRECTORY + "/GATE_Developer_8.0/plugins"));
              Gate.setPluginsHome(new File("D:\\Program Files\\GATE_Developer_8.0\\plugins"));
          }
          
          Gate.init();
          gate.Document doc;
          doc=Factory.newDocument(text);
          System.out.println(doc.getContent());
          summary = doc.getContent().toString();
      } catch(GateException ge) {
          ge.printStackTrace();
      }

        return summary;
               
        
      
    }
}

    