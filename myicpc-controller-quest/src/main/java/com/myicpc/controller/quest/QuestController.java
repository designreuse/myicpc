package com.myicpc.controller.quest;

import com.myicpc.controller.GeneralController;
import com.myicpc.model.contest.Contest;
import com.myicpc.model.quest.QuestChallenge;
import com.myicpc.model.quest.QuestParticipant;
import com.myicpc.model.quest.QuestSubmission;
import com.myicpc.model.teamInfo.ContestParticipant;
import com.myicpc.repository.quest.QuestChallengeRepository;
import com.myicpc.repository.quest.QuestSubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceResolver;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.mobile.device.site.SitePreference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Roman Smetana
 */
@Controller
public class QuestController extends GeneralController {

    @Autowired
    private QuestSubmissionRepository submissionRepository;

    @Autowired
    private QuestChallengeRepository challengeRepository;

    @RequestMapping(value = "/{contestCode}/quest", method = RequestMethod.GET)
    public String questHomepage(@PathVariable final String contestCode, Model model) {
        Contest contest = getContest(contestCode, model);

        List<QuestChallenge> challenges = challengeRepository.findByContestAvailableChallenges(new Date(), contest);
        List<QuestSubmission> list = submissionRepository.getVoteInProgressSubmissions(contest);
        if (list.size() >= 4) {
            model.addAttribute("voteCandidates", list);
        }

        model.addAttribute("challenges", challenges);

        return "quest/quest";
    }

    @RequestMapping(value = "/{contestCode}/quest/challenges", method = RequestMethod.GET)
    public String questChallenges(@PathVariable final String contestCode, Model model, SitePreference sitePreference) {
        Contest contest = getContest(contestCode, model);

        model.addAttribute("challenges", challengeRepository.findOpenChallengesByContestOrderByName(new Date(), contest));

        return resolveView("quest/challenges", "quest/challenges_mobile", sitePreference);
    }

    @RequestMapping(value = "/{contestCode}/quest/challenge/{challengeId}", method = RequestMethod.GET)
    public String questChallengeDetail(@PathVariable final String contestCode, @PathVariable String challengeId, Model model, RedirectAttributes redirectAttributes, final Device device) {
        try {
            initiateChallengeDetailModel(challengeId, contestCode, model, device);
        } catch (EntityNotFoundException e) {
            errorMessage(redirectAttributes, "quest.challenge.notFound");
            return "redirect:" + getContestURL(contestCode) + "/quest/challenges";
        }
        model.addAttribute("hideTitle", true);
        return "quest/challengeDetail";
    }

    @RequestMapping(value = "/{contestCode}/quest/ajax/challenge/{challengeId}", method = RequestMethod.GET)
    public String questChallengeAjaxDetail(@PathVariable final String contestCode, @PathVariable String challengeId, Model model, RedirectAttributes redirectAttributes, final Device device) {
        try {
            initiateChallengeDetailModel(challengeId, contestCode, model, device);
        } catch (EntityNotFoundException e) {
            errorMessage(redirectAttributes, "quest.challenge.notFound");
            return "redirect:" + getContestURL(contestCode) + "/quest/challenges";
        }
        return "quest/fragment/challengeDetail";
    }

    private void initiateChallengeDetailModel(final String challengeId, final String contestCode, final Model model, Device device) {
        Contest contest = getContest(contestCode, model);

        QuestChallenge challenge;
        try {
            Long id = Long.parseLong(challengeId);
            challenge = challengeRepository.findOne(id);
        } catch (Exception ex) {
            String code = challengeId.substring(contest.getQuestConfiguration().getHashtagPrefix().length());
            challenge = challengeRepository.findByHashtagSuffix(code);
        }
        if (challenge == null) {
            throw new EntityNotFoundException();
        }
        model.addAttribute("device", device);
        model.addAttribute("challenge", challenge);
        model.addAttribute("acceptedSubmissions", mock(8));
        model.addAttribute("pendingSubmissions", mock(4));
        model.addAttribute("rejectedSubmissions", mock(2));
    }


    private List<QuestSubmission> mock(int g) {
        List<QuestSubmission> submissions = new ArrayList<>();

        Object[] data = new Object[] {
                "Roman",  "Smetana", "https://instagramimages-a.akamaihd.net/profiles/profile_4396850_75sq_1374919240.jpg"
        };

        QuestSubmission submission = new QuestSubmission();
        QuestParticipant questParticipant = new QuestParticipant();
        ContestParticipant contestParticipant = new ContestParticipant();
        contestParticipant.setFirstname((String) data[0]);
        contestParticipant.setLastname((String) data[1]);
        contestParticipant.setProfilePictureUrl((String) data[2]);
        questParticipant.setContestParticipant(contestParticipant);
        submission.setParticipant(questParticipant);


        for (int i = 0; i < g; i++) {
            submissions.add(submission);
        }

        return submissions;
    }

}