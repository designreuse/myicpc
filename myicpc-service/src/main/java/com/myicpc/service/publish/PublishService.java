package com.myicpc.service.publish;

import com.google.gson.JsonObject;
import com.myicpc.model.contest.Contest;
import com.myicpc.model.poll.Poll;
import com.myicpc.model.poll.PollOption;
import com.myicpc.model.quest.QuestSubmission;
import com.myicpc.model.social.Notification;
import com.myicpc.service.notification.NotificationService;
import org.atmosphere.cpr.AtmosphereFramework;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.util.ServletContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;

/**
 * Service responsible for broadcasting the events to SSE and WebSockets channels
 *
 * @author Roman Smetana
 */
@Service
public class PublishService {
    public static final Logger logger = LoggerFactory.getLogger(PublishService.class);

    private static final String PREFIX = "/pubsub/";
    /**
     * Web socket channel for submissions and contest related messages
     */
    public static final String SCOREBOARD_CHANNEL = "scoreboard";
    /**
     * Web socket channel for submissions and contest related messages
     */
    public static final String PROBLEM_CHANNEL = "problem";
    /**
     * Web socket channel for poll related messages
     */
    public static final String POLL = "poll";
    /**
     * Web socket channel for submissions and notification messages
     */
    public static final String NOTIFICATION = "notification";
    /**
     * Web socket channel for Quest
     */
    public static final String QUEST = "quest";
    /**
     * Web socket channel for kiosk
     */
    public static final String KIOSK = "kiosk";

    private BroadcasterFactory broadcasterFactory;

    private BroadcasterFactory getBroadcasterFactory() {
        if (broadcasterFactory == null) {
            ServletContext servletContext = ServletContextFactory.getDefault().getServletContext();
            AtmosphereFramework framework = (AtmosphereFramework) servletContext.getAttribute("AtmosphereServlet");
            broadcasterFactory = framework.getBroadcasterFactory();
        }
        return broadcasterFactory;
    }

    /**
     * Broadcast an event that refresh started
     */
    public void broadcastEventFeedRefresh(final String contestCode) {
        JsonObject refreshObject = new JsonObject();
        refreshObject.addProperty("type", "refresh");
        atmospherePublish(PREFIX + contestCode + "/" + SCOREBOARD_CHANNEL, refreshObject.toString());
    }

    /**
     * Broadcast a team submission to channel SCOREBOARD_CHANNEL
     */
    public void broadcastTeamProblem(final JsonObject teamProblemJSON, final String contestCode) {
        atmospherePublish(PREFIX + contestCode + "/" + SCOREBOARD_CHANNEL, teamProblemJSON.toString());
    }

    /**
     * Broadcast the notification to notification channel
     *
     * @param notification
     *            notification, which triggered the event
     */
    public void broadcastNotification(final Notification notification, final Contest contest) {
        if (notification.isOffensive()) {
            // ignore offensive notifications
            return;
        }
        JsonObject notificationObject = NotificationService.getNotificationInJson(notification);

        atmospherePublish(PREFIX + contest.getCode() + "/" + NOTIFICATION, notificationObject.toString());
    }

    /**
     * Broadcast the contest submissions on a problem to the problem channel
     *
     * @param teamSubmission
     *            submission, which triggered the event
     */
    public void broadcastProblem(JsonObject teamSubmission, String problemCode, String contestCode) {
        atmospherePublish(PREFIX + contestCode + "/" + PROBLEM_CHANNEL + "/" + problemCode,
                teamSubmission.toString());
    }

    /**
     * Broadcast the quest submissions on a submission change
     *
     * @param questSubmission quest submission to be broadcasted
     * @param contestCode contest code
     */
    public void broadcastQuestSubmission(QuestSubmission questSubmission, String contestCode) {
        JsonObject submissionObject = new JsonObject();
        submissionObject.addProperty("type", "submission");
        atmospherePublish(PREFIX + contestCode + "/" + QUEST, submissionObject.toString());
    }

    /**
     * Broadcast the poll updates
     *
     * @param poll poll
     * @param option selected poll option
     */
    public void broadcastPollAnswer(final Poll poll, final PollOption option) {
        JsonObject pollUpdate = new JsonObject();
        pollUpdate.addProperty("pollId", poll.getId());
        pollUpdate.addProperty("optionId", option.getId());
        pollUpdate.addProperty("optionName", option.getName());
        pollUpdate.addProperty("votes", option.getVotes());
        atmospherePublish(PREFIX + poll.getContest().getCode() + "/" + POLL, pollUpdate.toString());
    }

    /**
     * Broadcast the kiosk updates
     *
     * @param contestCode contest code
     * @param action
     */
    public void broadcastKioskPage(String contestCode, String action) {
        JsonObject kioskPageObject = new JsonObject();
        kioskPageObject.addProperty("type", action);
        atmospherePublish(PREFIX + contestCode + "/" + KIOSK, kioskPageObject.toString());
    }

    /**
     * Send a message to channel
     *
     * @param channel channel
     * @param message message
     */
    private void atmospherePublish(final String channel, final String message) {
        BroadcasterFactory broadcasterFactory = getBroadcasterFactory();
        if (broadcasterFactory != null) {
            Broadcaster broadcaster = broadcasterFactory.lookup(channel);
            if (broadcaster != null) {
                broadcaster.broadcast(message);
                return;
            }
        }
        logger.debug("Channel " + channel + " failed. Message not send: " + message);
    }
}
