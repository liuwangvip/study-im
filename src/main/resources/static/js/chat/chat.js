var vm = new Vue({
    el: '#app',
    data: function () {
        return {
            userNameForm: {
                username: ''
            },
            username: '',
            showUser: true,
            messageList: '',
            stompClient: null,
            timer: '',
            serverUrl: '',
            headers: {},
            connected: false,
            rules: {
                username: [
                    {required: true, message: '请输入用户名', trigger: 'blur'},
                ]
            }
        }
    },
    methods: {
        startChat: function (formName) {
            debugger;
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    this.showUser = false;
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
        getAvatarColor: function (messageSender) {
            var hash = 0;
            for (var i = 0; i < messageSender.length; i++) {
                hash = 31 * hash + messageSender.charCodeAt(i);
            }
            var index = Math.abs(hash % colors.length);
            return colors[index];
        },
        initWebSocket: function () {
            this.connect();
            let that = this;
            // 断开重连机制,尝试发送消息,捕获异常发生时重连
            this.timer = setInterval(() => {
                try {
                    that.stompClient.send("test");
                } catch (err) {
                    console.log("断线了: " + err);
                    that.connect();
                }
            }, 5000);
        }
        ,
        /**
         * 建立ws链接
         * @param event
         */
        connect: function (event) {
            // 建立连接对象
            let socket = new SockJS('http://127.0.0.1:8080/ws');
            // 获取STOMP子协议的客户端对象
            this.stompClient = Stomp.over(socket);
            // 定义客户端的认证信息,按需求配置
            this.headers = {
                Authorization: ''
            }
            // 向服务器发起websocket连接
            this.stompClient.connect(this.headers, this.onConnectSuccess, this.onConnectError);
        }
        ,
        /**
         * 链接成功
         */
        onConnectSuccess: function () {
            debugger;
            this.connected = true;
            /**
             * 订阅服务器发给topic/public的消息
             */
            this.stompClient.subscribe('/topic/public', this.onMessageReceived);
        }
        ,
        /**
         * 接收服务器消息
         * @param payload
         */
        onMessageReceived: function (payload) {
            var message = JSON.parse(payload.body);

            console.log("接收服务器发送的消息：" + JSON.stringify(message));
        }
        ,
        /**
         * 发送消息
         * @param path
         */
        sendMsg: function (path) {
            this.stompClient.send(path,
                this.headers,
                JSON.stringify({sender: username, type: 'JOIN'})
            )
        }
        ,
        /**
         * 连接失败
         */
        onConnectError: function (err) {
            console.log("连接服务器失败,err:" + err);
        }
        ,
        disconnect: function () {
            if (this.stompClient) {
                this.stompClient.disconnect();
                console.log("断开和服务其的ws连接")
            }
        }
        ,
        getServerUrl: function (callback) {
            let url = window.location.href;
            if (url.indexOf("/") > 0) {
                this.serverUrl = url.slice(0, url.indexOf("/"));
            } else {
                this.serverUrl = url;
            }
            callback();
        }
    }
    ,
    mounted: function () {
        this.getServerUrl(this.initWebSocket);
    }
    ,
    beforeDestroy: function () {
        // 页面离开时断开连接,清除定时器
        this.disconnect();
        clearInterval(this.timer);
    }
})