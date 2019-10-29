package fantasytennisjava;

import javax.swing.*;
import java.awt.*; // FlowLayout
import java.awt.event.*;

// not tested
public class LoadPredictionsDialog extends JDialog {
    JLabel fileLabel;
    JComboBox fileComboBox;
    JButton okButton;
    FlowLayout mainLayout;

    LoadPredictionsDialog() {
        super(); // invoke parent constructor

        // widgets
        this.fileLabel = new JLabel();
        this.fileLabel.setText("Select predictions to load");
        String[] cbItems = LoadPredictionsDialog.getCbItems();
        this.fileComboBox = new JComboBox(cbItems);
        this.okButton = new JButton();
        this.okButton.setText("OK");

        // layouts
        this.mainLayout = new FlowLayout();
        this.setLayout(this.mainLayout);
        this.add(this.fileLabel);
        this.add(this.fileComboBox);
        this.add(this.okButton);

        // event listeners
        this.okButton.addActionListener(new ActionListener () {
            public void actionPerformed(ActionEvent evt) {
                // todo
            }
        });

        // frame
        ImageIcon logo = new ImageIcon("res/icon.png");
        this.setIconImage(logo.getImage());
        this.setSize(400, 500);
        this.setTitle("Select predictions");
    }
    
    static String[] getCbItems() {
        // todo: walk through root, directories, files in pred data directory to fill cbItems
        String[] cbItems = { "us_open.db", "french_open.db", "rogers_cup.db" };
        return cbItems;
    }
}