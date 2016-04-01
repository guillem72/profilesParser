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

/**
 *
 * @author Guillem LLuch Moll guillem72@gmail.com
 */
public class ECFMap {
    
        private String code;
        private String text;
        private String level;
        private String allTxt;
        private ArrayList<String> ieee_terms;

    public ArrayList<String> getIeee_terms() {
        return ieee_terms;
    }

    public void setIeee_terms(ArrayList<String> ieee_terms) {
        this.ieee_terms = ieee_terms;
    }
    
        
        public String getAllTxt() {
        return allTxt;
    }

    public void setAllTxt(String allTxt) {
        this.allTxt = allTxt;
    }

        
        public ECFMap(String code, String text, String level) {
            this.code = code;
            this.text = text;
            this.level = level;
        }

    public ECFMap(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        String res= "ECFMap{" + "code=" + code + ", text=" + text + ", level=" + level + '}';
        return res;
    }

    public String toString(boolean ext){
        return toString();
    }
   
        
        

        
        
        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }
        
}
