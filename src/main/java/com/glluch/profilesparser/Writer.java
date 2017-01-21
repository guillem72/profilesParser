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
 * A class for write files
 *
 * @author Guillem LLuch Moll guillem72@gmail.com
 */
public class Writer {

    /**
     * The factor which the proper term will be multiplied
     */
    //TODO make static
    public double term_boost = 2.0;

    /**
     * For each profile, writes an xml file for add to Solr. TODO Make the read dir a var.
     * @param path The directory where the files will be written.
     * @throws IOException if can't read o write
     */
    public void profiles2SolrXML(String path) throws IOException {
        ProfileHtmlReader phr = new ProfileHtmlReader();
        ArrayList<ICTProfile> ps = phr.readerDir("resources/ict_profiles");

        for (ICTProfile pp : ps) {
            HashMap<String, Double> ieee = Profile2IEEE.fillTerms(pp);

            String xml = "<add><doc>" + System.lineSeparator();
            xml += "<field name=\"id\"";
            xml += ">" + System.lineSeparator();
            xml += pp.getTitle();
            xml += "</field>" + System.lineSeparator();
            xml += "<field name=\"type\">ICT_profile</field>" + System.lineSeparator();
            xml += terms2xml("term", ieee);
            xml += competences2xml(pp.getEcfs());
            xml += "</doc></add>";
            //competencesXMLsolr
            String fileTitle = pp.getTitle() + ".xml";
            FileUtils.writeStringToFile(new File(path + fileTitle), xml, "utf8");

        }

    }

    /**
     * Given a lists of code of competences, makes the piece of xml to write it.
     * @param ecfs A list of ECFMaps, all the competences levels in the profile.
     * @return An xml string which is the part representing the competences levels. 
     */
    protected String competences2xml(ArrayList<ECFMap> ecfs) {
        //competence_
        String res = "";
        for (ECFMap ecf : ecfs) {
            res += "<field name=\"competence_\" >"
                + ecf.getCode() + " " + ecf.getLevel() + "</field>" + System.lineSeparator();
        }
        return res;
    }

    /**
     * Prepare the terms for Solr's xml.
     * @param field_name The name of the Solr's field.
     * @param terms A hash map terms -&gt; counts to be transform as xml.
     * @return An xml string which is the part representing the terms.
     */
    protected String terms2xml(String field_name, HashMap<String, Double> terms) {
        String text = "";
        Set pterms = terms.keySet();
        for (Object t0 : pterms) {
            String t = (String) t0;
            text += "<field name=\"" + field_name + "\" "
                + " boost=\"" + term_boost * terms.get(t) + "\""
                + ">"
                + t + "</field>" + System.lineSeparator();
        }
        return text;
    }
}
