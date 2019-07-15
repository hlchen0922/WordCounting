package wordCounter;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.Test;

public class JUnitTesting_CounterManager {

	@Test
	public void test() throws FileNotFoundException {
		String dirPath = Paths.get("").toAbsolutePath().toString();
		CounterManager manager = new CounterManager(dirPath + "\\src\\files");
		ArrayList<String> fileNames = manager.getAllFileName();
				
		assertEquals(fileNames.size(), 4);
		assertTrue(fileNames.contains("sample.txt"));
	}

}
