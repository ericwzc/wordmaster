package org.words.test;

import org.words.gui.WordMasterView;
import org.words.utils.SentenceTmp;

import javax.swing.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class TransApp {

	private JFrame frame;
	private ObjectInputStream ois = null;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
                    new WordMaster("");
//					TransApp window = new TransApp("./words.txt");
//					window.frame.setVisible(true);
                    JFrame frame = new JFrame("WordMaster");
                    frame.setDefaultCloseOperation(2);
                    JPanel jPanel = new WordMasterView();
                    frame.add(jPanel);
                    frame.pack();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public TransApp(String fileName) throws FileNotFoundException, IOException {
		new WordMaster(fileName);
		ois =  new ObjectInputStream(new FileInputStream("./sentences.txt"));
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Translation");
		frame.setBounds(100, 100, 400, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel originalLabel = new JLabel("OriginalArea:");
		originalLabel.setBounds(30, 5, 90, 20);	
		frame.getContentPane().add(originalLabel);
		
		final JTextArea originalArea = new JTextArea();
		originalArea.setBounds(20, 25, 340, 80);
		originalArea.setLineWrap(true);		//change line auto 
		originalArea.setWrapStyleWord(true);		//make word completely
		frame.getContentPane().add(originalArea);
		
		JLabel translatedLabel = new JLabel("TranslatedArea:");
		translatedLabel.setBounds(30, 115, 100, 20);
		frame.getContentPane().add(translatedLabel);
		
		final JTextArea translatedArea = new JTextArea();
		translatedArea.setBounds(20, 135, 340, 80);
		translatedArea.setLineWrap(true);
		translatedArea.setWrapStyleWord(true);
		frame.getContentPane().add(translatedArea);
			
		JButton beforeButton = new JButton("<");
		beforeButton.setBounds(195, 226, 45, 25);
		frame.getContentPane().add(beforeButton);
		
		final JButton startButton = new JButton("Start");
		startButton.setBounds(245, 226, 65, 25);
		frame.getContentPane().add(startButton);
		
		JButton nextButton = new JButton(">");
		nextButton.setBounds(315, 226, 45, 25);
		frame.getContentPane().add(nextButton);

		beforeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		
		startButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(startButton.getText().equals("Start")) {
					startButton.setText("Stop");					
					SentenceTmp sentence;
					try {
						sentence = (SentenceTmp)ois.readObject();
						originalArea.setText(sentence.getOriginal());
						translatedArea.setText(sentence.getTranslate());
						
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
					
				} else {
					startButton.setText("Start");
					originalArea.setText("");
					translatedArea.setText("");
				}
				
			}
		});
		
		nextButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		
	}
	
}

