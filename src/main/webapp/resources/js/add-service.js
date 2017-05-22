/**
 *
 */
var dropdown = $('.ui.dropdown')
    .dropdown()
;

var exitConfirm = function () {
    $(window).on('beforeunload', function () {
        return "Данные будут утеряны. Вы уверенные что хотите покинуть страницу?";
    });

    $(document).on("submit", "form", function () {
        $(window).off('beforeunload');
    });
};

$(function () {
    exitConfirm();
});

var uploadServicesPhotos = function (_csrf) {
    var uploadPhotoForm = document.forms[0]
        , serviceInformationForm = document.forms[1];                //TODO: add validation
    var formData = new FormData(uploadPhotoForm);
    var xhr = new XMLHttpRequest();

    var photos = document.getElementById('Photo').files;

    if (!(photos.length > 5)) {
        for (var i = 0; i < photos.length; i++) {
            formData.set('file', photos[i]);
            xhr.open("POST", "/uploadFile/loadServicePhoto+"
                + document.getElementById('nameOfService').value + "?" + _csrf, true);
            xhr.send(formData);
        }

        serviceInformationForm.submit();
    }
};

var serviceValidate = function () {
    return false;
};

var imageUpload = function () {

};
