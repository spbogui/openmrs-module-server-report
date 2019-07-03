<%@ include file="/WEB-INF/template/include.jsp"%>
<openmrs:require privilege="Manage Server Report" otherwise="/login.htm" redirect="/module/ServerReport/manageIndicatorRates.form" />
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>
<script>

</script>

<h5 style="text-transform: uppercase;" id="page-title">
    <spring:message code="ServerReport.manageReportTitle" />
</h5>

<div class="box">

    <div class="container-fluid">
        <%@ include file="template/reportAdministrationHeader.jsp"%>
        <h6 class="alert alert-info">
            Gestion des <spring:message code="ServerReport.manageIndicatorRate" />  -
            <c:if test="${mode == 'View' || empty mode}">Liste</c:if>
            <c:if test="${mode == 'form'}">Formulaire</c:if>
        </h6>
        <div class="row">
            <c:if test="${mode == 'View'}">
                <div class="col-md-12 mb-2">
                    <form:form action="" commandName="getIndicatorForm" id="form" method="get" >
                        <table cellspacing="0" cellpadding="5">
                            <tr>
                                <td><button class="btn btn-success" type="submit" value="form" name="mode">Ajouter</button></td>
                            </tr>
                        </table>
                    </form:form>
                </div>
                <div class="col-md-12">
                    <table class="table table-sm table-bordered table-hover">
                        <thead>
                        <tr>
                            <th>Code</th>
                            <th>Nom</th>
                            <th width="50px"></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="report" items="${indicatorRates}">
                            <tr>
                                <td class="align-middle">${report.code}</td>
                                <td class="align-middle">${report.name}</td>
                                <td class="align-middle text-center">
                                    <div class="btn-group">
                                        <c:url value="/module/ServerReport/manageIndicatorRates.form" var="url">
                                            <c:param name="categoryId" value="${report.indicatorRateId}"/>
                                        </c:url>
                                        <a href="${ url }" class="btn"><i class="fas fa-edit text-info"></i></a>
                                        <c:url value="/module/ServerReport/manageIndicatorRates.form" var="urlsup">
                                            <c:param name="delId" value="${report.indicatorRateId}"/>
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
                <div class="col-sm-6 mb-2">
                    <form:form action="" commandName="indicatorRate" id="form" method="post" cssClass="">
                        <form:hidden path="indicatorRateId"/>
                        <form:hidden path="uuid"/>
                        <div class="form-group row">
                            <label for="code" class="col-sm-2 col-form-label text-right">Code</label>
                            <div class="col-sm-4">
                                <form:input path="code" id="code" cssClass="form-control"/>
                                <small id="codeHelp" class="form-text text-muted text-warning">
                                    <form:errors cssClass="error" path="code"/>
                                </small>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="name" class="col-sm-2 col-form-label text-right">Nom de l'indicateur</label>
                            <div class="col-sm-10">
                                <form:input path="name" id="name" cssClass="form-control"/>
                                <small id="nameHelp" class="form-text text-muted text-warning">
                                    <form:errors cssClass="error" path="name"/>
                                </small>
                            </div>
                        </div>
                        <%--<div class="form-group row">--%>
                            <%--<label for="numerator" class="col-sm-2 col-form-label text-right">Num&eacute;rateur</label>--%>
                            <%--<div class="col-sm-10">--%>
                                <%--<form:select path="numerator" id="numerator" cssClass="form-control">--%>
                                    <%--<c:forEach var="nIndicator" items="${numeratorIndicators}">--%>
                                        <%--<option value="${nIndicator.name}">${nIndicator.name}</option>--%>
                                    <%--</c:forEach>--%>
                                <%--</form:select>--%>
                                <%--<small id="numeratorIndicatorHelp" class="form-text text-muted text-warning">--%>
                                    <%--<form:errors cssClass="error" path="numerator"/>--%>
                                <%--</small>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <%--<div class="form-group row">--%>
                            <%--<label for="denominator" class="col-sm-2 col-form-label text-right">Num&eacute;rateur</label>--%>
                            <%--<div class="col-sm-10">--%>
                                <%--<form:select path="denominator" id="denominator" cssClass="form-control">--%>
                                    <%--<c:forEach var="nIndicator" items="${denominatorIndicators}">--%>
                                        <%--<option value="${nIndicator.name}">${nIndicator.name}</option>--%>
                                    <%--</c:forEach>--%>
                                <%--</form:select>--%>
                                <%--<small id="denominatorIndicatorHelp" class="form-text text-muted text-warning">--%>
                                    <%--<form:errors cssClass="error" path="denominator"/>--%>
                                <%--</small>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <div class="form-group row">
                            <label for="formula" class="col-sm-2 col-form-label text-right">Formule de calcul</label>
                            <div class="col-sm-10">
                                <form:textarea path="formula" id="formula" cssClass="form-control" rows="2"/>
                                <small id="descriptionHelp" class="form-text text-muted text-warning">
                                    <form:errors cssClass="error" path="formula"/>
                                </small>
                            </div>
                            <div class="offset-2 col-sm-6">
                                <div class="btn-group">
                                    <button type="button" id="plus" class="btn btn-secondary"><i class="fas fa-plus"></i></button>
                                    <button type="button" id="minus" class="btn btn-secondary"><i class="fas fa-minus"></i></button>
                                    <button type="button" id="times" class="btn btn-secondary"><i class="fas fa-times"></i></button>
                                    <button type="button" id="divide" class="btn btn-secondary"><i class="fas fa-divide"></i></button>
                                </div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="category" class="col-sm-2 col-form-label text-right">Cat&eacute;gorie</label>
                            <div class="col-sm-6">
                                <small id="categoryHelp" class="form-text text-muted text-warning">
                                    <form:errors cssClass="error" path="category"/>
                                </small>
                                <form:select path="category" id="category" cssClass="form-control">
                                    <<option value=""></option>
                                    <c:forEach var="c" items="${categories}">
                                        <option value="${c.name}" <c:if test="${c.name == indicatorRate.category.name}">selected="selected"</c:if> >${c.name}</option>
                                    </c:forEach>
                                </form:select>
                            </div>
                        </div>

                        <div class="form-group row">
                            <label for="description" class="col-sm-2 col-form-label text-right">Description</label>
                            <div class="col-sm-10">
                                <form:textarea path="description" id="description" cssClass="form-control" rows="2"/>
                                <small id="descriptionHelp" class="form-text text-muted text-warning">
                                    <form:errors cssClass="error" path="description"/>
                                </small>
                            </div>
                        </div>
                        <div class="offset-2 col-sm-6">
                            <button type="submit" class="btn btn-success" name="add">Enregistrer</button>
                            <a class="btn btn-info" style="text-decoration: none; color: white"
                               href="${pageContext.request.contextPath}/module/ServerReport/manageIndicatorRates.form">
                                Voir la liste
                            </a>
                        </div>
                    </form:form>
                </div>
                <div class="col-sm-6">
                    <table class="table table-sm table-bordered table-hover">
                        <thead class="">
                        <tr>
                            <th>Code</th>
                            <th>Nom</th>
                            <th width="10px"></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="ind" items="${indicators}">
                            <tr>
                                <td>${ind.code}</td>
                                <td>${ind.name}</td>
                                <td class="text-center">
                                    <div class="btn-group">
                                        <button class="btn"><i class="fas fa-plus-circle text-success"></i></button>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>

                        </tbody>
                    </table>
                </div>
            </c:if>
        </div>
    </div>

</div>

<%@ include file="/WEB-INF/template/footer.jsp"%>