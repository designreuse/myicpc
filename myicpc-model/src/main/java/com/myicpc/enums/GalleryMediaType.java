package com.myicpc.enums;

/**
 * @author Roman Smetana
 */

import java.util.HashMap;
import java.util.Map;

/**
 * Source type
 *
 * @author Roman Smetana
 */
@SuppressWarnings("CanBeFinal")
public enum GalleryMediaType {
    VINE("vi"), INSTAGRAM_IMAGE("im"), INSTAGRAM_VIDEO("iv"), GALLERY("ga"), YOUTUBE_VIDEO("yt");

    /**
     * Code of the source
     */
    private final String code;
    /**
     * mapping between type and code
     */
    private static final Map<String, GalleryMediaType> CODEMAP = new HashMap<>();

    static {
        for (GalleryMediaType service : GalleryMediaType.values()) {
            CODEMAP.put(service.getCode(), service);
        }
    }

    GalleryMediaType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    /**
     * @param code type code
     * @return get {@link GalleryMediaType} for code
     */
    public static GalleryMediaType getMediaToCode(String code) {
        return CODEMAP.get(code);
    }

    /**
     * Translate {@link GalleryMediaType} into {@link com.myicpc.enums.NotificationType}
     *
     * @param galleryMediaType gallery media type
     * @return notification type
     */
    public static NotificationType toNotificationType(GalleryMediaType galleryMediaType) {
        try {
            return NotificationType.valueOf(galleryMediaType.toString());
        } catch (IllegalArgumentException | NullPointerException ex) {
            return null;
        }
    }

    /**
     * @return is Vine
     */
    public boolean isVine() {
        return this == GalleryMediaType.VINE;
    }

    /**
     * @return is image from Instagram
     */
    public boolean isInstagramImage() {
        return this == GalleryMediaType.INSTAGRAM_IMAGE;
    }

    /**
     * @return is video from Instagram
     */
    public boolean isInstagramVideo() {
        return this == GalleryMediaType.INSTAGRAM_VIDEO;
    }

    /**
     * @return is Picasa gallery
     */
    public boolean isGallery() {
        return this == GalleryMediaType.GALLERY;
    }

    /**
     * @return is Youtube video
     */
    public boolean isYoutubeVideo() {
        return this == GalleryMediaType.YOUTUBE_VIDEO;
    }
}
