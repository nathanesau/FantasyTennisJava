package fantasytennisjava;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// not tested
public class SavePredictionsDialog extends JDialog {

    // widgets
    JLabel fileNameLabel;
    JTextField fileNameLE;
    JButton okButton;
    FlowLayout mainLayout;

    SavePredictionsDialog(String defaultFName) {
        super(); // invoke parent constructor

        // widgets
        this.fileNameLabel = new JLabel();
        this.fileNameLabel.setText("Specify file name (.db)");
        this.fileNameLE = new JTextField();
        this.fileNameLE.setText(defaultFName);
        this.okButton = new JButton();
        this.okButton.setText("OK");

        // layout
        this.mainLayout = new FlowLayout();
        this.setLayout(this.mainLayout);
        this.add(this.fileNameLabel);
        this.add(this.fileNameLE);
        this.add(this.okButton);

        // event listeners
        this.okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                // todo
            }
        });
        
        // frame
        ImageIcon logo = new ImageIcon("res/icon.png");
        this.setIconImage(logo.getImage());
        this.setTitle("Save predictions");
        this.setSize(400, 500);
    }
}