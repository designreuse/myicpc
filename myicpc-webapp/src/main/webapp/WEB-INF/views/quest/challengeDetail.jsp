<%@ include file="/WEB-INF/views/includes/taglibs.jsp" %>

<t:template>
    <jsp:attribute name="headline">
        ${challenge.name}
    </jsp:attribute>
    <jsp:attribute name="title">
        ${challenge.name}
    </jsp:attribute>

    <jsp:attribute name="headlineRight">
        <c:if test="${not disablePagination}">
            <div class="btn-group btn-group-sm" role="group" style="margin-top: 5px;">
                <t:button href="${contestURL}/quest/challenge/${prevChallenge.id}"><t:glyphIcon icon="chevron-left" /></t:button>
                <t:button href="${contestURL}/quest/challenge/${nextChallenge.id}"><t:glyphIcon icon="chevron-right" /></t:button>
            </div>
        </c:if>
    </jsp:attribute>

    <jsp:body>
        <%@ include file="/WEB-INF/views/quest/fragment/questInfo.jsp" %>

        <%@ include file="/WEB-INF/views/quest/fragment/challengeDetail.jsp" %>
    </jsp:body>
</t:template>
