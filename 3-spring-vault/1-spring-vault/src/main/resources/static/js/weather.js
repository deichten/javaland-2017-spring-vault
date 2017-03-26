$(function(){

    // add event handler for user add functionality
    $("div#weathercheck button").click(function() {
        country = $(this).parent().find("input[placeholder='country']");
        city    = $(this).parent().find("input[placeholder='city']");

        if (country.val().length > 0 && city.val().length > 0) {
            $.ajax({
                url: '/weather/' + country.val() + '/' + city.val(),
                dataType: 'json',
                success: function(data) {
                    $("div#result").append('<div class="panel panel-default">'
                        + '<div class="panel-heading">' + city.val() + ', ' + country.val() +'</div>'
                        + '<div class="panel-body">'
                        + '<img src="http://openweathermap.org/img/w/' + data.weatherIcon + '.png" />'
                        + data.temperature.toFixed(1) + '°C'
                        + '</div></div>');

                    country.val('');
                    city.val('');
                },
                error: function(data) {
                    country.css('background-color', '#FF8888');
                    city.css('background-color', '#FF8888');
                    console.log(data);
                }
            });
        }
        return false;
    });

});
