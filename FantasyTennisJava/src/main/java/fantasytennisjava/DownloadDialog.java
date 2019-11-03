package fantasytennisjava;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

// tested
public class DownloadDialog extends JDialog {

    private static final long serialVersionUID = 166L;

    JLabel urlLabel;
    JComboBox<String> urlComboBox;
    JButton okButton;
    FlowLayout mainLayout;

    DownloadDialog(JFrame parent, Map<String, String> downloadOptions) {
        super(parent, Dialog.ModalityType.DOCUMENT_MODAL); // invoke parent constructor

        // widgets
        this.urlLabel = new JLabel();
        this.urlLabel.setText("Specify tournament to download HTML bracket for...");
        String []cbItems = downloadOptions.keySet().toArray(new String[0]);
        this.urlComboBox = new JComboBox<String>(cbItems);
        this.okButton = new JButton();
        this.okButton.setText("OK");

        // layout
        this.mainLayout = new FlowLayout();
        this.setLayout(this.mainLayout);
        this.add(this.urlLabel);
        this.add(this.urlComboBox);
        this.add(this.okButton);

        // event listeners
        this.okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                dispose();
            }
        });

        // frame
        ImageIcon logo = new ImageIcon("res/icon.png");
        this.setIconImage(logo.getImage());
        this.setTitle("Download HTML Bracket");
        this.setSize(400, 500);
    }

    static Map<String, String> getDownloadOptions() {
        ATPArchiveParser parser = new ATPArchiveParser();
        parser.parseArchiveURL("https://www.atptour.com/en/scores/results-archive?year=2019");

        return parser.tournamentList;
    }
}