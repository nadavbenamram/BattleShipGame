globalJsonObj = NaN;

$(function() {
    $("#leaveGameButton").click(function ()
    {
        if(globalJsonObj.GameJson.CurrentPlayer.Name == $("#parametersFromJsp").attr("userName"))
        {
            window.location.replace("gamefinished?gametitle="
                + $("#parametersFromJsp").attr("gameName") + "&"
                + "username=" + $("#parametersFromJsp").attr("userName"));
        }
        else
        {
            alert("You can leave at your own turn only!");
        }
    });
    $.ajaxSetup({cache: false});
    ajaxGameShow();
    setInterval(ajaxGameShow, 2000);
});

function ajaxGameShow() {
    $.ajax({
        url: "gameshow",
        success: function (gameFromServer) {
            refreshGameShow(gameFromServer);
        }
    });
}

function refreshGameShow(gamesFromServer) {
    (function (){
        var xmlHttp = new XMLHttpRequest();
        xmlHttp.open( "GET", "gameshow?"
            + "valueOfError=" + $("#parametersFromJsp").attr("valueOfError") + "&"
            + "userName=" + $("#parametersFromJsp").attr("userName") + "&"
            + "gameName=" + $("#parametersFromJsp").attr("gameName")
            , false );
        xmlHttp.send( null );
        var jsonData = xmlHttp.responseText;
        var jsonObj = JSON.parse(jsonData);
        globalJsonObj = jsonObj;
        fillTables(jsonObj);
        fillPlayerStatistics(jsonObj);
        fillGameStatistics(jsonObj);
        fillStatusLabel(jsonObj);
        if(jsonObj.IsGameFinished)
        {
            window.location.replace("gamefinished?gametitle=" + jsonObj.GameJson.Title + "&"
                + "username=/"/"");
        }
    })();
}

function fillStatusLabel(jsonObj)
{
    if (typeof jsonObj != 'undefined')
    {
        $("#statusLabel").empty();
        if(jsonObj.IsWaitingToSecondPlayer)
        {
            $("#statusLabel").text("Waiting for second player...");
        }
        else if(jsonObj.IsGameStartedNow)
        {
            $("#statusLabel").text("Game Started!");
        }
    }
}

function fillGameStatistics(jsonObj)
{
    if (typeof jsonObj != 'undefined')
    {
        $("#gameType").text(jsonObj.GameJson.GameType);
        $("#currentPlayer").text(jsonObj.GameJson.CurrentPlayer.Name);
    }
}

function fillPlayerStatistics(jsonObj)
{
    if (typeof jsonObj != 'undefined')
    {
        $("#playerName").text(jsonObj.PlayerStatistics.Name);
        $("#playerScore").text(jsonObj.PlayerStatistics.Score.toString());
    }
}

function fillTables(jsonObj)
{
    if (typeof jsonObj != 'undefined')
    {
        var trStart = "<tr>";
        var trEnd = "</tr>";
        var td;

        if ($('#GameTable').length > 0) {
            $('#GameTable').empty();
        }

        $("#GameTable").append(trStart);
        for(var i = 0; i <= jsonObj.GameJson.BoardSize; i++)
        {
            if(i == 0)
            {
                td = "<td align=\"center\">" + "-" + "</td>";
            }
            else
            {
                td = "<td align=\"center\">" + i.toString() + "</td>";
            }

            $("#GameTable").append(td);
        }

        $("#GameTable").append(trEnd);
        for(var i = 1; i <= jsonObj.GameJson.BoardSize; i++)
        {
            $("#GameTable").append(trStart);
            for(var j = 0; j <= jsonObj.GameJson.BoardSize; j++)
            {
                if(j == 0)
                {
                    td = "<td align=\"center\" horizontal-align: middle>" + String.fromCharCode(64 + i) + "</td>";
                }
                else
                {
                    var imgSrc;
                    switch(jsonObj.GameBoard[i][j]) {
                        case 'EMPTY':
                            imgSrc = "<img src=\"resources/empty.png\" alt=\"empty cell\" style=\"width:13px;height:13px;\" "
                            break;
                        case 'BATTLE_SHIP':
                            imgSrc = "<img src=\"resources/battleShip.png\" alt=\"battle ship cell\" style=\"width:13px;height:13px;\" "
                            break;
                        case 'MINE':
                            imgSrc = "<img src=\"resources/mine.png\" alt=\"mine cell\" style=\"width:13px;height:13px;\" "
                            break;
                        case 'BATTLE_SHIP_HIT':
                            imgSrc = "<img src=\"resources/battleShipHit.png\" alt=\"battle ship hit cell\" style=\"width:13px;height:13px;\" "
                            break;
                        case 'HIT':
                            imgSrc = "<img src=\"resources/hit.png\" alt=\"hit cell\" style=\"width:13px;height:13px;\" "
                            break;
                    }

                    td = "<td align=\"center\">"
                        + "<div class=\"divCell\" ondrop=\"drop(event)\" ondragover=\"allowDrop(event)\">"
                        + imgSrc
                        + "x-value=\""
                        + j.toString()
                        + "\" y-value=\""
                        + i.toString()
                        + "\">"
                        + "</div></td>";
                }

                $("#GameTable").append(td);
            }
            $("#GameTable").append(trEnd);
        }

        if ($('#TraceTable').length > 0) {
            $('#TraceTable').empty();
        }

        $("#TraceTable").append(trStart);
        for(var i = 0; i <= jsonObj.GameJson.BoardSize; i++)
        {
            if(i == 0)
            {
                td = "<td align=\"center\">" + "-" + "</td>";
            }
            else
            {
                td = "<td align=\"center\">" + i.toString() + "</td>";
            }

            $("#TraceTable").append(td);
        }

        $("#TraceTable").append(trEnd);
        for(var i = 1; i <= jsonObj.GameJson.BoardSize; i++)
        {
            $("#TraceTable").append(trStart);
            for(var j = 0; j <= jsonObj.GameJson.BoardSize; j++)
            {
                if(j == 0)
                {
                    td = "<td align=\"center\" horizontal-align: middle;>" + String.fromCharCode(64 + i) + "</td>";
                }
                else
                {
                    var imgSrc;
                    switch(jsonObj.TraceBoard[i][j]) {
                        case 'EMPTY':
                            imgSrc = "<img src=\"resources/empty.png\" alt=\"empty cell\" style=\"width:13px;height:13px;\">"
                            break;
                        case 'BATTLE_SHIP':
                            imgSrc = "<img src=\"resources/battleShip.png\" alt=\"battle ship cell\" style=\"width:13px;height:13px;\">"
                            break;
                        case 'MINE':
                            imgSrc = "<img src=\"resources/mine.png\" alt=\"mine cell\" style=\"width:13px;height:13px;\">"
                            break;
                        case 'BATTLE_SHIP_HIT':
                            imgSrc = "<img src=\"resources/battleShipHit.png\" alt=\"battle ship hit cell\" style=\"width:13px;height:13px;\">"
                            break;
                        case 'HIT':
                            imgSrc = "<img src=\"resources/hit.png\" alt=\"hit cell\" style=\"width:13px;height:13px;\">"
                            break;
                    }

                    td = "<td align=\"center\" class=\"touchableCell\" x-value=\""
                        + j.toString()
                        + "\" y-value=\""
                        + i.toString()
                        + "\">"
                        + imgSrc
                        + "</td>";
                }

                $("#TraceTable").append(td);
            }
            $("#TraceTable").append(trEnd);
        }

        $(".touchableCell").click(function ()
        {
            self = $(this);
            ajaxGameShow();
            if(!jsonObj.IsWaitingToSecondPlayer)
            {
                if(jsonObj.PlayerStatistics.Name == jsonObj.GameJson.CurrentPlayer.Name)
                {
                    var xmlHttp = new XMLHttpRequest();
                    xmlHttp.open( "GET", "attack?"
                        + "pointx=" + self.attr("x-value") + "&"
                        + "pointy=" + self.attr("y-value") + "&"
                        + "gametitle=" + jsonObj.GameJson.Title
                        , false );
                    xmlHttp.send( null );
                    var responseData = xmlHttp.responseText;
                    if(responseData != "")
                    {
                        alert(responseData);
                    }
                }
                else
                {
                    alert("You should wait for your turn!");
                }
            }
            else
            {
                alert("You should wait for other player!");
            }
            ajaxGameShow();
        });
    }
}

function allowDrop(ev) {
    ev.preventDefault();
}

function drag(ev) {
    ev.dataTransfer.setData("text", ev.target.id);
}

function drop(ev) {
    ev.preventDefault();
    var data = ev.dataTransfer.getData("text");
    ajaxGameShow();
    if(!globalJsonObj.IsWaitingToSecondPlayer)
    {
        if(globalJsonObj.PlayerStatistics.Name == globalJsonObj.GameJson.CurrentPlayer.Name)
        {
            var xmlHttp = new XMLHttpRequest();
            xmlHttp.open( "GET", "putmine?"
                + "pointx=" + ev.target.getAttribute("x-value") + "&"
                + "pointy=" + ev.target.getAttribute("y-value") + "&"
                + "gametitle=" + globalJsonObj.GameJson.Title
                , false );
            xmlHttp.send( null );
            var responseData = xmlHttp.responseText;
            if(responseData != "")
            {
                alert(responseData);
            }
        }
        else
        {
            alert("You should wait for your turn!");
        }
    }
    else
    {
        alert("You should wait for other player!");
    }
    ajaxGameShow();
}