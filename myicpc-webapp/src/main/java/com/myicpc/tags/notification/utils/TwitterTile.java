package com.myicpc.tags.notification.utils;

import com.myicpc.commons.utils.MessageUtils;
import com.myicpc.model.social.Notification;
import com.myicpc.tags.notification.SocialTile;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import java.io.IOException;
import java.util.Locale;

/**
 * @author Roman Smetana
 */
public class TwitterTile extends SocialTile {

    public TwitterTile(Notification notification, boolean isTemplate, Locale locale, PageContext pageContext) {
        super(notification, isTemplate, locale, pageContext);
    }

    @Override
    protected String getTitle() {
        Object[] params = new Object[2];
        if (isTemplate) {
            params[0] = "{{authorName}}";
            params[1] = "{{title}}";
        } else {
            params[0] = notification.getAuthorName();
            params[1] = notification.getTitle();
        }
        return String.format("%s <small>@%s</small>", params);
    }

    @Override
    protected void renderFooterAppendix(JspWriter out) throws IOException, JspException {
        Object[] params = new Object[2];
        if (isTemplate) {
            params[0] = "{{externalId}}";
        } else {
            params[0] = notification.getExternalId();
        }
        params[1] = MessageUtils.getMessage("retweet", locale);
        out.write(String.format(" &middot; <a href=\"https://twitter.com/intent/retweet?tweet_id=%s\"><span class=\"glyphicon glyphicon-retweet\"></span> %s</a>", params));
    }

    @Override
    protected void renderFooterTitle(JspWriter out) throws IOException, JspException {
        out.print(String.format("<span class=\"fa fa-twitter\"></span> %s", MessageUtils.getMessage("twitter", locale)));
    }
}
