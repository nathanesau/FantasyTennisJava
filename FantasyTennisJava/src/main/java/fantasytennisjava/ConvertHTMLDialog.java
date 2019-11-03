package fantasytennisjava;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// not tested
public class ConvertHTMLDialog extends JDialog {

    private static final long serialVersionUID = 690L;

    // widgets
    JLabel fileLabel;
    JComboBox<String> fileComboBox;
    JButton okButton;
    FlowLayout mainLayout;

    ConvertHTMLDialog () {
        super(); // invoke parent constructor

        // widgets
        this.fileLabel = new JLabel();
        this.fileLabel.setText("Select HTML file to convert");
        String[] cbItems = ConvertHTMLDialog.getCbItems();
        this.fileComboBox = new JComboBox<String>(cbItems);
        this.okButton = new JButton();
        this.okButton.setText("OK");

        // layout
        this.mainLayout = new FlowLayout();
        this.setLayout(this.mainLayout);
        this.add(this.fileLabel);
        this.add(this.fileComboBox);
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
        this.setTitle("Select HTML Bracket");
        this.setSize(400, 500);
    }

    static String[] getCbItems() {
        String[] cbItems = {"french_open.html", "rogers.html", "atp_finals.html"}; // todo: get proper items
        return cbItems;
    }
}