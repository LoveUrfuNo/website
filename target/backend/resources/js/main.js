;(function () {
    'use strict';
    // iPad and iPod detection
    var isiPad = function () {
        return (navigator.platform.indexOf("iPad") != -1);
    };

    var isiPhone = function () {
        return (
            (navigator.platform.indexOf("iPhone") != -1) ||
            (navigator.platform.indexOf("iPod") != -1)
        );
    };

    var fullHeight = function () {

        $('.js-fullheight').css('height', $(window).height());
        $(window).resize(function () {
            $('.js-fullheight').css('height', $(window).height());
        });

    };
    // Owl Carousel
    var owlCrouselFeatureSlide = function () {
        var owl = $('.owl-carousel1');
        owl.owlCarousel({
            items: 3,
            loop: true,
            margin: 40,
            stagePadding: 10,
            responsiveClass: true,
            nav: true,
            dots: true,
            smartSpeed: 500,
            navText: [
                "<i class='icon-chevron-left owl-direction'></i>",
                "<i class='icon-chevron-right owl-direction'></i>"
            ]
        });

        $('.owl-carousel2').owlCarousel({
            loop: true,
            margin: 10,
            nav: true,
            dots: true,
            responsive: {
                0: {
                    items: 1
                },
                600: {
                    items: 3
                },
                1000: {
                    items: 3
                }
            },
            navText: [
                "<i class='icon-chevron-left owl-direction'></i>",
                "<i class='icon-chevron-right owl-direction'></i>"
            ]
        })
    };
    // Animations
    var contentWayPoint = function () {
        var i = 0;

        $('.animate-box').waypoint(function (direction) {

            if (direction === 'down' && !$(this.element).hasClass('animated')) {

                i++;

                $(this.element).addClass('item-animate');
                setTimeout(function () {

                    $('body .animate-box.item-animate').each(function (k) {
                        var el = $(this);
                        setTimeout(function () {
                            var effect = el.data('animate-effect');
                            if (effect === 'fadeIn') {
                                el.addClass('fadeIn animated');
                            } else {
                                el.addClass('fadeInUp animated');
                            }

                            el.removeClass('item-animate');
                        }, k * 200, 'easeInOutExpo');
                    });

                }, 100);

            }

        }, {offset: '85%'});
    };

    var parallax = function () {
        $(window).stellar({
            horizontalScrolling: false,
            hideDistantElements: false,
            responsive: true

        });
    };

    var offcanvasMenu = function () {

        $('#full').prepend('<div id="gtco-offcanvas" />');
        $('#full').prepend('<a href="#" class="js-gtco-nav-toggle gtco-nav-toggle gtco-nav-white"><i></i></a>');
        var clone1 = $('.menu-1 > ul').clone();
        $('#gtco-offcanvas').append(clone1);
        var clone2 = $('.menu-2 > ul').clone();
        $('#gtco-offcanvas').append(clone2);

        $('#gtco-offcanvas .has-dropdown').addClass('offcanvas-has-dropdown');
        $('#gtco-offcanvas')
            .find('li')
            .removeClass('has-dropdown');

        // Hover dropdown menu on mobile
        $('.offcanvas-has-dropdown').mouseenter(function () {
            var $this = $(this);

            $this
                .addClass('active')
                .find('ul')
                .slideDown(500, 'easeOutExpo');
        }).mouseleave(function () {

            var $this = $(this);
            $this
                .removeClass('active')
                .find('ul')
                .slideUp(500, 'easeOutExpo');
        });


        $(window).resize(function () {

            if ($('body').hasClass('offcanvas')) {

                $('body').removeClass('offcanvas');
                $('.js-gtco-nav-toggle').removeClass('active');

            }
        });
    };

    var burgerMenu = function () {

        $('body').on('click', '.js-gtco-nav-toggle', function (event) {
            var $this = $(this);


            if ($('body').hasClass('overflow offcanvas')) {
                $('body').removeClass('overflow offcanvas');
                $('body').css('overflow-y', 'visible');
            } else {
                $('body').addClass('overflow offcanvas');
                $('body').css('overflow', 'hidden');
            }
            $this.toggleClass('active');
            event.preventDefault();

        });
    };

    var formTab = function () {

        $('.tab-menu a').on('click', function (event) {
            var $this = $(this),
                data = $this.data('tab');
            $('.tab-menu li').removeClass('active');
            $this.closest('li').addClass('active');
            $('.tab .tab-content-inner').removeClass('active');
            $this.closest('.tab').find('.tab-content-inner[data-content="' + data + '"]').addClass('active');
            event.preventDefault();
        });
    };

    var backgroundSlider = function () {
        var counter = 1;
        var image = $(".backgroundSlider");
        var images = ["http://www.2fons.ru/pic/201406/1920x1200/2fons.ru-21289.jpg", "http://www.nastol.com.ua/pic/201203/1920x1080/nastol.com.ua-17840.jpg", "http://img1.joyreactor.cc/pics/post/full/красивые-картинки-Нью-Йорк-америка-ночь-1076757.jpeg"];

        setInterval(function () {
            image.fadeOut(1000, function () {
                image.css("background-image", "url(" + images[counter++] + ")");
                image.fadeIn(1000);
            });
            if (counter === images.length) {
                counter = 0;
            }
        }, 10000);
    };

    var profileLoader = function () {
        var newForm = $('.form-wrap2').html();
        var locationAddress = location.pathname;
        var profileAddressNotActivated = "/profile";
        var profileAddressActivated = "/profile/registration";
        if (locationAddress === profileAddressNotActivated || locationAddress === profileAddressActivated) {
            $('.form-wrap').empty().append(newForm);
        }
    };

    var activateString = function () {
        var activateButton = $(".profile button");

        setInterval(function () {
            activateButton.removeClass("btn-info").addClass("btn-warning");
            setTimeout(function () {
                activateButton.removeClass("btn-warning").addClass("btn-info");
            }, 2000)
        }, 4000);
    };

    var autoCompelte = function () {
        var xhr = new XMLHttpRequest();

        var input = document.getElementById('tags').valueOf();
        input.oninput = function () {
            xhr.open('GET', '/auto_complete/' + input.value, true);
            xhr.send();
            xhr.onreadystatechange = function () {
                var data = this.responseText.replace(/["|[|\]]/g, '').split(',');
                //alert(data[0]);
                var availableTags = [
                    data[0],
                    data[1],
                    data[2],
                    data[3],
                    data[4]
                ];

                $("#tags").autocomplete({
                    source: availableTags
                });
            };
        };
    };

    // Document on load.
    $(function () {
        fullHeight();
        formTab();
        owlCrouselFeatureSlide();
        contentWayPoint();
        offcanvasMenu();
        burgerMenu();
        backgroundSlider();
        parallax();
        profileLoader();
        activateString();
        autoCompelte();
    });
}());