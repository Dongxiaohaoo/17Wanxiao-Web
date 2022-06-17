<%--
  Created by IntelliJ IDEA.
  User: Dongxiaohao
  Date: 2022/6/1
  Time: 5:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>完美校园签到</title>
    <link rel="Shortcut Icon" href="image/search.png" type="image/x-icon"/>
    <link rel="stylesheet" href="css/index.css">
    <script src="//lib.sinaapp.com/js/jquery/3.1.0/jquery-3.1.0.min.js"></script>
    <link rel="stylesheet" href="css/bootstrap.css"/>
    <script src="js/axios.js"/>
    <script src="js/layer.js"></script>
    <link rel="stylesheet" href="layui/css/layui.css">
    <script src="layui/layui.js"></script>

</head>
<style>
    #banner {
        top: 0;
        width: 100%;
        position: absolute;
    }

    .pay {
        width: 18%;
        position: absolute;
    }
</style>
<body>
<img src="image/zfb.jpg" class="pay" id="pay" style="display: none">
<ul class="layui-nav layui-bg-black" id="banner">
    <li class="layui-nav-item"><a href="https://dongxiaohao.top/">我的博客</a></li>
    <li class="layui-nav-item"><a href="https://github.com/Dongxiaohaoo">GitHub</a></li>
    <li class="layui-nav-item">
        <a href="javascript:;">打赏</a>
        <dl class="layui-nav-child">
            <dd><a href="javascript:void (0)" onclick="showQRCode('image/wx.png')">微信</a></dd>
            <dd><a href="javascript:void (0)" onclick="showQRCode('image/zfb.jpg')">支付宝</a></dd>
        </dl>
    </li>
    <li class="layui-nav-item">
        <a href="javascript:;">更多业务</a>
        <dl class="layui-nav-child">
            <dd><a href="https://faka.dongxiaohao.top/">破解路由器</a></dd>
        </dl>
    </li>
</ul>
<div>
    <from>
        <table>
            <tr>
                <td>
                    <div class="input-group mb-3">
                        <span class="input-group-addon">手机号</span>
                        <input type="text" id="Phone" class="form-control">
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <div class="input-group mb-3">
                        <span class="input-group-addon">设备id</span>
                        <input type="text" id="devId" readonly class="form-control">
                    </div>
                </td>
            </tr>
            <tr id="sms_line" style="display: none">
                <td>
                    <div class="input-group mb-3">
                        <span class="input-group-addon">验证码</span>
                        <input type="text" id="smsCode" class="form-control">
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="button" id="change" value="随机设备ID" class="btn btn-sm btn-dark"/>
                    <input type="button" value="发送验证码" onclick="send(this)" class="btn btn-sm btn-success"/>
                    <input type="button" value="验证码登录" onclick="login()" id="sms_btn" style="display: none" class="btn btn-sm btn-info"/>
                </td>
            </tr>
        </table>
    </from>
</div>

<div id="statistical">
    网站由<b><a href="https://reajason.top/">ReaJason</a></b>提供技术支持
</div>
</body>
<script>
    layer.alert("本网站不作任何储存用户信息行为，只为打卡注册设备ID所用",{
        icon:6,
    })
    function showLogin() {
        document.getElementById("change").style.display = "none";
        document.getElementById("sms_line").style.display = "block";
        document.getElementById("sms_btn").style.display = "inline";
    }

    function login() {
        let phone = document.getElementById("Phone").value;
        let devId = document.getElementById("devId").value;
        let smsCode = document.getElementById("smsCode").value;
        axios.get("user?opr=slogin&phone=" + phone +"&sms="+smsCode +"&dev=" + devId).then(function (res) {
            let result = res.data;
            console.log(result)
            if (result['status'] == 1){
                layer.alert(result['msg'], {
                    icon: 1,
                });
            }else {
                layer.alert(result['errmsg'], {
                    icon: 2,
                });
            }
        }).catch(function (err) {
            layer.alert("系统出故障了！！", {
                icon: 2,
            });
        });
    }
    function showQRCode(url) {
        var img = "<img src='" + url + "' />";
        layer.open({
            type: 1,
            shade: false,
            title: false, //不显示标题
            shade: 0.6,
            // area: ['auto', 'auto'],
            // area: [img.width + 'px', img.height + 'px'],
            id: "pp",
            content: img, //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
            cancel: function () {
                //layer.msg('图片查看结束！', { time: 5000, icon: 6 });
            }
        });
    }

    function hiddenQRcode() {
        let elementById = document.getElementById("pay");
        elementById.style.display = "none"
    }

    function randomDevId() {
        let dev = document.getElementById("devId");
        dev.value = parseInt(Math.random() * Math.pow(10, 16));
    }

    randomDevId()
    document.getElementById("change").addEventListener("click", randomDevId)

    function checkPhone() {
        var phone = document.getElementById('Phone').value;
        if (!(/^1[3|4|5|6|7|8|9]\d{9}$/.test(phone))) {
            alert("手机号码有误，请重填");
            return false;
        }
        return true;
    }

    function send(sendBtn) {
        //检测手机号是否合法
        if (checkPhone()) {
            sendBtn.disabled = true;
            let phone = document.getElementById("Phone").value;
            let devId = document.getElementById("devId").value;
            axios.get("user?opr=send_msg&phone=" + phone + "&dev=" + devId).then(function (res) {
                let result = res.data;
                showLogin()
                console.log(result)
                layer.alert(result['msg'], {
                    time: 5 * 1000
                    , success: function (layero, index) {
                        var timeNum = this.time / 1000, setText = function (start) {
                            layer.title((start ? timeNum : --timeNum) + ' 秒后关闭', index);
                        };
                        setText(!0);
                        this.timer = setInterval(setText, 1000);
                        if (timeNum <= 0) clearInterval(this.timer);
                    }
                    , end: function () {
                        clearInterval(this.timer);
                    }
                });
            }).catch(function (err) {
                layer.alert("系统出故障啦！", {
                    icon: 1,
                });
            });
        }
    }
</script>
<script>
    layui.use('element', function () {
        var element = layui.element; //导航的hover效果、二级菜单等功能，需要依赖element模块
        //监听导航点击
        element.on('nav(demo)', function (elem) {
            console.log(elem)
            layer.msg(elem.text());
        });
    });
</script>
</html>
