package utils;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import gate.Corpus;
import model.New;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class RssUtils {

    private RssUtils(){}

    //Given a RSS link returns a list of news with the title and the link of each piece of news
    public static List <New> extractLinks(String rssSite) throws Exception{


        List<New> news = new ArrayList<New>();

        URL url  = new URL(rssSite);
        XmlReader reader = null;

        try {
            reader = new XmlReader(url);
            SyndFeed feed = new SyndFeedInput().build(reader);


            for (Object o : feed.getEntries()) {
                SyndEntry entry = (SyndEntry) o;
                New customNew = new New(entry.getTitle(), entry.getLink());
                news.add(customNew);
            }

            //result.put("news", news);
        } finally {
            if (reader != null)
                reader.close();
        }
        //log.info("***********************");
        return news;
    }

    //Given a piece of news link extracts the content
    public static String extractFromDiarioFacha(String url) throws IOException {
        String text="";
        Document doc;
        Corpus corpus;
        doc = (Document) Jsoup.connect(url).get();
        String title;
        //System.out.println(doc.title());
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
}
