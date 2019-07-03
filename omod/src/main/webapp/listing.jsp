<%@ include file="/WEB-INF/template/include.jsp"%>
<openmrs:require privilege="Manage Server Report" otherwise="/login.htm" redirect="/module/ServerReport/listing.form" />
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>

<script type="application/javascript">
    var locations = [];
    <c:if test="${not empty json}">
    locations = ${json};
    </c:if>

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

        $("#search").click(function(e) {
            if ($('#q').val() !== null || $('#q').val() !== undefined) {
                $("#location-list").jstree(true).search($("#q").val());
            }
        });

//        $('#q').change(function (e) {
//            if ($(this).val() !== null || $(this).val() !== undefined) {
//                $("#location-list").jstree(true).search($("#q").val());
//            }
//        });

//        $('#location-list').on('activate_node.jstree', function (e, data) {
//            var node = null;
//            if (data === undefined || data.node === undefined || data.node.id === undefined) {
//                $('#locationId').value("");
//
//            } else {
//                node = data.node.id;
//            }
//            alert('clicked node: ' + data.node.id);
//            $('#locationId').value(node);
//        });

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

        /*var node = $('#location-list').jstree('get_selected').attr('id');
        $('#locationId').value(node);
        $(".nano-pane").css("display","block");
        $(".nano-slider").css("display","block");*/

        $("#table-listing").dataTable({
            dom: 'B<"clear">lfrtip',
            buttons: {
                name: 'primary',
                buttons: [ 'copy', 'excel' ]
            },
            "pageLength": 20,
            //"order": [[1, "desc"]],
            "scrollX": true,
            "language": {
                "zeroRecords": "Aucun enregistrement ce jour",
                paginate: {
                    previous: 'Pr&eacute;c&eacute;dent',
                    next:     'Suivant'
                },
                "info":"Affichage de _START_ a _END_ sur _TOTAL_ ",
                "search": "Filtrer"
            },
            "lengthChange": false,
            "stripeClasses": [ 'odd', 'even' ]
        });
    });
</script>

<h5 style="text-transform: uppercase;" id="page-title">
    Listing des patients
</h5>
<div class="box">
    <div class="container-fluid">
        <c:if test="${mode == 'param'}">
            <div class="alert alert-info">
                <h5 class="alert-heading">
                    <span><i class="fas fa-list"></i> ${listing.name}</span>
                </h5>
                <p class="text-sm-left">${listing.description}</p>
            </div>
        </c:if>
        <div class="row">
            <c:if test="${mode == 'list'}">
                <div class="col-md-12">
                    <div class="card">
                        <div class="card-header">Listings disponibles</div>
                        <div class="card-body">
                            <div class="list-group">
                                <c:forEach var="l" items="${listings}">
                                    <div class="list-group-item list-group-item-info">
                                        <h6 class="text-lg-left">
                                            <a href="${pageContext.request.contextPath}/module/ServerReport/listing.form?listingId=${l.listingId}&mode=param">
                                                    ${l.name}
                                            </a>
                                        </h6>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
            </c:if>

            <c:if test="${mode == 'param'}">

                <form:form action="" commandName="runForm" id="form" method="get" cssClass="row col-md-12 mb-5">
                    <form:hidden path="listingId" id="listingId"/>
                    <form:hidden path="locationId" id="locationId"/>
                    <div class="col-md-6">
                        <div class="card">
                            <div class="card-header">Liste des Etablissements</div>
                            <div class="card-body nano" style="height: 350px;">
                                <div id="location-list" class="nano-content"></div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="card">
                            <div class="card-header">P&eacute;riode / Date</div>
                            <div class="card-body">
                                <div class="form-group col-md-5">
                                    <label for="startDate" class="label">Date de d&eacute;but</label>
                                    <div class="input-group">
                                        <c:if test="${!hasStartDate}">
                                            <form:input path="startDate" id="startDate" cssClass="form-control" disabled="true"/>
                                        </c:if>
                                        <c:if test="${hasStartDate}">
                                            <form:input path="startDate" id="startDate" cssClass="form-control datepicker"/>
                                        </c:if>
                                        <div class="input-group-append">
                                            <span class="input-group-text"><i class="fas fa-calendar"></i></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group col-md-5">
                                    <label for="endDate" class="label">Date de fin</label>
                                    <div class="input-group">
                                        <c:if test="${!hasEndDate}">
                                            <form:input path="endDate" id="endDate" cssClass="form-control" disabled="true"/>
                                        </c:if>
                                        <c:if test="${hasEndDate}">
                                            <form:input path="endDate" id="endDate" cssClass="form-control"/>
                                        </c:if>
                                        <div class="input-group-append">
                                            <span class="input-group-text"><i class="fas fa-calendar"></i></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <button type="submit" class="btn btn-success" name="mode" value="run">Ex&eacute;cuter</button>
                                </div>
                            </div>
                        </div>
                    </div>

                </form:form>
            </c:if>

            <c:if test="${mode == 'run'}">
                <div class="col-md-12">
                    <div class="btn-group mb-2">
                        <a href="${pageContext.request.contextPath}/module/ServerReport/listing.form?listingId=${listing.listingId}&mode=param"
                           class="btn btn-info">
                            <i class="fas fa-arrow-left fa-1x"></i>
                        </a>
                        <a href="" class="btn btn-success" id="reload-btn">
                            <i class="fas fa-eye fa-1x"></i>
                        </a>
                        <a href="${pageContext.request.contextPath}/module/ServerReport/listing.form" class="btn btn-secondary">
                            <i class="fas fa-list fa-1x"></i>
                        </a>
                    </div>

                    <div class="alert alert-info alert">
                        <h4 class="alert-heading">
                                ${location}
                        </h4>

                        <div class="alert alert-light">
                            <h5>${listing.name}
                                <c:choose>
                                    <c:when test="${hasStartDate && hasEndDate}">
                                        entre le <fmt:formatDate type="date" value="${ startDate }" pattern="dd/MM/yyyy" /> et le
                                        <fmt:formatDate type="date" value="${ endDate }" pattern="dd/MM/yyyy" />
                                    </c:when>
                                    <c:when test="${hasStartDate && !hasEndDate}">
                                        &agrave; la date du <fmt:formatDate type="date" value="${ startDate }" pattern="dd/MM/yyyy" />
                                    </c:when>
                                    <c:when test="${!hasStartDate && hasEndDate}">
                                        &agrave; la date du <fmt:formatDate type="date" value="${ endDate }" pattern="dd/MM/yyyy" />
                                    </c:when>
                                    <c:otherwise>
                                        pas d'information de date
                                    </c:otherwise>
                                </c:choose></h5>
                        </div>

                        <%--<div class="row">--%>
                            <%--<div class="col-md-12">--%>
                                <%--<table class="table">--%>
                                    <%--<tr>--%>
                                        <%--<td>Site / R&eacute;gion / Dstrict</td>--%>
                                        <%--<td>${location}</td>--%>
                                    <%--</tr>--%>
                                    <%--<tr>--%>
                                        <%--<td>Date</td>--%>
                                        <%--<td><fmt:formatDate type="date" value="${ startDate }" pattern="dd/MM/yyyy" /></td>--%>
                                        <%--<td><fmt:formatDate type="date" value="${ endDate }" pattern="dd/MM/yyyy" /></td>--%>
                                    <%--</tr>--%>
                                <%--</table>--%>
                            <%--</div>--%>
                        <%--</div>--%>

                    </div>
                    <c:choose>
                        <c:when test="${empty listingResult}">
                            <div class="alert alert-warning">
                                Aucun patient &agrave; afficher pour les crit&egrave;res et param&egrave;tres saisis
                            </div>
                        </c:when>
                        <c:otherwise>
                            <table class="table table-hover table-bordered table-striped table-sm" id="table-listing" width="100%">
                                <thead>
                                <tr>
                                    <c:forEach var="column" items="${columnNames}">
                                        <th>${column}</th>
                                    </c:forEach>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="map" items="${listingResult}">
                                    <tr>
                                        <c:forEach var="entry" items="${map}">
                                            <td>
                                                <c:choose>
                                                    <c:when test="${fct:contains(entry.key, 'Date') || fct:contains(entry.key, 'Naissance')}">
                                                        <fmt:formatDate type="date" value="${ entry.value }" pattern="dd/MM/yyyy" />
                                                    </c:when>
                                                    <c:otherwise>
                                                        ${entry.value}
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </c:forEach>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:if>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/template/footer.jsp"%>