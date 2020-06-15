/**
 * 回复问题
 */
function post() {
    var parentId = $("#comment_id").val();
    var content = $("#comment_content").val();
    if(!content){
        alert("评论不能为空!!!");
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        data: JSON.stringify({
            "parentId": parentId,
            "type": 1,
            "content": content
        }),
        contentType:"application/json",
        success: function (result) {
            if (result.code==200){
               window.location.reload();
            }else if(result.code==2003){
                var confirm = window.confirm(result.message);
                if(confirm){
                    window.open("https://github.com/login/oauth/authorize?client_id=d217cbd0705a004e0bdb&redirect_uri=http://localhost:9090/callback&scope=user&state=1");
                    window.localStorage.setItem("closable",true);
                }
            } else{
                alert(result.message);
            }
            console.log(result);
        },
        dataType: "json"
    });
}

/**
 * 展开和关闭评论面板
 */
function collapseComment(obj) {
    var id = obj.getAttribute("data-id");
    var collapseDiv =  $("#comment"+id);
    var attribute = collapseDiv.attr("data-target");

    if(attribute){
        collapseDiv.removeClass("show");
        collapseDiv.removeAttr("data-target");
    }else{
        collapseDiv.addClass("show");
        collapseDiv.attr("data-target","a");
    }

}