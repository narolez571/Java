<%@ include file="/WEB-INF/jsp/includes/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/header.jsp" %>

<h2 id="title">Release Version Summary</h2>

<table>
    <tr>
        <th>Release Version</th>
        <td>${releaseVersion.version}</td>
    </tr>
    <tr>
        <th>Description</th>
        <td id="description">${releaseVersion.description}</td>
    </tr>
    <tr>
        <th>Create Date</th>
        <td>${releaseVersion.createDate}</td>
    </tr>
    <c:forEach items="${releaseVersion.componentVersions}" var="componentVersions">
        <tr>
            <th>${componentVersions.key}</th>
            <td>${componentVersions.value}</td>
        </tr>
    </c:forEach>
</table>


<%@ include file="/WEB-INF/jsp/includes/footer.jsp" %>

