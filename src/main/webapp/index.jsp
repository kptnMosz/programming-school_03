<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: wojtek
  Date: 29.06.18
  Time: 12:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/fragments/header.jsp" %>
<h3>Rozwiazania</h3>
<table border="1">
<c:forEach items="${solutions}" var="solution">
    <tr><td>${solution.description}</td> <td>${solution.created}</td></tr>
</c:forEach>
</table>

<%@include file="fragments/footer.jsp" %>
</body>
</html>
