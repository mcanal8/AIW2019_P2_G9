package callingSUMMA;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import gate.Annotation;
import gate.AnnotationSet;
import gate.CorpusController;
import gate.Factory;
import gate.Gate;
import gate.creole.ExecutionException;
import gate.creole.ResourceInstantiationException;
import gate.util.GateException;
import gate.util.OffsetComparator;
import gate.util.persistence.PersistenceManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import model.New;


/**
 *
 * @author UPF
 */
public class callingSUMMA {

    private static final String USER_PROJECT_DIRECTORY = System.getProperty("user.dir");
    private static final String USER_HOME_DIRECTORY = System.getProperty("user.home");
    public static CorpusController application;
    private static String gappToTest="";

    //Initializes GATE with a gapp file
    public static void initializeGATE(String gappName) throws IOException  {

        gappToTest = gappName;

      try {

          if(Gate.getGateHome() == null) {
              //Gate.setGateHome(new File("C:\\Users\\u124275\\Desktop\\gate-8.0-build4825-BIN"));
              //Gate.setGateHome(new File(USER_HOME_DIRECTORY + "/GATE_Developer_8.0"));
              Gate.setGateHome(new File("D:\\Program Files\\GATE_Developer_8.0"));
          }

          if(Gate.getPluginsHome() == null) {
              //Gate.setPluginsHome(new File("C:\\Users\\u124275\\Desktop\\gate-8.0-build4825-BIN\\plugins"));
              //Gate.setPluginsHome(new File(USER_HOME_DIRECTORY + "/GATE_Developer_8.0/plugins"));
              Gate.setPluginsHome(new File("D:\\Program Files\\GATE_Developer_8.0\\plugins"));
          }
          
          Gate.init();
          
          application =
                        (CorpusController)
                        PersistenceManager.loadObjectFromFile(new
                             File("."+File.separator+"gapps"+File.separator+gappToTest));


      } catch(GateException ge) {
          ge.printStackTrace();
      }

      
    }

    //Given a text makes a summary of it
    public static void getSummary(New pieceOfNews) {

        String summary="";

        gate.Document doc;
        try {
            doc=Factory.newDocument(pieceOfNews.getContent());

            gate.Corpus corpus;
            corpus=Factory.newCorpus("");
            corpus.add(doc);
            application.setCorpus(corpus);
            application.execute();

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

            pieceOfNews.setSummary(summary);

        } catch (ResourceInstantiationException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    } 
    
}

    