package org.words.test;

import org.words.gui.WordMasterView;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TransApp {

    private JFrame frame;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
//                    WordMaster wm = new WordMaster();
//                    wm.initWordsSentences();
//                    wm.initUser();
//                    wm.initTasks();
                    JFrame frame = new JFrame("WordMaster");
                    frame.setDefaultCloseOperation(3);
                    JPanel jPanel = new WordMasterView();
                    frame.add(jPanel);
                    frame.pack();
                    frame.setVisible(true);
                    frame.setLocation(250, 250);
                    frame.setSize(600, 250);
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        });
    }

    /**
     * Create the application.
     *
     * @throws IOException
     * @throws FileNotFoundException
     */
    public TransApp() throws FileNotFoundException, IOException {
        new WordMaster();
    }
}

