package utils;

import java.io.*;

public class webCreator {

    public static void createHTML(){
        //URL url;
        //XmlReader reader = null;
        //Document doc;
        //Corpus corpus;
        try {
            OutputStreamWriter osw;

            File fout = new File("./src/main/output/NoticiasElpais.html");
            BufferedReader template = null;

                template = new BufferedReader(new FileReader("./src/main/resources/news.html"));


            String content = "";

            String str;
            while ((str = template.readLine()) != null) {
                content = content + (str);
            }


            System.out.println("AAAA");
            System.out.println(content);


            String header=

                    "<!DOCTYPE html>\n" +
                            "<head>"+
                            "<meta charset=\"UTF-8\">"+
                            "</head>"+
                            "<html>\n" +
                            "<body>\n" +
                            "\n" +
                            "<h1>Estas son las últimas noticias</h1>\n" ;

            String footer="</body>\n" +
                    "</html>";
            FileOutputStream writer=new FileOutputStream(fout);

                /*BufferedReader in = new BufferedReader(new FileReader("mypage.html"));
                   String str;
                   while ((str = in.readLine()) != null) {
                        contentBuilder.append(str);
                    }
                    in.close();
                } catch (IOException e) {
                }*/


            osw=new OutputStreamWriter(writer,"utf-8");

            osw.append(header+"\n");
            osw.flush();

            String title;
            String link;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
