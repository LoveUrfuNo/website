

var gradientBackground = function () {
    var degrees = 0;
    setInterval(function () {
        degrees++;
        $('body').css('background-image', 'linear-gradient('+degrees+'deg, #F06FB4, white)');
    }, 60000/360)
};

gradientBackground();
