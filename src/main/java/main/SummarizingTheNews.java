package main;

import gate.Annotation;
import gate.AnnotationSet;
import gate.Document;
import gate.util.OffsetComparator;
import model.New;
import utils.SimpleHTMLExtractor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static rssutils.RssUtils.extractLinks;

public class SummarizingTheNews {

    private static final String URL_EL_PAIS_PORTADA = "http://ep00.epimg.net/rss/elpais/portada.xml";
    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(SummarizingTheNews.class);
    

    public static void main(String[] args) throws Exception {

        org.apache.log4j.BasicConfigurator.configure();

        log.info("Starting engine...");
        List<New> news = extractLinks(URL_EL_PAIS_PORTADA);
        log.info("Extracted links from El_Pais_Portada...");

        for(New currentNew : news) {
            String content = "";
            content = SimpleHTMLExtractor.callGATE(currentNew.getLink());

            currentNew.setContent(content);
            log.info(content);
        }

        log.info("END");

    }
    
    public static String getSummary(Document doc) {
        String summary="";
        String dc=doc.getContent().toString();
        AnnotationSet sentences=doc.getAnnotations("EXTRACT").get("Sentence");
        // sort the annotations
        Annotation sentence;
        Long start, end;
        ArrayList<Annotation> sentList=new ArrayList(sentences);
        Collections.sort(sentList,new OffsetComparator());
        for(int s=0;s<sentList.size();s++) {
            sentence=sentList.get(s);
            start=sentence.getStartNode().getOffset();
            end  =sentence.getEndNode().getOffset();
            summary=summary+dc.substring(start.intValue(), end.intValue())+"\n";
        }
        
        return summary;
    } 
    
}
