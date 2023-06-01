$(function () {
    //这里需要注意的是，prompt有两个参数，前面是提示的话，后面是当对话框出来后，在对话框里的默认值
    var username = "";
    while (true) {
        //弹出一个输入框，输入一段文字，可以提交
        username = prompt("请输入您的名字", ""); //将输入的内容赋给变量 name ，
        if (username.trim() === "")//如果返回的有内容
        {
            alert("名称不能输入空")
        } else {
            $("#username").text(username);
            break;
        }
    }

    var ws = new WebSocket("ws://127.0.0.1:8182/chat");
    ws.onopen = function () {
        console.log("连接成功.")
        sendActiveMsg(1);
    }
    ws.onmessage = function (evt) {
        showMessage(evt.data);
    }
    ws.onclose = function (){
        console.log("连接关闭")
    }

    ws.onerror = function (){
        console.log("连接异常")
    }

    function showMessage(message) {
        var obj = JSON.parse(message);
        $("#msg_list").append(`<li class="active"}>
                                  <div class="main">
                                    <img class="avatar" width="30" height="30" src="/img/user.png">
                                    <div>
                                        <div class="user_name">${obj.send}</div>
                                        <div class="text">${obj.info}</div>
                                    </div>                       
                                   </div>
                              </li>`);
        // 置底
        setBottom();
    }

    $('#my_test').bind({
        focus: function (event) {
            event.stopPropagation()
            $('#my_test').val('');
            $('.arrow_box').hide()
        },
        keydown: function (event) {
            event.stopPropagation()
            if (event.keyCode === 13) {
                if ($('#my_test').val().trim() === '') {
                    this.blur()
                    $('.arrow_box').show()
                    setTimeout(() => {
                        this.focus()
                    }, 1000)
                } else {
                    $('.arrow_box').hide()
                    //发送消息
                    sendMsg(2);
                    this.blur()
                    setTimeout(() => {
                        this.focus()
                    })
                }
            }
        }
    });
    $('#send').on('click', function (event) {
        event.stopPropagation()
        if ($('#my_test').val().trim() === '') {
            $('.arrow_box').show()
        } else {
            sendMsg(2);
        }
    })

    function sendActiveMsg(type) {
        //发送消息
        // message = username + ":" + message;
        const send={
            send:$("#username").text(),
            receive:"",
            info:"大家好，我来啦！",
            type:type,
            id:Math.round(Math.random() * 10000),
        }
        ws.send(JSON.stringify(send));
    }

    function sendMsg(type) {

        var message = $("#my_test").val();
        $("#msg_list").append(`<li class="active"}>
                                  <div class="main self">
                                      <div class="text">` + message + `</div>
                                  </div>
                              </li>`);
        $("#my_test").val('');

        //发送消息
        // message = username + ":" + message;
        const send={
            send:$("#username").text(),
            receive:"",
            info:message,
            type:type,
            id:Math.round(Math.random() * 10000),
        }
        ws.send(JSON.stringify(send));
        // 置底
        setBottom();
    }

    // 置底
    function setBottom() {
        // 发送消息后滚动到底部
        const container = $('.m-message')
        const scroll = $('#msg_list')
        container.animate({
            scrollTop: scroll[0].scrollHeight - container[0].clientHeight + container.scrollTop() + 100
        });
    }
});