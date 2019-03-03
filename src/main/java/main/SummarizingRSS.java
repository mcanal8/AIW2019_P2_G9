/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import model.New;
import gate.GateUtils;
import utils.WebBuilder;

import java.util.List;

import static utils.RssUtils.*;


public class SummarizingRSS {

    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(SummarizingRSS.class);
    private static final String GAPP ="MySUMMARIZER.gapp";
    private static final String RSS_URL ="http://ep00.epimg.net/rss/elpais/portada.xml";
    private static final String NEWS_FEED_NAME = "El Pais";

    /**
     * Main method that execute our application
     * First of all, it extract the news given a rss url
     * (title, link, content and summary)
     * Finally, it creates a html file with all the summary news
     * @param args: main args
     * @throws Exception: main exception
     */
   public static void main(String[] args) throws Exception {

       org.apache.log4j.BasicConfigurator.configure();

       log.info("Starting engine...");
       GateUtils.initializeGATE(GAPP);
       List<New> news = extractNews(RSS_URL);
       log.info("Extracted links from " + NEWS_FEED_NAME);

       log.info("Creating website...");
       WebBuilder.createWebsite(news, NEWS_FEED_NAME);
       log.info("Program ended execution");
   }
}
