package Whatsapp;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditorTexto {

	private BufferedReader reader=null;
	private PrintWriter writer=null;
	
	public void runEdition(String UriOpen,String names,int type){
		File file=new File(UriOpen);
		String lineRead=null;
		String lineEditted=null;
		String outputName=UriOpen.substring(0, UriOpen.length()-4);
		try {
		   this.writer=new PrintWriter(outputName+"E.txt","UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
				
		try{
			
			this.reader=new BufferedReader(new FileReader(file)); 
		    
			while((lineRead=reader.readLine())!=null)
		    {
		    
				if(lineRead.length()>1){
					
					//Most of the words start with 2 letters in a chat conversation
					
					//FIRST I TRIM THE HOUR AND DATES
					lineEditted=this.eliminateDates(lineRead,names);
					//ELIMINATE WEBSITES,EMAILS,ETC..
					lineEditted=eliminateWeb(lineEditted);
					if(lineEditted.length()>1){
						//ELIMINATE SYMBOLS AND NUMBERS
						lineEditted=this.eliminateSymbols(lineEditted);
						if(lineEditted.length()>1){
							
							//ELIMINATE EMOJIS
							lineEditted=this.eliminateEmojis(lineEditted);
							
							if(lineEditted.length()>1){
								
								lineEditted=this.eliminateWhiteSpaces(lineEditted);
								
								if(lineEditted.length()>1){
									
									lineEditted=eliminateFirstSpace(lineEditted);
								} 
								
							}
							
							
						}
						
					}
					
					System.out.println("Linea Original:"+lineRead+"\n");
					
					
					if(lineEditted.length()>1){
						
						String[] lines=splitFinalLine(lineEditted);
						
						for(int i=0;i<lines.length;i++){
							writer.println(lines[i]);
							System.out.println("Linea Editada:"+lines[i]+"\n");
						}
					}
						
						
						
				}
		    	
		    }
			
			writer.close();
			
			
		}catch(FileNotFoundException e){
			
		}catch (IOException e) {
			// TODO: handle exception
		}finally {
			try{
				if(reader!=null){
					
					reader.close();
				}
				
			}catch(IOException e){
				
				
			}
			
			
		}

		
		
	}
	
	
	
	
	private String[] splitFinalLine(String line){
		
		String regex="\\s+(?=[A-Z])";
        String[] lines=line.split(regex);

		return lines;
	}
	
	private String eliminateFirstSpace(String line){
		
		
		Pattern patron=Pattern.compile("^[\\s]+");
		Matcher matcheador=patron.matcher(line);
		
		return matcheador.replaceAll("");
		
	
	}
	
	private String eliminateWhiteSpaces(String line){
		
		Pattern patron=Pattern.compile("[\\s]{2,}");
		Matcher matcheador=patron.matcher(line);
		
		return matcheador.replaceAll(" ");
		
	}
	
	private String eliminateWeb(String line){
		
		
		String newLine=null;
		String regex="[\\S]+://[\\S]+|www[\\S]+com|<[\\S]+[\\s]+[\\S]+>|[\\S]+@[\\S]+.com";
		
		Pattern patron=Pattern.compile(regex);
		Matcher matcheador=patron.matcher(line);
		newLine=matcheador.replaceAll("");
		
		
		return newLine;
	}

	
	private String eliminateSymbols(String line){
		
		//Eliminates numbers, symbols, anything that is not a letter in any language
		
        Boolean fin=false;
        String regex="\\b((0?[1-9]|1[012])([:.h][0-5][0-9])?(\\s?[ap]m)|([01]?[0-9]|2[0-3])([:.h][0-5][0-9]))\\b";
        List<String>  t=new ArrayList<String>();
        int b=0;
        String newLine="" ;       
		Pattern patron=Pattern.compile(regex);
			
		Matcher matcheador=patron.matcher(line);
			
		
		while(matcheador.find()){
			fin=true;
	        t.add(line.substring(b, matcheador.start()).replaceAll("[^\\p{L}\\p{Z}]",""));
	        t.add(line.substring(matcheador.start(),matcheador.end()) );
	        b=matcheador.end();
		}
		
        t.add(line.substring(b,line.length()).replaceAll("[^\\p{L}\\p{Z}]",""));
		
		for (String ch : t)
		{
		    newLine += ch + " ";
		}

		if(!fin)
			newLine=line.replaceAll("[^\\p{L}\\p{Z}]","");

			
		return newLine;
	}
	
	private String eliminateDates(String line,String regex){
		
		byte[] utf8=null;
		String line_utf8=null;
		String newLine=line;
		
		
		try {
		   utf8 = line.getBytes("UTF-8");
		   line_utf8 = new String(utf8,"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		};
	    
		   
	    Pattern pattern=Pattern.compile(regex);	  
	    Matcher matcheador=pattern.matcher(line_utf8);
		
	    if(matcheador.find()){

		    int start=matcheador.end();
		    newLine=line.substring(start+1);
			    	
	    }
		
		return newLine;
	}
	
	private String eliminateEmojis(String line){
		
		
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
	
	
	
	
	
	
}
