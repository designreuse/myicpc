<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="ctx" value="${pageContext['request'].contextPath}"/>

<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.3/angular.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.3/angular-sanitize.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.3/angular-route.min.js" defer></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/modernizr/2.7.1/modernizr.min.js" defer></script>
<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.11.1/jquery-ui.min.js" defer></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js" defer></script>
<script src="<c:url value="/js/jquery/jquery.atmosphere.min.js" />" defer></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/underscore.js/1.5.2/underscore-min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/handlebars.js/1.3.0/handlebars.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery.touchswipe/1.6.4/jquery.touchSwipe.min.js" defer></script>
<script src="https://apis.google.com/js/platform.js" async defer></script>

<%-- MyICPC internal resources --%>
<script src="<c:url value='/js/myicpc/functions.js'/>"></script>

<script type="application/javascript">
    <%-- Notification count polling --%>
    notificationCountPolling('${ctx}', '${contestURL}');

    <%-- Feedback modal window --%>
    $(function() {
        $("#feedbackLink").click(function() {
            $.get("<spring:url value="${contestURL}/feedback-form" />", function(data) {
                $("#feedbackWrapper").html(data);
                $("#feedbackModal").modal('show');
            });
        });
    });

    $("#notification-counter-link").click(function() {
        var $featuredNotificationContainer = $("#featured-notification-container");
        if (!$featuredNotificationContainer.is(":visible")) {
            $.get("<spring:url value="${contestURL}/notification/featured-panel" />", function(data) {
                $featuredNotificationContainer.html(data);
                $featuredNotificationContainer.slideDown();
                $(window).scrollTop(0);
            });
        } else {
            $featuredNotificationContainer.slideUp();
        }
    });

    <%-- Container for share noticifation dialog --%>
    showShareDialog = function(notificationId) {
        $.get('<c:url value="${contestURL}/notification/" />' +notificationId + '/share', function(data) {
            $('#shareNotificationDialogContainer').html(data);
            $('#shareNotificationDialog').modal('show');
        })
    }

    function profilePictureError(image) {
        image.onerror = "";
        image.src = "<c:url value="/images/default-profile-small.png" />";
        return true;
    }
    profilePictureError

    if (typeof localStorage !== "undefined") {
        localStorage["currentContest"] = '${contest.code}';
    }
</script>
<div id="shareNotificationDialogContainer"></div>