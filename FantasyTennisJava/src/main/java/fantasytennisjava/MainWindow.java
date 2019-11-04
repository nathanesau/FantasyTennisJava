package fantasytennisjava;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import org.apache.commons.lang3.tuple.*;

// tested
public class MainWindow extends JFrame {

    private static final long serialVersionUID = 378L;

    // widget
    JLabel instructionLabel;

    // menus
    JMenuBar menuBar;
    JMenu fileMenu;
    JMenuItem downloadBracketItem;
    JMenuItem loadBracketItem;

    MainWindow() {
        super(); // invoke parent constructor

        // widgets
        this.instructionLabel = new JLabel();
        this.instructionLabel.setText("Load a bracket to get started...");

        this.setLayout(new GridBagLayout ());
        this.add(this.instructionLabel);
        this.pack();
    
        // menu items
        this.downloadBracketItem = new JMenuItem();
        this.downloadBracketItem.setText("Download Bracket");
        this.loadBracketItem = new JMenuItem();
        this.loadBracketItem.setText("Load Bracket");

        // event listeners
        this.downloadBracketItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                // key: tournament title, value: tourney link
                Map<String, String> downloadOptions = DownloadDialog.getDownloadOptions();
                DownloadDialog dialog = new DownloadDialog(MainWindow.this, downloadOptions);
                dialog.setVisible(true);

                String tourneyTitle = dialog.urlComboBox.getSelectedItem().toString();
                String url = "https://www.atptour.com" + downloadOptions.get(tourneyTitle);

                // get tournament db name from url
                int startIndex = url.indexOf("archive");
                int endIndex = url.indexOf("draws");
                String dbName = url.substring(startIndex + 8, endIndex - 1);
                dbName = dbName.replace('/', '_');
                dbName += ".db";

                ATPTournamentParser parser = new ATPTournamentParser();
                parser.parseTournamentURL(url);
                parser.fillDrawRowList();
                parser.fillPlayerRowList();
                
                TennisData data = new TennisData(parser.drawRowList, parser.playerRowList);
                TennisDatabase db = new TennisDatabase();
                db.saveDrawToDb(dbName, data);

                JOptionPane.showMessageDialog(MainWindow.this, "Bracket has been written to " + dbName);
            }
        });

        this.loadBracketItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ArrayList<String> loadOptions = LoadBracketDialog.getLoadOptions();
                LoadBracketDialog dialog = new LoadBracketDialog(MainWindow.this, loadOptions);
                dialog.setVisible(true);

                String dbName = dialog.fileComboBox.getSelectedItem().toString();
                ArrayList<Triple<Integer, String, String>> drawRowList = new ArrayList<Triple<Integer, String, String>>();
                ArrayList<Triple<String, String, String>> playerRowList = new ArrayList<Triple<String, String, String>>();
                TennisData data = new TennisData(drawRowList, playerRowList);
                TennisDatabase db = new TennisDatabase();
                db.loadDrawFromDb(dbName, data);

                JOptionPane.showMessageDialog(MainWindow.this, "Bracket " + dbName + " has been loaded");
            }
        });

        // menus
        this.fileMenu = new JMenu();
        this.fileMenu.setText("File");
        this.fileMenu.add(this.downloadBracketItem);
        this.fileMenu.add(this.loadBracketItem);

        // menu bar
        this.menuBar = new JMenuBar();
        this.menuBar.add(this.fileMenu);

        // frame
        ImageIcon logo = new ImageIcon("res/icon.png");
        this.setIconImage(logo.getImage());
        this.setSize(400, 500);
        this.setLayout(null);
        this.setTitle("Fantasy Tennis");
        this.setJMenuBar(this.menuBar);
    }
}