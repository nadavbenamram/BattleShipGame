function ajaxGameRoomsList() {
    $.ajax({
        url: "gameslist",
        success: function (gamesFromServer) {
            refreshGameRoomsList(gamesFromServer);
        }
    });
}


function refreshGameRoomsList(gamesFromServer) {
    //clear all current users
    if ($('#gamesTbody').length > 0) {
        $('#gamesTbody').empty();
    }
    if (typeof gamesFromServer != 'undefined') {
        $.each(gamesFromServer || [], function(index, game)
        {
            $('<form method="post" action="../joingame">'
            +'<tr>'
            +'<td align="center">' + game.GetTitle() + '</td>'
            +'<td align="center">' + game.GetOwner().GetName() + '</td>'
            +'<td align="center">' + game.GetGameManager().GetBoardSize() + '</td>'
            +'<td align="center">' + game.GetGameManager().GetGameType() + '</td>'
            +'</tr>'
            +' </form>'
            ).appendTo($("#userslist"));
        });
    }
}

$(function() {
    $.ajaxSetup({cache: false});
    setInterval(ajaxGameRoomsList, 2000);
});