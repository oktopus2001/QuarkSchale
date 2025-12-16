package hdm.lab.quizapp;



public class QuizApp {

	public static void main(String[] args) {
		// QuestionLoader testen
        FrageLesen loader = new FrageLesen();
        loader.load("src/hdm/lab/quizapp/questions_example.txt");

        System.out.println("Thema: " + loader.theme);
        System.out.println("Anzahl Fragen: " + loader.questions.size());
        System.out.println();

        // Alle Fragen ausgeben
        for (int i = 0; i < loader.questions.size(); i++) {
            Frage q = loader.questions.get(i);
            System.out.println("Frage " + (i + 1) + ": " + q.getQuestionText());
            System.out.println("  A: " + q.getAnswer(0));
            System.out.println("  B: " + q.getAnswer(1));
            System.out.println("  C: " + q.getAnswer(2));
            System.out.println("  D: " + q.getAnswer(3));
            System.out.println("  Korrekt: " + q.getCorrectAnswer());
            System.out.println();
        }

	}

}
