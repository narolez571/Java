<%@ include file="/WEB-INF/jsp/includes/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/header.jsp" %>

<c:choose>
    <c:when test="${releaseVersion.new}"><c:set var="method" value="post"/></c:when>
    <c:otherwise><c:set var="method" value="post"/></c:otherwise>
</c:choose>

<h2 id="title"><c:if test="${releaseVersion.new}">New </c:if>Release Version</h2>

<form:form modelAttribute="releaseVersion" method="${method}">
    <table>
        <c:forEach items="${componentVersionsBean.componentNames}" var="componentName">
            <tr>
                <td>${componentName} version: <form:errors path="componentVersions[${componentName}]"/></td>
                <td>
                    <form:select path="componentVersions[${componentName}]">
                        <form:option value="NONE" label="--- Select ---"/>
                        <form:options items="${componentVersionsBean.componentVersions[componentName]}"/>
                    </form:select>
                </td>
            </tr>
        </c:forEach>

        <tr>
            <td>
                Description: <form:errors path="description" ccsClass="errors"/>
            </td>
            <td>
                <form:input id="descriptionField" path="description" size="30" maxlength="180"/>
            </td>
        </tr>
        <tr>
            <td>
                <c:choose>
                    <c:when test="${releaseVersion.new}">
                        <p class="submit"><input id="addButton" type="submit" value="Add New Release Version"/></p>
                    </c:when>
                    <c:otherwise>
                        <p class="submit"><input id="updateButton" type="submit" value="Update Release Version"/></p>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
        <tr>
            <td>
                <c:if test="${!releaseVersion.new}">
                    <form:form method="delete">
                        <p class="submit"><input type="submit" value="Delete Release Version"/></p>
                    </form:form>
                </c:if>
            </td>
        </tr>
    </table>
</form:form>
<%@ include file="/WEB-INF/jsp/includes/footer.jsp" %>
