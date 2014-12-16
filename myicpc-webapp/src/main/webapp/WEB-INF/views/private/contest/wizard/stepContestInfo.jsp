<%@ include file="/WEB-INF/views/includes/taglibs.jsp" %>

<c:if test="${not empty contest.code and not empty contest.webServiceSettings.wsCMToken}">
    <div class="alert alert-info" role="alert">
        <spring:message code="contestAdmin.sync.hint"/>&nbsp;<a href="#" class="alert-link"><spring:message
            code="contestAdmin.sync.link"/></a>
    </div>
</c:if>

<t:springInput labelCode="contest.name" path="name" required="true"/>
<t:springInput labelCode="contest.shortName" path="shortName" required="true"/>
<t:springInput labelCode="contest.startTime" path="startTime" id="startTime" required="true"
               hintCode="contest.startTime.hint"/>
<t:springInput type="number" labelCode="contest.timeDifference" path="contestSettings.timeDifference" id="startTime"
               required="true" hintCode="contest.timeDifference.hint"/>
<div class="form-group">
    <label class="col-sm-3 control-label"><spring:message code="contest.showTeamNames"/>:* </label>

    <div class="col-sm-9">
        <label for="showTeamNamesTrue" class="normal-label">
            <form:radiobutton path="contestSettings.showTeamNames" id="showTeamNamesTrue" value="true"/>
            &nbsp;<spring:message code="contest.showTeamNames.hint.1"/>
        </label>
        <br/>
        <label for="showTeamNamesFalse" class="normal-label">
            <form:radiobutton path="contestSettings.showTeamNames" id="showTeamNamesFalse" value="false"/>
            &nbsp;<spring:message code="contest.showTeamNames.hint.2"/>
        </label>
    </div>
</div>
<t:springInput labelCode="contest.hashtag" path="hashtag" required="true" hintCode="contest.hashtag.hint"/>

<script type="text/javascript">
    $(function () {
        $('#startTime').datetimepicker(datePickerOptions);
    });
</script>