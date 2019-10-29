package fantasytennisjava;

import javax.swing.*; // JButton, JFrame, JPanel
import java.awt.*; // FlowLayout
import java.awt.event.*;

// tested
public class DownloadDialog extends JDialog {

    JLabel urlLabel;
    JComboBox urlComboBox;
    JLabel fnameLabel;
    JTextField fnameLE;
    JButton okButton;
    FlowLayout mainLayout;

    DownloadDialog(String[] downloadOptions, String defaultFName) {
        super(); // invoke parent constructor

        // widgets
        this.urlLabel = new JLabel();
        this.urlLabel.setText("Specify tournament to download HTML bracket for...");
        this.urlComboBox = new JComboBox(downloadOptions);
        this.fnameLabel = new JLabel();
        this.fnameLabel.setText("Specify output filename...");
        this.fnameLE = new JTextField();
        this.fnameLE.setText(defaultFName);
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
                int index = urlComboBox.getSelectedIndex();
                String url = "https://www.atptour.com/en/scores/archive/cincinnati/422/2019/draws"; // todo: use index
                String fname = "out.html"; // todo: read from settings
        
                // todo: download url and save to fname
                JOptionPane.showMessageDialog(null, "Bracket has been downloaded");
            }
        });

        // frame
        ImageIcon logo = new ImageIcon("res/icon.png");
        this.setIconImage(logo.getImage());
        this.setTitle("Download HTML Bracket");
        this.setSize(400, 500);
    }

    static String[] getDownloadOptions(String tmp_dir) {
        // todo: some bs4 logic - open inputFName and parse
        String[] tournaments = {"US Open", "French Open", "Rogers Cup", "ATP Finals"};
        return tournaments;
    }

    static void downloadArchive(int year, String out_dir) {
        String url = "https://www.atptour.com/en/scores/results-archive" + "?year=" + Integer.toString(year);
        String fname = out_dir + "archive.html";

        // todo: download url, save to fname
    }
}