package utils;

import model.New;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static utils.FileUtils.*;

public class WebBuilder {

    private WebBuilder(){}

    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(WebBuilder.class);
    private static final String WEBSITE = "voidWebsite.html";

    public static void createWebsite(List<New> news, String newsFeedName){
        List<String> formattedNews = formatNews(news);
        String content = getContentFromFile(getResourceFolderPath(WEBSITE));
        assert content != null;
        buildWebsite(formattedNews, newsFeedName, content);
    }

    private static List<String> formatNews(List<New> news){

        List<String> formattedNews = new ArrayList<String>();

        for(New currentNew : news){
            String formattedNew = "    <div class=\"w3-third w3-container w3-margin-bottom\">\n" +
                    "      <img src=\"/w3images/mountains.jpg\" alt=\"Norway\" style=\"width:100%\" class=\"w3-hover-opacity\">\n" +
                    "      <div class=\"w3-container w3-white\">";
            formattedNew = formattedNew + "<p><b>" + currentNew.getTitle() + "</b></p>";
            formattedNew = formattedNew + "<p>" + currentNew.getSummary() + "</p>\n" +
                    "      </div>\n" +
                    "    </div>";
            formattedNews.add(formattedNew);
        }

        return formattedNews;
    }

    private static void buildWebsite(List<String> formattedNews, String newsFeedName, String content){

        String finalWebPage;
        String[] parts = content.split("<div class=\"w3-row-padding\">");

        for(String str : formattedNews){
            parts[1] = str + parts[1];
        }

        finalWebPage = parts[0] + parts[1];

        try {

            OutputStreamWriter osw;
            File fout = new File("."+File.separator+"src"+File.separator+"main"+File.separator+"output"+File.separator+ "ultimas_noticias_"+newsFeedName+".html");

            FileOutputStream writer=new FileOutputStream(fout);
            osw=new OutputStreamWriter(writer,"utf-8");
            osw.append(finalWebPage+"\n");
            osw.flush();
            osw.close();

        } catch (FileNotFoundException e) {
            log.error(e.getMessage() != null ? e.getMessage(): "FileNotFoundException building website.");
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage() != null ? e.getMessage(): "UnsupportedEncodingException building website.");
        } catch (IOException e) {
            log.error(e.getMessage() != null ? e.getMessage(): "IOException building website.");
        }
    }
}


