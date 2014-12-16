package com.myicpc.service.scoreboard.eventFeed.dto;

import com.myicpc.model.contest.Contest;
import com.myicpc.model.eventFeed.EventFeedControl;
import com.myicpc.service.scoreboard.eventFeed.EventFeedVisitor;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author Roman Smetana
 */
@XStreamAlias("finalized")
public class FinalizedXML extends XMLEntity<Contest> {
    @XStreamAlias("last-gold")
    private Integer lastGold;
    @XStreamAlias("last-silver")
    private Integer lastSilver;
    @XStreamAlias("last-bronze")
    private Integer lastBronze;

    @Override
    public void mergeTo(Contest contest) {

    }

    @Override
    public void accept(EventFeedVisitor visitor, Contest contest, EventFeedControl eventFeedControl) {
        visitor.visit(this, contest, eventFeedControl);
    }

    public Integer getLastGold() {
        return lastGold;
    }

    public void setLastGold(Integer lastGold) {
        this.lastGold = lastGold;
    }

    public Integer getLastSilver() {
        return lastSilver;
    }

    public void setLastSilver(Integer lastSilver) {
        this.lastSilver = lastSilver;
    }

    public Integer getLastBronze() {
        return lastBronze;
    }

    public void setLastBronze(Integer lastBronze) {
        this.lastBronze = lastBronze;
    }
}