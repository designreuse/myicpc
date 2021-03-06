package com.myicpc.master.bean;

import com.myicpc.master.HATimerService;
import com.myicpc.master.dao.ContestDao;
import com.myicpc.master.exception.AuthenticationException;
import com.myicpc.master.exception.WebServiceException;
import com.myicpc.master.service.quest.QuestService;
import com.myicpc.master.service.social.TwitterService;
import com.myicpc.master.service.social.VineService;
import com.myicpc.model.contest.Contest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A simple example to demonstrate a implementation of a cluster-wide singleton timer.
 *
 * @author <a href="mailto:wfink@redhat.com">Wolf-Dieter Fink</a>
 */
@Singleton(name = "SocialSchedulerBean")
@Local(IMasterBean.class)
public class SocialSchedulerBean implements IMasterBean {
    private static final Logger logger = LoggerFactory.getLogger(HATimerService.class);
    private static final ConcurrentMap<Long, String> hashtagMapping = new ConcurrentHashMap<>();

    private final AtomicBoolean started = new AtomicBoolean(false);

    @Resource
    private TimerService timerService;

    @Inject
    private ContestDao contestDao;

    @Inject
    private TwitterService twitterService;

    @Inject
    private VineService vineService;

    @Inject
    private QuestService questService;

    @Timeout
    public void scheduler(Timer timer) {
        List<Contest> activeContests = contestDao.getActiveContests();
        for (Contest contest : activeContests) {
            String expectedHashtag = hashtagMapping.get(contest.getId());
            handleHashtagUpdate(expectedHashtag, contest);

            processVineUpdates(contest);
            processOpenQuestChallenge(contest);
        }

        logger.info("HASingletonTimer: Info=" + timer.getInfo());
        started.set(true);
    }

    @Override
    public void initialize() {
        List<Contest> activeContests = contestDao.getActiveContests();
        for (Contest contest : activeContests) {
            twitterService.startTwitterStreaming(contest);
            hashtagMapping.put(contest.getId(), contest.getHashtag());
        }

        ScheduleExpression sexpr = new ScheduleExpression();
        // set schedule to every 30 seconds for demonstration
        sexpr.hour("*").minute("*").second("0/30");
        // persistent must be false because the timer is started by the HASingleton service
        timerService.createCalendarTimer(sexpr, new TimerConfig("ahoj", false));
    }

    @Override
    public void stop() {
        logger.info("Stop all existing HASingleton timers");
        for (Timer timer : timerService.getTimers()) {
            logger.trace("Stop HASingleton timer: " + timer.getInfo());
            timer.cancel();
        }
        twitterService.stopAllTwitterStreams();
        started.set(false);
    }

    @Override
    public AtomicBoolean getStarted() {
        return started;
    }

    private void handleHashtagUpdate(String expectedHashtag, Contest contest) {
        // hashtag is not known, new contest
        if (expectedHashtag == null) {
            twitterService.startTwitterStreaming(contest);
            hashtagMapping.put(contest.getId(), contest.getHashtag());
            return;
        }
        // hashtag has changed
        if (!contest.getHashtag().equalsIgnoreCase(expectedHashtag)) {
            twitterService.stopTwitterStreaming(contest);
            twitterService.startTwitterStreaming(contest);
            hashtagMapping.put(contest.getId(), contest.getHashtag());
            return;
        }
    }

    private void processVineUpdates(final Contest contest) {
        try {
            vineService.getNewPosts(contest);
        } catch (WebServiceException e) {
            logger.error("An error occurred during parsing the Vine response", e);
        } catch (AuthenticationException e) {
            logger.error("Vine login failed.", e);
        }
    }

    private void processOpenQuestChallenge(final Contest contest) {
        questService.processNewOpenQuestChallenges(contest);
    }
}
