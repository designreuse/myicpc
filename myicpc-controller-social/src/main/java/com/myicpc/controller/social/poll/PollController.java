package com.myicpc.controller.social.poll;

import com.google.gson.JsonArray;
import com.myicpc.commons.utils.CookieUtils;
import com.myicpc.controller.GeneralController;
import com.myicpc.enums.NotificationType;
import com.myicpc.model.contest.Contest;
import com.myicpc.model.poll.Poll;
import com.myicpc.model.social.Notification;
import com.myicpc.repository.social.NotificationRepository;
import com.myicpc.social.poll.PollService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.site.SitePreference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author Roman Smetana
 */
@Controller
public class PollController extends GeneralController {

    @Autowired
    private PollService pollService;

    @Autowired
    private NotificationRepository notificationRepository;

    @RequestMapping(value = "/{contestCode}/polls", method = RequestMethod.GET)
    public String polls(@PathVariable final String contestCode, Model model,
                                @CookieValue(value = "answeredPolls", required = false) String answeredPollsCookie,
                                HttpServletRequest request, HttpServletResponse response, SitePreference sitePreference) {
        Contest contest = getContest(contestCode, model);

        Set<Long> answeredPolls = CookieUtils.getCookieAsLongs(answeredPollsCookie);
        List<Poll> polls = pollService.getOpenPollsWithOptions(contest, new Date());
        JsonArray jsonData = pollService.getPollChartData(polls, answeredPolls);

        model.addAttribute("polls", polls);
        model.addAttribute("pollData", jsonData.toString());
        model.addAttribute("sideMenuActive", "poll");

        return resolveView("poll/polls", "poll/polls_mobile", sitePreference);
    }

    @RequestMapping(value = "/{contestCode}/poll/submit-answer", method = RequestMethod.POST)
    public void submitPollAnswer(@PathVariable final String contestCode, @RequestParam Long pollId, @RequestParam Long optionId,
                                 @CookieValue(value = "answeredPolls", required = false) String answeredPolls,
                                 @CookieValue(value = "ignoreFeaturedNotifications", required = false) String ignoreFeaturedNotifications,
                                 HttpServletRequest request, HttpServletResponse response) {
        answerPoll(pollId, optionId, answeredPolls, ignoreFeaturedNotifications, request, response);
    }

    @RequestMapping(value = "/{contestCode}/poll/submit-answer-redirect", method = RequestMethod.POST)
    public String submitPollAnswerRedirect(@PathVariable final String contestCode, @RequestParam Long pollId, @RequestParam Long optionId,
                                 @CookieValue(value = "answeredPolls", required = false) String answeredPolls,
                                 @CookieValue(value = "ignoreFeaturedNotifications", required = false) String ignoreFeaturedNotifications,
                                 HttpServletRequest request, HttpServletResponse response) {
        answerPoll(pollId, optionId, answeredPolls, ignoreFeaturedNotifications, request, response);
        return "redirect:" + getContestURL(contestCode) + "/polls#/" + pollId;
    }

    private void answerPoll(Long pollId, Long optionId, String answeredPolls, String ignoreFeaturedNotifications,
                            HttpServletRequest request, HttpServletResponse response) {
        pollService.addVoteToPoll(pollId, optionId);

        CookieUtils.appendIdToCookie(answeredPolls, "answeredPolls", pollId.toString(), request, response);

        List<Notification> notifications = notificationRepository.findByEntityIdAndNotificationType(pollId, NotificationType.POLL_OPEN);
        String cookie = "";
        for (Notification notification : notifications) {
            if (StringUtils.isEmpty(ignoreFeaturedNotifications)) {
                cookie = notification.getId() + "";
            } else {
                cookie = ignoreFeaturedNotifications + "," + notification.getId();
            }
        }
        CookieUtils.setCookie(request, response, "ignoreFeaturedNotifications", cookie);
    }

    @RequestMapping(value = "/{contestCode}/poll/overview-template", method = RequestMethod.GET)
    public String overviewTemplate(@PathVariable String contestCode, Model model) {
        return "poll/template/overview";
    }

    @RequestMapping(value = "/{contestCode}/poll/detail-template", method = RequestMethod.GET)
    public String detailTemplate(@PathVariable String contestCode, Model model) {
        return "poll/template/detail";
    }
}
