$(function(){

    var updateTime = function(){
        $.ajax({
            url: '/time',
            dataType: 'json',
            success: function(data){
                $("h1#timestamp").text(data.time);
            },
            error: function(data) {
                console.log(data);
            }
        });
    };

    console.log("setting interval.");
    window.setInterval(updateTime, 1000);
});

