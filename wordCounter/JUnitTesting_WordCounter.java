package wordCounter;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

import org.junit.Test;

public class JUnitTesting_WordCounter {

	@Test
	public void test() throws FileNotFoundException {
		String filePath = Paths.get("").toAbsolutePath().toString();
		WordCounter counter = new WordCounter(filePath + "\\src\\files\\\\sub\\subsub\\sample.txt");
		Map<String, Integer> wordMap = counter.getWordMap();
		ArrayList<ArrayList<String>> wordArrayList = counter.getWordArrayList();
		
		int numAstilbe = wordMap.get("astilbe");
		int numLotus = wordMap.get("lotus");		
		
		assertEquals(counter.getFileName(), "sample.txt");
		assertEquals(numAstilbe, 3);
		assertEquals(numLotus, 5);
		assertEquals(wordArrayList.get(1).size(), 1);
		assertEquals(wordArrayList.get(1).get(0), "buddleja");
	}

}
