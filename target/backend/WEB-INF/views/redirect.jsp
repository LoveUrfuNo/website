<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>

<html>
<head>
    <title>Access Denied</title>
</head>

<body>
<table>
    <%
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().stream().findFirst().orElse(null).getAuthority().equals("ROLE_USER")) { %>

    <c:redirect url="/profile"/>

    <% } else if (auth.getAuthorities().stream().findFirst().orElse(null).getAuthority().equals("ROLE_ANONYMOUS")) { %>

    <c:redirect url="/main"/>

    <% } else if (auth.getAuthorities().stream().findFirst().orElse(null).getAuthority().equals("ROLE_NOT_ACTIVATED_USER")) { %>

    <c:redirect url="/profile/registration"/>

    <% } else if (auth.getAuthorities().stream().findFirst().orElse(null).getAuthority().equals("ROLE_ADMIN")) { %>

    <c:redirect url="/admin"/>

    <% } %>
</table>
</body>
</html>
