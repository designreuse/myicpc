package com.myicpc.service.scoreboard.eventFeed;


import com.myicpc.dto.eventFeed.visitor.EventFeedVisitor;
import com.myicpc.model.contest.Contest;

public interface EventFeedElement {
    void accept(EventFeedVisitor visitor, Contest contest);
}
