
<ul class="nav nav-tabs mb-2">
    <li class="nav-item">
        <a href="${pageContext.request.contextPath}/module/ServerReport/manageReport.form"
           class="nav-link <c:if test='<%= request.getRequestURI().contains("/manageReport") %>'>active</c:if>">
            <spring:message code="ServerReport.manageReports" />
        </a>
    </li>
    <li class="nav-item">
        <a href="${pageContext.request.contextPath}/module/ServerReport/manageIndicators.form"
           class="nav-link <c:if test='<%= request.getRequestURI().contains("/manageIndicators") %>'>active</c:if>">
            <spring:message code="ServerReport.manageIndicator" />
        </a>
    </li>
    <li class="nav-item">
        <a href="${pageContext.request.contextPath}/module/ServerReport/manageIndicatorDataSet.form"
           class="nav-link <c:if test='<%= request.getRequestURI().contains("/manageIndicatorDataSet") %>'>active</c:if>">
            <spring:message code="ServerReport.manageIndicatorDataSet" />
        </a>
    </li>
    <li class="nav-item">
        <a href="${pageContext.request.contextPath}/module/ServerReport/manageIndicatorRates.form"
           class="nav-link <c:if test='<%= request.getRequestURI().contains("/manageIndicatorRates") %>'>active</c:if>">
            <spring:message code="ServerReport.manageIndicatorRate" />
        </a>
    </li>
    <li class="nav-item">
        <a href="${pageContext.request.contextPath}/module/ServerReport/manageIndicatorRateDataSet.form"
           class="nav-link <c:if test='<%= request.getRequestURI().contains("/manageIndicatorRateDataSet") %>'>active</c:if>">
            <spring:message code="ServerReport.manageIndicatorRateDataSet" />
        </a>
    </li>
    <li class="nav-item">
        <a href="${pageContext.request.contextPath}/module/ServerReport/manageCategory.form"
           class="nav-link <c:if test='<%= request.getRequestURI().contains("/manageCategory") %>'>active</c:if>">
            <spring:message code="ServerReport.manageCategory" />
        </a>
    </li>
    <li class="nav-item">
        <a href="${pageContext.request.contextPath}/module/ServerReport/manageOption.form"
           class="nav-link <c:if test='<%= request.getRequestURI().contains("/manageOption") %>'>active</c:if>">
            <spring:message code="ServerReport.manageOption" />
        </a>
    </li>
    <li class="nav-item">
        <a href="${pageContext.request.contextPath}/module/ServerReport/manageListing.form"
           class="nav-link <c:if test='<%= request.getRequestURI().contains("/manageListing") %>'>active</c:if>">
            <spring:message code="ServerReport.manageListing" />
        </a>
    </li>
    <li class="nav-item">
        <a href="${pageContext.request.contextPath}/module/ServerReport/manageParameter.form"
           class="nav-link <c:if test='<%= request.getRequestURI().contains("/manageParameter") %>'>active</c:if>">
            <spring:message code="ServerReport.manageParameter" />
        </a>
    </li>
</ul>