$(function(){

    // add event handler for user add functionality
    $("div#apikey button").click(function() {
        apikey = $(this).parent().find("input[placeholder='new API Key']");

        if (apikey.val().length > 0) {
            $.ajax({
                url: '/key',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({"key":apikey.val()}),
                success: function() {
                    apikey.val('');
                    apikey.css('background-color', '#88DD88');
                    apikey.prop('disabled', true);
                },
                error: function() {
                    apikey.css('background-color', '#DD8888');
                }
            });
        }
        return false;
    });

});