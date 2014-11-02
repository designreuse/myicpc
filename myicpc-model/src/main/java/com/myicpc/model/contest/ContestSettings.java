package com.myicpc.model.contest;

import com.myicpc.enums.FeedRunStrategyType;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

/**
 * Represents the settings for the contest
 *
 * @author Roman Smetana
 */
@Embeddable
public class ContestSettings implements Serializable {
    private static final long serialVersionUID = -7122410076109471464L;

    private String email;

    @URL
    private String eventFeedURL;
    private String eventFeedUsername;
    private String eventFeedPassword;
    @Enumerated(EnumType.STRING)
    private FeedRunStrategyType scoreboardStrategyType;
    private boolean generateMessages;
    @URL
    private String JSONScoreboardURL;

    private String editActivityURL;
    private String teamPicturesURL;

    private Integer year;
    private Integer timeDifference = 0;
    private boolean showTeamNames;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Livestream URL
     */
    @URL
    private String livestreamURL;
    /**
     * Backup URL for livestream
     */
    @URL
    private String livestreamBackupURL;
    /**
     * URL for PDF file with contest problems
     */
    @URL
    private String problemPDFURL;

    public String getEventFeedURL() {
        return eventFeedURL;
    }

    public void setEventFeedURL(String eventFeedURL) {
        this.eventFeedURL = eventFeedURL;
    }

    public String getEventFeedUsername() {
        return eventFeedUsername;
    }

    public void setEventFeedUsername(String eventFeedUsername) {
        this.eventFeedUsername = eventFeedUsername;
    }

    public String getEventFeedPassword() {
        return eventFeedPassword;
    }

    public void setEventFeedPassword(String eventFeedPassword) {
        this.eventFeedPassword = eventFeedPassword;
    }

    public FeedRunStrategyType getScoreboardStrategyType() {
        return scoreboardStrategyType;
    }

    public void setScoreboardStrategyType(FeedRunStrategyType scoreboardStrategyType) {
        this.scoreboardStrategyType = scoreboardStrategyType;
    }

    public boolean isGenerateMessages() {
        return generateMessages;
    }

    public void setGenerateMessages(boolean generateMessages) {
        this.generateMessages = generateMessages;
    }

    public String getJSONScoreboardURL() {
        return JSONScoreboardURL;
    }

    public void setJSONScoreboardURL(String JSONScoreboardURL) {
        this.JSONScoreboardURL = JSONScoreboardURL;
    }

    public String getEditActivityURL() {
        return editActivityURL;
    }

    public void setEditActivityURL(String editActivityURL) {
        this.editActivityURL = editActivityURL;
    }

    public String getTeamPicturesURL() {
        return teamPicturesURL;
    }

    public void setTeamPicturesURL(String teamPicturesURL) {
        this.teamPicturesURL = teamPicturesURL;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getTimeDifference() {
        return timeDifference;
    }

    public void setTimeDifference(Integer timeDifference) {
        this.timeDifference = timeDifference;
    }

    public boolean isShowTeamNames() {
        return showTeamNames;
    }

    public void setShowTeamNames(boolean showTeamNames) {
        this.showTeamNames = showTeamNames;
    }

    public String getLivestreamURL() {
        return livestreamURL;
    }

    public void setLivestreamURL(String livestreamURL) {
        this.livestreamURL = livestreamURL;
    }

    public String getLivestreamBackupURL() {
        return livestreamBackupURL;
    }

    public void setLivestreamBackupURL(String livestreamBackupURL) {
        this.livestreamBackupURL = livestreamBackupURL;
    }

    public String getProblemPDFURL() {
        return problemPDFURL;
    }

    public void setProblemPDFURL(String problemPDFURL) {
        this.problemPDFURL = problemPDFURL;
    }

}