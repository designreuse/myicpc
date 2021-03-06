<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" uri="http://myicpc.baylor.edu/tags" %>
<%@ taglib prefix="util" uri="http://myicpc.baylor.edu/functions"%>

<nav id="mobile-top-menu" class="navbar navbar-inverse navbar-fixed-top mobile">
    <div class="navbar-header">
        <a class="navbar-brand" href='<spring:url value="${contestURL}/" />'>
            <span class="fa fa-home"></span> <spring:message code="app.name"/>
        </a>
        <div class="pull-right" style="margin: 10px 5px;">
            <a href="javascript:void(0)" id="notification-counter-link" style="display: inline-block">
                <span id="notification-counter" class="label ${featuredNotificationsCount == 0 ? 'label-default' : 'label-danger'} notification-counter">
                    ${featuredNotificationsCount}
                </span>
            </a>
            <span id="mobile-top-menu-addon" style="display: inline-block">

            </span>
        </div>
    </div>
</nav>

<nav id="mobile-submenu" class="navbar navbar-default">
    <table style="width: 100%; text-align: center;">
        <tbody>
        <tr>
            <c:if test="${util:scheduleModuleEnabled(contest)}">
                <td class="${sideMenuActive eq 'schedule' ? 'active' : '' }">
                    <t:emptyLink id="main-schedule-link">
                        <span class="glyphicon glyphicon-calendar"></span>
                    </t:emptyLink>
                </td>
            </c:if>
            <td class="${sideMenuActive eq 'scoreboard' ? 'active' : '' }">
                <t:emptyLink id="main-scoreboard-link">
                    <span class="glyphicon glyphicon-list"></span>
                </t:emptyLink>
            </td>
            <c:if test="${util:questModuleEnabled(contest)}">
                <td class="${sideMenuActive eq 'quest' ? 'active' : '' }">
                    <t:emptyLink id="main-quest-link">
                        <span class="glyphicon glyphicon-screenshot"></span>
                    </t:emptyLink>
                </td>
            </c:if>
            <c:if test="${util:galleryModuleEnabled(contest)}">
                <td class="${sideMenuActive eq 'gallery' ? 'active' : '' }">
                    <c:if test="${not util:officialGalleryModuleEnabled(contest)}">
                        <a href="<spring:url value="${contestURL}/gallery" />" class="side-menu-gallery">
                            <span class="glyphicon glyphicon-camera"></span>
                        </a>
                    </c:if>
                    <c:if test="${util:officialGalleryModuleEnabled(contest)}">
                        <t:emptyLink id="main-gallery-link">
                            <span class="glyphicon glyphicon-camera"></span>
                        </t:emptyLink>
                    </c:if>
                </td>
            </c:if>
            <td><t:emptyLink id="main-misc-link"><span
                    class="fa fa-ellipsis-v"></span></t:emptyLink>
        </tr>
        </tbody>
    </table>
</nav>

<div id="main-schedule-submenu" style="display: none;">
    <table class="width100 text-center mobile-submenu">
        <%@ include file="/WEB-INF/views/includes/topMenu/scheduleSubmenu.jsp" %>
    </table>
</div>

<div id="main-scoreboard-submenu" style="display: none;">
    <table class="width100 text-center mobile-submenu">
        <%@ include file="/WEB-INF/views/includes/topMenu/scoreboardSubmenu.jsp" %>
    </table>
</div>

<div id="main-quest-submenu" style="display: none;">
    <table class="width100 text-center mobile-submenu">
        <%@ include file="/WEB-INF/views/includes/topMenu/questSubmenu.jsp" %>
    </table>
</div>

<div id="main-gallery-submenu" style="display: none;">
    <table class="width100 text-center mobile-submenu">
        <%@ include file="/WEB-INF/views/includes/topMenu/gallerySubmenu.jsp" %>
    </table>
</div>

<div id="main-misc-submenu" style="display: none;">
    <table class="width100 text-center mobile-submenu">
        <tr>
            <td>
                <c:if test="${util:pollModuleEnabled(contest)}">
                    <t:topSubmenuLink labelCode="nav.polls" url="${contestURL}/polls" icon="glyphicon glyphicon-bullhorn"/>
                </c:if>
            </td>
            <td>
                <c:if test="${util:rssModuleEnabled(contest)}">
                    <t:topSubmenuLink labelCode="nav.rss" url="${contestURL}/blog" icon="fa fa-rss" />
                </c:if>
            </td>
        </tr>
    </table>
</div>

<div id="featured-notification-container"></div>