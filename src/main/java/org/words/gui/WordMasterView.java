/*
 * Created by JFormDesigner on Sat Mar 19 19:43:21 CST 2016
 */

package org.words.gui;

import com.google.inject.Inject;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.ELProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.words.service.StudyService;
import org.words.to.SentenceTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

/**
 * View class
 *
 * @author Eric Wang
 */
@SuppressWarnings({"UnusedParameters", "Convert2Lambda", "Convert2MethodRef", "FieldCanBeLocal", "squid:S1612", "squid:S1213", "squid:S1948", "squid:S1199", "squid:S1192"})
public class WordMasterView extends JPanel {
    // class data
    private static final String LOADING_PLEASE_WAIT = "Loading, please wait...";
    private static final String NOTHING_TO_LEARN_REVIEW = "Nothing to learn/review!";
    public static final String SEGOE_UI = "Segoe UI";

    // instance data
    @Inject
    private StudyService studyService;

    private transient Logger logger = LoggerFactory.getLogger(WordMasterView.class);

    private transient SentenceTO sentenceTO = new SentenceTO();
    private transient LinkedList<String> words = new LinkedList<>();
    private transient List<SentenceTO> tos = new ArrayList<>();
    private transient int idx = 0;
    private transient String mp3Path = System.getProperty("user.home") + File.separator + "mp3" + File.separator;
    private transient ExecutorService exec = Executors.newSingleThreadExecutor();
    private transient Future<?> listenHook;

    // constructor

    /**
     * Constructor
     */
    public WordMasterView() {
        initComponents();
        learnPanel.setVisible(false);
        reviewPanel.setVisible(false);
        studyButton.setEnabled(false);
        reviewBtn.setEnabled(false);
        listenBtn.setEnabled(false);
    }

    public SentenceTO getSentenceTO() {
        return sentenceTO;
    }

    public void setSentenceTO(SentenceTO sentenceTO) {
        SentenceTO old = this.sentenceTO;
        try {
            SentenceTO newTo = (SentenceTO) sentenceTO.clone();
            hideKeyword(newTo);
            this.sentenceTO = newTo;
            firePropertyChange("sentenceTO", old, newTo);
        } catch (CloneNotSupportedException e) {
            logger.error("clone error:{}", e);
        }
    }

    // private
    private void hideKeyword(SentenceTO sentenceTO) {
        String word = sentenceTO.getWord().getName();
        String english = sentenceTO.getEnglish();
        sentenceTO.setEnglish(english.replaceAll("(?i)\\b" + word.substring(0, (word.length() + 1) >> 1) + ".*?\\b", word.substring(0, 1) + "____"));
    }

    private void nextButtonPressed(ActionEvent e) {//NOSONAR squid:S1172
        SentenceTO cur = tos.get(idx);
        meaningLabel.setText("");
        idx = (idx + 1) % tos.size();
        setSentenceTO(tos.get(idx));
        upFamilarity(cur);
    }

    private void upFamilarity(SentenceTO sentenceTO) {
        exec.execute(new Runnable() {//NOSONAR
            @Override
            public void run() {
                studyService.familarityUp(sentenceTO);
            }
        });
    }

    private void downFamilarity(SentenceTO sentenceTO) {
        exec.execute(new Runnable() {//NOSONAR
            @Override
            public void run() {
                studyService.familarityDown(sentenceTO);
            }
        });
    }

    private void prevButtonPressed(ActionEvent e) {//NOSONAR squid:S1172
        SentenceTO cur = tos.get(idx);
        meaningLabel.setText("");
        idx = (idx + tos.size() - 1) % tos.size();
        setSentenceTO(tos.get(idx));
        upFamilarity(cur);
    }

    private void reviewBtnMouseClicked(MouseEvent e) {//NOSONAR squid:S1172
        if (listenHook != null && !listenHook.isDone())
            listenHook.cancel(true);

        reviewPanel.setVisible(true);
        learnPanel.setVisible(false);

        newNum.setEnabled(false);
        studiedNum.setEnabled(false);

        revEnglishLabel.setText(LOADING_PLEASE_WAIT);
        revNext.setEnabled(false);
        revPrev.setEnabled(false);
        showAnswer.setEnabled(false);

        if (!tos.isEmpty()) {
            updateReviewBoard();
            revNext.setEnabled(true);
            revPrev.setEnabled(true);
            showAnswer.setEnabled(true);
        } else {
            revEnglishLabel.setText(NOTHING_TO_LEARN_REVIEW);
        }
    }

    private int intValue(String s) {
        int i = 0;
        try {
            i = Integer.parseInt(s);
        } catch (NumberFormatException ignored) {
            logger.info("error in parsing number:{}", ignored);
        }
        return i;
    }

    private void updateReviewBoard() {
        fragmentPanel.removeAll();

        SentenceTO to = tos.get(idx);
        revChineseLabel.setText(to.getChinese());
        revEnglishLabel.setText("");
        String[] wds = to.getEnglish().split("\\s+");
        this.words = new LinkedList<>(Arrays.asList(wds));
        Collections.shuffle(Arrays.asList(wds));

        for (String word : wds) {
            final JLabel label = new JLabel();
            label.setText(word);
            label.setBorder(BorderFactory.createLineBorder(Color.black));
            label.setFont(new Font(SEGOE_UI, Font.PLAIN, 22));
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (label.getText().equalsIgnoreCase(WordMasterView.this.words.getFirst())) {
                        WordMasterView.this.words.removeFirst();
                        String txt = revEnglishLabel.getText();
                        revEnglishLabel.setText(txt.isEmpty() ? txt + label.getText() : txt + " " + label.getText());
                        fragmentPanel.remove(label);
                        fragmentPanel.repaint();
                    }
                }
            });
            fragmentPanel.add(label);
        }
    }

    private void revNextActionPerformed(ActionEvent e) {
        nextButtonPressed(e);
        updateReviewBoard();
    }

    private void revPrevActionPerformed(ActionEvent e) {//NOSONAR squid:S1172
        prevButtonPressed(e);
        updateReviewBoard();
    }

    private void showAnswerActionPerformed(ActionEvent e) {//NOSONAR squid:S1172
        revEnglishLabel.setText(tos.get(idx).getEnglish());
        fragmentPanel.removeAll();
        fragmentPanel.repaint();
        downFamilarity(tos.get(idx));
    }

    private void showAllBtnActionPerformed(ActionEvent e) {//NOSONAR squid:S1172
        learnEnglish.setText(tos.get(idx).getEnglish());
        meaningLabel.setText("<html>" +
                getSentenceTO().getMeaning().getTxt().replaceAll("(?m)<span class='method-en'>", "<br/><span class='method-en'>")
                + "</html>");
        downFamilarity(tos.get(idx));
    }

    private void loadBtnActionPerformed(ActionEvent e) {//NOSONAR squid:S1172
        newNum.setEnabled(false);
        studiedNum.setEnabled(false);
        loadBtn.setEnabled(false);

        exec.execute(new Runnable() {//NOSONAR
            @Override
            public void run() {
                if (tos.isEmpty())
                    tos = studyService .loadTasks(intValue(newNum.getText()), intValue(studiedNum.getText()));
                SwingUtilities.invokeLater(new Runnable() {//NOSONAR
                    @Override
                    public void run() {
                        studyButton.setEnabled(true);
                        reviewBtn.setEnabled(true);
                        listenBtn.setEnabled(true);
                    }
                });
            }
        });
    }

    private void listenActionPerformed(ActionEvent e) {//NOSONAR squid:S1172
        exec.execute(new Runnable() {//NOSONAR
            @Override
            public void run() {
                play(getSentenceTO().getWord().getName());
            }
        });
    }

    private void play(String wordName) {
        if(wordName == null)
            return;
        InputStream is = null;
        BufferedInputStream bs = null;
        try {
            is = new FileInputStream(mp3Path + wordName + ".mp3");
            bs = new BufferedInputStream(is);
            new Player(bs).play();
        } catch (JavaLayerException | FileNotFoundException e) {
            logger.error("error during play:{}", e);
        } finally {
            if (bs != null) {
                try {
                    bs.close();
                } catch (IOException ignore) {
                }
            }
        }
    }

    private void studyButtonMouseClicked(MouseEvent e) {//NOSONAR squid:S1172
        if (listenHook != null && !listenHook.isDone())
            listenHook.cancel(true);

        learnPanel.setVisible(true);
        reviewPanel.setVisible(false);
        prevBtn.setEnabled(false);
        nextBtn.setEnabled(false);
        showAllBtn.setEnabled(false);

        learnEnglish.setText(LOADING_PLEASE_WAIT);

        learnEnglish.setText("");
        if (!tos.isEmpty()) {
            newNum.setEnabled(false);
            studiedNum.setEnabled(false);
            setSentenceTO(tos.get(idx));
            prevBtn.setEnabled(true);
            nextBtn.setEnabled(true);
            showAllBtn.setEnabled(true);
        } else {
            learnEnglish.setText(NOTHING_TO_LEARN_REVIEW);
        }
    }

    private void listenBtnMouseClicked(MouseEvent e) {//NOSONAR squid:S1172
        listenHook = exec.submit(new Runnable() {
            List<SentenceTO> tts = new ArrayList<>(tos);

            @Override
            public void run() {
                Collections.shuffle(tts);
                try {
                    while (!Thread.interrupted()) {
                        for (SentenceTO sentenceTO : tts) {
                            if (Thread.interrupted())
                                break;
                            play(sentenceTO.getWord().getName());
                            TimeUnit.SECONDS.sleep(3);
                        }
                    }
                } catch (InterruptedException e1) {
                    logger.info("interrupted:{}", e1);
                    Thread.currentThread().interrupt();
                }
            }
        });
    }


    public void shutDown() {
        exec.shutdownNow();
    }

    private void initComponents() {//NOSONAR
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        learnPanel = new JPanel();
        learnChinese = new JLabel();
        learnEnglish = new JLabel();
        meaningLabel = new JLabel();
        learnNavPanel = new JPanel();
        prevBtn = new JButton();
        showAllBtn = new JButton();
        nextBtn = new JButton();
        listen = new JButton();
        toolBar1 = new JToolBar();
        studyButton = new JButton();
        reviewBtn = new JButton();
        listenBtn = new JButton();
        newNum = new JTextField();
        studiedNum = new JTextField();
        loadBtn = new JButton();
        reviewPanel = new JPanel();
        revChineseLabel = new JLabel();
        revEnglishLabel = new JLabel();
        fragmentPanel = new JPanel();
        revNavPanel = new JPanel();
        revPrev = new JButton();
        showAnswer = new JButton();
        revNext = new JButton();

        //======== this ========
        setLayout(new FormLayout(
                "pref:grow, $lcgap, default",
                "fill:default, $pgap, pref, $lgap, default"));

        //======== learnPanel ========
        {
            learnPanel.setLayout(new FormLayout(
                    "center:default:grow, $lcgap, default",
                    "2*(default, $pgap), 2*(default, $lgap), default"));
            learnPanel.add(learnChinese, CC.xy(1, 1, CC.CENTER, CC.DEFAULT));

            //---- learnEnglish ----
            learnEnglish.setText("text");
            learnEnglish.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            learnPanel.add(learnEnglish, CC.xy(1, 3, CC.CENTER, CC.DEFAULT));
            learnPanel.add(meaningLabel, CC.xy(1, 5));

            //======== learnNavPanel ========
            {
                learnNavPanel.setLayout(new FormLayout(
                        "pref:grow, $lcgap, default:grow, 2*($lcgap, default)",
                        "default"));

                //---- prevBtn ----
                prevBtn.setText("<");
                prevBtn.addActionListener(e -> prevButtonPressed(e));
                learnNavPanel.add(prevBtn, CC.xy(1, 1));

                //---- showAllBtn ----
                showAllBtn.setText(":)");
                showAllBtn.addActionListener(e -> showAllBtnActionPerformed(e));
                learnNavPanel.add(showAllBtn, CC.xy(3, 1));

                //---- nextBtn ----
                nextBtn.setText(">");
                nextBtn.addActionListener(e -> nextButtonPressed(e));
                learnNavPanel.add(nextBtn, CC.xy(5, 1, CC.CENTER, CC.DEFAULT));

                //---- listen ----
                listen.setText("#");
                listen.addActionListener(e -> listenActionPerformed(e));
                learnNavPanel.add(listen, CC.xy(7, 1));
            }
            learnPanel.add(learnNavPanel, CC.xy(1, 7, CC.CENTER, CC.DEFAULT));
        }
        add(learnPanel, CC.xy(1, 5));

        //======== toolBar1 ========
        {

            //---- studyButton ----
            studyButton.setText("Hint Mode");
            studyButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    studyButtonMouseClicked(e);
                }
            });
            toolBar1.add(studyButton);
            toolBar1.addSeparator();

            //---- reviewBtn ----
            reviewBtn.setText("Assemble Mode");
            reviewBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    reviewBtnMouseClicked(e);
                }
            });
            toolBar1.add(reviewBtn);
            toolBar1.addSeparator();

            //---- listenBtn ----
            listenBtn.setText("Listen Mode");
            listenBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    listenBtnMouseClicked(e);
                }
            });
            toolBar1.add(listenBtn);

            //---- newNum ----
            newNum.setText("20");
            newNum.setToolTipText("N.O. of new words to learn");
            toolBar1.add(newNum);
            toolBar1.addSeparator();

            //---- studiedNum ----
            studiedNum.setText("80");
            studiedNum.setToolTipText("N.O. of studied words to refresh");
            toolBar1.add(studiedNum);
            toolBar1.addSeparator();

            //---- loadBtn ----
            loadBtn.setText("Load");
            loadBtn.addActionListener(e -> loadBtnActionPerformed(e));
            toolBar1.add(loadBtn);
        }
        add(toolBar1, CC.xy(1, 1));

        //======== reviewPanel ========
        {
            reviewPanel.setLayout(new FormLayout(
                    "center:default:grow, $lcgap, default",
                    "3*(default, $pgap), default"));
            reviewPanel.add(revChineseLabel, CC.xy(1, 1));

            //---- revEnglishLabel ----
            revEnglishLabel.setText("text");
            revEnglishLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            reviewPanel.add(revEnglishLabel, CC.xy(1, 3));

            //======== fragmentPanel ========
            {
                fragmentPanel.setLayout(new FlowLayout());
            }
            reviewPanel.add(fragmentPanel, CC.xy(1, 5));

            //======== revNavPanel ========
            {
                revNavPanel.setLayout(new FormLayout(
                        "3*(default, $lcgap), default",
                        "default"));

                //---- revPrev ----
                revPrev.setText("<");
                revPrev.addActionListener(e -> revPrevActionPerformed(e));
                revNavPanel.add(revPrev, CC.xy(1, 1));

                //---- showAnswer ----
                showAnswer.setText(":)");
                showAnswer.addActionListener(e -> showAnswerActionPerformed(e));
                revNavPanel.add(showAnswer, CC.xy(3, 1));

                //---- revNext ----
                revNext.setText(">");
                revNext.addActionListener(e -> revNextActionPerformed(e));
                revNavPanel.add(revNext, CC.xy(5, 1));
            }
            reviewPanel.add(revNavPanel, CC.xy(1, 7));
        }
        add(reviewPanel, CC.xy(1, 3));

        //---- bindings ----
        bindingGroup = new BindingGroup();
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
                this, ELProperty.create("${sentenceTO.chinese}"),
                learnChinese, BeanProperty.create("text"), "chineseLabel"));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
                this, ELProperty.create("${sentenceTO.english}"),
                learnEnglish, BeanProperty.create("text"), "englishLabel"));
        bindingGroup.bind();
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel learnPanel;
    private JLabel learnChinese;
    private JLabel learnEnglish;
    private JLabel meaningLabel;
    private JPanel learnNavPanel;
    private JButton prevBtn;
    private JButton showAllBtn;
    private JButton nextBtn;
    private JButton listen;
    private JToolBar toolBar1;
    private JButton studyButton;
    private JButton reviewBtn;
    private JButton listenBtn;
    private JTextField newNum;
    private JTextField studiedNum;
    private JButton loadBtn;
    private JPanel reviewPanel;
    private JLabel revChineseLabel;
    private JLabel revEnglishLabel;
    private JPanel fragmentPanel;
    private JPanel revNavPanel;
    private JButton revPrev;
    private JButton showAnswer;
    private JButton revNext;
    private BindingGroup bindingGroup;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
