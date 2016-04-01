package com.glluch.profilesparser;

import com.glluch.jsolr.SQuery;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

/**
 *
 * @author Guillem LLuch Moll guillem72@gmail.com
 */
public class Main {

    public static void main(String[] args) throws IOException, SolrServerException {
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
            SolrDocumentList list = sq.buildAndExecute(ieee);
            String comp="";
                    for (SolrDocument doc:list){
                        comp+=doc.toString();        
        }
            System.out.println(comp);
        }
    }
}
