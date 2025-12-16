package hdm.lab.quizapp;

import javax.swing.JFrame;

public class QuizFenster extends JFrame {
	
	public QuizFenster() {
		setTitle("Quiz");
		setSize(1200, 800);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		FragenLaden fl = new FragenLaden();
		fl.laden("src/hdm/lab/quizapp/questions_example.txt");
		
		setTitle("Quiz: " + fl.thema);
		
		add(new QuizPanel(fl.liste, fl.thema));
		
		setVisible(true);
	}
}
