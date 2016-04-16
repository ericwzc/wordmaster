/*
 * Created by JFormDesigner on Sat Mar 19 19:43:21 CST 2016
 */

package org.words.gui;

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
import java.util.List;

/**
 * @author User #1
 */
public class WordMasterView extends JPanel {
    private SentenceTO sentenceTO = new SentenceTO();
    private List<SentenceTO> tos;
    private int idx = 0;
    private Object lock = new Object();

    public WordMasterView() {
        initComponents();
        bindingGroup.addBindingListener(new LoggingBindingListener(new JLabel()));
    }

    private void studyButtonActionPerformed(ActionEvent e) {
        tos = ServiceRegistry.getServiceInstance(TaskService.class).getSentences4Today();
        if(tos.size() > 0) {
            setSentenceTO(tos.get(0));
        }
    }

    public SentenceTO getSentenceTO() {
        return sentenceTO;
    }

    public void setSentenceTO(SentenceTO sentenceTO) {
        SentenceTO old = this.sentenceTO;
        this.sentenceTO = sentenceTO;
        firePropertyChange("sentenceTO", old, sentenceTO);
    }

    private void nextButtonPressed(ActionEvent e) {
        synchronized (lock) {
            idx = (idx + 1 ) % tos.size();
            setSentenceTO(tos.get(idx));
        }
    }

    private void prevButtonPressed(ActionEvent e) {
        synchronized (lock) {
            idx = (idx + tos.size() - 1) % tos.size();
            setSentenceTO(tos.get(idx));
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        toolBar1 = new JToolBar();
        studyButton = new JButton();
        reviewBtn = new JButton();
        label1 = new JLabel();
        label2 = new JLabel();
        panel1 = new JPanel();
        button1 = new JButton();
        label3 = new JLabel();
        button2 = new JButton();

        //======== this ========
        setLayout(new FormLayout(
            "default:grow, $lcgap, default",
            "fill:default, $lgap, 2*(pref:grow, $pgap), pref, $lgap, default"));

        //======== toolBar1 ========
        {

            //---- studyButton ----
            studyButton.setText("Start Learn");
            studyButton.addActionListener(e -> studyButtonActionPerformed(e));
            toolBar1.add(studyButton);
            toolBar1.addSeparator();

            //---- reviewBtn ----
            reviewBtn.setText("Start Review");
            toolBar1.add(reviewBtn);
        }
        add(toolBar1, CC.xy(1, 1));

        //---- label1 ----
        label1.setText("text");
        add(label1, CC.xy(1, 3, CC.CENTER, CC.DEFAULT));

        //---- label2 ----
        label2.setText("text");
        add(label2, CC.xy(1, 5, CC.CENTER, CC.DEFAULT));

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
        add(panel1, CC.xy(1, 7, CC.CENTER, CC.DEFAULT));

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
    private JToolBar toolBar1;
    private JButton studyButton;
    private JButton reviewBtn;
    private JLabel label1;
    private JLabel label2;
    private JPanel panel1;
    private JButton button1;
    private JLabel label3;
    private JButton button2;
    private BindingGroup bindingGroup;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
