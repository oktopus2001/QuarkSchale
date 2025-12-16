package hdm.lab.quizapp;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class QuizPanel extends JPanel implements ActionListener {
	
	ArrayList<Frage> fragen;
	int nummer = 0;
	int punkte = 0;
	long start;
	
	int zeit = 50; // 5 sekunden = 50 x 100ms
	Timer ticker;
	Timer warte;
	
	JLabel obenLabel;
	JLabel frageLabel;
	JButton btn1, btn2, btn3, btn4;
	JPanel kreisePanel;
	JProgressBar timerBar;
	
	public QuizPanel(ArrayList<Frage> fragen, String thema) {
		this.fragen = fragen;
		
		setLayout(new BorderLayout(10, 10));
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
		
		// oben: thema
		obenLabel = new JLabel(thema, JLabel.CENTER);
		obenLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
		add(obenLabel, BorderLayout.NORTH);
		
		// mitte
		JPanel mitte = new JPanel();
		mitte.setLayout(new BoxLayout(mitte, BoxLayout.Y_AXIS));
		mitte.setBackground(Color.WHITE);
		
		// kreise fuer fortschritt
		kreisePanel = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				int x = 100;
				for (int i = 0; i < 10; i++) {
					if (i < punkte) {
						g.setColor(new Color(100, 200, 100));
					} else {
						g.setColor(new Color(200, 200, 200));
					}
					g.fillOval(x, 5, 30, 30);
					x = x + 40;
				}
			}
		};
		kreisePanel.setPreferredSize(new Dimension(600, 40));
		kreisePanel.setMaximumSize(new Dimension(600, 40));
		kreisePanel.setBackground(Color.WHITE);
		mitte.add(kreisePanel);
		
		mitte.add(Box.createVerticalStrut(20));
		
		// timer balken
		timerBar = new JProgressBar(0, 50);
		timerBar.setValue(50);
		timerBar.setPreferredSize(new Dimension(500, 25));
		timerBar.setMaximumSize(new Dimension(500, 25));
		mitte.add(timerBar);
		
		mitte.add(Box.createVerticalStrut(40));
		
		// frage
		frageLabel = new JLabel("", JLabel.CENTER);
		frageLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		frageLabel.setAlignmentX(CENTER_ALIGNMENT);
		mitte.add(frageLabel);
		
		add(mitte, BorderLayout.CENTER);
		
		// unten: 4 buttons
		JPanel unten = new JPanel(new GridLayout(2, 2, 20, 20));
		unten.setBackground(Color.WHITE);
		unten.setPreferredSize(new Dimension(800, 250));
		
		btn1 = new JButton(); btn1.addActionListener(this);
		btn2 = new JButton(); btn2.addActionListener(this);
		btn3 = new JButton(); btn3.addActionListener(this);
		btn4 = new JButton(); btn4.addActionListener(this);
		
		btn1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		btn2.setFont(new Font("SansSerif", Font.PLAIN, 16));
		btn3.setFont(new Font("SansSerif", Font.PLAIN, 16));
		btn4.setFont(new Font("SansSerif", Font.PLAIN, 16));
		
		unten.add(btn1);
		unten.add(btn2);
		unten.add(btn3);
		unten.add(btn4);
		
		add(unten, BorderLayout.SOUTH);
		
		// timer
		ticker = new Timer(100, e -> tick());
		warte = new Timer(2000, e -> weiter());
		warte.setRepeats(false);
		
		start = System.currentTimeMillis();
		zeigeFrage();
	}
	
	void zeigeFrage() {
		Frage f = fragen.get(nummer);
		f.mischen();
		
		frageLabel.setText("<html><center>" + f.text + "</center></html>");
		
		btn1.setText(f.antworten[0]); btn1.setBackground(null); btn1.setEnabled(true);
		btn2.setText(f.antworten[1]); btn2.setBackground(null); btn2.setEnabled(true);
		btn3.setText(f.antworten[2]); btn3.setBackground(null); btn3.setEnabled(true);
		btn4.setText(f.antworten[3]); btn4.setBackground(null); btn4.setEnabled(true);
		
		zeit = 50;
		timerBar.setValue(50);
		ticker.start();
		
		kreisePanel.repaint();
	}
	
	void tick() {
		zeit--;
		timerBar.setValue(zeit);
		
		if (zeit <= 0) {
			ticker.stop();
			
			// zeit abgelaufen = falsch
			btn1.setEnabled(false);
			btn2.setEnabled(false);
			btn3.setEnabled(false);
			btn4.setEnabled(false);
			
			Frage f = fragen.get(nummer);
			getButton(f.richtig).setBackground(Color.GREEN);
			
			warte = new Timer(2000, e -> zeigeEnde(false));
			warte.setRepeats(false);
			warte.start();
		}
	}
	
	JButton getButton(int i) {
		if (i == 0) return btn1;
		if (i == 1) return btn2;
		if (i == 2) return btn3;
		return btn4;
	}
	
	int getIndex(JButton b) {
		if (b == btn1) return 0;
		if (b == btn2) return 1;
		if (b == btn3) return 2;
		return 3;
	}
	
	public void actionPerformed(ActionEvent e) {
		ticker.stop();
		
		JButton geklickt = (JButton) e.getSource();
		int index = getIndex(geklickt);
		
		btn1.setEnabled(false);
		btn2.setEnabled(false);
		btn3.setEnabled(false);
		btn4.setEnabled(false);
		
		Frage f = fragen.get(nummer);
		getButton(f.richtig).setBackground(Color.GREEN);
		
		if (index == f.richtig) {
			// richtig
			punkte++;
			kreisePanel.repaint();
			
			if (nummer >= 9) {
				warte = new Timer(2000, ev -> zeigeEnde(true));
				warte.setRepeats(false);
				warte.start();
			} else {
				warte.start();
			}
		} else {
			// falsch
			geklickt.setBackground(Color.RED);
			warte = new Timer(2000, ev -> zeigeEnde(false));
			warte.setRepeats(false);
			warte.start();
		}
	}
	
	void weiter() {
		nummer++;
		zeigeFrage();
	}
	
	void zeigeEnde(boolean gewonnen) {
		long dauer = System.currentTimeMillis() - start;
		int sek = (int)(dauer / 1000);
		
		removeAll();
		setLayout(new GridBagLayout());
		
		JPanel box = new JPanel();
		box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
		box.setBackground(Color.WHITE);
		
		JLabel titel = new JLabel(gewonnen ? "Gratulation!" : "Spiel beendet");
		titel.setFont(new Font("SansSerif", Font.BOLD, 36));
		titel.setAlignmentX(CENTER_ALIGNMENT);
		box.add(titel);
		
		box.add(Box.createVerticalStrut(30));
		
		JLabel info = new JLabel(punkte + " von 10 Fragen richtig");
		info.setFont(new Font("SansSerif", Font.PLAIN, 22));
		info.setAlignmentX(CENTER_ALIGNMENT);
		box.add(info);
		
		box.add(Box.createVerticalStrut(15));
		
		JLabel zeitLabel = new JLabel("Zeit: " + sek + " Sekunden");
		zeitLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
		zeitLabel.setAlignmentX(CENTER_ALIGNMENT);
		box.add(zeitLabel);
		
		box.add(Box.createVerticalStrut(40));
		
		JButton nochmal = new JButton("Nochmal spielen");
		nochmal.setAlignmentX(CENTER_ALIGNMENT);
		nochmal.addActionListener(e -> neustart());
		box.add(nochmal);
		
		add(box);
		revalidate();
		repaint();
	}
	
	void neustart() {
		nummer = 0;
		punkte = 0;
		start = System.currentTimeMillis();
		
		// fenster neu laden
		JFrame fenster = (JFrame) SwingUtilities.getWindowAncestor(this);
		fenster.dispose();
		new QuizFenster();
	}
}
