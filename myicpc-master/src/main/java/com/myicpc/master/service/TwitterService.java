package com.myicpc.master.service;

import com.myicpc.enums.NotificationType;
import com.myicpc.master.HATimerService;
import com.myicpc.model.contest.Contest;
import com.myicpc.model.social.BlacklistedUser;
import com.myicpc.model.social.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusAdapter;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Roman Smetana
 */
@Singleton
public class TwitterService extends GeneralService {
    private static final Logger logger = LoggerFactory.getLogger(TwitterService.class);

    public void startTwitterStreaming(Contest contest) {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setOAuthConsumerKey(contest.getWebServiceSettings().getTwitterConsumerKey())
                .setOAuthConsumerSecret(contest.getWebServiceSettings().getTwitterConsumerSecret())
                .setOAuthAccessToken(contest.getWebServiceSettings().getTwitterAccessToken())
                .setOAuthAccessTokenSecret(contest.getWebServiceSettings().getTwitterAccessTokenSecret());
        TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
        twitterStream.addListener(new TwitterStatusListener(contest));
        twitterStream.filter(new FilterQuery(0, null, new String[] { "#" + contest.getHashtag() }));

    }

    class TwitterStatusListener extends StatusAdapter {
        private Contest contest;

        public TwitterStatusListener(Contest contest) {
            this.contest = contest;
        }

        @Override
        public void onException(final Exception ex) {
            logger.error(ex.getMessage(), ex);
        }

        @Override
        public void onStatus(Status status) {
            Notification notification = new Notification();
            notification.setTitle(status.getUser().getScreenName());
            notification.setBody(parseTweetText(status));
            notification.setHashtags(getHashtagsFromTweet(status.getText()));
            notification.setNotificationType(NotificationType.TWITTER);
            notification.setExternalId(String.valueOf(status.getId()));
            if (status.getRetweetedStatus() != null) {
                notification.setRetweetedId(status.getRetweetedStatus().getId());
            }
            notification.setAuthorName(status.getUser().getName());
            notification.setProfilePictureUrl(status.getUser().getProfileImageURL());
            notification.setTimestamp(status.getCreatedAt());
            notification.setContest(contest);

            // send the received notification
            TwitterService.this.sendSocialNotification(notification);
        }

        /**
         * Finds Twitter hashtags, usernames, and URLs in the tweet
         *
         * @param status
         *            Twitter tweet
         * @return tweet message enhanced by HTML tags
         */
        protected String parseTweetText(final Status status) {
            String text = null;
            if (status.isRetweet() && status.getRetweetedStatus() != null) {
                text = "RT @" + status.getRetweetedStatus().getUser().getScreenName() + ": " + status.getRetweetedStatus().getText();
            } else {
                text = status.getText();
            }
            text = text.replaceAll("((https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|])", "<a href='$1'>$1</a>");
            text = text.replaceAll("@([\\p{L}+0-9-_]+)", "<a href='http://twitter.com/$1'>@$1</a>");
            text = text.replaceAll("#([\\p{L}+0-9-_]+)", "<a href='https://twitter.com/search/?src=hash&amp;q=%23$1'>#$1</a>");
            text = text.replaceAll("([\\ud800-\\udbff\\udc00-\\udfff])", "");
            return text;
        }

        /**
         * Gets all hashtags from the tweet body separated by |
         *
         * @param tweet
         *            tweet body
         * @return hashtags separated by |
         */
        protected String getHashtagsFromTweet(final String tweet) {
            Pattern pattern = Pattern.compile("(#[\\p{L}+0-9-_]+)");
            Matcher matcher = pattern.matcher(tweet);
            StringBuilder hashtags = new StringBuilder("|");
            while (matcher.find()) {
                hashtags.append(matcher.group().substring(1)).append("|");
            }
            return hashtags.toString();
        }

    }
}
