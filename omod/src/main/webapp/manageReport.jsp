<%@ include file="/WEB-INF/template/include.jsp"%>
<openmrs:require privilege="Manage Server Report" otherwise="/login.htm" redirect="/module/ServerReport/manageReport.form" />
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>

<script type="text/javascript">

    function selectAll(id) {
        $("#" + id).each(function(){
            $(this).attr("selected", "selected");
        });
    }

    function moveOptionFromSelectToOther(select, other/*, formSelect*/) {
        $("#" + select + " :selected").each(function(){
            $("#" + other).append('<option value="'+$(this).val()+'">'+$(this).html()+'</option>');
            $(this).remove();
        });
        sortElement(other);
        /*$("#" + formSelect + " option").each(function(){
            $(this).attr("selected", "selected");
        });*/
    }

    function moveForward() {
        var forwardVariable = '';
        $("#availableIndicatorDataSets :selected").each(function () {
            forwardVariable += '<option value="'+$(this).val()+'" selected="selected">'+$(this).html()+'</option>';
            $(this).remove();
        });

        $("#selectedIndicatorDataSets").append(forwardVariable);

        sortElement("selectedIndicatorDataSets");
    }

    function moveBackward()
    {
        var backwardVariable='';
        //taking the selected items of list 2 and concatenating to a variable named backward_variable.
        $("#selectedIndicatorDataSets :selected").each(function(){
            backwardVariable+='<option value="'+$(this).val()+'">'+$(this).html()+'</option>';
            $(this).remove();
        });

//        $("#selectedIndicatorDataSets").each(function(){
//            $(this).attr("selected", "selected");
//        });

        //Now appending the selected firs list's element to the list 1.
        $("#availableIndicatorDataSets").append(backwardVariable);


        //Sorting the list 2 so that it shows the list alphabetically
        sortElement("availableIndicatorDataSets");

    }

    function sortElement(id)
    {
        var selectElement = $('#'+id);
        var optionsElement = selectElement.children('option').get();
        optionsElement.sort(function(a, b) {
            var compA = $(a).text().toUpperCase();
            var compB = $(b).text().toUpperCase();
            return (compA < compB) ? -1 : (compA > compB) ? 1 : 0;
        });

        $.each(optionsElement, function(index, items) { selectElement.append(items); });

    }

    $(document).ready(function () {

        sortElement("availableIndicatorDataSets");
        sortElement("selectedIndicatorDataSets");

        $("#forward").click(function () {
            //alert('forward');
            moveForward();
        });

        $("#backward").click(function () {
            moveBackward();
        });

        $("#down").click(function () {
            //alert('forward');
            moveOptionFromSelectToOther('availableParameters', 'selectedParameters');
        });

        $("#up").click(function () {
            moveOptionFromSelectToOther('selectedParameters', 'availableParameters');
        });

        $('#availableIndicatorDataSets option').dblclick(function (e) {
            $("#selectedIndicatorDataSets").append('<option value="'+$(this).val()+'" selected="selected">'+$(this).html()+'</option>');
            $(this).remove();
            sortElement("selectedIndicatorDataSets");
            //moveForward();
        });

        $('#selectedIndicatorDataSets option').dblclick(function (e) {
            $("#availableIndicatorDataSets").append('<option value="'+$(this).val()+'" selected="selected">'+$(this).html()+'</option>');
            $(this).remove();
            $("#selectedIndicatorDataSets").each(function(){
                $(this).attr("selected", "selected");
            });
            sortElement("availableIndicatorDataSets");
            //moveBackward();
        });

        $('#availableParameters option').dblclick(function () {
            moveOptionFromSelectToOther('availableParameters', 'selectedParameters'/*, 'selectedParameters'*/);
        });

        $('#selectedParameters option').dblclick(function (e) {
            moveOptionFromSelectToOther('selectedParameters', 'availableParameters'/*, 'selectedParameters'*/);
        });

        $("#form").submit(function (e) {
            //e.preventDefault();
            var selectIndicatorDataSet = document.getElementById("selectedIndicatorDataSets");
            for (var i = 0; i < selectIndicatorDataSet.options.length; i++) {
                selectIndicatorDataSet.options[i].selected="selected";
            }

            var selectedParameter = document.getElementById("selectedParameters");
            for (var j = 0; j < selectedParameter.options.length; j++) {
                selectedParameter.options[j].selected="selected";
            }

            return true;
        })
    });

</script>

<h5 style="text-transform: uppercase;" id="page-title">
    <spring:message code="ServerReport.manageReportTitle" />
</h5>
<div class="box">
    <div class="container-fluid">
        <%@ include file="template/reportAdministrationHeader.jsp"%>
        <h6 class="alert alert-info">
            Gestion des <spring:message code="ServerReport.manageReports" />  -
            <c:if test="${mode == 'View' || empty mode}">Liste</c:if>
            <c:if test="${mode == 'form'}">Formulaire</c:if>
        </h6>
        <div class="row">
            <c:if test="${mode == 'View'}">
                <div class="col-md-12 mb-2">
                    <form:form action="" commandName="getReportForm" id="form" method="get" cssClass="form-inline">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="indicatorType" id="inlineRadio1" value="nonCalculated" checked="checked">
                            <label class="form-check-label" for="inlineRadio1">Rapport indicateur</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="indicatorType" id="inlineRadio2" value="calculated">
                            <label class="form-check-label" for="inlineRadio2">Rapport indicateur Calcul&eacute;s</label>
                        </div>
                        <button class="btn btn-success" type="submit" value="form" name="mode">Ajouter</button>
                    </form:form>
                </div>
                <div class="col-md-12">
                    <table class="table table-sm table-bordered table-hover">
                        <thead>
                        <tr>
                            <th>Nom du rapport</th>
                            <th width="50px"></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="report" items="${reports}">
                            <tr>
                                <td>${report.name}</td>
                                <td class="text-center">
                                    <div class="btn-group">
                                        <c:url value="/module/ServerReport/manageReport.form" var="url">
                                            <c:param name="reportId" value="${report.reportId}"/>
                                            <c:if test="${not empty report.indicatorRateDataSets}">
                                                <c:param name="indicatorType" value="calculated"/>
                                            </c:if>
                                            <c:if test="${not empty report.indicatorDataSets}">
                                                <c:param name="indicatorType" value="nonCalculated"/>
                                            </c:if>
                                        </c:url>
                                        <a href="${ url }" class="btn"><i class="fas fa-edit text-info"></i></a>
                                        <c:url value="/module/ServerReport/manageReport.form" var="urlsup">
                                            <c:param name="delId" value="${report.reportId}"/>
                                        </c:url>
                                        <a href="${ urlsup }" class="btn" onclick="return confirm('Voulez-vous vraiment supprimer la ligne ?');">
                                            <i class="fas fa-trash text-danger"></i>
                                        </a>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>

                </div>
            </c:if>

            <c:if test="${mode == 'form'}">
                <div class="col-sm-12 mb-2">
                    <form:form action="" commandName="serverReport" id="form" method="post" cssClass="">
                        <form:hidden path="reportId"/>
                        <form:hidden path="uuid"/>
                        <div class="form-group row">
                            <label for="name" class="col-sm-2 col-form-label text-right">Nom du rapport</label>
                            <div class="col-sm-6">
                                <form:input path="name" id="name" cssClass="form-control"/>
                                <small id="nameHelp" class="form-text text-muted text-warning">
                                    <form:errors cssClass="error" path="name"/>
                                </small>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="description" class="col-sm-2 col-form-label text-right">Description</label>
                            <div class="col-sm-6">
                                <form:textarea path="description" id="description" cssClass="form-control" rows="2"/>
                                <small id="descriptionHelp" class="form-text text-muted text-warning">
                                    <form:errors cssClass="error" path="description"/>
                                </small>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="roles" class="col-sm-2 col-form-label text-right">Roles utilisateurs</label>
                            <div class="col-sm-6">
                                <form:input path="roles" id="roles" cssClass="form-control"/>
                                <small id="nameHelp" class="form-text text-muted text-warning">
                                    <form:errors cssClass="error" path="roles"/>
                                </small>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="availableIndicatorDataSets" class="col-sm-2 col-form-label text-right">Groupe d'indicateurs</label>
                            <c:choose>
                                <c:when test = "${not empty availableIndicatorDataSets || not empty serverReport.indicatorDataSets}">
                                    <div class="col-sm-8 mb-2">
                                        <select name="indicatorDataSets" id="availableIndicatorDataSets" multiple="multiple" class="form-control">
                                            <c:forEach var="aIndicatorDataSet" items="${availableIndicatorDataSets}">
                                                <option value="${aIndicatorDataSet.name}" >${aIndicatorDataSet.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <button type="button" class="btn btn-sm btn-info" id="forward"><i class="fas fa-arrow-down"></i></button>
                                    <div class="offset-2 col-sm-8">
                                        <form:select path="indicatorDataSets" id="selectedIndicatorDataSets" cssClass="form-control">
                                            <c:forEach var="indicatorDataSet" items="${serverReport.indicatorDataSets}">
                                                <option value="${indicatorDataSet.name}">${indicatorDataSet.name}</option>
                                            </c:forEach>
                                            <%--<form:options items="${category.options}" itemValue="name" itemLabel="name"/>--%>
                                        </form:select>
                                    </div>
                                    <button type="button" class="btn btn-sm btn-info" id="backward"><i class="fas fa-arrow-up"></i></button>
                                    <small id="descriptionHelp" class="form-text text-muted text-warning">
                                        <form:errors cssClass="error" path="indicatorDataSets"/>
                                    </small>
                                </c:when>

                                <c:when test = "${not empty availableIndicatorRateDataSets || not empty serverReport.indicatorRateDataSets}">
                                    <div class="col-sm-8 mb-2">
                                        <select name="indicatorRateDataSets" id="availableIndicatorDataSets" multiple="multiple" class="form-control">
                                            <c:forEach var="aIndicatorRateDataSet" items="${availableIndicatorRateDataSets}">
                                                <option value="${aIndicatorRateDataSet.name}" >${aIndicatorRateDataSet.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <button type="button" class="btn btn-sm btn-info" id="forward"><i class="fas fa-arrow-down"></i></button>
                                    <div class="offset-2 col-sm-8">
                                        <form:select path="indicatorRateDataSets" id="selectedIndicatorDataSets" cssClass="form-control">
                                            <c:forEach var="indicatorRateDataSet" items="${serverReport.indicatorRateDataSets}">
                                                <option value="${indicatorRateDataSet.name}">${indicatorRateDataSet.name}</option>
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                    <button type="button" class="btn btn-sm btn-info" id="backward"><i class="fas fa-arrow-up"></i></button>
                                    <small id="descriptionHelp" class="form-text text-muted text-warning">
                                        <form:errors cssClass="error" path="indicatorRateDataSets"/>
                                    </small>
                                </c:when>

                                <c:otherwise>
                                    <div class="col-sm-8 form-control">Aucun groupe d'indicateurs &agrave; afficher</div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="form-group row">
                            <label for="availableParameters" class="col-sm-2 col-form-label text-right">Param&egrave;tre</label>
                            <div class="col-sm-5 mb-2">
                                <select name="aParameters" id="availableParameters" multiple="multiple" class="form-control">
                                    <c:forEach var="aParameter" items="${availableParameters}">
                                        <option value="${aParameter.name}">${aParameter.name} | ${aParameter.parameterDataType}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <button type="button" class="btn btn-sm btn-info" id="down"><i class="fas fa-arrow-down"></i></button>
                            <div class="offset-2 col-sm-5">
                                <form:select path="parameters" id="selectedParameters" cssClass="form-control">
                                    <c:forEach var="sParameter" items="${serverReport.parameters}">
                                        <option value="${sParameter.name}">${sParameter.name} | ${sParameter.parameterDataType}</option>
                                    </c:forEach>
                                </form:select>
                            </div>
                            <button type="button" class="btn btn-sm btn-info" id="up"><i class="fas fa-arrow-up"></i></button>
                            <small id="parametersHelp" class="form-text text-muted text-warning">
                                <form:errors cssClass="error" path="parameters"/>
                            </small>
                        </div>
                        <div class="offset-2 col-sm-6">
                            <button type="submit" class="btn btn-success" name="add">Enregistrer</button>
                            <a class="btn btn-info" style="text-decoration: none; color: #ffffff;"
                               href="${pageContext.request.contextPath}/module/ServerReport/manageReport.form">
                                Voir la liste
                            </a>
                        </div>
                    </form:form>
                </div>
            </c:if>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/template/footer.jsp"%>