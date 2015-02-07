package com.myicpc.service.timeline;

import com.google.common.collect.Lists;
import com.myicpc.model.contest.Contest;
import com.myicpc.model.social.Notification;
import com.myicpc.repository.social.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Roman Smetana
 */
@Service
@Transactional
public class TimelineService {
    public static final int POSTS_PER_PAGE = 30;
    /**
     * Notification types presented on the timeline
     */
    public static final List<Notification.NotificationType> TIMELINE_TYPES = Lists.newArrayList(
            Notification.NotificationType.SCOREBOARD_SUCCESS,
            Notification.NotificationType.ANALYST_MESSAGE
    );

    @Autowired
    private NotificationRepository notificationRepository;

    public List<Notification> getTimelineNotifications(Contest contest) {
        Pageable pageable = new PageRequest(0, POSTS_PER_PAGE);
        Page<Notification> timelineNotifications = notificationRepository.findByNotificationTypesOrderByIdDesc(TIMELINE_TYPES, contest, pageable);
        return timelineNotifications.getContent();
    }


}