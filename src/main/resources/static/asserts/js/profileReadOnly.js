/**
 *
 */

function profileReadOnly(){
    $('.profile-input').attr("disabled","disabled");
}

function profileEnable(){
    if ($('.profile-input').attr("disabled")==undefined) {
        $('.profile-input').attr("disabled","disabled");
    }else {
        $('.profile-input').removeAttr("disabled");
    }
}