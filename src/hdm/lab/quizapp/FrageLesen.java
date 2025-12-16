package hdm.lab.quizapp;

import java.util.ArrayList;
import java.util.List;

import hdm.shared.Toolbox;

public class FrageLesen {

    public String theme = "";
    public List<Frage> questions = new ArrayList<>();

    public void load(String filepath) {
        String content = Toolbox.readFile(filepath);

        if (content == null) {
            System.out.println("Fehler: Datei konnte nicht gelesen werden.");
            return;
        }

        String[] lines = content.split("\n");

        // Erste Zeile = Thema
        theme = lines[0].trim();

        // Fragen einlesen (ab Zeile 2)
        int i = 1;
        while (i < lines.length) {
            // Leere Zeilen ueberspringen
            if (lines[i].trim().isEmpty()) {
                i++;
                continue;
            }

            // Frage + 4 Antworten
            String questionText = lines[i].trim();
            String[] answers = new String[4];
            for (int j = 0; j < 4; j++) {
                answers[j] = lines[i + 1 + j].trim();
            }

            // Erste Antwort ist immer korrekt (Index 0)
            questions.add(new Frage(questionText, answers, 0));

            i += 5; // Naechste Frage
        }
    }
}