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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * A class for work with several aspects of an ICT profile.
 * @author Guillem LLuch Moll guillem72@gmail.com
 */
public class ICTProfile {

    private String title;
    private String summary;
    private Mission mission;
    private ArrayList<String> tasks;
    private String kpi;
    private ArrayList<ECFMap> ecfs=new ArrayList<>();
    protected HashMap <String,Double> pterms=new HashMap <>();
    protected HashMap <String, Double> rterms=new HashMap <>();
    
    /**
     * A way to get a friendly string for the competences of the profile.
     * @return A friendly string for the competences of the profile.
     */
    public String compentences(){
        return ecfs.toString();
    }
    
    @Override
    public String toString() {
        return "ICTProfile{" + "title=" + title + ",\n summary=" + summary + 
                ",\n mission=" + mission.toString() + ",\n tasks=" + tasks + 
                ",\n kpi=" + kpi + ",\n ecfs=" + ecfs.toString() + '}';
    }

    /**
     * Get the terms asociated with a profile without its counts.
     * @return the asociated terms.
     */
    public ArrayList <String> onlyTerms(){
        ArrayList <String> res=new ArrayList <>();
        res.addAll(pterms.keySet());
        return res;
    }
    
    
    public HashMap<String,Double> getPterms() {
        return pterms;
    }

    public void setPterms(HashMap<String, Double> pterms) {
        this.pterms = pterms;
    }
    
    /**
     * Transform the counts of the terms as in integers in a counts as doubles. 
     * @param pterms The map of terms to counts.
     */
    public void setPtermsI2D(HashMap<String, Integer> pterms) {
        Set keys=pterms.keySet();
        for(Object key0:keys){
            String key=(String)key0;
            this.pterms.put(key, NumberUtils.createDouble(pterms.get(key).toString()));
           
        }
        
    }

    public HashMap<String, Double> getRterms() {
        return rterms;
    }

    public void setRterms(HashMap<String,Double> rterms) {
        this.rterms = rterms;
    }

    /**
     * A pretty representation of the competences codes of the current profile. 
     * @return The list of competence codes.
     */
    public String getCompenteCodes(){
        String res="";
        for (ECFMap ecf:ecfs){
            res+=ecf.getCode()+" ";
        }
        return res.trim();
    }
    
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public ArrayList<String> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<String> tasks) {
        this.tasks = tasks;
        
    }

    public String getKpi() {
        return kpi;
    }

    public void setKpi(String kpi) {
        this.kpi = kpi;
    }

    public ArrayList<ECFMap> getEcfs() {
        return ecfs;
    }

    public void setEcfs(ArrayList<ECFMap> ecfs) {
        this.ecfs = ecfs;
    }

    /**
     * Build a string to show the tasks for the current profile.
     * @return The string representing the tasks or a string saying the title of
     * the profile and that there isn't any task.
     */
    public String plainTasks(){
            String res="";
            if (tasks!=null){
            for(String a:tasks){
                res+=" "+a;
            }}
            else {System.out.println(title+" doesn't have tasks!!");}
            return res;
        }
}
