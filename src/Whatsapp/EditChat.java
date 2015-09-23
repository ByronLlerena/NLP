package Whatsapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditChat {
	
	public final static String nameCorpora="corpusWhatsapp.txt";
	private String pathFolder="";
	
	public void runEdition(String Uri){
		//Uri is the folder where all conversations are stored
		
		String files[]=new File(Uri).list();
		BufferedReader reader=null;
		this.pathFolder=Uri;
		String lineRead="";
		String lineEddited="";
		
		try {
			PrintWriter writer=new PrintWriter(pathFolder+nameCorpora);
			
			for(String nameFile:files){
				
				reader=new BufferedReader(new FileReader(this.pathFolder+nameFile));
		        
				while((lineRead=reader.readLine())!=null){
					
					
					lineEddited=removeInfo(lineRead);
					
					//Filtrate only sentences with more than a word
					
					
					
					
					
				}
				
				
				
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	    	
		
		
	}
	
	
	private String removeInfo(String line){
		
		if (hasInfo(line)){
			
			//int index=line.
			
		}else{
			
			
			return line;
		}
			
			
		
		
		return null;
		
	}
	
	private boolean hasInfo(String line){
		
		String regexDate="";
		String regexTime="";
		Pattern patternDate=Pattern.compile(regexDate);
		Pattern patternTime=Pattern.compile(regexTime);
		
		String[] words=line.split("\\s");
		Matcher matchedorDate=patternDate.matcher(words[0]);
		Matcher matcheadorTime=patternTime.matcher(words[1]);
		
		
		return matcheadorTime.find()&matchedorDate.find();
		
	}

}
