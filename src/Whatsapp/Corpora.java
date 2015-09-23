package Whatsapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Corpora {

	public final static String CorpusName="corpusWhatsapp.txt";
	public final static String CorpusSentences="corpusSentences.txt";
	public final static String CorpusWords="corpusWords.txt";
	private String allChatsName="joinedChats.txt";
	private String allChatsPath="";
	private String pathFiles="";
	private String finalFolder="chatsReviewed/";
	
	
	public Corpora(String Uri) {
		// TODO Auto-generated constructor stub
		String [] files=new File(Uri).list();
		File file=null;
	    this.pathFiles=Uri;
		BufferedReader reader=null;
		String lineRead="";
		PrintWriter writer=null;
		this.allChatsPath=Uri+allChatsName;
		
		try {
			writer=new PrintWriter(Uri+allChatsName,"UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for(int i=0;i<files.length;i++){
			
			this.pathFiles=Uri+files[i];
			file=new File(this.pathFiles);
			
			
			try {
			
				
				reader=new BufferedReader(new FileReader(file));
				
				while((lineRead=reader.readLine())!=null){
					
					if(lineRead.length()>1){
						
						if(lineRead.split("\\s").length>1)
						 writer.println(lineRead.toLowerCase());
						
						
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
		this.pathFiles=Uri;
		writer.close();
		mixUpCorpora();
		
	}
	
	
	@SuppressWarnings("resource")
	public void mixUpCorpora(){
		
		File file=new File(allChatsPath);
		String lineRead="";
		//ArrayList in Java has a get(int index) method.  int is a signed 32 bit value, with a maximum value of 2,147,483,647
		// If the arraylist overpasses this number I might have to create a new array
		List<String> allText=new ArrayList<String>();
		PrintWriter writer=null;
		
		
		try {
			
								
			BufferedReader reader=new BufferedReader(new FileReader(file));
			
			
			
			while((lineRead=reader.readLine())!=null){
				
				allText.add(lineRead);
				
				
			}
			
			Collections.shuffle(allText);	
			
			Iterator<String> iter=allText.iterator();
		
			writer=new PrintWriter(pathFiles+CorpusName,"UTF-8");
		
			while(iter.hasNext()){
		
				writer.println(iter.next());	
			
			}
			
			writer.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	
	public void splitSentences(){
		
		File file=new File(pathFiles+CorpusName);
		String lineRead=null;
		String[] words=null;
		
		
		try {
			
			PrintWriter writer=new PrintWriter(pathFiles+finalFolder+CorpusWords,"UTF-8");
			
			@SuppressWarnings("resource")
			BufferedReader reader=new BufferedReader(new FileReader(file));
			
			while((lineRead=reader.readLine())!=null){
				
				words=lineRead.split("\\s");
				
				for(String word:words){
				
					writer.println(word);
					 
					
				}
				writer.println();
				
			}
			
			writer.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
