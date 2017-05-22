<%@ taglib prefix="th" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
    <title>Deal is in development now</title>
    <meta http-equiv = "content-type" content = "text/html" charset = "UTF-8">
    <link rel = "stylesheet" type = "text/css" href = "${pageContext.request.contextPath}/resources/css/index.css">
</head>
<body>
<div id = "development">
    <p>Site is in development now. Enter the password to proceed.</p><br>
    <spring:form method="post"  modelAttribute="userForm" action="/main">
        Password: <form:input path="enteredPassword"/><br/>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
    </spring:form>
</div>
</body>
</html>
