package fantasytennisjava;

import java.util.*;
import org.apache.commons.lang3.tuple.*;

public class TennisData {

    // database variables
    ArrayList<Triple<Integer, String, String>> drawRowList; // round, player1, player2
    ArrayList<Triple<String, String, String>> playerRowList; // player, seed, country

    TennisData(ArrayList<Triple<Integer, String, String>> drawRowList,
            ArrayList<Triple<String, String, String>> playerRowList) {
        this.drawRowList = drawRowList;
        this.playerRowList = playerRowList;
    }
}