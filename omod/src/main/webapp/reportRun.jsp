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
                /*{
                    "text" : "Côte d'Ivoire",
                    //"state" : {"opened" : true },
                    "children" : [
                        {
                            "text" : "ABIDJAN 1 GRANDS PONTS",
                            "state" : { "selected" : false },
                            "icon" : "fa fa-flash"
                        },
                        { "text" : "ABIJDAN 2" }
                    ]
                }*/
                'themes' : {
                    'name' : "default",
                    'variant' : "medium",
                    'responsive' : true
                }
            },
            "plugins" : ["ui","themes","checkbox"]
        });

        $('#location-list').on('activate_node.jstree', function (e, data) {
            if (data === undefined || data.node === undefined || data.node.id === undefined)
                return;
            alert('clicked node: ' + data.node.id);
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
            <div class="col-md-5">
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
            <div class="col-md-7">
                <div class="card">
                    <div class="card-header">Rapports Ex&eacute;cut&eacute;s</div>
                    <div class="card-body">
                        <table class="table table-hover table-bordered">
                            <thead>
                            <tr>
                                <th>Nom du rapport</th>
                                <th>P&eacute;riode</th>
                                <th>Date ex&eacute;cution</th>
                                <th>Site/R&eacute;gion/District</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>

                            <tr>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
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
                <form>
                <div class="card mb-2">
                    <div class="card-header">Param&egrave;tres</div>
                    <div class="card-body">

                    </div>
                </div>
                <div class="card">
                    <div class="card-header">Liste des Etablissements</div>
                    <div class="card-body nano" style="height: 300px;">
                        <div id="location-list" class="nano-content"></div>
                    </div>
                </div>
                </form>
            </div>
            <div class="col-sm-8">

                <div class="card">
                    <div class="card-header"><i class="fas fa-eye"></i> ${report.name}</div>
                    <div class="card-body">

                    </div>
                </div>
            </div>
        </c:if>
    </div>

    </div>
</div>

<%@ include file="/WEB-INF/template/footer.jsp"%>