import java.io.FileNotFoundException;
import java.util.ArrayList;

import wordCounter.CounterManager;;

public class Run_Example{
	
	public static void main(String[] args) {
		int paramLength = args.length;
		CounterManager cm = null;
		
		//expecting user specify a directory as a command line argument
		//if no directory specified, report error.		
		if(paramLength == 0) {
			System.out.println("No directory specified.");
			return;
		}else {			
			try {
				if(paramLength == 1) {cm = new CounterManager(args[0]);}
				
				//if more than 1 argument assigned, the second one is expected as the word number to report.
				else {cm = new CounterManager(args[0], Integer.parseInt(args[1]));}
				
				ArrayList<String> fileNames = cm.getAllFileName();
				for(String f : fileNames) {cm.printHotWordListPerFile(f);}
				
				cm.printHotWordListPerSystem();
				
			}catch(FileNotFoundException e) {
				System.out.println("Invalid direcotry.");
			}
		}		
	}
}
