package fantasytennisjava;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.lang.Math;

// tested
public class MainWindow extends JFrame {

    private static final long serialVersionUID = 378L;

    // widgets
    JButton button;

    // menus
    JMenuBar menuBar;
    JMenu fileMenu;
    JMenuItem downloadBracketItem;
    JMenuItem convertHTMLToDbItem;
    JMenuItem loadBracketItem;
    JMenu editMenu;
    JMenuItem preferencesItem;
    JMenu predictionsMenu;
    JMenuItem savePredictionsItem;
    JMenuItem loadPredictionsItem;

    MainWindow() {
        super(); // invoke parent constructor

        // widgets
        this.button = new JButton("Click");
        this.button.setBounds(130, 100, 100, 100); // x, y, width, height
        this.add(button);

        // menu items
        this.downloadBracketItem = new JMenuItem();
        this.downloadBracketItem.setText("Download Bracket");
        this.convertHTMLToDbItem = new JMenuItem();
        this.convertHTMLToDbItem.setText("Convert HTML to DB");
        this.loadBracketItem = new JMenuItem();
        this.loadBracketItem.setText("Load Bracket");
        this.preferencesItem = new JMenuItem();
        this.preferencesItem.setText("Preferences");
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

                ATPTournamentParser parser = new ATPTournamentParser();
                parser.parseTournamentURL(url);
                parser.fillDrawRowList();
                parser.fillPlayerRowList();
                
                TennisData data = new TennisData(parser.drawRowList, parser.playerRowList);
                TennisDatabase db = new TennisDatabase();
                db.saveDrawToDb(data);

                JOptionPane.showMessageDialog(MainWindow.this, "Bracket has been written to DB");
            }
        });

        this.convertHTMLToDbItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ConvertHTMLDialog dialog = new ConvertHTMLDialog();
                dialog.setVisible(true);
            }
        });

        this.loadBracketItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                LoadBracketDialog dialog = new LoadBracketDialog();
                dialog.setVisible(true);
            }
        });

        this.preferencesItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                PreferencesDialog dialog = new PreferencesDialog();
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
        this.fileMenu.add(this.convertHTMLToDbItem);
        this.fileMenu.add(this.loadBracketItem);
        this.editMenu = new JMenu();
        this.editMenu.setText("Edit");
        this.editMenu.add(this.preferencesItem);
        this.predictionsMenu = new JMenu();
        this.predictionsMenu.setText("Predictions");
        this.predictionsMenu.add(this.savePredictionsItem);
        this.predictionsMenu.add(this.loadPredictionsItem);

        // menu bar
        this.menuBar = new JMenuBar();
        this.menuBar.add(this.fileMenu);
        this.menuBar.add(this.editMenu);
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