/*
 * Created by JFormDesigner on Sat Mar 19 19:43:21 CST 2016
 */

package org.words.gui;

import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;
import javax.swing.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;
import org.apache.commons.beanutils.BeanUtils;
import org.jdesktop.beansbinding.*;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.words.dao.SentenceDao;
import org.words.hbm.*;
import org.words.to.*;
import org.words.utils.HibernateUtils;

/**
 * @author User #1
 */
public class WordMasterView extends JPanel {
    public WordMasterView() {
        initComponents();
    }

    private void studyButtonActionPerformed(ActionEvent e) {
        // TODO start transaction
        SentenceTO to = new SentenceDao().getSentences().get(0);
        try {
            BeanUtils.copyProperties(sentenceTO, to);
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        } catch (InvocationTargetException e1) {
            e1.printStackTrace();
        }
        //TODO end transaction
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
        button2 = new JButton();
        sentenceTO = new SentenceTO();

        //======== this ========
        setLayout(new FormLayout(
            "center:pref:grow",
            "fill:default, 2*($lgap, default:grow), $lgap, default"));

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
                "$button, $lcgap, $button",
                "default"));

            //---- button1 ----
            button1.setText("<");
            panel1.add(button1, CC.xy(1, 1));

            //---- button2 ----
            button2.setText(">");
            panel1.add(button2, CC.xy(3, 1));
        }
        add(panel1, CC.xy(1, 7, CC.CENTER, CC.DEFAULT));

        //---- bindings ----
        bindingGroup = new BindingGroup();
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
            sentenceTO, BeanProperty.create("chinese"),
            label1, BeanProperty.create("text")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
            sentenceTO, BeanProperty.create("english"),
            label2, BeanProperty.create("text")));
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
    private JButton button2;
    private SentenceTO sentenceTO;
    private BindingGroup bindingGroup;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
