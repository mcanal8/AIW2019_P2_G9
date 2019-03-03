package gate;

import gate.creole.ExecutionException;
import gate.creole.ResourceInstantiationException;
import gate.util.GateException;
import gate.util.OffsetComparator;
import gate.util.persistence.PersistenceManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GateUtils {

    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(GateUtils.class);
    private static final String USER_PROJECT_DIRECTORY = System.getProperty("user.dir");
    private static final String USER_HOME_DIRECTORY = System.getProperty("user.home");
    private static CorpusController application;

    private GateUtils(){}

    public static void initializeGATE(String gappName) throws IOException  {

      try {
          checkGateConfig();
          Gate.init();
          application = (CorpusController) PersistenceManager.loadObjectFromFile(new File("."+File.separator+"gapps"+File.separator+gappName));

      } catch(GateException ge) {
          log.error(ge.getMessage() != null ? ge.getMessage(): "Error initializing gate.");
      }
    }

    //Given a text makes a summary of it
    public static String getSummary(String text) {

        StringBuilder summary= new StringBuilder();
        gate.Document doc;

        try {
            doc=Factory.newDocument(text);

            gate.Corpus corpus;
            corpus=Factory.newCorpus("");
            corpus.add(doc);
            application.setCorpus(corpus);
            application.execute();

            String dc=doc.getContent().toString();
            AnnotationSet sentences = doc.getAnnotations("EXTRACT").get("Sentence");
            // sort the annotations
            Annotation sentence;
            Long start;
            Long end;
            List<Annotation> sentList= new ArrayList(sentences);
            Collections.sort(sentList, new OffsetComparator());

            for (Annotation annotation : sentList) {
                sentence = annotation;
                start = sentence.getStartNode().getOffset();
                end = sentence.getEndNode().getOffset();
                summary.append(dc, start.intValue(), end.intValue()).append("\n");
            }

            return summary.toString();

        } catch (ResourceInstantiationException e) {
            log.error(e.getMessage() != null ? e.getMessage() : "ResourceInstantiationException while getting summarizer.");
        } catch (ExecutionException e) {
            log.error(e.getMessage() != null ? e.getMessage() : "ExecutionException while getting summarizer.");
        }
        return null;
    }

    private static void checkGateConfig() {

        if(Gate.getGateHome() == null) {
            Gate.setGateHome(new File(USER_HOME_DIRECTORY + "/GATE_Developer_8.0"));
            //Gate.setGateHome(new File("C:\\Users\\u124275\\Desktop\\gate-8.0-build4825-BIN"));
            //Gate.setGateHome(new File("D:\\Program Files\\GATE_Developer_8.0"));
        }

        if(Gate.getPluginsHome() == null) {
            Gate.setPluginsHome(new File(USER_HOME_DIRECTORY + "/GATE_Developer_8.0/plugins"));
            //Gate.setPluginsHome(new File("C:\\Users\\u124275\\Desktop\\gate-8.0-build4825-BIN\\plugins"));
            //Gate.setPluginsHome(new File("D:\\Program Files\\GATE_Developer_8.0\\plugins"));
        }
    }

}

    