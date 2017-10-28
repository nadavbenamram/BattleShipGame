<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Game Finished</title>
</head>
<body>
<%
    String gameTitle = (String)request.getAttribute("gameTitle");
    String leftUserName = (String)request.getAttribute("leftusername");
%>
<script type="text/javascript">
    alert("<%= gameTitle %>");
</script>
<%
    if(leftUserName != null)
    {%>
<script type="text/javascript">
    alert("<%= leftUserName + " left the game" %>");
</script>
<%}%>

Game finished + statistics

button return to lobby.
</body>
</html>
