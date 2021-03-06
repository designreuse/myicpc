<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<t:templateGeneralAdmin>
	<jsp:attribute name="title">
		${headlineTitle}
	</jsp:attribute>

	<jsp:attribute name="headline">
		${headlineTitle}
	</jsp:attribute>
	
	<jsp:attribute name="breadcrumb">
	    <li><a href="<spring:url value="/private/users" />"><spring:message code="userAdmin.title" /></a></li>
		  <li class="active">${headlineTitle}</li>
	</jsp:attribute>

	<jsp:body>
		<div class="container">
			<spring:url var="formAction" value="${formURL}" />
			<form:form class="form-horizontal" role="form" action="${formAction}" commandName="systemUser">
			  <c:if test="${showCredentials}">
					<t:springInput labelCode="user.username" path="username" required="true" />
					<t:springPassword labelCode="user.password" path="password" required="true"></t:springPassword>
					<t:springPassword labelCode="user.passwordCheck" path="passwordCheck" required="true"></t:springPassword>
			  </c:if>
			  <c:if test="${showEnabled}">
			    <t:springCheckbox labelCode="user.enabled" path="enabled" />
			  </c:if>
			  <t:springInput labelCode="user.firstname" path="firstname" />
			  <t:springInput labelCode="user.lastname" path="lastname" />
			  <c:if test="${showRoles}">
                  <div class="form-group">
                    <form:label path="stringRoles" class="col-sm-3 control-label">
                        <spring:message code="user.roles" />: </form:label>
                    <div class="col-sm-9 form-element">
                        <form:select path="stringRoles" class="form-control" multiple="true" items="${roles}" id="systemUserRoles"></form:select>
                    </div>
                  </div>
			  </c:if>

			  <hr />
			  <div class="form-group text-right">
					<button type="reset" class="btn btn-default">
						<spring:message code="reset" /> </button>
					<a href="<spring:url value="/private/users" />" class="btn btn-default"><spring:message code="cancel" /> </a>
					<button type="submit" class="btn btn-primary">
						<spring:message code="save" />
					</button>
			  </div>
			</form:form>

			<link rel="stylesheet" href="<c:url value='/static/css/chosen.min.css'/>" type="text/css">
			<script src="<c:url value='/static/js/jquery/chosen.jquery.min.js'/>" defer></script>
			<script>
				$(function() {
					$("#systemUserRoles").chosen();
				});
			</script>
		</div>
    </jsp:body>
</t:templateGeneralAdmin>