/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import gate.*;
import model.New;
import callingSUMMA.callingSUMMA;
import utils.webCreator;

import java.util.List;

import static utils.RssUtils.extractFromDiarioFacha;
import static utils.RssUtils.extractLinks;

/**
 *
 * @author UPF
 */
public class SummarizingRSS {

    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(SummarizingRSS.class);
  
    public static String gappToTest="MySUMMARIZER.gapp";
    // this is the controller to load the GAPP
    public static CorpusController application;
    public static String rssSite;

    public static final String elPais="http://ep00.epimg.net/rss/elpais/portada.xml";
    public static String newsFeedName;

    //Application entry point
   public static void main(String[] args) throws Exception {
       rssSite = elPais;
       newsFeedName="El_Pais";

       org.apache.log4j.BasicConfigurator.configure();

       log.info("Starting engine...");
       List<New> news = extractLinks(rssSite);
       log.info("Extracted links from El_Pais_Portada...");

       log.info("Extracting piece of news content from link...");
       for(New currentNew : news) {
           String content = "";
           content = extractFromDiarioFacha(currentNew.getLink());
           currentNew.setContent(content);
           //log.info(content);
       }

       log.info("Using SUMMA for GATE to summarize the news...");
       callingSUMMA.initializeGATE(gappToTest);
       for(New currentNew : news) {
            callingSUMMA.getSummary(currentNew);
       }

        printer(news);
       webCreator.createWebsite(news, newsFeedName);
       log.info("Program ended execution");

   }

    //Prints in the terminal the news and its summaries
    public static void printer(List<New> news){
       log.info("Printing results...");

       for(New currentNew : news){
           System.out.println(currentNew.getTitle());
           System.out.println(currentNew.getLink());
           System.out.println("\n CONTENT:");
           System.out.println(currentNew.getContent());
           System.out.println("\n SUMMARY");
           System.out.println(currentNew.getSummary());
           System.out.println("\n **************************************************** \n\n");
       }

       log.info("End of the News");
    }
    
}
