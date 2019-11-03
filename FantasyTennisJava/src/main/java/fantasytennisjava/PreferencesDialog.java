package fantasytennisjava;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// not tested
public class PreferencesDialog extends JDialog {

    private static final long serialVersionUID = 175L;

    // widgets
    JLabel dataDirLabel;
    JTextField dataDirLE;
    JLabel htmlDirLabel;
    JTextField htmlDirLE;
    JLabel customDirLabel;
    JTextField customDirLE;
    JButton okButton;
    FlowLayout mainLayout;

    PreferencesDialog() {
        super(); // invoke parent constructor

        // widgets
        this.dataDirLabel = new JLabel();
        this.dataDirLabel.setText("Data Directory");
        this.dataDirLE = new JTextField();
        this.dataDirLE.setText("Default data_dir"); // todo: set initial value properly
        this.htmlDirLabel = new JLabel();
        this.htmlDirLabel.setText("HTML Directory");
        this.htmlDirLE = new JTextField();
        this.htmlDirLE.setText("Default html_dir"); // todo: set initial value properly
        this.customDirLabel = new JLabel();
        this.customDirLabel.setText("Predictions Directory");
        this.customDirLE = new JTextField();
        this.customDirLE.setText("Default customI_dir"); // todo: set initial value properly
        this.okButton = new JButton();
        this.okButton.setText("OK");

        // layout
        this.mainLayout = new FlowLayout();
        this.setLayout(this.mainLayout);
        this.add(this.dataDirLabel);
        this.add(this.dataDirLE);
        this.add(this.htmlDirLabel);
        this.add(this.htmlDirLE);
        this.add(this.customDirLabel);
        this.add(this.customDirLE);

        // event listeners
        this.okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                // todo
            }
        });

        // frame
        ImageIcon logo = new ImageIcon("res/icon.png");
        this.setIconImage(logo.getImage());
        this.setTitle("Preferences");
        this.setSize(400, 500);
    }
}