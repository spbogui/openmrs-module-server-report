<%@ include file="/WEB-INF/template/include.jsp"%>
<openmrs:require privilege="Manage Server Report" otherwise="/login.htm" redirect="/module/ServerReport/manageCategory.form" />
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>
<script type="text/javascript">

    $(document).ready(function () {

        sortElement("availableOptions");
        sortElement("selectedOptions");

        $("#forward").click(function () {
            alert('forward');
            moveForward();
        });

        $("#backward").click(function () {
            moveBackward();
        });

        $('#availableOptions option').dblclick(function (e) {
            moveForward();
        });

        $('#selectedOptions option').dblclick(function (e) {
            moveBackward();
        });

        function moveForward() {
            var forwardVariable = '';
            $("#availableOptions :selected").each(function () {
                forwardVariable += '<option value="'+$(this).val()+'" selected="selected">'+$(this).html()+'</option>';
                $(this).remove();
            });

            $("#selectedOptions").append(forwardVariable);

            sortElement("selectedOptions");
        }

        function moveBackward()
        {
            var backwardVariable='';
            //taking the selected items of list 2 and concatenating to a variable named backward_variable.
            $("#selectedOptions :selected").each(function(){
                backwardVariable+="<option value='"+$(this).val()+"'>"+$(this).html()+"</option>";
                $(this).remove();
            });

            $("#selectedOptions").each(function(){
                $(this).attr("selected", "selected");
            });

            //Now appending the selected firs list's element to the list 1.
            $("#availableOptions").append(backwardVariable);


            //Sorting the list 2 so that it shows the list alphabetically
            sortElement("availableOptions");

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
    });

    function validateMyForm()
    {
        var select = document.getElementById("selectedOptions");
        for (var i = 0; i < select.options.length; i++) {
            select.options[i].selected="selected";
        }
        return true;
    }

</script>

<h5 style="text-transform: uppercase;" id="page-title">
    <spring:message code="ServerReport.manageReportTitle" />
</h5>
<div class="box">

    <div class="container-fluid">
        <%@ include file="template/reportAdministrationHeader.jsp"%>
        <h6 class="alert alert-info">
            Gestion des <spring:message code="ServerReport.manageCategory" /> -
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
                            <th>Nom de la cat&eacute;gorie</th>
                            <th width="50px"></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="category" items="${categories}">
                            <tr>
                                <td>${category.code}</td>
                                <td>${category.name}</td>
                                <td class="text-center">
                                    <div class="btn-group">
                                        <c:url value="/module/ServerReport/manageCategory.form" var="url">
                                            <c:param name="categoryId" value="${category.categoryId}"/>
                                        </c:url>
                                        <a href="${ url }" class="btn"><i class="fas fa-edit text-info"></i></a>
                                        <c:url value="/module/ServerReport/manageCategory.form" var="urlsup">
                                            <c:param name="delId" value="${category.categoryId}"/>
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
                    <form:form action="" commandName="category" id="form" method="post" cssClass="">
                        <form:hidden path="categoryId"/>
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
                            <label for="description" class="col-sm-2 col-form-label text-right">Option de Cat&eacute;gorie</label>
                            <div class="col-sm-3">
                                <select name="optionId" id="availableOptions" multiple="multiple" class="form-control">
                                    <c:forEach var="aOption" items="${availableOptions}">
                                        <option value="${aOption.name}" >${aOption.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-sm-3">
                                <form:select path="options" id="selectedOptions" cssClass="form-control">
                                    <c:forEach var="sOption" items="${category.options}">
                                        <option value="${sOption.name}">${sOption.name}</option>
                                    </c:forEach>
                                    <%--<form:options items="${category.options}" itemValue="name" itemLabel="name"/>--%>
                                </form:select>
                                <small id="descriptionHelp" class="form-text text-muted text-warning">
                                    <form:errors cssClass="error" path="options"/>
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
                            <a class="btn btn-info" style="text-decoration: none; color: #ffffff;"
                               href="${pageContext.request.contextPath}/module/ServerReport/manageCategory.form">
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