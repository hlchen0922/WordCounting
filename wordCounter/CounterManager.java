/********************************/
/*       COUNTER MANAGER        */
/********************************/
/*    A program to handle a     */ 
/*  system level word counting. */
/*                              */
/*  Manage multiple WordCounter */
/* objects that deal with files */
/*      in the same folder,     */
/*   and print the results in   */
/*     file or folder base.     */
/********************************/

package wordCounter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.*;

public class CounterManager {
	private ArrayList<WordCounter> counters;
	private Map<String, Integer> sysWordMap;
	private File fileProcessor;	
	private int limit;	
	
	/* two constructors allowing users to specify (or not) 
	 * how many top frequently showed words they'd like this program to report.
	 * the default number is 10.*/
	public CounterManager(String path, int limit) throws FileNotFoundException {
		this.counters = new ArrayList<WordCounter>();
		this.sysWordMap = new HashMap<String, Integer>();
		this.fileProcessor = new File(path);		
		this.limit = limit;
		
		this.collectAllFiles(this.fileProcessor);
	}
	
	public CounterManager(String path) throws FileNotFoundException {
		this.counters = new ArrayList<WordCounter>();
		this.sysWordMap = new HashMap<String, Integer>();
		this.fileProcessor = new File(path);		
		this.limit = 10;
		
		this.collectAllFiles(this.fileProcessor);
	}
	
	
	/* check all files in the specified directory.
	 * create one WordCounter object per file if the file is readable.
	 * recursively checking sub-folder if any */
	private void collectAllFiles(File fSession) {
		if(fSession.isDirectory()) {
			for(File f : fSession.listFiles()) {
				collectAllFiles(f);
			}			
		}else {
			try {
				if(fSession.canRead()) {					
					this.counters.add(new WordCounter(fSession.getAbsolutePath()));
				}
			}catch(FileNotFoundException e) {
				System.out.println(fSession.getAbsolutePath() + " is not found.");
			}
		}		
	}
	
	
	public ArrayList<String> getAllFileName() {
		ArrayList<String> allFileName = new ArrayList<String>();
		for(WordCounter counter : this.counters) {
			allFileName.add(counter.getFileName());
		}
		return allFileName;
	}
	
	
	private WordCounter getCounterByName(String fileName) {
		for(WordCounter counter : this.counters) {
			if(counter.getFileName().equals(fileName)) {return counter;}
		}
		return null;
	}
	
	
	/* print out # words that most frequently showed in a specific file */
	/* the words with the same appearing times will all be listed if its previous round does not reach the limit number.
	 * so the final list could possibly be longer than the specified limit number.*/
	public void printHotWordListPerFile(String fileName) {
		WordCounter counter = this.getCounterByName(fileName);
		ArrayList<ArrayList<String>> wordListPerFile = counter.getWordArrayList();
		int numberOfPrinted = 0;
		
		System.out.println("File: " + counter.getFileName() + ":");
		for(int i = wordListPerFile.size()-1; i > 0 && numberOfPrinted < this.limit; i--) {
			if(wordListPerFile.get(i).size() != 0) {
				for(String word : wordListPerFile.get(i)) {
					System.out.println(word + ": " + i);
					numberOfPrinted++;					
				}				
			}
		}		
		System.out.println("***************");
	}
	
	
	/* print out # words that most frequently showed in all files of current folder */	
	public void printHotWordListPerSystem() {
		
		/*summarize all appearing words and accumulate their numbers in current directory*/
		for(WordCounter counter : counters) {
			Map<String, Integer> counterMap = counter.getWordMap();
			for(String key : counterMap.keySet()) {
				if(this.sysWordMap.containsKey(key)) {
					Integer sum = this.sysWordMap.get(key) + counterMap.get(key);
					this.sysWordMap.put(key, sum);
				}else {
					this.sysWordMap.put(key, counterMap.get(key));
				}
			}
		}
		
		/* sorted map by value with entrySet and stream */
		Map<String, Integer> sortedSysWordMap = this.sysWordMap
										  .entrySet()
										  .stream()
										  .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
										  .collect(
												  toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
												  LinkedHashMap::new)
												  );
		
		/* print # of the most often showed words*/
		int listedNumber = 0;
		Set<String> keys = sortedSysWordMap.keySet();	
		System.out.println("All files in current folder: ");
		for(String key : keys) {
			System.out.println(key + ": " + sortedSysWordMap.get(key));
			listedNumber++;
			if(listedNumber == this.limit) {
				System.out.println("***************");
				return;
			}
		}
		System.out.println("***************");
	}	
}