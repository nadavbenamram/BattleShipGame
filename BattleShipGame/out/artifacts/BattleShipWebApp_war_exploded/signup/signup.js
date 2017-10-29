$("#loginButton").click(function(){
    $.get("/signup?username=" + $("#loginName").val(), function(data, status){
        if (data.indexOf("YES") >= 0)
        {
            window.location = "/games?username=" + $("#loginName").val();
        }
        else
        {
            //alert("Username " + $("#loginName").val() + " already signed up!")
            alert(data)
        }
    });
});