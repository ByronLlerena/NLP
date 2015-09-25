package Whatsapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditChat {
	
	private final String nameSentenceCorpora="corpusSentencesWhatsapp.txt";
	private final String nameWordCorpora="corpusWordWhatsapp.txt";
	private String pathFolder="";
	private PrintWriter writerWordCorpus;
	private PrintWriter writerSentenceCorpus;
	
	public void runEdition(String Uri){
		//Uri is the folder where all conversations are stored
		
		String files[]=new File(Uri).list();
		BufferedReader reader=null;
		this.pathFolder=Uri;
		String lineRead="";
		String lineEddited="";
		
		try {
			this.writerSentenceCorpus=new PrintWriter(this.pathFolder+this.nameSentenceCorpora,"UTF-8");
			this.writerWordCorpus=new PrintWriter(this.pathFolder+this.nameWordCorpora,"UTF-8");
				
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			
			
			for(String nameFile:files){
				
				reader=new BufferedReader(new FileReader(this.pathFolder+nameFile));
		        
				while((lineRead=reader.readLine())!=null){
					
					
					lineEddited=removeInfo(lineRead);
					
					//Filtrate only sentences with more than a word
					
					if(isValid(lineEddited)){
						
						lineEddited=removeSymbols(lineEddited);
						lineEddited=removeEmojis(lineEddited);
						lineEddited=removeExtraWhiteSpaces(lineEddited);
						lineEddited=removeFirstWhiteSpace(lineEddited);
						
						
						if(isUsefulData(lineEddited)){
							String[] lines=splitLineInCapital(lineEddited);
							
							for(int i=0;i<lines.length;i++){
								sentenceCorpora(lines[i]);
								wordCorpora(lines[i]);
									
							}
							
							
						}
					}
					
					
					
					
				}
				reader.close();
				
				
			}
			
			this.writerSentenceCorpus.close();
			this.writerWordCorpus.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			try{
				if(reader!=null){
					
					reader.close();
				}
				
			}catch(IOException e){
				
				
			}
		}
		
		
	    	
		
		
	}
	
	
	private String removeInfo(String line){
		
		/*There are different type of hours and date formats at the begining of each
		 * sentecne which is why after analyzing them these are the formats found and their
		 * different combinations:
		 *   
		 *   DATES
		 *   =====
		 *   
		 *   1D) Date type 1: 24/7/15 
		 *   in format dd/mm/year, excluding the zero number for months and days
		 *   and the 20 in 2015
		 *   
		 *   2D) Date type 2: 07/08/2015,
		 *   in the format dd/mm/year it makes use of the number zero
		 *   and besides of using 20 in 2015 it ends with a comma
		 *   
		 *   3D) Date type 3: 09/14/15 
		 *   is the same as number 2 but it does not use the comma
		 *   
		 *   TIMES
		 *   =====
		 *   
		 *   1T) Time type 1: 14:18:46:
		 *                     8:39:39:
		 *       it does not make use of the zero number in the hours
		 *       but it does in the minutes and seconds
		 *   
		 *   2T)  9:29 AM - 
		 *       12:27 PM -  
		 *       
		 *   3T) 11:37 -
		 *        9:45 -
		 *        
		 *   4T) 8:52 PM: 
		 *   
		 *   COMBINATIONS
		 *   ============
		 *   
		 *   1D+1T
		 *   2D+2T
		 *   2D+3T
		 *   3D+4T
		 *   
		 *   Based on this combinations 4 regex are created
		 *   regex1=>1d+1H,3D+4H
		 *   regex2=>2D+2H,2D+3h
		 *   
		 *   
		 *   
		 *   */
		
			String regex1="(\\w+/\\w+/\\w+)\\s(\\w+:\\w+).+:";
			String regex2="(\\w+/\\w+/\\w+),\\s(\\w+:\\w+).+:";
			String regexComplete=regex1+"|"+regex2;
			
			Matcher matcheador=Pattern.compile(regexComplete).matcher(line);
			
			
		if (matcheador.find()){
			//If the line has info it will remove it, otherwise it will not edit it at all
			
			return matcheador.replaceAll("");
			
		}else{
			
			return line;
		}	
			
		
		
		 
		
	}
   
	private boolean isValid(String line){
		
		//It returns true if any number is present or email , website,etc.. It is done because if we take this off sentences tend to loose syntax 
		
		String newLine=null;
		String regex="[\\S]+://[\\S]+|www[\\S]+com|<[\\S]+[\\s]+[\\S]+>|[\\S]+@[\\S]+.com|[0123456789]";
		
		Pattern patron=Pattern.compile(regex);
		Matcher matcheador=patron.matcher(line);
		
		return !matcheador.find();
	}
	
	private String removeSymbols(String line){
	
		
		return line.replaceAll("[^\\p{L}\\p{Z}]","");
		
	}
	
   private String removeEmojis(String line){
		
		
		byte[] utf8=null;
		String line_utf8=null;
		
		try {
		   utf8 = line.getBytes("UTF-8");
		   line_utf8 = new String(utf8,"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		};
	    
		
	    String regex="[\uD83C-\uDBFF\uDC00-\uDFFF]+";   
	    Pattern pattern=Pattern.compile(regex);	  
	    Matcher matcheador=pattern.matcher(line_utf8);
	    
	    return matcheador.replaceAll("");
	
		
	}
	
   private String removeFirstWhiteSpace(String line){
		
		
		Pattern patron=Pattern.compile("^[\\s]+");
		Matcher matcheador=patron.matcher(line);
		
		return matcheador.replaceAll("");
		
	
	}
	
	private String removeExtraWhiteSpaces(String line){
		
		Pattern patron=Pattern.compile("[\\s]{2,}");
		Matcher matcheador=patron.matcher(line);
		
		return matcheador.replaceAll(" ");
		
	}
	
	private String[] splitLineInCapital(String line){
		
		String regex="\\s+(?=[A-Z])";
        String[] lines=line.split(regex);

		return lines;
	}
	
	private boolean isUsefulData(String line){
		/*It is a useful data if its length is > 1 and it is more than a word
		 * */
		
	 return line.split("\\s").length>1&&line.length()>1;
	}
	
	private void sentenceCorpora(String line){
		
	
			this.writerSentenceCorpus.println(line.toLowerCase());
			
	}
	
	private void closeWriterSentence(){
		
		this.writerSentenceCorpus.close();
	}
	
	private void closeWriterWord(){
		this.writerWordCorpus.close();
	}
	
	private void wordCorpora(String line){
		
		String[] words=line.split("\\s");
		
					
		for(String word:words){
		
			this.writerWordCorpus.println(word);
			 
			
		}
		this.writerWordCorpus.println();
		
	}
	

}
