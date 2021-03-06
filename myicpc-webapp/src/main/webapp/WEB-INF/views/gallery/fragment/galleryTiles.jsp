<%@ include file="/WEB-INF/views/includes/taglibs.jsp" %>

<c:forEach var="notification" items="${notifications}">
    <a href="#" onclick="Gallery.showGalleryModal(this);" class="gallery-thumbnail" data-toggle="modal" data-target="#galleryPopup"
       data-id="${notification.id}"
       data-image-url="${notification.imageUrl}"
       data-video-url="${notification.videoUrl}"
       data-thumbnail-url="${notification.thumbnailUrl}"
       data-url="${notification.url}"
       data-author-name="${notification.authorName}"
       data-author-avatar="${notification.profilePictureUrl}">
        <c:if test="${not empty notification.videoUrl}">
            <div class="play-button glyphicon glyphicon-play-circle"></div>
        </c:if>
        <img src="${notification.thumbnailUrl}" alt="${notification.title}" onerror="removeOnError(this, ${notification.id})">
    </a>
</c:forEach>

<script type="application/javascript" class="gallery-tiles-load-script">
    var lastNotificationId = ${not empty lastNotificationId ? lastNotificationId : -1};
    if (lastNotificationId == -1) {
        $('.load-more-btn').hide();
    }
    if(window.jQuery) {
        $('.gallery-tiles-load-script').remove();
    }
</script>