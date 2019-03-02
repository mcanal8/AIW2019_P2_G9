package utils;

import model.New;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class webCreator
{
    private static String htmlCode = "";
    private static List<String> formatedNews;

    public static void createWebsite(List<New> news, String newsFeedName){

        formatNews(news);
        loadFile(".\\src\\main\\resources\\voidWebsite.html");


    }


    private static void loadFile(String filePath)
    {

        File file = new File(filePath);

        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file));

        String st;
        while ((st = br.readLine()) != null) {
            //System.out.println(st);
            htmlCode = htmlCode + st;
        }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void formatNews(List<New> news){

        for(New currentNew : news){
            String formatedNew = "    <div class=\"w3-third w3-container w3-margin-bottom\">\n" +
                    "      <img src=\"/w3images/mountains.jpg\" alt=\"Norway\" style=\"width:100%\" class=\"w3-hover-opacity\">\n" +
                    "      <div class=\"w3-container w3-white\">";
            formatedNew = formatedNew + "<p><b>" + currentNew.getTitle() + "</b></p>";
            formatedNew = "<p>" + currentNew.getSummary() + "</p>\n" +
                    "      </div>\n" +
                    "    </div>";
            formatedNews.add(formatedNew);
        }

    }

    private static void buildWebsite(String newsFeedName){
        try {
            OutputStreamWriter osw;
            File fout=new File("."+File.separator+"output"+File.separator+
                    "ultimas_noticias_"+newsFeedName+".html");

            FileOutputStream writer=new FileOutputStream(fout);
            osw=new OutputStreamWriter(writer,"utf-8");


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


}


