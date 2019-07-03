<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fct" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ include file="includeScript.jsp"%>

<script>
    $(document).ready(function (e) {
        $('li.nav-item > a.active')
            .css('background-color', '#1aac9b')
            .css('color', 'white')
            .css('border-color', '#1aac9b')
            .css('text-decoration', 'none');

        $.fn.dataTable.moment( 'HH:mm MMM D, YY' );
        $.fn.dataTable.moment( 'dddd, MMMM Do, YYYY' );
        $.fn.dataTable.moment( 'DD/MM/YYYY' );
        $.fn.dataTable.moment( 'dd/MM/yyyy' );

        jQuery.extend( jQuery.fn.dataTableExt.oSort, {
            "date-uk-pre": function ( a ) {
                if (a === null || a === "") {
                    return 0;
                }
                var ukDatea = a.split('/');
                return (ukDatea[2] + ukDatea[1] + ukDatea[0]) * 1;
            },

            "date-uk-asc": function ( a, b ) {
                return ((a < b) ? -1 : ((a > b) ? 1 : 0));
            },

            "date-uk-desc": function ( a, b ) {
                return ((a < b) ? 1 : ((a > b) ? -1 : 0));
            }
        } );

        <%--$.datepicker.setDefaults({--%>
            <%--showOn: "both",--%>
            <%--//buttonImageOnly: true,--%>
            <%--//buttonImage: "${pageContext.request.contextPath}/moduleResources/ptme/images/calendar.gif",--%>
            <%--buttonText: "Calendar"--%>
        <%--});--%>
//        $.datepicker.setDefaults( $.datepicker.regional[ "fr" ] );
//
//        $(".datepicker").datepicker({
//            dateFormat: 'dd/mm/yy',
////                dayNames: [ "Dimanche", "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi" ],
//            dayNamesShort: [ "Dim", "Lun", "Mar", "Mer", "Jeu", "Ven", "Sam" ],
//            monthNamesShort: [ "Jan", "Fev", "Mar", "Avr", "Mai", "Jui", "Juil", "Aou", "Sep", "Oct", "Nov", "Dec" ]
////                monthNames: [ "Janvier", "Fevrier", "Mars", "Avril", "Mai", "Juin", "Juillet", "Aout", "Septembre", "Octobre", "Novembre", "Decembre" ]
//        });
        $('.datepicker').datepicker({
            locale: 'fr-fr',
            format: 'dd/MM/yyyy'
        });

    });
</script>


<spring:htmlEscape defaultHtmlEscape="true" />
<ul id="menu">
	<li class="first <c:if test='<%= request.getRequestURI().contains("/dashboard") %>'>active</c:if>">
		<a
		href="${pageContext.request.contextPath}/module/ServerReport/dashboard.form"><spring:message
				code="ServerReport.dashboard" /></a>
	</li>
	<li <c:if test='<%= request.getRequestURI().contains("/report") || request.getRequestURI().contains("executeReport.form") %>'>class="active"</c:if>>
		<a
		href="${pageContext.request.contextPath}/module/ServerReport/reportRun.form"><spring:message
				code="ServerReport.report" /></a>
	</li>
	<li class="<c:if test='<%= request.getRequestURI().contains("/listing") %>'>active</c:if>">
		<a
				href="${pageContext.request.contextPath}/module/ServerReport/listing.form">
			<spring:message code="ServerReport.listing" />
		</a>
	</li>
	<li <c:if test='<%= request.getRequestURI().contains("/dataQuality") %>'>class="active"</c:if>>
		<a
		href="${pageContext.request.contextPath}/module/ServerReport/dataQuality.form"><spring:message
				code="ServerReport.dataQuality" /></a>
	</li>
	<li <c:if test='<%= request.getRequestURI().contains("/manage") %>'>class="active"</c:if>>
		<a
		href="${pageContext.request.contextPath}/module/ServerReport/manageReport.form"><spring:message
				code="ServerReport.manage" /></a>
	</li>

	
	<!-- Add further links here -->
</ul>
