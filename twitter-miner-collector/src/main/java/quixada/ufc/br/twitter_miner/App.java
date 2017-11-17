package quixada.ufc.br.twitter_miner;

import java.io.IOException;

import quixada.ufc.br.twitter_miner.listeners.StatusListernerImpl;
import twitter4j.FilterQuery;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws TwitterException, IOException {
		FilterQuery tweetFilterQuery = new FilterQuery();		
		tweetFilterQuery.language("en");
		tweetFilterQuery.track("super mario odyssey", "mario odyssey", "#supermarioodyssey", "#marioodyssey");
		
		String dirpath = System.getProperty("user.dir") + "\\data";
		
		String filename = "super-mario-odyssey2.tsv";
		
		StatusListener listener = new  StatusListernerImpl(dirpath, filename);
		
		TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
		twitterStream.addListener(listener);
		twitterStream.filter(tweetFilterQuery);
	}
}
