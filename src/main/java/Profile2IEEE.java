package com.glluch.profilesparser;
import com.glluch.findterms.DataMapper;
import com.glluch.findterms.FindTerms;
import com.glluch.findterms.RDFReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.commons.io.FileUtils;
import org.apache.jena.rdf.model.Model;

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

/**
 *
 * @author Guillem LLuch Moll guillem72@gmail.com
 */
public class Profile2IEEE {
    public static ArrayList<String> doit(ICTProfile p) {
    ArrayList<String> res=new ArrayList<>();
    RDFReader lector = new RDFReader();
        String filename;
        
        filename="resources/IEEE_reasoner20022016.owl";
       
        Model model;
        model = lector.reader(filename);
        //test getallterms
        
        DataMapper dm=new DataMapper();
        dm.setModel(model);
        ArrayList<String> labels=dm.getAllLabels();
        //System.out.println(labels.size());
        
        FindTerms finder=new FindTerms();
        FindTerms.vocabulary=labels;
        String doc="";
        doc+=p.getTitle()+" "+p.getSummary()+" "+p.getMission().plainString();
        doc+=" "+p.plainTasks()+" "+p.getKpi();
        //System.out.println(doc);
        res=finder.found(doc);
        return res;
}
}
