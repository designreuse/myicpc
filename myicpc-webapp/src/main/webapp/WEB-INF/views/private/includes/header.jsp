<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="t" uri="http://myicpc.baylor.edu/tags" %>
<%@ taglib prefix="util" uri="http://myicpc.baylor.edu/functions"%>

<nav id="admin-navbar" class="navbar navbar-default">

    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
            <span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href='<spring:url value="/private/home" />'><spring:message code="app.nameAdmin" /></a>
    </div>

    <div class="collapse navbar-collapse navbar-ex1-collapse">
        <ul class="nav navbar-nav">
            <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown"><spring:message code="nav.admin.contest" /> <b class="caret"></b></a>
                <ul class="dropdown-menu">
                    <li><a href='<spring:url value="/private${contestURL}/teams" />'><spring:message code="nav.admin.teams" /></a></li>
                    <li><a href='<spring:url value="/private${contestURL}/teams/synchronize" />'>- <spring:message code="nav.admin.teams.sync" /></a></li>
                    <li class="divider"></li>
                    <li><a href='<spring:url value="/private${contestURL}/participants" />'><spring:message code="nav.admin.participants" /></a></li>
                </ul>
            </li>
            <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown"><spring:message code="nav.admin.notifications" /> <b class="caret"></b></a>
                <ul class="dropdown-menu">
                    <li><a href='<spring:url value="/private/timeline" />'><spring:message code="nav.admin.timeline" /></a></li>
                    <li><a href='<spring:url value="/private/notifications" />'><spring:message code="nav.admin.notifications.all" /></a></li>
                    <li><a href='<spring:url value="/private/notifications/featured" />'><spring:message code="nav.admin.notifications.featured" /></a></li>
                    <li><a href='<spring:url value="/private/blacklist" />'><spring:message code="nav.admin.blacklist" /></a></li>
                    <li><a href='<spring:url value="/private/notifications/suspicious" />'><spring:message code="nav.admin.notifications.suspicious" /></a></li>
                    <li class="divider"></li>
                    <li><a href='<spring:url value="/private/notifications/admin" />'><spring:message code="nav.admin.notifications.icpc" /></a></li>
                </ul>
            </li>
            <c:if test="${util:scheduleModuleEnabled(contest)}">
                <li><a href='<spring:url value="/private${contestURL}/schedule" />'><spring:message code="nav.admin.schedule" /></a></li>
            </c:if>
            <c:if test="${util:questModuleEnabled(contest) or util:techtrekModuleEnabled(contest)}">
                <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown"><spring:message code="nav.admin.quest" /> <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <c:if test="${util:questModuleEnabled(contest)}">
                            <li><a href='<spring:url value="/private${contestURL}/quest/challenges" />'><spring:message code="nav.admin.challenges" /></a></li>
                            <li><a href='<spring:url value="/private${contestURL}/quest/participants" />'><spring:message code="nav.admin.quest.participants" /></a></li>
                            <li><a href='<spring:url value="/private${contestURL}/quest/submissions" />'><spring:message code="nav.admin.quest.submissions" /></a></li>
                            <li><a href='<spring:url value="/private${contestURL}/quest/votes" />'><spring:message code="nav.admin.quest.votes" /></a></li>
                        </c:if>
                        <c:if test="${util:questModuleEnabled(contest) and util:techtrekModuleEnabled(contest)}">
                            <li class="divider"></li>
                        </c:if>
                        <c:if test="${util:techtrekModuleEnabled(contest)}">
                            <li><a href='<spring:url value="/private${contestURL}/techtrek" />'><spring:message code="nav.admin.techtrek" /></a></li>
                        </c:if>
                    </ul>
                </li>
            </c:if>
            <c:if test="${util:rssModuleEnabled(contest)}">
                <li><a href='<spring:url value="/private${contestURL}/rss" />'><spring:message code="nav.admin.rssFeed" /></a></li>
            </c:if>
            <c:if test="${util:pollModuleEnabled(contest)}">
                <li><a href='<spring:url value="/private${contestURL}/polls" />'><spring:message code="nav.admin.polls" /></a></li>
            </c:if>
            <c:if test="${util:galleryModuleEnabled(contest)}">
                <li><a href='<spring:url value="/private${contestURL}/gallery" />'><spring:message code="nav.admin.gallery" /></a></li>
            </c:if>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <%@ include file="/WEB-INF/views/private/includes/header_right_appendix.jsp"%>
        </ul>
    </div>
</nav>
