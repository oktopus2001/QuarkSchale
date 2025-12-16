package hdm.lab.quizapp;

import java.util.Collections;
import java.util.ArrayList;

public class Frage {
	
	String text;
	String[] antworten = new String[4];
	int richtig = 0;
	
	public void mischen() {
		String richtigeAntwort = antworten[richtig];
		
		ArrayList<String> temp = new ArrayList<String>();
		temp.add(antworten[0]);
		temp.add(antworten[1]);
		temp.add(antworten[2]);
		temp.add(antworten[3]);
		Collections.shuffle(temp);
		
		for (int i = 0; i < 4; i++) {
			antworten[i] = temp.get(i);
			if (antworten[i].equals(richtigeAntwort)) {
				richtig = i;
			}
		}
	}
}
