<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="t" uri="http://myicpc.baylor.edu/tags"%>

<c:if test="${not empty participants}">
	<table class="table table-striped">
		<thead>
			<tr>
				<th><spring:message code="participant.name" /></th>
				<th><spring:message code="participant.roles" /></th>
				<th><spring:message code="participant.twitter" /></th>
				<th><spring:message code="participant.vine" /></th>
				<th><spring:message code="participant.instagram" /></th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="participant" items="${participants}">
				<tr>
					<td>${participant.officialFullname}</td>
					<td>
						<c:forEach var="association" items="${participant.teamAssociations}">
							<spring:message code="${association.contestParticipantRole.code}" text="${association.contestParticipantRole.label}" /> ${not empty association.teamInfo ? ' - ' : ''} <c:out value="${association.teamInfo.contestTeamName}" /><br />
						</c:forEach>
					</td>
					<td>${participant.twitterUsername}</td>
					<td>${participant.vineUsername}</td>
					<td>${participant.instagramUsername}</td>
					<td class="text-right">
						<t:editButton url="/private${contestURL}/participant/${participant.id}/edit" />
                        <t:deleteButton url="/private/participants/${participant.id}/delete" confirmMessageCode="participantAdmin.delete.confirm" confirmMessageArgument="${teamMember.fullname}" />
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</c:if>

<c:if test="${empty participants}">
	<div class="no-items-available">
		<spring:message code="participantAdmin.noResult" />
	</div>
</c:if>
