var swalType={
    SUCCEED:"success",
    ERROR:"error",
    WARN:"warning"
};
var swalColor={
    success:"#304ffe",
    warning:"#DD6B55",
    error:"#f60e0e"
};

function swalBasic(title) {
    swal({
        title: title,
        confirmButtonColor: swalColor[swalType.SUCCEED]
    });
}
function swalText(title,text) {
    swal({
        title: title,
        text: text,
        confirmButtonColor: swalColor[swalType.SUCCEED]
    });
}
function swalTextWithType(title,text,type) {
    swal({
        title: title,
        text: text,
        type: type,
        confirmButtonColor: swalColor[type]
    });
}

function swalTextWithFunction(title,text,type,func) {
    swal({
        title: title,
        text: text,
        type: type,
        confirmButtonColor: swalColor[type],
        showCancelButton: true,
        confirmButtonText: "是的",
        cancelButtonText: "取消",
        closeOnConfirm: false,
        closeOnCancel: false
    },func);
}