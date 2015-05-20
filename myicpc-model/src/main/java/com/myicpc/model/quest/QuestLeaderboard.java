package com.myicpc.model.quest;

import com.myicpc.enums.ContestParticipantRole;
import com.myicpc.model.IdGeneratedContestObject;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

/**
 * A leader board shows the score of the participants. Each leader board groups
 * {@link com.myicpc.model.quest.QuestParticipant} by its {@link com.myicpc.enums.ContestParticipantRole}
 *
 * @author Roman Smetana
 */
@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "idgen", sequenceName = "QuestLeaderboard_id_seq")
public class QuestLeaderboard extends IdGeneratedContestObject {
    private static final long serialVersionUID = -7014437099443310905L;
    private static final String SEPARATOR = ",";

    /**
     * Leaderboard name
     */
    private String name;
    /**
     * Code used in URL
     */
    private String urlCode;

    /**
     * Allowed participant roles
     */
    private String roles;

    private boolean published;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlCode() {
        return urlCode;
    }

    public void setUrlCode(String urlCode) {
        this.urlCode = urlCode;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    @Transient
    public String[] getParsedRoles() {
        if (roles == null) {
            return null;
        }
        return roles.split(SEPARATOR);
    }

    @Transient
    public ContestParticipantRole[] getContestParticipantRoles() {
        if (roles == null) {
            return null;
        }
        String[] names = roles.split(SEPARATOR);
        ContestParticipantRole[] roles = new ContestParticipantRole[names.length];
        for (int i = 0; i < roles.length; i++) {
            try {
                roles[i] = ContestParticipantRole.valueOf(names[i]);
            } catch (IllegalArgumentException ex) {
                // ignore
            }
        }
        return roles;
    }

    @Transient
    public void setParsedRoles(String[] parsedRoles) {
        this.roles = StringUtils.join(parsedRoles, SEPARATOR);
    }
}
