package main;

import model.New;
import utils.SimpleHTMLExtractor;

import java.util.ArrayList;

import static rssutils.RssUtils.extractLinks;

public class SummarizingTheNews {

    private static final String URL_EL_PAIS_PORTADA = "http://ep00.epimg.net/rss/elpais/portada.xml";
    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(SummarizingTheNews.class);

    public static void main(String[] args) throws Exception {

        org.apache.log4j.BasicConfigurator.configure();

        log.info("Starting engine...");
        ArrayList<New> news = extractLinks(URL_EL_PAIS_PORTADA);
        log.info("Extracted links from El_Pais_Portada...");

        for(New currentNew : news) {
            String content = "";
            content = SimpleHTMLExtractor.callGATE(currentNew.getLink());

            currentNew.setContent(content);
        }

        log.info("END");

    }
}
