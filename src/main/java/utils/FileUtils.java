package utils;

import java.io.*;
import java.net.URL;

class FileUtils {

    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(FileUtils.class);

    private FileUtils(){}

    /**
     * This method returns a file which is placed in the resource folder of the project
     * @param resource: the file path we want to obtain
     * @return the file url
     */
    static String getResourceFolderPath(String resource) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource(resource);
        assert url != null;
        return url.getFile();
    }

    /**
     * It extracts the content from a file
     * @param filePath: file path
     * @return the content extracted
     */

    static String getContentFromFile(String filePath) {

        StringBuilder content = new StringBuilder(new StringBuilder());

        File file = new File(filePath);

        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file));

            String st;
            while ((st = br.readLine()) != null) {
                content.append(st);
            }

            return content.toString();

        } catch (FileNotFoundException e) {
            log.error(e.getMessage() != null ? e.getMessage(): "FileNotFoundException loading content from file.");
        } catch (IOException e) {
            log.error(e.getMessage() != null ? e.getMessage(): "IOException loading content from file.");
        }
        return null;
    }
}
