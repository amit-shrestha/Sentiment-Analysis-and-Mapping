package twitterauthentication;

import twitter4j.conf.ConfigurationBuilder;
public class TwitterConnection {
	public static ConfigurationBuilder getConfBuilder() {
		ConfigurationBuilder confBuilder= new ConfigurationBuilder();
		confBuilder.setDebugEnabled(true)
		.setOAuthConsumerKey("1GyfJM9W9OSFLS2UYHtY14ZlZ")
		.setOAuthConsumerSecret("PcOhOJJQMDhKc2QiIohA94GI0rD9U2GJuZzvojczJAebugLym8")
		.setOAuthAccessToken("715830749618958336-hAoaoHyifLLVLkKQ70rdbevtaHu4H0e")
		.setOAuthAccessTokenSecret("rAU3KP8Qrw1dly4Cczt6z6HH81tMZGjpnwsx3zP0xET6i");
		
		return confBuilder;
	}
}
