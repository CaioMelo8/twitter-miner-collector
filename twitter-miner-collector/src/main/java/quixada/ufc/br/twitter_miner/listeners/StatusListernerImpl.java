package quixada.ufc.br.twitter_miner.listeners;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

public class StatusListernerImpl implements StatusListener {
	
	private String dirpath;
	private String filename;
	private FileWriter fileWriter;
	private int count;
	
	public StatusListernerImpl(String dirpath, String filename) throws IOException {
		this.dirpath = dirpath;
		this.filename = filename;
		
		File dir = new File(dirpath);
		
		if (!dir.exists()){
			dir.mkdirs();
		}
		
		File file = new File(dirpath + "\\" + filename);
		
		if (!file.exists()){
			file.createNewFile();
		}
		
		this.fileWriter = new FileWriter(dirpath + "\\" + filename, true);
		this.count = 0;
	}
	
	public String getDirpath() {
		return dirpath;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public void onStatus(Status status) {
		try {
			String author = "@" + status.getUser().getScreenName();
			String description = status.getText();
			Boolean isRetweet = status.isRetweet();
			String retweet_description = null;
			
			if (isRetweet){
				retweet_description = status.getRetweetedStatus().getText();
			}
			
			String text =  author + "\t" + description + "\t" + retweet_description + "\t" + isRetweet;
			text = text.replace("\n", " ");
			
			System.out.println(text);
			fileWriter.write(text + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		count++;
		
		if (count >= 5){
			try {
				fileWriter.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			count = 0;
		}
	}

	public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}

	public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}

	public void onException(Exception ex) {
		ex.printStackTrace();
	}

	public void onScrubGeo(long arg0, long arg1) {}

	public void onStallWarning(StallWarning arg0) {}
}
