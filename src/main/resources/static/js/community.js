/**
 * 回复问题
 */
function postComment() {
    var parentId = $("#comment_id").val();
    var content = $("#comment_content").val();
    post(parentId,content,1);
}
function postSecondComment(obj) {
    var parentId = obj.getAttribute("data-id");
    var content = $("#scomment"+parentId).val();
   post(parentId,content,2);
}
function post(id,content,type) {
    if(!content){
        alert("评论不能为空!!!");
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        data: JSON.stringify({
            "parentId": id,
            "type": type,
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
//在Jquery里格式化Date日期时间数据
function timeStamp2String(time){
    var datetime = new Date();
    datetime.setTime(time);
    var year = datetime.getFullYear();
    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
    var hour = datetime.getHours()< 10 ? "0" + datetime.getHours() : datetime.getHours();
    var minute = datetime.getMinutes()< 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
    var second = datetime.getSeconds()< 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
    return year + "-" + month + "-" + date;
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
        var subCommentContainer = $("#subComment"+id);
        if(subCommentContainer.children().length==1){
            $.getJSON( "/comment/"+id, function( data ) {
                $.each( data.data.reverse(), function( index, val ) {
                    var li = $( "<li/>", {
                        "class": "media comment-split"
                    });
                    var leftDiv = $( "<div/>", {
                        "class": "media-left"
                    }).append($( "<img/>", {
                        "class": "media-object avatar img-rounded",
                        src: val.user.avatarUrl
                    }));
                    var bodyDiv = $( "<div/>", {
                        "class": "media-body",
                    });
                    var name = $( "<h4/>", {
                        "class": "media-right",
                        html:val.user.name
                    });
                    var content = $( "<h5/>", {
                        "class": "padding-left",
                        html:val.comment.content
                    });
                    var riqi = $( "<span/>", {
                        "class": "pull-right",
                        html:timeStamp2String(val.comment.gmtCreate)
                    });
                    bodyDiv.append(name);
                    bodyDiv.append(content)
                    li.append(leftDiv);
                    li.append(bodyDiv);
                    li.append(riqi);
                    subCommentContainer.prepend(li);
                });
            });
        }
        collapseDiv.addClass("show");
        collapseDiv.attr("data-target","a");

    }
}

function showTags() {
    $("#tags").show();

}
function selectTag(obj) {
    var tag = obj.getAttribute("data-tag");
    var pre = $("#tag").val();
    if(pre==""){
        $("#tag").val(tag);
    }else{
        if(pre.indexOf(tag)==-1){
            $("#tag").val(pre+","+tag);
        }

    }


}