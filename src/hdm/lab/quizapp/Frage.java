package hdm.lab.quizapp;

public class Frage {

	private String fragesatz;
	private String[] antworten;
	private int richtig;
	
	public Frage(String fragesatz, String[] antworten, int richtig) {
		
		this.fragesatz = fragesatz;
		this.antworten = antworten;
		this.richtig = richtig;
		
	}

	public String getQuestionText() {
		// TODO Auto-generated method stub
		return this.fragesatz;
	}

	public String getAnswer(int i) {
		// TODO Auto-generated method stub
		return this.antworten[i];
	}

	public int getCorrectAnswer() {
		// TODO Auto-generated method stub
		return this.richtig;
	}	
}
