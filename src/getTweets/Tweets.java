package getTweets;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import analysis.Analysis;
import client.Home;
import database.DBConnection;
import filter.Filter;
import models.Variables;
import services.Functions;
import stem.Stemmer;
import twitter4j.GeoLocation;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import twitterauthentication.TwitterConnection;

public class Tweets implements Functions {
	Connection con = null;
	public Tweets() {
		con = DBConnection.getDBCon();
	}

	ConfigurationBuilder confbuild = TwitterConnection.getConfBuilder();
	TwitterFactory tf = new TwitterFactory(confbuild.build());
	twitter4j.Twitter twitter = tf.getInstance();

	@Override
	public boolean searchTopic(Variables v) {
		int i = 1;
		int limit=0;
		int remaining=0;
		int resetTime=0;
		int timeUntilReset=0;
		String topic = v.getTopic();
		String tweets;
		String longitude = null;
		String latitude = null;
		String del_sql = "delete from imported";
		Statement del_stm;
		try {
			del_stm = con.createStatement();
			del_stm.execute(del_sql);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String sql = "insert into imported(Id,Created_At,Tweet_Id,Tweets,Latitude,Longitude)values(?,?,?,?,?,?)";
		Query query = new Query(topic);
		query.setLang("en");
		QueryResult result = null;
		do {
			try {
				result = twitter.search(query);
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			RateLimitStatus rate=result.getRateLimitStatus();
			if(rate!=null)
			{
			limit=rate.getLimit();
			remaining=rate.getRemaining();
			resetTime=rate.getResetTimeInSeconds();
			timeUntilReset=rate.getSecondsUntilReset();
			System.out.println("limit= "+limit);
			System.out.println("remaining= "+remaining);
			System.out.println("reset time= "+resetTime);
			System.out.println("time until reset= "+timeUntilReset);
			Home.setLabel1("Remaining Request= "+remaining);
			Home.setLabel2("Time until Reset= "+timeUntilReset);
			}
			
			for (Status tweet : result.getTweets()) {
					
				try {
					PreparedStatement pstm = con.prepareStatement(sql);
					pstm.setInt(1, i);
					pstm.setString(2, new String(tweet.getCreatedAt().toString()));
					pstm.setLong(3, tweet.getId());
					tweets = tweet.getText();
					pstm.setString(4, tweets);
					latitude = null;
					longitude = null;
					GeoLocation geo = tweet.getGeoLocation();
					if (geo != null) {
						latitude = Double.toString(geo.getLatitude());
						longitude = Double.toString(geo.getLongitude());
					}
					pstm.setString(5, latitude);
					pstm.setString(6, longitude);
					
					i++;
					pstm.execute();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		} while ((query = result.nextQuery()) != null);
		return false;
	}

	@Override
	public List<Variables> getAllData() {
		List<Variables> tweetList = new ArrayList<Variables>();
		String sql = "select * from  imported";
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				Variables v = new Variables();
				v.setSn(rs.getInt("Id"));
				v.setCreatedAt(rs.getString("Created_At"));
				v.setTweetId(rs.getLong("Tweet_Id"));
				v.setTweets(rs.getString("Tweets"));
				tweetList.add(v);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return tweetList;
	}

	@Override
	public void filter() {
		List<String> tokenList = new ArrayList<String>();
		List<String> stopWordsRemovedList = new ArrayList<String>();
		List<String> filteredList = new ArrayList<String>();
		int id;
		String tweets;
		try {
			String read_sql = "select * from imported";
			Statement read_stm = con.createStatement();
			ResultSet rs = read_stm.executeQuery(read_sql);
			while (rs.next()) {
				id = rs.getInt("Id");
				tweets = rs.getString("Tweets").toLowerCase();
				tokenList = Filter.tokenizer(tweets);
				stopWordsRemovedList = Filter.stopWordsRemoval(tokenList);
				Iterator<String> itr = stopWordsRemovedList.iterator();
				while (itr.hasNext()) {
					String punctuationRemovedWord = Filter.punctuationsRemoval(itr.next());
					if (punctuationRemovedWord == null) {

					} else {
						String stemmedWord = Stemmer.main(punctuationRemovedWord);
						filteredList.add(stemmedWord);
					}
				}

				String update_sql = "update imported set Filtered_Tweets=? where Id='" + id + "'";
				PreparedStatement update_pstm = con.prepareStatement(update_sql);
				update_pstm.setString(1, new String(filteredList.toString()));
				update_pstm.execute();
				Analysis.main(filteredList, id);
				filteredList.clear();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
