package com.myicpc.model.contest;

import com.myicpc.model.IdGeneratedObject;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Date;

/**
 * Contest for which MyICPC runs
 *
 * @author Roman Smetana
 */
@Cacheable
@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "idgen", sequenceName = "Contest_id_seq")
public class Contest extends IdGeneratedObject {
    private static final long serialVersionUID = 4939779370882579754L;

    /**
     * Name of the contest
     */
    @Column(unique = true)
    @NotNull
    private String name;

    @Column(unique = true)
    @NotNull
    private String shortName;

    @Column(unique = true)
    @NotNull
    private String code;

    private Long externalId;

    private String hashtag;

    private Integer timeDifference = 0;

    /**
     * If the contest is hidden, it is not shown in the listing on the landing page
     */
    private boolean hidden;

    /**
     * Start date time of the contest
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd H:mm:ss")
    private Date startTime;
    /**
     * Length of the contest in seconds
     */
    private int length;
    /**
     * Time penalty in minutes for failed submission
     */
    private double penalty;
    /**
     * Show team names or university names
     */
    private boolean showTeamNames;
    /**
     * Represents the settings for the contest
     */
    @NotNull
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "contestSettingsId", nullable = false)
    private ContestSettings contestSettings = new ContestSettings();

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "webServiceSettingsId", nullable = false)
    private WebServiceSettings webServiceSettings = new WebServiceSettings();

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "mapConfigurationId", nullable = false)
    private MapConfiguration mapConfiguration = new MapConfiguration();

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "questConfigurationId", nullable = false)
    private QuestConfiguration questConfiguration = new QuestConfiguration();

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "moduleConfigurationId", nullable = false)
    private ModuleConfiguration moduleConfiguration = new ModuleConfiguration();

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getExternalId() {
        return externalId;
    }

    public void setExternalId(Long externalId) {
        this.externalId = externalId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(final Date startTime) {
        this.startTime = startTime;
    }

    public int getLength() {
        return length;
    }

    public void setLength(final int length) {
        this.length = length;
    }

    public double getPenalty() {
        return penalty;
    }

    public void setPenalty(final double penalty) {
        this.penalty = penalty;
    }

    public boolean isShowTeamNames() {
        return showTeamNames;
    }

    public void setShowTeamNames(boolean showTeamNames) {
        this.showTeamNames = showTeamNames;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public Integer getTimeDifference() {
        return timeDifference;
    }

    public void setTimeDifference(Integer timeDifference) {
        this.timeDifference = timeDifference;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public ContestSettings getContestSettings() {
        return contestSettings;
    }

    public void setContestSettings(ContestSettings contestSettings) {
        this.contestSettings = contestSettings;
    }

    public WebServiceSettings getWebServiceSettings() {
        return webServiceSettings;
    }

    public void setWebServiceSettings(WebServiceSettings webServiceSettings) {
        this.webServiceSettings = webServiceSettings;
    }

    public MapConfiguration getMapConfiguration() {
        return mapConfiguration;
    }

    public void setMapConfiguration(MapConfiguration mapConfiguration) {
        this.mapConfiguration = mapConfiguration;
    }

    public QuestConfiguration getQuestConfiguration() {
        return questConfiguration;
    }

    public void setQuestConfiguration(QuestConfiguration questConfiguration) {
        this.questConfiguration = questConfiguration;
    }

    public ModuleConfiguration getModuleConfiguration() {
        return moduleConfiguration;
    }

    public void setModuleConfiguration(ModuleConfiguration moduleConfiguration) {
        this.moduleConfiguration = moduleConfiguration;
    }

    public int getContestYear() {
        if (startTime != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startTime);
            return calendar.get(Calendar.YEAR);
        }
        return 0;
    }
}
