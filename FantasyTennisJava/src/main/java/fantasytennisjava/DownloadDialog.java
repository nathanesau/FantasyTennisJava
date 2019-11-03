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
    JLabel fnameLabel;
    JTextField fnameLE;
    JButton okButton;
    FlowLayout mainLayout;

    DownloadDialog(JFrame parent, Map<String, String> downloadOptions) {
        super(parent, Dialog.ModalityType.DOCUMENT_MODAL); // invoke parent constructor

        // widgets
        this.urlLabel = new JLabel();
        this.urlLabel.setText("Specify tournament to download HTML bracket for...");
        String []cbItems = downloadOptions.keySet().toArray(new String[0]);
        this.urlComboBox = new JComboBox<String>(cbItems);
        this.fnameLabel = new JLabel();
        this.fnameLabel.setText("Specify output filename...");
        this.fnameLE = new JTextField();
        this.fnameLE.setText("out.html");
        this.fnameLE.setToolTipText("Example: out.html");
        this.okButton = new JButton();
        this.okButton.setText("OK");

        // layout
        this.mainLayout = new FlowLayout();
        this.setLayout(this.mainLayout);
        this.add(this.urlLabel);
        this.add(this.urlComboBox);
        this.add(this.fnameLabel);
        this.add(this.fnameLE);
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
        
        Map<String, String> downloadOptions = new HashMap<String, String>();

        ATPArchiveParser parser = new ATPArchiveParser();
        parser.parseArchiveURL("https://www.atptour.com/en/scores/results-archive?year=2019");

        return parser.tournamentList;
    }
}