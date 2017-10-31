<%@ page import="Utils.ContextManager" %>
<%@ page import="Utils.Game" %>
<%@ page import="java.util.List" %>
<%@ page import="Utils.Constants" %>
<%@ page import="Utils.SessionManager" %>
<%@ page import="static Utils.Constants.USER_NAME_PARAM_NAME" %>
<%@ page import="static Utils.Constants.GAME_TITLE_PARAM_NAME" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
    <link rel="stylesheet" type="text/css" href="../resources/style.css">
    <script type="text/javascript" src="../resources/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="../resources/games.js"></script>
    <script type="text/javascript" src="../resources/users.js"></script>
    <style>
        a{
            font-size: 20px;
            text-shadow: 4px 3px 0px #fff, 9px 8px 0px rgba(0,0,0,0.15);
        }
    </style>
    <TITLE>Games Page</TITLE>
</head>

<body>
<%
    String value = (String)request.getAttribute(Constants.GAME_LOAD_FAILED_ATT_NAME);
    String username = (String)request.getAttribute("username");
    if(value != null)
    {%>
<script type="text/javascript">
    alert("<%= value %>");
</script>
<%}%>
<style>
    #listsDiv{
        float: left;
        top: 0;
        left: 0;
        margin-bottom:10px;
    }
    #gamesDiv {
        float: left;
        margin-right: 5px;
    }
    #usersDiv {
        float: right;
    }
    table {
        border-collapse: collapse;
    }
    table td, table th {
        border: 1px solid black;
    }
    #uploadGame{
        clear: both;
    }
</style>

<div type="hidden" id="parameterJsp" user-name="<%= username %>"></div>

<div id="listsDiv">
    <div id="gamesDiv">
        <a>Games List:</a>
        <table class="tablesClass">
            <thead>
            <tr>
                <td align="center">Game Name</td>
                <td align="center">Game's owner</td>
                <td align="center">Board size</td>
                <td align="center">Game type</td>
                <td align="center">Game Status</td>
            </tr>
            </thead>
            <tbody id="gamesTbody">
            </tbody>

        </table>
    </div>

    <div id="usersDiv">
        <a>Users List:</a>
        <table class="tablesClass">
            <thead>
            <tr>
                <td align="center">User Name</td>
                <td align="center">Number Of Games</td>
            </tr>
            </thead>
            <tbody id="usersTbody">
            </tbody>
        </table>
    </div>
</div>

<div id="uploadGame">
    <hr />
    <form method="post" action="../uploadgame" enctype="multipart/form-data">
        <label for="gameUpload">Upload game</label><br />
        <input type="text" name="gameTitle"/><br/><br/>
        <input type="file" accept=".xml" name="gameData" id="gameUpload"/><br/><br/>
        <input type="submit" value="Upload Game" />
    </form>
    <hr/>
</div>


<input type="button" value="Logout" id="logout"></input>
</body>

</html>