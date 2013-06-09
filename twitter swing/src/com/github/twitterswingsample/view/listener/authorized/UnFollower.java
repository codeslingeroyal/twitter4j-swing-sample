package com.github.twitterswingsample.view.listener.authorized;

import com.github.twitterswingsample.view.panels.ConsolePanel;
import com.github.twitterswingsample.view.panels.TwitterUserPanel;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

/**
 * ActionListener that unfollows another user
 * 
 * @author multiprogger
 */
public class UnFollower extends AuthorizedAction{

	private TwitterUserPanel panel;
	private User user;
	
	public UnFollower(Twitter twitter, User user, TwitterUserPanel panel) {
		super(twitter);
		this.panel = panel;
		this.user = user;
	}

	public void run() {
		try {
			getTwitter().destroyFriendship(user.getId());
			panel.followStatusChanged();
		} catch (TwitterException e) {
			ConsolePanel.getInstance().printMessage(new String[]{
					"unable to unfollow @" + user.getScreenName()
			});
		}
	}
}