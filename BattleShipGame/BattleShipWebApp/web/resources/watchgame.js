globalJsonObj = NaN;

$(function() {
    $("#leaveGameButton").click(function ()
    {
        window.location.replace("games");
    });
    $.ajaxSetup({cache: false});
    ajaxWatchGame();
    setInterval(ajaxWatchGame, 2000);
});

function ajaxWatchGame() {
    refreshGameWatch();
}

function refreshGameWatch()
{
    (function (){
        var xmlHttp = new XMLHttpRequest();
        xmlHttp.open( "GET", "watchgame?"
            + "gameName=" + $("#parametersFromJsp").attr("gameName")
            , false );
        xmlHttp.send( null );
        var jsonData = xmlHttp.responseText;
        var jsonObj = JSON.parse(jsonData);
        globalJsonObj = jsonObj;
        fillTables(jsonObj);
        fillPlayerStatistics(jsonObj);
        fillGameStatistics(jsonObj);
        fillChat(jsonObj);
        if(jsonObj.IsGameFinished)
        {
            alert("The game finished");
            window.location.replace("games");
        }
    })();
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

function fillChat(jsonObj)
{
    if (typeof jsonObj != 'undefined') {
        var ulStart = "<ul>";
        var ulEnd = "</ul>";
        var li;

        $("#gameChat").empty();

        $("#gameChat").append(ulStart);
        for (var i = 0; i < jsonObj.GameJson.NumOfMessage; i++) {
            li = "<li>"
                + jsonObj.GameJson.UserNameChat[i]
                + ": "
                + jsonObj.GameJson.MessageChat[i]
                + "</li>";

            $("#gameChat").append(li);
        }

        $("#gameChat").append(ulEnd);
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
                        + imgSrc
                        + "</td>";
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

                    td = "<td align=\"center\">"
                        + imgSrc
                        + "</td>";
                }

                $("#TraceTable").append(td);
            }
            $("#TraceTable").append(trEnd);
        }
    }
}