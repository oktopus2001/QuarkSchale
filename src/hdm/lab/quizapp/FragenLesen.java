package hdm.lab.quizapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class FragenLesen {
	File file = new File("src/hdm/lab/quizapp/questions_example.txt");
	
	private static String readFromFile(File file) {
		String output = "";

		Reader in = null;
		try {
			in = new InputStreamReader(new FileInputStream(file), "utf-8");
			boolean endOfFileReached = false;
			// Lese ein Zeichen ein, solange bis das Ende der Datei erreicht wird
			while (!endOfFileReached) {
				int i = in.read();
				// Ueberpruefe ob Ende der Datei erreich wurde
				if (i == -1) {
					endOfFileReached = true;
				} else {
					output += (char) i;
				}
			}
		} catch (IOException e) {
			System.out.println("Fehler beim Lesen aus Datei: " + e);
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				System.out.println("Fehler beim Schließen der Datei: " + e);
			}
		}

		return output;
	}
	
}
