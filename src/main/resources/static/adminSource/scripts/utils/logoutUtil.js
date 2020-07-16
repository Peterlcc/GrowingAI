function logoutWithUrl(logoutUrl,loginUrl) {
    swalTextWithFunction("确定要注销吗？","注销后需要重新登录",swalType.WARN,function (isConfirm) {
        if (isConfirm) {
            $.ajax({
                type: 'get',
                url: logoutUrl,
                cache: false,
                success: function (data) {
                    swalTextWithType("注销成功", "", swalType.SUCCEED);
                    setTimeout(function () {
                        window.location.href=loginUrl;
                    }, 1000);
                },
                error: function (data) {
                    swalTextWithType("注销失败", "请检查您的参数", swalType.ERROR);
                }
            });
        }else {
            console.log("func:logoutWithUrl canceled");
            swalTextWithType("操作取消", "", swalType.ERROR);
        }
    });
}