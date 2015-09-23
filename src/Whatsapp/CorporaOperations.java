package Whatsapp;

public class CorporaOperations {


	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
         String EdditedChatsDirectory="/Users/byronllerena/Dropbox/NLP/Word Prediction/Whatsapp Conversations/conversaciones parte1/conversaciones editadas/";
          
        Corpora corpus=new Corpora(EdditedChatsDirectory);
        corpus.mixUpCorpora();
        corpus.splitSentences();
         
	}

}
