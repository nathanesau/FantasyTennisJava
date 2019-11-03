package fantasytennisjava;

import java.util.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import java.io.*;

// This class is designed to parse the list of tournaments from ATP archive
// The tournament selected by user will be parsed using ATPTournamentParser class
public class ATPArchiveParser {

    // output variables
    Map<String, String> tournamentList; // key: tournament title, value: tournament URL

    ATPArchiveParser() {
        this.tournamentList = new HashMap<String, String>();
    }

    void parseArchiveURL(String URL) {
        try {
            Document doc = Jsoup.connect(URL).get();

            for(Element trTag : doc.getElementsByTag("tr")) {
                for (Element tourneyResultTag : trTag.getElementsByClass("tourney-result")) {
                    String tourneyTitle = "";
                    String tourneyLink = "";

                    // tournament title
                    Elements spanTags = tourneyResultTag.getElementsByTag("span");
                    for(Element spanTag : spanTags) {
                        Elements tourneyTitleTags = spanTag.getElementsByClass("tourney-title");
                        Element tourneyTitleTag = tourneyTitleTags.first();
                        if (tourneyTitleTag != null) {
                            tourneyTitle = tourneyTitleTag.text();
                            break;
                        }
                    }

                    // tourney link
                    Elements linkTags = tourneyResultTag.select("a");
                    for(Element linkTag : linkTags) {
                        String link = linkTag.attr("href");
                        if (link.contains("singles")) {
                            tourneyLink = link;
                            break;
                        }
                    }

                    if (!tourneyTitle.isEmpty() && !tourneyLink.isEmpty()) {
                        tournamentList.put(tourneyTitle, tourneyLink);
                    }
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}