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
import java.util.logging.Logger;

/**
 *
 * @author Guillem LLuch Moll guillem72@gmail.com
 */
public class ICTProfile {

    private String title;
    private String summary;
    private Mission mission;
    private ArrayList<String> tasks;
    private String kpi;
    private ArrayList<ECFMap> ecfs=new ArrayList<>();

    @Override
    public String toString() {
        return "ICTProfile{" + "title=" + title + ",\n summary=" + summary + 
                ",\n mission=" + mission.toString() + ",\n tasks=" + tasks + 
                ",\n kpi=" + kpi + ",\n ecfs=" + ecfs.toString() + '}';
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
