<%@ include file="/WEB-INF/template/include.jsp"%>
<openmrs:require privilege="Manage Server Report" otherwise="/login.htm" redirect="/module/ServerReport/reportRun.form" />
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>

<script type="application/javascript">
    var locations = ${json};

    //console.log(locations);

    $(document).ready(function () {
        $('#location-list').jstree({
            'core' : {
                "multiple" : false,
                'data' : locations,
                'themes' : {
                    'name' : "default",
                    'variant' : "medium",
                    'responsive' : true
                }
            },
            "plugins" : ["ui","themes","checkbox", "search", "state"]
        });


        $('#location-list').on('select_node.jstree', function (e, data) {
            if (data === undefined || data.node === undefined || data.node.id === undefined) {
                alert("not selected");
                return;
            }
            //alert('clicked node: ' + data.node.id);
            $('#locationId').val(data.node.id);
        });
        $('#location-list').on('deselect_node.jstree', function (e, data) {
            $('#locationId').val("");
        });
        $(".nano-pane").css("display","block");
        $(".nano-slider").css("display","block");
    });
</script>

<h5 style="text-transform: uppercase;" id="page-title">
    <spring:message code="ServerReport.title" />
</h5>
<div class="box">
    <div class="container-fluid">
        <%@ include file="template/reportLocalHeader.jsp"%>
        <h6 class="alert alert-info">
            <spring:message code="ServerReport.nationalReports" /> -
            <c:if test="${mode == 'View' || empty mode}">Liste</c:if>
            <c:if test="${mode == 'run'}">Ex&eacute;cution</c:if>
        </h6>
        <div class="row">
            <c:if test="${mode == 'View'}">
                <div class="col-md-4">
                    <div class="card">
                        <div class="card-header">Liste des rapports</div>
                        <div class="card-body">
                            <div class="list-group">
                                <c:forEach var="report" items="${reports}">
                                    <div class="list-group-item">
                                        <a href="${pageContext.request.contextPath}/module/ServerReport/reportRun.form?reportId=${report.reportId}">
                                                ${report.name}
                                        </a>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-8">
                    <div class="card">
                        <div class="card-header">Rapports Ex&eacute;cut&eacute;s</div>
                        <div class="card-body">
                            <table class="table table-hover table-bordered">
                                <thead>
                                <tr>
                                    <th>Date ex&eacute;cution</th>
                                    <%--<th>Date de d&eacute;but</th>
                                    <th>Date de fin</th>--%>
                                    <th>Nom du rapport</th>
                                    <th>Site/R&eacute;gion/District</th>
                                    <th></th>
                                </tr>
                                </thead>

                                <tbody>
                                <c:forEach var="rq" items="${reportRequests}">
                                    <tr>
                                        <td><fmt:formatDate type="date" value="${rq.requestDate}" pattern="dd/MM/yyyy HH:mm:ss"/></td>
                                        <%--<td><fmt:formatDate type="date" value="${rq.requestPeriodStartDate}" pattern="dd/MM/yyyy"/></td>
                                        <td><fmt:formatDate type="date" value="${rq.requestPeriodEndDate}" pattern="dd/MM/yyyy"/></td>--%>
                                        <%--<td>--%>
                                            <%--<c:forEach var="person" items="${ rq.creator.person.names }">--%>
                                                <%--${person.familyName} ${person.middleName} ${person.givenName}--%>
                                            <%--</c:forEach>--%>
                                        <%--</td>--%>
                                        <td>${rq.name}</td>
                                        <td>${rq.requestLocation.name}</td>
                                        <td class="align-middle">
                                            <table class="table">
                                                <tr>
                                                    <td>
                                                        <c:url value="/module/ServerReport/reportRun.form" var="viewUrl">
                                                            <c:param name="reportViewId" value="${rq.requestId}"/>
                                                        </c:url>
                                                        <a href="${ viewUrl }"><img src="<c:url value="/images/file.gif"/>" alt="View"></a>
                                                    </td>
                                                    <td>
                                                        <c:url value="/module/ServerReport/reportRunExcelView.form" var="excelUrl">
                                                            <c:param name="reportExcelId" value="${rq.requestId}"/>
                                                        </c:url>
                                                        <a href="${ excelUrl }" target="_blank">
                                                            <img height="15px" width="16px" src="<c:url value="/moduleResources/ptme/images/excel.png"/>" alt="Excel">
                                                        </a>
                                                    </td>
                                                    <c:if test="${rq.saved == false}">
                                                        <td>
                                                            <c:url value="/module/ServerReport/reportRun.form" var="saveUrl">
                                                                <c:param name="reportSaveId" value="${rq.requestId}"/>
                                                            </c:url>
                                                            <a href="${ saveUrl }"><img src="<c:url value="/images/save.gif"/>" alt="Save"></a>
                                                        </td>
                                                    </c:if>
                                                    <td>
                                                        <c:url value="/module/ServerReport/reportRun.form" var="urlsup">
                                                            <c:param name="delId" value="${rq.requestId}"/>
                                                        </c:url>
                                                        <a href="${ urlsup }" onclick="return confirm('Voulez-vous vraiment supprimer la ligne ?');">
                                                            <img src="/openmrs/images/trash.gif" alt="Supprimer">
                                                        </a>
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </c:if>
            <c:if test="${mode == 'run'}">
                <div class="col-md-12">
                        <%--<div class="list-group mb-2">--%>
                        <%--<div class="list-group-item list-group-item-info">--%>
                        <%--${report.name}--%>
                        <%--&lt;%&ndash;<p>${report.description}</p>&ndash;%&gt;--%>
                        <%--</div>--%>
                        <%--</div>--%>
                </div>

                <div class="col-sm-4">
                    <form action="" method="post">
                        <input type="hidden" name="reportId" id="reportId" value="${report.reportId}">
                        <input type="hidden" name="locationId" id="locationId" value="">
                        <div class="card mb-2">
                            <div class="card-header">Liste des Etablissements</div>
                            <div class="card-body nano" style="height: 300px;">
                                <div id="location-list" class="nano-content"></div>
                            </div>
                        </div>
                        <div class="card mb-2">
                            <div class="card-header">Param&egrave;tres</div>
                            <div class="card-body">
                                <c:forEach var="parameter" items="${parameters}">
                                    <div class="form-row">
                                        <c:choose>
                                            <c:when test="${fct:contains(parameter.parameterDataType, 'Date')}">
                                                <div class="form-group col-md-12">
                                                    <label for="${parameter.name}" class="label">${parameter.label}</label>
                                                    <div class="input-group">
                                                        <input type="text" name="${parameter.name}" id="${parameter.name}" class="form-control">
                                                        <div class="input-group-append">
                                                            <span class="input-group-text"><i class="fas fa-calendar"></i></span>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:when>
                                            <c:when test="${fct:contains(parameter.parameterDataType, 'Integer')}">
                                                <label for="${parameter.name}" class="label">${parameter.label}</label>
                                                <input type="number" name="${parameter.name}" id="${parameter.name}" class="form-control">
                                            </c:when>
                                            <c:otherwise>
                                                <label for="${parameter.name}" class="label">${parameter.label}</label>
                                                <input type="text" name="${parameter.name}" id="${parameter.name}" class="form-control">
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                        <button type="submit" class="btn btn-success">Ex&eacute;cuter</button>
                    </form>
                </div>
                <div class="col-sm-8">

                    <div class="card">
                        <div class="card-header"><i class="fas fa-eye"></i> ${report.name}</div>
                        <div class="card-body">
                            <table class="table table-hover table-bordered">
                                <thead>
                                <tr>
                                    <th>Date ex&eacute;cution</th>
                                    <th>Auteur</th>
                                    <th>Nom du rapport</th>
                                    <th>Site/R&eacute;gion/District</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="request" items="${reportRequests}">
                                    <tr>
                                        <td><fmt:formatDate type="date" value="${request.requestDate}" pattern="dd/MM/yyyy HH:mm:ss"/></td>
                                        <td>
                                            <c:forEach var="person" items="${ request.creator.person.names }">
                                                ${person.familyName} ${person.middleName} ${person.givenName}
                                            </c:forEach>
                                        </td>
                                        <td>${request.name}</td>
                                        <td>${request.requestLocation.name}</td>
                                        <td>
                                            <table class="table">
                                                <tr>
                                                    <td>
                                                        <c:url value="/module/ServerReport/reportRun.form" var="viewUrl">
                                                            <c:param name="reportViewId" value="${request.requestId}"/>
                                                        </c:url>
                                                        <a href="${ viewUrl }"><img src="<c:url value="/images/file.gif"/>" alt="View"></a>
                                                    </td>
                                                    <td>
                                                        <c:url value="/module/ServerReport/reportRunExcelView.form" var="excelUrl">
                                                            <c:param name="reportExcelId" value="${request.requestId}"/>
                                                        </c:url>
                                                        <a href="${ excelUrl }" target="_blank">
                                                            <img height="15px" width="16px" src="<c:url value="/moduleResources/ptme/images/excel.png"/>" alt="Excel">
                                                        </a>
                                                    </td>
                                                    <c:if test="${request.saved == false}">
                                                        <td>
                                                            <c:url value="/module/ServerReport/reportRun.form" var="saveUrl">
                                                                <c:param name="reportSaveId" value="${request.requestId}"/>
                                                                <c:param name="reportId" value="${report.reportId}"/>
                                                            </c:url>
                                                            <a href="${ saveUrl }"><img src="<c:url value="/images/save.gif"/>" alt="Save"></a>
                                                        </td>
                                                    </c:if>
                                                    <td>
                                                        <c:url value="/module/ServerReport/reportRun.form" var="urlsup">
                                                            <c:param name="delId" value="${request.requestId}"/>
                                                            <c:param name="reportId" value="${report.reportId}"/>
                                                        </c:url>
                                                        <a href="${ urlsup }" onclick="return confirm('Voulez-vous vraiment supprimer la ligne ?');">
                                                            <img src="/openmrs/images/trash.gif" alt="Supprimer">
                                                        </a>
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </c:if>
            <c:if test="${mode == 'reportView'}">
                <div class="col-md-12">
                    <table class="table table-sm">

                        <tr>
                            <td>Region / District / Site</td>
                            <td>${request.requestLocation.name}</td>
                        </tr>
                        <tr>
                            <td>Raport </td>
                            <td>${request.name}</td>
                        </tr>

                    </table>
                    <hr>

                    <c:forEach var="dataSet" items="${reportValues.dataSetXmlClasses}">
                        <c:set var = "countLine" value = "${0}"/>
                        <div class="card">
                            <div class="card-header">${dataSet.name}</div>
                            <div class="card-body">
                                <table class="table table-sm table-bordered table-hover">
                                    <c:forEach var="indicator" items="${dataSet.indicatorXmlClasses}">
                                        <c:if test="${countLine == 0}">
                                            <thead>
                                            <tr>
                                                <th class="align-middle">Indicateurs</th>
                                                <c:forEach var="val" items="${indicator.valueXmlClasses}">
                                                    <th class="text-center text-sm-center align-middle">${val.coName}</th>
                                                </c:forEach>
                                            </tr>
                                            </thead>
                                        </c:if>
                                        <tr>
                                            <td width="400px">${indicator.name}</td>
                                            <c:forEach var="val" items="${indicator.valueXmlClasses}">
                                                <td class="text-center align-middle">${val.value}</td>
                                            </c:forEach>
                                        </tr>
                                        <c:set var = "countLine" value = "${countLine + 1}"/>
                                    </c:forEach>
                                </table>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:if>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/template/footer.jsp"%>