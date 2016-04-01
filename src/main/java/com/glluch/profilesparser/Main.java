package com.glluch.profilesparser;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Guillem LLuch Moll guillem72@gmail.com
 */
public class Main {
     public static void main(String[] args)  throws IOException{
  ProfileHtmlReader phr=new ProfileHtmlReader();
  //ICTProfile p=phr.reader("resources/ict_profiles/Enterprise Architect.html");
  //System.out.println(p.toString());  
  ArrayList <ICTProfile> ps=phr.readerDir("resources/ict_profiles");
   ;
   //System.out.println(Profile2IEEE.doit(p));
  for (ICTProfile pp:ps){
      System.out.println("\n"+pp.getTitle());  
      System.out.println(Profile2IEEE.doit(pp));
  }
 
  
        

    }
}
