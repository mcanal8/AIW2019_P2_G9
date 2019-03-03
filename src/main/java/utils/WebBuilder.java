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

    /**
     * It creates a website given a list of news and a feed name
     * @param news: all the news we want to include in our website
     * @param newsFeedName: the title
     */
    public static void createWebsite(List<New> news, String newsFeedName){
        List<String> formattedNews = formatNews(news);
        String content = getContentFromFile(getResourceFolderPath(WEBSITE));
        assert content != null;
        buildWebsite(formattedNews, newsFeedName, content);
    }

    /**
     * This method formats the website given a list of news
     * @param news: the list of news given
     * @return: the format string list including the news
     */
    private static List<String> formatNews(List<New> news){

        List<String> formattedNews = new ArrayList<String>();

        for(New currentNew : news){
            StringBuilder formattedNew = new StringBuilder("    <div class=\"w3-third w3-container w3-margin-bottom\">\n" +
                    "      <div class=\"w3-container w3-white\">");
            formattedNew.append("<p><b><a href=").append(currentNew.getLink()).append(">").append(currentNew.getTitle()).append("</a></b></p>");
            formattedNew.append("<i>Escrito por: ").append(currentNew.getAuthor()).append(" el ").append(currentNew.getDate()).append("</i>");
            formattedNew.append("<p>").append(currentNew.getSummary()).append("</p>\n");
            formattedNew.append("<p><b>Temas relacionados:</b> ");
            Object[] categories = currentNew.getCategories().toArray();

            for(Object str : categories){
                formattedNew.append(str.toString()).append("  ");
            }
            formattedNew.append("</p>").append("      </div>\n").append("    </div>");
            formattedNews.add(formattedNew.toString());
        }

        return formattedNews;
    }

    /**
     * It builds the website with the formattedNews, the feed name and the content
     * @param formattedNews: news formatted correctly
     * @param newsFeedName: the feed name
     * @param content: the content of the html
     */
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


