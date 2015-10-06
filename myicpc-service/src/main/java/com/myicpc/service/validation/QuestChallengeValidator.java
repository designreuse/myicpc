package com.myicpc.service.validation;

import com.myicpc.model.quest.QuestChallenge;
import com.myicpc.repository.quest.QuestChallengeRepository;
import com.myicpc.service.exception.BusinessValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Business validator for {@link QuestChallenge}
 *
 * @author Roman Smetana
 */
@Component
public class QuestChallengeValidator extends BusinessEntityValidator<QuestChallenge> {

    @Autowired
    private QuestChallengeRepository challengeRepository;

    @Override
    public void validate(QuestChallenge challenge) throws BusinessValidationException {
        QuestChallenge duplicated = challengeRepository.findByHashtagSuffixAndContest(challenge.getHashtagSuffix(), challenge.getContest());

        if (duplicated != null && !duplicated.getId().equals(challenge.getId())) {
            throw new BusinessValidationException("quest.error.duplicatedHashtag");
        }
    }
}
