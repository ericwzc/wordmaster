/*
 * Created by JFormDesigner on Sat Mar 19 19:43:21 CST 2016
 */

package org.words.gui;

import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;
import org.apache.commons.beanutils.BeanUtils;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.ELProperty;
import org.words.factory.ServiceFactory;
import org.words.service.TaskService;
import org.words.to.SentenceTO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author User #1
 */
public class WordMasterView extends JPanel {
    private SentenceTO sentenceTO = new SentenceTO();
    private List<SentenceTO> tos;
    private int idx = 0;

    public WordMasterView() {
        initComponents();
        bindingGroup.addBindingListener(new LoggingBindingListener(new JLabel()));
    }

    private void studyButtonActionPerformed(ActionEvent e) {
        try {
            tos = ServiceFactory.getServiceInstance(TaskService.class).getSentences();
            BeanUtils.copyProperties(sentenceTO, tos.get(idx % tos.size()));
        } catch (IllegalAccessException e1) {
        } catch (InvocationTargetException e1) {
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
            "default:grow",
            "fill:default, $lgap, 2*(pref, $pgap), pref"));

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
                "pref:grow, $lcgap, 2*(pref:grow)",
                "default"));

            //---- button1 ----
            button1.setText("<");
            panel1.add(button1, CC.xy(1, 1));
            panel1.add(label3, CC.xy(3, 1, CC.CENTER, CC.DEFAULT));

            //---- button2 ----
            button2.setText(">");
            panel1.add(button2, CC.xy(4, 1));
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
