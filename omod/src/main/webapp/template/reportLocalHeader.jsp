<ul class="nav nav-tabs mb-2">
    <li class="nav-item">
        <a href="${pageContext.request.contextPath}/module/ServerReport/reportRun.form"
           class="nav-link <c:if test='<%= request.getRequestURI().contains("/reportRun") %>'>active</c:if>">
            <spring:message code="ServerReport.nationalReports" />
        </a>
    </li>
    <li class="nav-item">
        <a href="${pageContext.request.contextPath}/module/ServerReport/reportRunIndicator.form"
           class="nav-link <c:if test='<%= request.getRequestURI().contains("/reportRunIndicator") %>'>active</c:if>">
            <spring:message code="ServerReport.indicator" />
        </a>
    </li>
</ul>