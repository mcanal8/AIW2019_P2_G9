package rssutils;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import main.SummarizingTheNews;
import model.New;

import java.net.URL;
import java.util.*;

public class RssUtils {

    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(SummarizingTheNews.class);

    private RssUtils(){}

    public static Map<String, List<New>> extractLinks(String rssSite) throws Exception{

        Map<String, List<New>> result = new HashMap<String, List<New>>();
        List<New> news = new ArrayList<New>();

        URL url  = new URL(rssSite);
        XmlReader reader = null;

        try {
            reader = new XmlReader(url);
            SyndFeed feed = new SyndFeedInput().build(reader);
            result.put("feedTitle", Collections.singletonList(new New(feed.getTitle(), null)));

            for (Object o : feed.getEntries()) {
                SyndEntry entry = (SyndEntry) o;
                New customNew = new New(entry.getTitle(), entry.getLink());
                news.add(customNew);
            }

            result.put("news", news);
        } finally {
            if (reader != null)
                reader.close();
        }
        log.info("***********************");
        return result;
    }
}
