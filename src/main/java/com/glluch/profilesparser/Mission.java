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
import java.util.List;

/**
 *
 * @author Guillem LLuch Moll guillem72@gmail.com
 */
public class Mission {
        private String text;
       
       
        private ArrayList <String> account;
        private ArrayList <String> respon;
        private ArrayList <String> contrib;

        public Mission(String text) {
            this.text = text;
            account=new ArrayList<>();
            respon=new ArrayList<>();
            contrib=new ArrayList<>();
            
            
        }

    @Override
    public String toString() {
        return "Mission{" + "\n\ttext=" + text +  
                ",\n\t account=" + account + 
                ",\n\t respon=" + respon + ",\n\t contrib=" + contrib + '}';
    }
        
        
        public String plainString(){
        String res=text;
        res+=plainAccount()+plainRespon()+plainContrib();
        return res;
        }
        
        public void addAccount(List <String> txt){
            account.addAll(txt);
        }
        
        public void addAccount(String txt){
            account.add(txt);
        }
        
        public void addRespon(String txt){
            respon.add(txt);            
        }
        public void addRespon(List <String> txt){
            respon.addAll(txt);
        }
        
        public void addContrib(String txt){
            contrib.add(txt);
        }
        public void addContrib(List <String> txt){
            contrib.addAll(txt);
        }
        
        public String getText() {
            return text;
          
        }

        public void setText(String text) {
            this.text = text;
        }
        
        public String plainAccount(){
            String res="";
            for(String a:account){
                res+=" "+a;
            }
            return res;
        }
        
        public String plainRespon(){
            String res="";
            for(String a:respon){
                res+=" "+a;
            }
            return res;
        }
        
        public String plainContrib(){
            String res="";
            for(String a:contrib){
                res+=" "+a;
            }
            return res;
        }
       
}
