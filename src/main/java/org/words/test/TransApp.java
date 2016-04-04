package org.words.test;

import org.apache.commons.beanutils.ConvertUtils;
import org.words.converter.GenericConverter;
import org.words.gui.WordMasterView;
import org.words.hbm.*;
import org.words.to.*;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TransApp {

    private JFrame frame;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        registerConverters();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new WordMaster().initUser();
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

    //<editor-fold desc="Converters">
    public static void registerConverters() {
        ConvertUtils.register(new GenericConverter<Word, WordTO>(Word.class, WordTO.class), WordTO.class);
        ConvertUtils.register(new GenericConverter<Sentence, SentenceTO>(Sentence.class, SentenceTO.class), SentenceTO.class);
        ConvertUtils.register(new GenericConverter<Plan, PlanTO>(Plan.class, PlanTO.class), PlanTO.class);
        ConvertUtils.register(new GenericConverter<Task, TaskTO>(Task.class, TaskTO.class), TaskTO.class);
        ConvertUtils.register(new GenericConverter<User, UserTO>(User.class, UserTO.class), UserTO.class);
        ConvertUtils.register(new GenericConverter<WordTO, Word>(WordTO.class, Word.class), Word.class);
        ConvertUtils.register(new GenericConverter<SentenceTO, Sentence>(SentenceTO.class, Sentence.class), Sentence.class);
        ConvertUtils.register(new GenericConverter<PlanTO, Plan>(PlanTO.class, Plan.class), Plan.class);
        ConvertUtils.register(new GenericConverter<TaskTO, Task>(TaskTO.class, Task.class), Task.class);
        ConvertUtils.register(new GenericConverter<UserTO, User>(UserTO.class, User.class), User.class);
    }
    //</editor-fold>

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

