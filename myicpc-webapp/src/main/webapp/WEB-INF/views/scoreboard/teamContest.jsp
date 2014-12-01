<%@ include file="/WEB-INF/views/includes/taglibs.jsp" %>

<t:template>

  <jsp:attribute name="title">
      ${team.name}
  </jsp:attribute>
  
  <jsp:attribute name="headline">
      <span id="team_${team.id}_rank" class="label label-info">${team.rank }</span> ${team.name}
  </jsp:attribute>

  <jsp:body>
      <div class="col-sm-12">
          <ul class="nav nav-pills">
              <t:menuItem activeItem="contest" active="${tab}" url="${contestURL}/team/${team.id}"><spring:message code="teamHome.nav.contest" /></t:menuItem>
              <t:menuItem activeItem="profile" active="${tab}" url="${contestURL}/team/${team.id}"><spring:message code="teamHome.nav.about" /></t:menuItem>
              <t:menuItem activeItem="insight" active="${tab}" url="${contestURL}/team/${team.id}"><spring:message code="teamHome.nav.insight" /></t:menuItem>
              <t:menuItem activeItem="social" active="${tab}" url="${contestURL}/team/${team.id}"><spring:message code="teamHome.nav.social" /></t:menuItem>
          </ul>
      </div>

      <div class="col-sm-6">
          <h3><spring:message code="teamHome.contest.timeline"/></h3>
          <table class="table table-condensed team-timeline">
              <tbody>
              <c:forEach var="event" items="${timeline}">
                  <tr class="${event.notification.notificationType.scoreboardSuccess ? 'success' : ''} ${event.notification.notificationType.scoreboardFailed ? 'danger' : ''}">
                      <td rowspan="2" class="fit-content time-cell">${util:formatMinutes(event.contestTime)}</td>
                      <td>
                          <strong><spring:message code="problem" /> ${event.teamProblem.problem.code}</strong>
                      </td>
                      <td class="text-right">
                          <c:if test="${event.notification.notificationType.scoreboardSuccess}">
                              <strong><spring:message code="teamHome.contest.timeline.rank" arguments="${event.teamProblem.oldRank},${event.teamProblem.newRank}" /></strong>
                          </c:if>
                      </td>
                  </tr>
                  <tr class="${event.notification.notificationType.scoreboardSuccess ? 'success' : ''} ${event.notification.notificationType.scoreboardFailed ? 'danger' : ''}">
                      <td colspan="2">${event.notification.body}</td>
                  </tr>
              </c:forEach>
              </tbody>
          </table>
      </div>

      <div class="col-sm-6">
          <h3><spring:message code="teamHome.contest.rankHistory" /></h3>
          <h3><spring:message code="teamHome.contest.problems" /></h3>

          <table class="table">
              <thead>
                  <tr>
                      <th>#</th>
                      <th><spring:message code="problem" /></th>
                      <th class="text-center"><spring:message code="problem.solved" /></th>
                  </tr>
              </thead>
              <tbody>
                <c:forEach var="problem" items="${problems}">
                    <tr>
                        <td>${problem.code}</td>
                        <td><a href="<spring:url value="${contestURL}/problem/${problem.code}" />">${problem.name}</a></td>
                        <td class="text-center" id="problem_${problem.id}_for_team_${team.id}">
                            <c:choose>
                                <c:when test="${not empty team.currentProblems[problem.id] and team.currentProblems[problem.id].solved}">
                                    <span class="glyphicon glyphicon-ok"></span>
                                </c:when>
                                <c:when test="${not empty team.currentProblems[problem.id] and not team.currentProblems[problem.id].solved}">
                                    <div class="progress problem-progress" title="<spring:message code="team.submission.passed" arguments="${team.currentProblems[problem.id].numTestPassed},${team.currentProblems[problem.id].totalNumTests}" />">
                                        <div class="progress-bar progress-bar-danger" role="progressbar"
                                             aria-valuenow="<fmt:formatNumber value="${team.currentProblems[problem.id].numTestPassed*100/team.currentProblems[problem.id].totalNumTests}"  type="number" maxFractionDigits="0"/>" aria-valuemin="0"
                                             aria-valuemax="100" style="width: <fmt:formatNumber value="${team.currentProblems[problem.id].numTestPassed*100/team.currentProblems[problem.id].totalNumTests}"  type="number" maxFractionDigits="0"/>%"
                                                ></div>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <span class="glyphicon glyphicon-remove"></span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
              </tbody>
          </table>
      </div>
  </jsp:body>
</t:template>
