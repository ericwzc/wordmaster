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
                    new WordMaster();
                    JFrame frame = new JFrame("WordMaster");
                    frame.setDefaultCloseOperation(2);
                    JPanel jPanel = new WordMasterView();
                    frame.add(jPanel);
                    frame.pack();
                    frame.setVisible(true);
                } catch (Exception e) {
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

