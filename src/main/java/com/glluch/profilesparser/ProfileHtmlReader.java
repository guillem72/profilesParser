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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Guillem LLuch Moll guillem72@gmail.com
 */
public class ProfileHtmlReader implements iProfileReader {

    /*
    private HashMap <String,ECFMap> comps;

    public ProfileHtmlReader() {
        this.comps = new HashMap <>();
    }
     */
    @Override
    public ICTProfile reader(String filename) {
        ICTProfile res = new ICTProfile();

        try {
            File input = new File(filename);
            Document doc = Jsoup.parse(input, "UTF-8");
            Element ts = doc.select("h2").first();
            res.setTitle(ts.ownText().trim());
            String allTxt = doc.getElementById("clipboard_text").text();
            //Get summary, the text  Mission and KPI
            int i = 0;
            Elements txts = doc.select("h3 + p");
            for (Element text : txts) {
                if (i == 0) {
                    res.setSummary(text.ownText());
                }
                if (i == 1) {
                    res.setMission(new Mission(text.ownText()));
                }
                if (i == 2) {
                    res.setKpi(text.ownText());
                }
                i++;
            }

            //Get Mission Deliverables and tasks
            String acc=StringUtils.substringBetween(allTxt, "Accountable","Responsible").trim();
            String respon=StringUtils.substringBetween(allTxt, "Responsible","Contributor").trim();
            String contrib=StringUtils.substringBetween(allTxt, "Contributor","Main task/s").trim();
            String tks=StringUtils.substringBetween(allTxt, "Main task/s","KPI area ").trim();
            
            HashMap <Integer,String> uls=new HashMap <>();
           i=0;
           if (StringUtils.isNotEmpty(acc)) uls.put(i++, "Accountable");
           if (StringUtils.isNotEmpty(respon)) uls.put(i++, "Responsible");
           if (StringUtils.isNotEmpty(contrib)) uls.put(i++, "Contributor");
           if (StringUtils.isNotEmpty(tks)) uls.put(i++, "Main task/s");
            //System.out.println(uls.toString());
            Elements html_uls=doc.select("ul");
            i=0;
            for (Element ul :html_uls){
                 String target=uls.get(i);
                res=place(res,target,ul);
                i++;
            }
           
            //res.setTasks(tasks);
            
            //Get Competences
            i = 0;
            Elements cs = doc.select("h4");//the first h4 is not a comptence

            ArrayList<String> comps = new ArrayList<>();//all comptences are here but not the levels
            for (Element c : cs) {
                if (i != 0) {
                    comps.add(c.ownText());
                }
                i++;
            }//for

            

            res.setEcfs(foundLevels(comps, allTxt));
           
            //first p after first h3  h3:eq(0) + p
        } catch (IOException ex) {
            Logger.getLogger(ProfileHtmlReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    private ArrayList<ECFMap> foundLevels(ArrayList<String> limits, String allTxt)  {
  
        ArrayList<ECFMap> res = new ArrayList<>();
        String posterior = null;
        for (String f : limits) {

            if (posterior != null) {
                //System.out.println("Now="+f);
                res.addAll(this.getLevel(allTxt,posterior,f));

            }
            posterior = f;
        }
        res.addAll(this.getLevel(allTxt,posterior,null));

        return res;
    }

    private ArrayList<ECFMap> getLevel(String allTxt, String posterior, String f)                  {
        //throws java.lang.ArrayIndexOutOfBoundsException
        ArrayList<ECFMap> res = new ArrayList<>();
        String chunk ;
        if (f != null) {
            chunk = StringUtils.substringBetween(allTxt, posterior, f).trim();
        } else {
            chunk = StringUtils.substringAfter(allTxt, posterior).trim();
        }
        String prelevels = StringUtils.substringAfter(chunk, "Proficiency Levels");
        String[] levels = StringUtils.splitByWholeSeparator(prelevels, "Proficiency Level");
        //System.out.println("levels:\n"+levels.toString());
        int i = 0;
        while (i < levels.length) {
            if (levels[i].length() > 1) {
                String c3 = levels[i].substring(1, 2);
               HashMap <Integer,String> c0;
               c0=parseCompName(posterior);
                
                String c1=c0.get(0);
                String c2=c0.get(1);
                //System.out.println(c1+" "+c2+", level "+c3);
                ECFMap em=new ECFMap(c1,c2,c3);
                res.add(em);
                //System.out.println(levels[i] + "\n-->" + levels[i].substring(1, 2));
            }
            i++;
        }
        return res;
    }
    
    private HashMap <Integer,String> parseCompName(String comp){
       HashMap <Integer,String> res=new HashMap <Integer,String>();
        String[] parts=StringUtils.splitByWholeSeparator(comp,".");
        //System.out.println(comp);
        //System.out.println(parts.length);
              
        res.put(0,parts[0]+"."+parts[1]);
        int i=2;
         String p="";
        while (i<parts.length){
           p+=parts[i];
          
            i++;
        }
        res.put(1,p);
        
        return res;
    }

    private ArrayList<String> ul2array(Element list) {
        Elements llist = list.select("li");
        ArrayList<String> l = new ArrayList<>();
        for (Element li : llist) {
            l.add(li.ownText());
        }
        return l;
    }

    private ICTProfile place (ICTProfile ict, String quin, Element elem){
        ICTProfile ict2=ict;
        ArrayList lis=ul2array(elem);
       if (StringUtils.equals(quin,"Accountable")) ict.getMission().addAccount(lis);
       if (StringUtils.equals(quin,"Responsible")) ict.getMission().addRespon(lis);
       if (StringUtils.equals(quin,"Contributor")) ict.getMission().addContrib(lis);
       if (StringUtils.equals(quin,"Main task/s")) ict.setTasks(lis);      
        return ict2;
    }
    
    @Override
    public ArrayList<ICTProfile> readerDir(String dirname) {
       ArrayList<ICTProfile> profiles=new ArrayList<>();
        File dir = new File(dirname);
        
        File[] jobs=dir.listFiles();
         //System.out.println("jobs value="+Arrays.toString(jobs));
        for (File job:jobs){
            //System.out.println(job+", value="+job.toString());
            profiles.add(this.reader(job.toString()));
        }
        return profiles;
    }

}
