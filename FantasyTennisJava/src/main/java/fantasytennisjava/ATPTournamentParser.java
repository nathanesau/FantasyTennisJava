package fantasytennisjava;

import java.util.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import java.io.*;
import org.apache.commons.lang3.tuple.*;

// https://stackabuse.com/web-scraping-the-java-way/
// This class is designed to parse the draw for an individual ATP tournament
public class ATPTournamentParser {

    // parsed info
    private ArrayList<String> round1Players;
    private Map<String, String> countryDict;
    private Map<String, String> seedDict;
    private ArrayList<String> winPlayers;
    private Map<String, Integer> numWinsDict;

    // output variables
    public ArrayList<Triple<Integer, String, String>> drawRowList; // round, player1, player 2
    public ArrayList<Triple<String, String, String>> playerRowList; // player, seed, country

    ATPTournamentParser() {
        this.round1Players = new ArrayList<String>();
        this.countryDict = new HashMap<String, String>();
        this.seedDict = new HashMap<String, String>();
        this.winPlayers = new ArrayList<String>();
        this.numWinsDict = new HashMap<String, Integer>();

        this.drawRowList = new ArrayList<Triple<Integer, String, String>>();
        this.playerRowList = new ArrayList<Triple<String, String, String>>();
    }

    private void parseRound1Info(Element boxTag) {
        for (Element trTag : boxTag.getElementsByTag("tr")) {
            // parsed variables
            String playerName = "";
            String playerCountry = "";
            String seed = "";

            // read parsed information
            Elements aTags = trTag.getElementsByTag("a");
            if (!aTags.isEmpty()) {
                playerName = aTags.first().attr("data-ga-label");
                Elements imgTags = trTag.getElementsByTag("img");
                if (!imgTags.isEmpty()) {
                    playerCountry = imgTags.first().attr("src");
                }
            } else {
                playerName = "bye";
                playerCountry = "";
            }

            Elements spanTags = trTag.getElementsByTag("span");
            if (!spanTags.isEmpty()) {
                seed = spanTags.text();
                String[] substrPatterns = { "\n", "\t", "<", ">", "span", "\\", "/", "(", ")" };
                for (String substr : substrPatterns) {
                    seed = seed.replace(substr, "");
                }
            }

            // write parsed information
            round1Players.add(playerName);
            if (!playerName.isEmpty() && !playerCountry.isEmpty()) {
                countryDict.put(playerName, playerCountry);
            }
            if (!playerName.isEmpty() && !seed.isEmpty()) {
                seedDict.put(playerName, seed);
            }
        }
    }

    private void parseRoundXInfo(Element boxTag) {
        // parsed variables
        String winPlayerName = "";

        // read parsed information
        Elements aTags = boxTag.getElementsByTag("a");
        if (!aTags.isEmpty()) { // only true if match has happened
            winPlayerName = aTags.first().attr("data-ga-label");
        } else {
            winPlayerName = "unknown";
        }

        // write parsed information
        winPlayers.add(winPlayerName);
    }

    public void parseTournamentURL(String URL) {
        try {
            Document doc = Jsoup.connect(URL).get();

            for (Element boxTag : doc.getElementsByClass("scores-draw-entry-box")) {
                Elements tableTags = boxTag.getElementsByTag("table");

                if (!tableTags.isEmpty()) { // round 1
                    this.parseRound1Info(boxTag);
                } else { // round 2, ...
                    this.parseRoundXInfo(boxTag);
                }
            }

            // numWinsDict
            for(String playerName : round1Players) {
                int numWins = Collections.frequency(winPlayers, playerName);
                numWinsDict.put(playerName, numWins);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getWinPlayerThisRound(Pair<String, String> playerPair, int roundNum) {
        int leftWins = numWinsDict.getOrDefault(playerPair.getLeft(), 0);
        int rightWins = numWinsDict.getOrDefault(playerPair.getRight(), 0);
        
        if(leftWins >= roundNum) {
            return playerPair.getLeft();
        } else if (rightWins >= roundNum) {
            return playerPair.getRight();
        } else {
            return "unknown";
        }
    }

    private int getNumPlayersThisRound(int drawSize, int roundNum) {
        return (int)((double) drawSize / (int) Math.pow(2.0, (double) roundNum));
    }

    public void fillDrawRowList() {
        int drawSize = this.round1Players.size();
        int numRounds = (int) (Math.log((double) drawSize) / Math.log(2.0));
        assert(drawSize == 8 || drawSize == 16 || drawSize == 32 || drawSize == 64 || drawSize == 128);

        ArrayList<ArrayList<Pair<String, String>>> roundInfo = new ArrayList<ArrayList<Pair<String, String>>>();

        for(int roundNum = 0; roundNum < numRounds; roundNum++) {
            roundInfo.add(new ArrayList<Pair<String, String>>());
            int numPlayersThisRound = this.getNumPlayersThisRound(drawSize, roundNum);

            if(roundNum == 0) { // round 1
                for(int i = 0; i < numPlayersThisRound; i += 2) {
                    String player1Name = round1Players.get(i);
                    String player2Name = round1Players.get(i+1);
                    Pair<String, String> playerPair = Pair.of(player1Name, player2Name);
                    roundInfo.get(roundNum).add(playerPair);
                }
            } else { // round 2, ...
                if(numPlayersThisRound > 1) {
                    for(int i = 0; i < numPlayersThisRound; i += 2) {
                        Pair<String, String> pair1 = roundInfo.get(roundNum - 1).get(i);
                        Pair<String, String> pair2 = roundInfo.get(roundNum - 1).get(i+1);
                        String winner1 = this.getWinPlayerThisRound(pair1, roundNum);
                        String winner2 = this.getWinPlayerThisRound(pair2, roundNum);
                        Pair<String, String> winnerPair = Pair.of(winner1, winner2);
                        roundInfo.get(roundNum).add(winnerPair);
                    }
                } else { // last round
                    Pair<String, String> pair = roundInfo.get(roundNum- 1).get(0);
                    String winner = this.getWinPlayerThisRound(pair, roundNum);
                    Pair<String, String> winnerPair = Pair.of(winner, null);
                    roundInfo.get(roundNum).add(winnerPair);
                }
            }
        }

        // drawRowList
        for(int roundNum = 0; roundNum < numRounds; roundNum++) {
            if(roundNum != numRounds - 1) {
                for(int i = 0; i < roundInfo.get(roundNum).size(); i++) {
                    String player1Name = roundInfo.get(roundNum).get(i).getLeft();
                    String player2Name = roundInfo.get(roundNum).get(i).getRight();
                    drawRowList.add(Triple.of(roundNum+1, player1Name, player2Name));
                }
            } else {
                // last round
                String playerName = roundInfo.get(roundNum).get(0).getLeft();
                drawRowList.add(Triple.of(roundNum+1, playerName, ""));
            }
        }
    }

    public void fillPlayerRowList() {
        for(String playerName : round1Players) {
            String seed = seedDict.getOrDefault(playerName, "");
            String country = countryDict.getOrDefault(playerName, "");
            playerRowList.add(Triple.of(playerName, seed, country));
        }
    }
}