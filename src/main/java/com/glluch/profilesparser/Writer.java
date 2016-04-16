/*
 * Copyright 2016 Guillem LLuch Moll guillem72@gmail.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.glluch.profilesparser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Guillem LLuch Moll guillem72@gmail.com
 */
public class Writer {
 public double term_boost=2.0;
    public double related_boost=1.0;
    
    public void profiles2SolrXML(String path) throws IOException {
        ProfileHtmlReader phr = new ProfileHtmlReader();
        ArrayList<ICTProfile> ps = phr.readerDir("resources/ict_profiles");
        for (ICTProfile pp : ps) {
        HashMap<String, Double> ieee = Profile2IEEE.fillTerms(pp);
        String xml="<add><doc>";
        xml+="<field name=\"id\"";
        xml+=">";
        xml+=pp.getTitle();
         xml+="</field>";
         xml+="<field name=\"type\">ICT_profile</field>";
         xml+=terms2xml("term",ieee);       
       xml+=competences2xml(pp.getEcfs());
        xml+="</doc></add>";
        //competencesXMLsolr
        String fileTitle=pp.getTitle()+".xml";
        FileUtils.writeStringToFile(new File(path+fileTitle), xml, "utf8");
       
         }
        
    }
    
    protected String competences2xml(ArrayList<ECFMap> ecfs){
     //competence_
     String res="";
     for (ECFMap ecf:ecfs){
          res+="<field name=\"competence_\" >"
                   +ecf.getCode()+" "+ecf.getLevel()+"</field>";
     }
     return res;
    }
    
    protected String terms2xml(String field_name,HashMap <String,Double> terms){
        String text="";
      Set pterms=terms.keySet();
       for (Object t0: pterms){
           String t=(String) t0;
           text+="<field name=\""+field_name+"\" "
                   + " boost=\""+term_boost*terms.get(t)+"\""
                   + ">"
                   +t+"</field>";
       }
        return text;
    }
}
