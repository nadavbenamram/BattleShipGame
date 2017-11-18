<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<HTML>

<HEAD>
    <link rel="stylesheet" type="text/css" href="../resources/style.css">
    <TITLE>Login Page</TITLE>
</HEAD>

<BODY >
<h1>Welcome To BattleShip Website Game!</h1>
<table>
    <tr>
        <td><b>User name:</b></td>
        <td><input type="text" name="username" id="loginName"></td>
    </tr>
    <tr>
        <td><input type="button" value="Login" id="loginButton"></td>
    </tr>
</table>

<script type="text/javascript" src="../resources/jquery-3.2.1.min.js"></script>

<script>
    $("#loginButton").click(function(){
        $.get("../signup?username=" + $("#loginName").val(), function(data, status){
            if (data.indexOf("YES") >= 0)
            {
                window.location = "../games?username=" + $("#loginName").val();
            }
            else
            {
                alert(data)
            }
        });
    });
</script>
</BODY>

</HTML>