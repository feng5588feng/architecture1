<html>
<body>
<h2>Hello World!customermgr</h2>
<%@page import="com.sishuok.architecture1.customermgr.Customer1" %>
<%@ page import="com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer" %>

<%
    System.out.println("now in Customermgr c1.jsp");
    new Customer1().c1();
%>

</body>
</html>
