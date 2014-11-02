package com.myicpc.repository.quest;

import com.myicpc.model.contest.Contest;
import com.myicpc.model.quest.QuestChallenge;
import com.myicpc.model.quest.QuestParticipant;
import com.myicpc.model.quest.QuestSubmission;
import com.myicpc.model.teamInfo.TeamMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface QuestSubmissionRepository extends PagingAndSortingRepository<QuestSubmission, Long> {
    @Query("SELECT qs FROM QuestSubmission qs WHERE qs.submissionState = 'ACCEPTED' AND qs.voteSubmissionState = 'VOTE_WINNER' AND qs.challenge.contest = ?1")
    List<QuestSubmission> getVoteWinnersSubmissions(Contest contest);

    @Query("SELECT qs FROM QuestSubmission qs WHERE qs.submissionState = 'ACCEPTED' AND qs.voteSubmissionState = 'IN_PROGRESS' AND qs.challenge.contest = ?1")
    List<QuestSubmission> getVoteInProgressSubmissions(Contest contest);

    @Query("SELECT qs FROM QuestSubmission qs WHERE qs.participant = ?1 AND qs.submissionType != 'INVISIBLE' ORDER BY qs.created DESC")
    List<QuestSubmission> getLatestSubmisionsByParticipant(QuestParticipant participant, Pageable pageable);

    @Query("SELECT COUNT(qs) FROM QuestSubmission qs WHERE qs.voteSubmissionState = 'VOTE_WINNER' AND qs.challenge.contest = ?1")
    Long countWinningSubmissions(Contest contest);

    // -----
    @Query("SELECT qs FROM QuestSubmission qs ORDER BY qs.created DESC")
    Page<QuestSubmission> findAllOrderByCreated(Pageable pageable);

    QuestSubmission findByChallengeAndParticipant(QuestChallenge challenge, QuestParticipant participant);

    List<QuestSubmission> findBySubmissionState(QuestSubmission.QuestSubmissionState submissionState);

    List<QuestSubmission> findByExternalId(String externalId);

    @Query("SELECT qs FROM QuestSubmission qs WHERE qs.submissionType != 'INVISIBLE' ORDER BY RANDOM()")
    List<QuestSubmission> getRandomQuestSubmissions(Pageable pageable);

    @Query("SELECT qs FROM QuestSubmission qs WHERE qs.submissionState = ?1 ORDER BY RANDOM()")
    List<QuestSubmission> getRandomQuestSubmissions(QuestSubmission.QuestSubmissionState submissionState, Pageable pageable);

    @Query("SELECT qs FROM QuestSubmission qs WHERE qs.submissionState = 'ACCEPTED' AND qs.voteSubmissionState = 'VOTE_WINNER'")
    List<QuestSubmission> getVoteWinnersSubmissions();

    @Query("SELECT qs FROM QuestSubmission qs WHERE qs.submissionState = 'ACCEPTED' AND qs.voteSubmissionState = 'IN_PROGRESS'")
    List<QuestSubmission> getVoteInProgressSubmissions();

    @Query("SELECT qs FROM QuestSubmission qs WHERE qs.submissionState = 'ACCEPTED' AND qs.submissionType != 'INVISIBLE' AND qs.voteSubmissionState IS NULL ORDER BY RANDOM()")
    List<QuestSubmission> getVoteEligableSubmissions(Pageable pageable);

    @Query("SELECT COUNT(qs) FROM QuestSubmission qs WHERE qs.participant.id = ?1")
    Long countSubmissionByParticipant(Long questParticipantId);

    @Query("SELECT qs FROM QuestSubmission qs WHERE qs.challenge = ?1 AND qs.submissionState = 'ACCEPTED' ORDER BY qs.created DESC")
    List<QuestSubmission> getAcceptedSubmissionByChallenge(QuestChallenge challenge);

    @Query("SELECT qs FROM QuestSubmission qs WHERE qs.challenge = ?1 AND qs.submissionState = 'ACCEPTED' ORDER BY qs.created DESC")
    List<QuestSubmission> getAcceptedSubmissionByChallenge(QuestChallenge challenge, Pageable pageable);

    Page<QuestSubmission> findBySubmissionStateOrderByCreatedDesc(QuestSubmission.QuestSubmissionState submissionState, Pageable pageable);

    Page<QuestSubmission> findByChallengeOrderByCreatedDesc(QuestChallenge challenge, Pageable pageable);

    Page<QuestSubmission> findByParticipantOrderByCreatedDesc(QuestParticipant participant, Pageable pageable);

    Page<QuestSubmission> findBySubmissionStateAndChallengeOrderByCreatedDesc(QuestSubmission.QuestSubmissionState submissionState, QuestChallenge challenge,
                                                                              Pageable pageable);

    Page<QuestSubmission> findBySubmissionStateAndParticipantOrderByCreatedDesc(QuestSubmission.QuestSubmissionState submissionState, QuestParticipant participant,
                                                                                Pageable pageable);

    Page<QuestSubmission> findByParticipantAndChallengeOrderByCreatedDesc(QuestParticipant participant, QuestChallenge challenge, Pageable pageable);

    Page<QuestSubmission> findBySubmissionStateAndParticipantAndChallengeOrderByCreatedDesc(QuestSubmission.QuestSubmissionState submissionState,
                                                                                            QuestParticipant participant, QuestChallenge challenge, Pageable pageable);

    @Query("SELECT COUNT(qs) FROM QuestSubmission qs WHERE qs.voteSubmissionState = 'VOTE_WINNER'")
    Long countWinningSubmissions();

    List<QuestSubmission> findByParticipantTeamMemberOrderByIdDesc(TeamMember tm);
}