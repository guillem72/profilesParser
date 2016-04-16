package com.glluch.profilesparser;

import com.glluch.jsolr.SQuery;
import com.glluch.jsolr.SolrQ;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

/**
 *
 * @author Guillem LLuch Moll guillem72@gmail.com
 */
public class Main {

    public static void main(String[] args) throws IOException, SolrServerException {
       //profilesSimple();
       //profiles();
       //maps();
       write("resources/competencesXMLsolr/");
    }
    public static void write(String path) throws IOException {
    Writer w=new Writer();
    w.profiles2SolrXML(path);
    }
    public static void maps(){
        ProfileHtmlReader phr = new ProfileHtmlReader();
        ArrayList<ICTProfile> ps = phr.readerDir("resources/ict_profiles");
        for (ICTProfile pp : ps) {
            System.out.println("\n" + pp.getTitle());
            System.out.println(pp.compentences());
        }
    }
    public static void profiles() throws IOException, SolrServerException{
        ProfileHtmlReader phr = new ProfileHtmlReader();

        //ArrayList <ECFMap> p=phr.competences("resources/ict_profiles/Enterprise Architect.html");
        //System.out.println(p.toString());
        //System.out.println(phr.completeText("resources/ict_profiles/Enterprise Architect.html"));
        ArrayList<ICTProfile> ps = phr.readerDir("resources/ict_profiles");

        //System.out.println(Profile2IEEE.doit(p));
        for (ICTProfile pp : ps) {
            System.out.println("\n" + pp.getTitle());
            HashMap<String, Double>ieee=Profile2IEEE.fillTerms(pp);
            System.out.println("IEEE terms: "+ieee.toString());
            System.out.println("Competences="+pp.getCompenteCodes());
            SolrQ sq=new SolrQ();
             sq.setCustom("fl", "id");
            SolrDocumentList list = sq.buildAndExecute(ieee);
           String comp="";
                    for (SolrDocument doc:list){
                        comp+=doc.toString();        
        }
            System.out.println(comp);
        }
    }
    
    
    public static void profilesSimple() throws IOException, SolrServerException{
         ProfileHtmlReader phr = new ProfileHtmlReader();

        //ArrayList <ECFMap> p=phr.competences("resources/ict_profiles/Enterprise Architect.html");
        //System.out.println(p.toString());
        //System.out.println(phr.completeText("resources/ict_profiles/Enterprise Architect.html"));
        ArrayList<ICTProfile> ps = phr.readerDir("resources/ict_profiles");

        //System.out.println(Profile2IEEE.doit(p));
        for (ICTProfile pp : ps) {
            System.out.println("\n" + pp.getTitle());
            ArrayList<String>ieee=Profile2IEEE.doit(pp);
            System.out.println("IEEE terms: "+ieee);
            System.out.println("Competences="+pp.getCompenteCodes());
            SQuery sq=new SQuery();
            sq.setCustom("fl", "id");
            sq.setUrlString("http://localhost:8888/solr/ecf2");
            SolrDocumentList list = sq.buildAndExecute(ieee);
            String comp="";
                    for (SolrDocument doc:list){
                        comp+=doc.toString();        
        }
            System.out.println(comp);
            //System.out.println(sq.getUrlString());
        }
    }
}
