package com.myicpc.commons.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author Roman Smetana
 */
public class MessageUtils {
    private static final Logger logger = LoggerFactory.getLogger(MessageUtils.class);
    /**
     * Bundle with translations
     */
    private static ResourceBundle messageBundle;

    static {
        try {
            messageBundle = ResourceBundle.getBundle("i18n.text");
        } catch (NullPointerException | MissingResourceException ex) {
            logger.error("File with translations not available.", ex);
        }
    }

    public static String getMessage(final String key) {
        return messageBundle.getString(key);
    }

    public static String getMessage(final String key, final Object... params) {
        return MessageFormat.format(messageBundle.getString(key), params);
    }

}
