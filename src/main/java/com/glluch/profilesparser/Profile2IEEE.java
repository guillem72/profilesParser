package com.glluch.profilesparser;
import com.glluch.findterms.Vocabulary;
import com.glluch.findterms.FindTerms;
import com.glluch.findterms.Surrogate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

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
        public static double term_boost=2.0;
        public static double related_boost=1.0;
    
        public static HashMap<String,Double> fillTerms(ICTProfile p) {
    HashMap<String,Integer> res;
    FindTerms finder=new FindTerms();
        //FindTerms.vocabulary=labels;
        FindTerms.vocabulary=Vocabulary.get();
        //HashMap<String,Integer> foundAndCount
        String doc="";
        doc+=p.getTitle()+" "+p.getSummary()+" "+p.getMission().plainString();
        doc+=" "+p.plainTasks()+" "+p.getKpi();
        //System.out.println(doc);
        res=finder.foundAndCount(doc);
       p.setPtermsI2D(res);
       ArrayList <String> pterms=p.onlyTerms();
       Surrogate surro=new Surrogate(Vocabulary.jenaModel);
       HashMap<String, ArrayList<String>> rtermsRaw=surro.surrogatesForeach(pterms);
       HashMap <String,Double> rterms=preIntersect(p.getPterms(),rtermsRaw);
       p.setRterms(rterms);
      HashMap<String,Double>  res1=com.glluch.utils.JMap.intersectAndSum(p.getPterms(),p.getRterms());
        return res1;
}
        private static HashMap <String,Double> preIntersect(HashMap<String,Double> weights, HashMap<String, ArrayList<String>> rterms){
            HashMap <String,Double> res=new HashMap <>();
            Set keys=rterms.keySet();
            for (Object key0:keys){
                String key=(String) key0;
                double value0=weights.get(key);
                double value=value0*related_boost;
                if (res.isEmpty()) res=forEach(rterms.get(key),value);
                else{
                    res=com.glluch.utils.JMap.intersectAndSum(res,forEach(rterms.get(key),value));
                }
                
            }
            return res;
        }
        
        private static HashMap <String,Double> forEach(ArrayList <String> rterms,double val){
        HashMap <String,Double> res=new HashMap <>();
        for (String t:rterms){
            res.put(t,val);
        }
        return res;
        }
    
    
    public static ArrayList<String> doit(ICTProfile p) {
    ArrayList<String> res;
    FindTerms finder=new FindTerms();
        //FindTerms.vocabulary=labels;
        FindTerms.vocabulary=Vocabulary.get();
        String doc="";
        doc+=p.getTitle()+" "+p.getSummary()+" "+p.getMission().plainString();
        doc+=" "+p.plainTasks()+" "+p.getKpi();
        //System.out.println(doc);
        res=finder.found(doc);
        return res;
}
}
