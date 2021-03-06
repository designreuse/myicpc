package com.myicpc.model.eventFeed;

import com.myicpc.model.IdGeneratedContestObject;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.List;

/**
 * Problem specification in the contest
 *
 * @author Roman Smetana
 */
@Cacheable
@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "idgen", sequenceName = "Problem_id_seq")
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"code", "contestId"})})
public class Problem extends IdGeneratedContestObject {
    private static final long serialVersionUID = -6443123462601729127L;

    private Long systemId;

    /**
     * Letter representation
     */
    private String code;
    /**
     * Full problem name
     */
    private String name;
    /**
     * Number of total test cases
     */
    private int totalTestcases;

    /**
     * Team submissions
     */
    @OneToMany(mappedBy = "problem")
    private List<TeamProblem> teamProblems;

    public List<TeamProblem> getTeamProblems() {
        return teamProblems;
    }

    public void setTeamProblems(final List<TeamProblem> teamProblems) {
        this.teamProblems = teamProblems;
    }

    public Long getSystemId() {
        return systemId;
    }

    public void setSystemId(Long systemId) {
        this.systemId = systemId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<TeamProblem> getTeamEvents() {
        return teamProblems;
    }

    public void setTeamEvents(final List<TeamProblem> teamProblems) {
        this.teamProblems = teamProblems;
    }

    public int getTotalTestcases() {
        return totalTestcases;
    }

    public void setTotalTestcases(final int totalTestcases) {
        this.totalTestcases = totalTestcases;
    }
}
