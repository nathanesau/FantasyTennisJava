package fantasytennisjava;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

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
    JMenu predictionsMenu;
    JMenuItem savePredictionsItem;
    JMenuItem loadPredictionsItem;

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
        this.savePredictionsItem = new JMenuItem();
        this.savePredictionsItem.setText("Save Predictions");
        this.loadPredictionsItem = new JMenuItem();
        this.loadPredictionsItem.setText("Load Predictions");

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
                LoadBracketDialog dialog = new LoadBracketDialog();
                dialog.setVisible(true);
            }
        });

        this.savePredictionsItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                SavePredictionsDialog dialog = new SavePredictionsDialog("out.db");
                dialog.setVisible(true);
            }
        });

        this.loadPredictionsItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                LoadPredictionsDialog dialog = new LoadPredictionsDialog();
                dialog.setVisible(true);
            }
        });

        // menus
        this.fileMenu = new JMenu();
        this.fileMenu.setText("File");
        this.fileMenu.add(this.downloadBracketItem);
        this.fileMenu.add(this.loadBracketItem);
        this.predictionsMenu = new JMenu();
        this.predictionsMenu.setText("Predictions");
        this.predictionsMenu.add(this.savePredictionsItem);
        this.predictionsMenu.add(this.loadPredictionsItem);

        // menu bar
        this.menuBar = new JMenuBar();
        this.menuBar.add(this.fileMenu);
        this.menuBar.add(this.predictionsMenu);

        // frame
        ImageIcon logo = new ImageIcon("res/icon.png");
        this.setIconImage(logo.getImage());
        this.setSize(400, 500);
        this.setLayout(null);
        this.setTitle("Fantasy Tennis");
        this.setJMenuBar(this.menuBar);
    }
}