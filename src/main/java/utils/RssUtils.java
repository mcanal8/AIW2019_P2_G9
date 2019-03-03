package utils;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import model.New;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static gate.GateUtils.getSummary;

public class RssUtils {

    private RssUtils(){}

    //Given a RSS link returns a list of news with the title and the link of each piece of news
    public static List<New> extractNews(String rssSite) throws Exception{

        List<New> news = new ArrayList<New>();
        URL url  = new URL(rssSite);
        XmlReader reader = null;

        try {
            reader = new XmlReader(url);
            SyndFeed feed = new SyndFeedInput().build(reader);

            for (Object o : feed.getEntries()) {
                SyndEntry entry = (SyndEntry) o;
                New customNew = new New();
                customNew.setTitle(entry.getTitle());
                customNew.setLink(entry.getLink());

                String content = extractNewContentFromUrl(entry.getLink());
                customNew.setContent(content);
                customNew.setSummary(getSummary(content));
                news.add(customNew);
            }
        } finally {
            if (reader != null)
                reader.close();
        }

        return news;
    }

    private static String extractNewContentFromUrl(String url) throws IOException {

        StringBuilder text = new StringBuilder();
        Document doc;
        doc = Jsoup.connect(url).get();
        Elements elements = doc.getElementsByTag("div");
        String txt;

        for(Element ele: elements) {
            if(ele.hasAttr("itemprop") && ele.attributes().get("itemprop").equals("articleBody")) {
                txt=ele.text();
                text.append(" ").append(txt);
            }

        }
        return text.toString();
    }
}
