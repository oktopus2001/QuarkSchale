package hdm.lab.quizapp;

import java.util.ArrayList;
import hdm.shared.Toolbox;

public class FragenLaden {
	
	String thema = "";
	ArrayList<Frage> liste = new ArrayList<Frage>();
	
	void laden(String pfad) {
		String text = Toolbox.readFile(pfad);
		String[] zeilen = text.split("\n");
		
		thema = zeilen[0];
		
		int z = 1;
		while (z < zeilen.length) {
			if (zeilen[z].trim().equals("")) {
				z++;
				continue;
			}
			
			Frage f = new Frage();
			f.text = zeilen[z];
			f.antworten[0] = zeilen[z+1];
			f.antworten[1] = zeilen[z+2];
			f.antworten[2] = zeilen[z+3];
			f.antworten[3] = zeilen[z+4];
			f.richtig = 0;
			
			liste.add(f);
			z = z + 5;
		}
	}
}
