package com.example.student.newsapp.Common;

import java.util.HashMap;

/**
 * Created by deepshikha on 10/3/17.
 */

public interface ConstVariable {
    String SUCCESS = "ok";
    String URL = "url";
    String BASEURL = "baseurl";
    String STATUS = "status";
    String RESULT = "result";
    String ARTICLES = "articles";


    String APIKEY = "apiKey";
    String APIKEYID = "de5ea6e82155443b9b5ceb7f5a3accdc";
    String SOURCE = "source";
    String SORTBY = "sortBy";
    String TOP="top";
    String LATEST="latest";

    String ABCNEWS = "abc-news-au";
    String ALJAZEERAENGLISH = "al-jazeera-english";
    String ARSTECHNICA = "ars-technica";
    String ASSOCIATEDPRESS = "associated-press";
    String BBCNEWS = "bbc-news";
    String BBCSPORT = "bbc-sport";
    String BILD = "bild";
    String BLOOMBERG = "bloomberg";
    String BREITBARTNEWS = "breitbart-news";
    String BUSINESSINSIDER = "business-insider";
    String BUZZFEED = "buzzfeed";
    String CNBC = "cnbc";
    String DAILYMAIL = "daily-mail";
    String CNN = "cnn";
    String ENGADGET = "engadget";
    String ENTERTAINMENTWEEKLY = "entertainment-weekly";
    String ESPN = "espn";
    String ESPNCRICINFO = "espn-cric-info";
    String FINANCIALTIMES = "financial-times";
    String FOCUS = "focus";
    String FOOTBALLITALIA = "football-italia";
    String FORTUNE = "fortune";
    String FOURFOURTWO = "four-four-two";
    String FOXSPORTS = "fox-sports";
    String HACKERNEWS = "hacker-news";
    String IGN = "ign";
    String INDEPENDENT = "independent";
    String MASHABLE = "mashable";
    String METRO = "metro";
    String MIRROR = "mirror";
    String MTVNEWS = "mtv-news";
    String NATIONALGEOGRAPHIC = "national-geographic";
    String NEWSCIENTIST = "new-scientist";
    String NEWSWEEK = "newsweek";
    String NEWYORKMAGAZINE = "new-york-magazine";
    String NFLNEWS = "nfl-news";
    String POLYGON = "polygon";
    String RECODE = "recode";
    String REDDITRALL = "reddit-r-all";
    String REUTERS = "reuters";
    String SPIEGELONLINE = "spigel-online";
    String T3N = "t3n";
    String TALKSPORT = "talksport";
    String TECHCRUNCH = "techcrunch";
    String TECHRADAR = "techradar";
    String THEECONOMIST ="the-economist";
    String THEGUARDIANAU = "the-guardian-au";
    String THEGUARDIANUK = "the-guardian-uk";
    String THEHINDU = "the-hindu";
    String THEHUFFINGTONPOST = "the-huffington-post";
    String THELADBIBLE = "the-lad-bible";
    String THENEWYORKTIMES = "the-new-york-times";
    String THENEXTWEB= "the-next-web";
    String THESPORTBIBLE = "the-sport-bible";
    String THETELEGRAPH = "the-telegraph";
    String THETIMESOFINDIA = "the-times-of-india";
    String THEVERGE = "the-verge";
    String THEWALLSTREETJOURNAL = "the-wall-street-journal";
    String THEWASHINGTONPOST = "the-washington-post";
    String TIME = "time";
    String USATODAY = "usa-today";
    String GOOGLENEWS = "google-news";;





    String CHANNELABC = "ABC News";
    String CHANNELALJAZEERAENGLISH = "Al Jazeera English";
    String CHANNELARSTECHNICA = "Ars Technica";
    String CHANNELASSOCIATEDPRESS = "Associated Press";
    String CHANNELBBCNEWS = "BBC News";
    String CHANNELBBCSPORT = "BBC Sport";
    String CHANNELbild = "Bild";
    String CHANNELBLOOMBERG = "Bloomberg";
    String CHANNELBREITBARTNEWS = "Breitbart News";
    String CHANNELBUSINESSINSIDER = "Business Insider";
    String CHANNELBUZZFEED = "Buzz Feed";
    String CHANNELCNBC = "CNBC";
    String CHANNELDAILYMAIL = "Daily Mail";
    String CHANNELCNN = "CNN";
    String CHANNELDERTAGESSPIEGEL = "Der Tagesspiegel";
    String CHANNELDIEZEIT = "Die Zeit";
    String CHANNELENGADGET = "Engadget";
    String CHANNELENTERTAINMENTWEEKLY = "Entertainment";
    String CHANNELESPN = "ESPN";
    String CHANNELESPNCRICINFO = "ESPN Cric Info";
    String CHANNELFINANCIALTIMES = "Financial Times";
    String CHANNELFOCUS = "Focus";
    String CHANNELFOOTBALLITALIA = "Football Italia";
    String CHANNELFORTUNE = "Fortune";
    String CHANNELFOURFOURTWO = "FourFourTwo";
    String CHANNELFOXSPORTS = "Fox Sports";
    String CHANNELGOOGLENEWS = "Google News";
    String CHANNELHACKERNEWS = "Hacker News";
    String CHANNELIGN ="IGN";
    String CHANNELINDEPENDENT = "Independent";
    String CHANNELMASHABLE = "Mashsable";
    String CHANNELMETRO = "Metro";
    String CHANNELMIRROR = "Mirror";
    String CHANNELMTVNEWS = "Mtv News";
    String CHANNELNATIONALGEOGRAPHIC = "National Geographic";
    String CHANNELNEWSCIENTIST = "New Scientist";
    String CHANNELNEWSWEEK = "Newsweek";
    String CHANNELNEWYORKMAGAZINE = "New York Magazine";
    String CHANNELNFLNEWS = "Nfl News";
    String CHANNELPOLYGON = "Polygon";
    String CHANNELRECODE = "Recode";
    String CHANNELREDDITRALL = "Reddit/R/All";
    String CHANNELREUTERS = "Reuters";
    String CHANNELSPIEGELONLINE = "Spigel Online";
    String CHANNELT3N = "T3n";
    String CHANNELTALKSPORT = "Talksport";
    String CHANNELTECHCRUNCH = "Techcrunch";
    String CHANNELTECHRADAR = "Techradar";
    String CHANNELTHEECONOMIST ="The Economist";
    String CHANNELTHEGUARDIANUK = "THE GUARDIAN (UK)";
    String CHANNELTHEGUARDIANAU = "THE GUARDIAN (AU)";
    String CHANNELTHEHINDU = "The Hindu";
    String CHANNELTHEHUFFINGTONPOST = "The Huffington Post";
    String CHANNELTHELADBIBLE = "The Lad Bible";
    String CHANNELTHENEWYORKTIMES = "The New York Times";
    String CHANNELTHENEXTWEB= "The Next Web";
    String CHANNELTHESPORTBIBLE = "The Sport Bible";
    String CHANNELTHETELEGRAPH = "The Telegraph";
    String CHANNELTHETIMESOFINDIA = "The Times Of India";
    String CHANNELTHEVERGE = "The Verge";
    String CHANNELTHEWALLSTREETJOURNAL = "Wall Street Journal";
    String CHANNELTHEWASHINGTONPOST = "Washington Post";
    String CHANNELTIME = "Time";
    String CHANNELUSATODAY = "Usa Today";

    //API DATA
    String URLTOIMAGE="urlToImage";
    String DESCRIPTION="description";
    String AUTHOR="author";
    String TITLE="title";
    String URL_WEB="url";
    String PUBLISHEDAT="publishedAt";

    //INTENT
    String INTENT_CHANNELSOURCE="source";
    String INTENT_CHANNELNAME="channel_name";
    String INTENT_CHANNELURL = "channel_url";


    int HOME = 0;
    int LogIN = 1;
    int ImageUpload = 2;

    void callback(HashMap<String, Object> tmpMap, String dataType, int mode);
}
