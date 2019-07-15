/********************************/
/*         WORD COUNTER         */
/********************************/
/*    A program to count the    */ 
/*   frequencies of all words   */
/*    appear in a text file     */
/********************************/

package wordCounter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class WordCounter{
    private String fileName;
    private Map<String, Integer> wordMap;
    private ArrayList<ArrayList<String>> wordArrayList;
    private File fileSession;
    private Scanner fileReader;    
    private int maxCount;

    public WordCounter(String filePath) throws FileNotFoundException{
    	
    	/* Initialize all required sessions and variable. */
        this.fileSession = new File(filePath);
        this.fileReader = new Scanner(fileSession);
        this.wordMap = new HashMap<String, Integer>();
        this.wordArrayList = new ArrayList<ArrayList<String>>();
        this.fileName = fileSession.getName();        
        this.maxCount = 0;
        
        /* counting words in the current file as soon as an instance got created. */
        countWord();    

        this.fileReader.close();
    }

    
    private void countWord(){    	
        while(fileReader.hasNext()){        	
            String token = fileReader.next();       
            
            /* remove punctuation in each token and turn all characters to lowercase for case insensitive */
            token = token.replaceAll("\\pP", "").toLowerCase(); 
            if(!token.isEmpty()) {
	            if(this.wordMap.containsKey(token)){            	
	                Integer count = this.wordMap.get(token);
	                
	                /* keep tracking the highest frequency */
	                this.maxCount = Math.max(count+1, this.maxCount);                  
	                this.wordMap.put(token, count+1);
	            }else{
	                this.wordMap.put(token, 1);                
	            }
            }
        }        
	}
     
    
    public String getFileName(){return this.fileName;}

    
    /* this function convert the wordMap into an arraylist call wordArrayList */
    /* all words with the same appearing times will be collected to one arraylist of string, 
     * and added to wordArrayList where the index equals to the showing times of these words */
    public ArrayList<ArrayList<String>> getWordArrayList(){
    	/* initialize the capacity of wordArrayList as the number of the most frequently showed word in this file */
        for(int i = 0; i <= this.maxCount; i++) {        	
        	this.wordArrayList.add(i, new ArrayList<String>());        	
        }
        
        for(String key : this.wordMap.keySet()){
        	ArrayList<String> existedKeys = null;
            Integer wordCount = this.wordMap.get(key);
            existedKeys = this.wordArrayList.get(wordCount);        
            existedKeys.add(key);                        
        } 
        
        return this.wordArrayList;
    }
    
    public Map<String, Integer> getWordMap(){return this.wordMap;}
    
}