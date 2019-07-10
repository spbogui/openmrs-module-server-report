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
            Gestion des <spring:message code="ServerReport.manageParameter" />  -
            <c:if test="${mode == 'View' || empty mode}">Liste</c:if>
            <c:if test="${mode == 'form'}">Formulaire</c:if>
        </h6>
        <div class="row">
            <c:if test="${mode == 'View'}">
                <div class="col-md-12 mb-2">
                    <form:form action="" commandName="getParameterForm" id="form" method="get" >
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
                            <th>Libell&eacute;</th>
                            <th>Nom</th>
                            <th>Type de donn&eacute;es</th>
                            <th width="50px"></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="p" items="${parameters}">
                            <tr>
                                <td>${p.label}</td>
                                <td>${p.name}</td>
                                <td>${p.parameterDataType}</td>
                                <td class="text-center">
                                    <div class="btn-group">
                                        <c:url value="/module/ServerReport/manageParameter.form" var="url">
                                            <c:param name="parameterId" value="${p.parameterId}"/>
                                        </c:url>
                                        <a href="${ url }" class="btn"><i class="fas fa-edit text-info"></i></a>
                                        <c:url value="/module/ServerReport/manageParameter.form" var="urlsup">
                                            <c:param name="delId" value="${p.parameterId}"/>
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
                    <form:form action="" commandName="parameter" id="form" method="post" cssClass="">
                        <form:hidden path="parameterId"/>
                        <form:hidden path="uuid"/>
                        <div class="form-group row">
                            <label for="name" class="col-sm-2 col-form-label text-right">Libell&eacute; du param&egrave;tre</label>
                            <div class="col-sm-6">
                                <form:input path="label" id="label" cssClass="form-control"/>
                                <small id="nameHelp" class="form-text text-muted text-warning">
                                    <form:errors cssClass="error" path="label"/>
                                </small>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="name" class="col-sm-2 col-form-label text-right">Nom du param&egrave;tre</label>
                            <div class="col-sm-6">
                                <form:input path="name" id="name" cssClass="form-control"/>
                                <small id="nameHelp" class="form-text text-muted text-warning">
                                    <form:errors cssClass="error" path="name"/>
                                </small>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="parameterDataType" class="col-sm-2 col-form-label text-right">Type de donn&eacute;es</label>
                            <div class="col-sm-6">
                                <form:select path="parameterDataType" id="parameterDataType" cssClass="form-control">
                                    <option value=""></option>
                                    <option value="java.lang.String">Texte</option>
                                    <option value="java.util.Date">Date</option>
                                    <option value="java.lang.Integer">Nombre</option>
                                </form:select>
                                <small id="sqlQueryHelp" class="form-text text-muted text-warning">
                                    <form:errors cssClass="error" path="parameterDataType"/>
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