<%--
  Created by IntelliJ IDEA.
  User: 11
  Date: 2017/12/1
  Time: 11:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String ws = "ws://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <title>Title</title>
    <script src="<%=basePath%>statics/js/jquery-1.11.3.min.js"></script>
</head>
<body>
    <script>
        websocket = new WebSocket("<%=ws%>websocket");
        $(function(){
            $("#btn").click(function() {
                var text = $("#message").val();
                websocket.send(text);
            });
        })
    </script>
    <input type="text" id="message"><br/>
    <input type="button" id="btn" value="发送">
</body>
</html>
