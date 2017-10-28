function ajaxOnlineUsersList() {
    $.ajax({
        url: "userslist",
        success: function (usersFromServer) {
            refreshUsersList(usersFromServer);
        }
    });
}

function refreshUsersList(usersFromServer) {
    if ($('#usersTbody').length > 0) {
        $('#usersTbody').empty();
    }
    if (typeof usersFromServer != 'undefined') {
        for(var i=0;i<usersFromServer.length;i++)
        {
            var formStart = "<form method=\"post\" action=\"../joingame\">";
            var trStart = "<tr>";
            var td1="<td align=\"center\">"+usersFromServer[i]["Name"]+"</td>";
            var td2="<td align=\"center\">"+usersFromServer[i]["NumOfGames"]+"</td>"
            var trEnd = "</tr>";
            $("#usersTbody").append(trStart+td1+td2+trEnd);
        }
    }
}

$(function() {
    $.ajaxSetup({cache: false});
    ajaxOnlineUsersList();
    setInterval(ajaxOnlineUsersList, 2000);
});