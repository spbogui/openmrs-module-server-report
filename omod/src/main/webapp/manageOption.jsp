<%@ include file="/WEB-INF/template/include.jsp"%>
<openmrs:require privilege="Manage Server Report" otherwise="/login.htm" redirect="/module/ServerReport/manageOption.form" />
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>
<h5 style="text-transform: uppercase;" id="page-title">
    <spring:message code="ServerReport.manageReportTitle" />
</h5>
<div class="box">

    <div class="container-fluid">
        <%@ include file="template/reportAdministrationHeader.jsp"%>
        <h6 class="alert alert-info">
            Gestion des <spring:message code="ServerReport.manageOption" />  -
            <c:if test="${mode == 'View' || empty mode}">Liste</c:if>
            <c:if test="${mode == 'form'}">Formulaire</c:if>
        </h6>
        <div class="row">
            <c:if test="${mode == 'View'}">
                <div class="col-md-12 mb-2">
                    <form:form action="" commandName="getCategoryForm" id="form" method="get" >
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
                            <th>Nom de l'option</th>
                            <th width="50px"></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="cOption" items="${options}">
                            <tr>
                                <td>${cOption.code}</td>
                                <td>${cOption.name}</td>
                                <td class="text-center">
                                    <div class="btn-group">
                                        <c:url value="/module/ServerReport/manageOption.form" var="url">
                                            <c:param name="optionId" value="${cOption.optionId}"/>
                                        </c:url>
                                        <a href="${ url }" class="btn"><i class="fas fa-edit text-info"></i></a>
                                        <c:url value="/module/ServerReport/manageOption.form" var="urlsup">
                                            <c:param name="delId" value="${cOption.optionId}"/>
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
                    <form:form action="" commandName="categoryOption" id="form" method="post" cssClass="">
                        <form:hidden path="optionId"/>
                        <form:hidden path="uuid"/>
                        <div class="form-group row">
                            <label for="code" class="col-sm-2 col-form-label text-right">Code</label>
                            <div class="col-sm-6">
                                <form:input path="code" id="code" cssClass="form-control"/>
                                <small id="codeHelp" class="form-text text-muted text-warning">
                                    <form:errors cssClass="error" path="code"/>
                                </small>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="name" class="col-sm-2 col-form-label text-right">Nom de l'option</label>
                            <div class="col-sm-6">
                                <form:input path="name" id="name" cssClass="form-control"/>
                                <small id="nameHelp" class="form-text text-muted text-warning">
                                    <form:errors cssClass="error" path="name"/>
                                </small>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="sqlQuery" class="col-sm-2 col-form-label text-right">Requ&ecirc;te SQL</label>
                            <div class="col-sm-6">
                                <form:textarea path="sqlQuery" id="sqlQuery" cssClass="form-control" rows="2"/>
                                <small id="sqlQueryHelp" class="form-text text-muted text-warning">
                                    <form:errors cssClass="error" path="sqlQuery"/>
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
                        <div class="offset-2 col-sm-6">
                            <button type="submit" class="btn btn-success" name="add">Enregistrer</button>
                            <a class="btn btn-info" style="text-decoration: none; color: white"
                               href="${pageContext.request.contextPath}/module/ServerReport/manageOption.form">
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