<html>
<body>
<h2>Hello World!goodsmgrweb</h2>
<%@page import="com.sishuok.architecture1.goodsmgr.Goods1" %>
<%@ page import="com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer" %>

<%
    System.out.println("now in goods index.jsp");
    new Goods1().g1();
%>

</body>
</html>