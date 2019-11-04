package fantasytennisjava;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

// tested
public class LoadBracketDialog extends JDialog {

    private static final long serialVersionUID = 860L;

    JLabel fileLabel;
    JComboBox<String> fileComboBox;
    JButton okButton;
    FlowLayout mainLayout;

    LoadBracketDialog(JFrame parent, ArrayList<String> loadOptions) {
        super(parent, Dialog.ModalityType.DOCUMENT_MODAL); // invoke parent constructor

        // widgets
        this.fileLabel = new JLabel();
        this.fileLabel.setText("Select bracket to load");
        String[] cbItems = loadOptions.toArray(new String[0]);
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
                dispose();
            }
        });

        // frame
        ImageIcon logo = new ImageIcon("res/icon.png");
        this.setIconImage(logo.getImage());
        this.setTitle("Select Bracket");
        this.setSize(400, 500);
    }

    static ArrayList<String> getLoadOptions() {
        ArrayList<String> loadOptions = new ArrayList<String>();

        File folder = new File(".");
        File[] listOfFiles = folder.listFiles(); 
        for(File currentFile : listOfFiles) {
            if(currentFile.getName().endsWith(".db")) {
                loadOptions.add(currentFile.getName());
            }
        }

        return loadOptions;
    }
}