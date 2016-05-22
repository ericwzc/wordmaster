/*
 * Created by JFormDesigner on Sat Mar 19 19:43:21 CST 2016
 */

package org.words.gui;

import java.awt.*;
import java.awt.event.*;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.ELProperty;
import org.words.factory.ServiceRegistry;
import org.words.service.TaskService;
import org.words.to.SentenceTO;
import org.words.to.TaskTO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author User #1
 */
public class WordMasterView extends JPanel {
    private SentenceTO sentenceTO = new SentenceTO();
    private LinkedList<String> words;
    private List<SentenceTO> tos;
    private int idx = 0;
    private Object lock = new Object();

    public WordMasterView() {
        initComponents();
        bindingGroup.addBindingListener(new LoggingBindingListener(new JLabel()));
        learnPanel.setVisible(false);
        reviewPanel.setVisible(false);
    }

    private void studyButtonActionPerformed(ActionEvent e) {
        this.learnPanel.setVisible(true);
        this.reviewPanel.setVisible(false);
        tos = ServiceRegistry.getServiceInstance(TaskService.class).getSentences4Today();
        if (tos.size() > 0) {
            setSentenceTO(tos.get(0));
        }
    }

    public SentenceTO getSentenceTO() {
        return sentenceTO;
    }

    public void setSentenceTO(SentenceTO sentenceTO) {
        SentenceTO old = this.sentenceTO;
        sentenceTO = (SentenceTO) sentenceTO.clone();
        hideKeyword(sentenceTO);
        this.sentenceTO = sentenceTO;
        firePropertyChange("sentenceTO", old, sentenceTO);
    }

    private void hideKeyword(SentenceTO sentenceTO) {
        String word = sentenceTO.getWord().getName();
        String english = sentenceTO.getEnglish();
        sentenceTO.setEnglish(english.replaceAll("\\b" + word.substring(0, word.length() >> 1) + ".*?\\b", word.substring(0, 1) + "____"));
    }

    private void nextButtonPressed(ActionEvent e) {
        synchronized (lock) {
            idx = (idx + 1) % tos.size();
            setSentenceTO(tos.get(idx));
        }
    }

    private void prevButtonPressed(ActionEvent e) {
        synchronized (lock) {
            idx = (idx + tos.size() - 1) % tos.size();
            setSentenceTO(tos.get(idx));
        }
    }

    private void reviewBtnMouseClicked(MouseEvent e) {
        tos = ServiceRegistry.getServiceInstance(TaskService.class).getSentences4Today();
        updateReviewBoard();
    }

    private void updateReviewBoard(){
        reviewPanel.setVisible(true);
        learnPanel.setVisible(false);
        fragmentPanel.removeAll();

        SentenceTO to = tos.get(idx);
        label4.setText(to.getChinese());
        label5.setText("");
        String[] words = to.getEnglish().split("\\s+");
        this.words = new LinkedList<>(Arrays.asList(words));
        Collections.shuffle(Arrays.asList(words));

        for(String word : words) {
            final JLabel label = new JLabel();
            label.setText(word);
            label.setBorder(BorderFactory.createLineBorder(Color.black));
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(label.getText().equalsIgnoreCase(WordMasterView.this.words.getFirst())) {
                        WordMasterView.this.words.removeFirst();
                        String txt = label5.getText();
                        label5.setText(txt.isEmpty() ? txt + label.getText() : txt + " " + label.getText());
                        fragmentPanel.remove(label);
                        fragmentPanel.repaint();
                    }
                }
            });
            fragmentPanel.add(label);
        }
    }

    private void button4ActionPerformed(ActionEvent e) {
        nextButtonPressed(e);
        updateReviewBoard();
    }

    private void button3ActionPerformed(ActionEvent e) {
        prevButtonPressed(e);
        updateReviewBoard();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        learnPanel = new JPanel();
        label1 = new JLabel();
        label2 = new JLabel();
        panel1 = new JPanel();
        button1 = new JButton();
        label3 = new JLabel();
        button2 = new JButton();
        toolBar1 = new JToolBar();
        studyButton = new JButton();
        reviewBtn = new JButton();
        reviewPanel = new JPanel();
        label4 = new JLabel();
        label5 = new JLabel();
        fragmentPanel = new JPanel();
        panel2 = new JPanel();
        button3 = new JButton();
        button4 = new JButton();

        //======== this ========
        setLayout(new FormLayout(
            "default:grow, $lcgap, default",
            "fill:default, $pgap, pref, $lgap, default"));

        //======== learnPanel ========
        {
            learnPanel.setLayout(new FormLayout(
                "default:grow, $lcgap, default",
                "2*(default, $pgap), default"));

            //---- label1 ----
            label1.setText("text");
            learnPanel.add(label1, CC.xy(1, 1, CC.CENTER, CC.DEFAULT));

            //---- label2 ----
            label2.setText("text");
            learnPanel.add(label2, CC.xy(1, 3, CC.CENTER, CC.DEFAULT));

            //======== panel1 ========
            {
                panel1.setLayout(new FormLayout(
                    "pref:grow, $lcgap, default:grow, $lcgap, 27dlu:grow",
                    "default"));

                //---- button1 ----
                button1.setText("<");
                button1.addActionListener(e -> prevButtonPressed(e));
                panel1.add(button1, CC.xy(1, 1));
                panel1.add(label3, CC.xy(3, 1, CC.CENTER, CC.DEFAULT));

                //---- button2 ----
                button2.setText(">");
                button2.addActionListener(e -> nextButtonPressed(e));
                panel1.add(button2, CC.xy(5, 1, CC.CENTER, CC.DEFAULT));
            }
            learnPanel.add(panel1, CC.xy(1, 5, CC.CENTER, CC.DEFAULT));
        }
        add(learnPanel, CC.xy(1, 5));

        //======== toolBar1 ========
        {

            //---- studyButton ----
            studyButton.setText("Start Learn");
            studyButton.addActionListener(e -> studyButtonActionPerformed(e));
            toolBar1.add(studyButton);
            toolBar1.addSeparator();

            //---- reviewBtn ----
            reviewBtn.setText("Start Review");
            reviewBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    reviewBtnMouseClicked(e);
                }
            });
            toolBar1.add(reviewBtn);
        }
        add(toolBar1, CC.xy(1, 1));

        //======== reviewPanel ========
        {
            reviewPanel.setLayout(new FormLayout(
                "center:default:grow, $lcgap, default",
                "3*(default, $pgap), default"));

            //---- label4 ----
            label4.setText("text");
            reviewPanel.add(label4, CC.xy(1, 1));

            //---- label5 ----
            label5.setText("text");
            reviewPanel.add(label5, CC.xy(1, 3));

            //======== fragmentPanel ========
            {
                fragmentPanel.setLayout(new FlowLayout());
            }
            reviewPanel.add(fragmentPanel, CC.xy(1, 5));

            //======== panel2 ========
            {
                panel2.setLayout(new FormLayout(
                    "default, $lcgap, default",
                    "default"));

                //---- button3 ----
                button3.setText("<");
                button3.addActionListener(e -> button3ActionPerformed(e));
                panel2.add(button3, CC.xy(1, 1));

                //---- button4 ----
                button4.setText(">");
                button4.addActionListener(e -> button4ActionPerformed(e));
                panel2.add(button4, CC.xy(3, 1));
            }
            reviewPanel.add(panel2, CC.xy(1, 7));
        }
        add(reviewPanel, CC.xy(1, 3));

        //---- bindings ----
        bindingGroup = new BindingGroup();
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
            this, ELProperty.create("${sentenceTO.chinese}"),
            label1, BeanProperty.create("text"), "chineseLabel"));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
            this, ELProperty.create("${sentenceTO.english}"),
            label2, BeanProperty.create("text"), "englishLabel"));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
            this, ELProperty.create("${sentenceTO.word.name}"),
            label3, BeanProperty.create("text"), "wordname"));
        bindingGroup.bind();
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel learnPanel;
    private JLabel label1;
    private JLabel label2;
    private JPanel panel1;
    private JButton button1;
    private JLabel label3;
    private JButton button2;
    private JToolBar toolBar1;
    private JButton studyButton;
    private JButton reviewBtn;
    private JPanel reviewPanel;
    private JLabel label4;
    private JLabel label5;
    private JPanel fragmentPanel;
    private JPanel panel2;
    private JButton button3;
    private JButton button4;
    private BindingGroup bindingGroup;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
