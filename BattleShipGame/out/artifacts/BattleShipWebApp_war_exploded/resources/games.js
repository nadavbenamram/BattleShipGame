function ajaxGameRoomsList() {
    $.ajax({
        url: "gameslist",
        success: function (gamesFromServer) {
            refreshGameRoomsList(gamesFromServer);
        }
    });
}

function refreshGameRoomsList(gamesFromServer) {
    if ($('#gamesTbody').length > 0) {
        $('#gamesTbody').empty();
    }
    if (typeof gamesFromServer != 'undefined') {
        for(var i=0;i<gamesFromServer.length;i++)
        {
            var formStart = "<form method=\"post\" action=\"../joingame\">";
            var trStart = "<tr>";
            var td1="<td align=\"center\">"+gamesFromServer[i]["Title"]+"</td>";
            var td2="<td align=\"center\">"+gamesFromServer[i]["Owner"]+"</td>";
            var td3="<td align=\"center\">"+gamesFromServer[i]["BoardSize"]+"</td>";
            var td4="<td align=\"center\">"+gamesFromServer[i]["GameType"]+"</td>";
            var td5="<td align=\"center\">";
            var trEnd = "</tr>";
            var formEnd = "</form>";
            var td5ValueHelper = gamesFromServer[i]["ActivePlayersNum"];
            var td5MiddleValue = "";
            if (td5ValueHelper == 2)
            {
                td5MiddleValue = "<img src=\"/resources/red.png\" alt=\"active game\" style=\"width:15px;height:15px;\">";
            }
            else if(td5ValueHelper == 1)
            {
                td5MiddleValue = "<img src=\"/resources/yellow.png\" alt=\"inactive game\" style=\"width:15px;height:15px;\">";
            }
            else if(td5MiddleValue == 0)
            {
                td5MiddleValue = "<img src=\"/resources/green.jpg\" alt=\"inactive game\" style=\"width:15px;height:15px;\">";
            }
            td5 += td5MiddleValue + "</td>";
            var td6;
            if(td5ValueHelper != 2)
            {
                td6 = "<td align=\"center\"><input type=\"button\" class=\"joinButton\" data-title=\""+gamesFromServer[i]["Title"]+"\" value=\"Join\"/></td>";
            }
            else
            {
                td6 = "<td align=\"center\"><input type=\"button\" class=\"watchButton\" data-title=\""+gamesFromServer[i]["Title"]+"\" value=\"Watch\"/></td>";
            }
            $("#gamesTbody").append(trStart+td1+td2+td3+td4+td5+td6+trEnd);
        }

        $(".joinButton").click(function (){
            window.location.replace("../joingame?gametitle=" + $(this).attr("data-title"));
        });
        $(".watchButton").click(function (){
            window.location.replace("../watchgame?gametitle=" + $(this).attr("data-title"));
        });
    }
}

$(function() {
    $.ajaxSetup({cache: false});
    ajaxGameRoomsList();
    setInterval(ajaxGameRoomsList, 2000);
});