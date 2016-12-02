package org.words.test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.words.config.DbModule;
import org.words.config.ServiceModule;
import org.words.gui.WordMasterView;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Word App entry class, Swing GUI
 *
 * @author eric
 */
public class WordApp {

    private static Logger logger = LoggerFactory.getLogger(WordApp.class);

    private WordApp(){
        // empty constructor
    }
    /**
     * Launch the application.
     *
     * @param args command line args
     */
    @SuppressWarnings("Convert2Lambda")
    public static void main(String[] args) {
        JPanel jPanel = new WordMasterView();
        Injector injector = Guice.createInjector(new DbModule(), new ServiceModule());
        injector.injectMembers(jPanel);

        SwingUtilities.invokeLater(new Runnable() {//NOSONAR
            @Override
            public void run() {
                try {
                    JFrame frame = new JFrame("WordMaster");
                    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

                    frame.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                           jPanel.shutDown();
                        }
                    });
                    frame.add(jPanel);
                    frame.pack();
                    frame.setVisible(true);
                    frame.setLocation(250, 250);
                    frame.setSize(600, 250);
                } catch (Exception e) {
                    logger.error("error caught in main:{}", e);
                }
            }
        });
    }

}

