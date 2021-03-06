<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<div id="timeline-quest-carousel" class="carousel slide timeline-carousel" data-ride="carousel" data-interval="15000" data-keyboard="false">
    <div class="carousel-inner" role="listbox">
        <c:forEach var="notification" items="${openQuests}" varStatus="status">
            <div class="item ${status.index == 0 ? 'active' : ''}">
                <table class="width100">
                    <tr>
                        <td>
                            <a class="btn btn-link" href="#timeline-quest-carousel" role="button" data-slide="prev">
                                <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                                <span class="sr-only">Previous</span>
                            </a>
                        </td>
                        <td>
                            <h4>${notification.title} <small>#Quest2</small></h4>
                        </td>
                        <td class="text-right">
                            <a class="btn btn-link" href="#timeline-quest-carousel" role="button" data-slide="next">
                                <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                                <span class="sr-only">Next</span>
                            </a>
                        </td>
                    </tr>
                </table>
                <div class="content">${notification.body}</div>
                <div class="text-center">
                    <t:button context="primary"><spring:message code="quest.challenge.participate" /></t:button>
                </div>
            </div>
        </c:forEach>
    </div>
</div>