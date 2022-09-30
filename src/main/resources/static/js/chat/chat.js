var vm = new Vue({
    el: '#app',
    data: function () {
        return {
            loading: false,
            /**
             * 聊天室列表
             */
            roomList: [{name: "群聊"}],
            /**
             * 用户列表
             */
            userList: [],
            /**
             * 搜索
             */
            searchText: "",
            userInfo: {
                username: ''
            },
            showUser: true,
            /**
             * 消息内容
             */
            messageContent: "",
            colors: [
                '#2196F3', '#32c787', '#00BCD4', '#ff5652',
                '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
            ],
            //消息列表
            messageList: [
                {
                    sender: "刘望",
                    content: "",
                    type: "JOIN",
                    style: {"background-color": '#2196F3'},
                    date: "2022-09-30 10:00:01"
                },
                {
                    sender: "刘望",
                    content: "兄弟们我们不能天天带薪工作呀 我们是一群为梦想而奋斗的人，怎么能被金钱所困扰，我们应该追求梦想，而不应该追求金钱,兄弟们我们不能天天带薪工作呀 我们是一群为梦想而奋斗的人，怎么能被金钱所困扰，我们应该追求梦想，而不应该追求金钱,兄弟们我们不能天天带薪工作呀 我们是一群为梦想而奋斗的人，怎么能被金钱所困扰，我们应该追求梦想，而不应该追求金钱,兄弟们我们不能天天带薪工作呀 我们是一群为梦想而奋斗的人，怎么能被金钱所困扰，我们应该追求梦想，而不应该追求金钱",
                    type: "CHAT",
                    style: {"background-color": '#2196F3'},
                    date: "2022-09-30 10:05:01"
                },
                {
                    sender: "张三",
                    content: "兄弟们我们不能天天带薪工作呀 我们是一群为梦想而奋斗的人，怎么能被金钱所困扰，我们应该追求梦想，而不应该追求金钱,兄弟们我们不能天天带薪工作呀 我们是一群为梦想而奋斗的人，怎么能被金钱所困扰，我们应该追求梦想，而不应该追求金钱,兄弟们我们不能天天带薪工作呀 我们是一群为梦想而奋斗的人，怎么能被金钱所困扰，我们应该追求梦想，而不应该追求金钱,兄弟们我们不能天天带薪工作呀 我们是一群为梦想而奋斗的人，怎么能被金钱所困扰，我们应该追求梦想，而不应该追求金钱",
                    type: "CHAT",
                    style: {"background-color": '#FF9800'},
                    date: "2022-09-30 10:05:01"
                },
                {
                    sender: "李四",
                    content: "兄弟们我们不能天天带薪工作呀 我们是一群为梦想而奋斗的人，怎么能被金钱所困扰，我们应该追求梦想，而不应该追求金钱,兄弟们我们不能天天带薪工作呀 我们是一群为梦想而奋斗的人，怎么能被金钱所困扰，我们应该追求梦想，而不应该追求金钱,兄弟们我们不能天天带薪工作呀 我们是一群为梦想而奋斗的人，怎么能被金钱所困扰，我们应该追求梦想，而不应该追求金钱,兄弟们我们不能天天带薪工作呀 我们是一群为梦想而奋斗的人，怎么能被金钱所困扰，我们应该追求梦想，而不应该追求金钱",
                    type: "CHAT",
                    style: {"background-color": '#2196F3'},
                    date: "2022-09-30 10:05:01"
                },
                {
                    sender: "刘望",
                    content: "兄弟们我们不能天天带薪工作呀 我们是一群为梦想而奋斗的人，怎么能被金钱所困扰，我们应该追求梦想，而不应该追求金钱,兄弟们我们不能天天带薪工作呀 我们是一群为梦想而奋斗的人，怎么能被金钱所困扰，我们应该追求梦想，而不应该追求金钱,兄弟们我们不能天天带薪工作呀 我们是一群为梦想而奋斗的人，怎么能被金钱所困扰，我们应该追求梦想，而不应该追求金钱,兄弟们我们不能天天带薪工作呀 我们是一群为梦想而奋斗的人，怎么能被金钱所困扰，我们应该追求梦想，而不应该追求金钱",
                    type: "CHAT",
                    style: {"background-color": '#2196F3'},
                    date: "2022-09-30 10:05:01"
                },
                {
                    sender: "刘望",
                    content: "兄弟们我们不能天天带薪工作呀 我们是一群为梦想而奋斗的人，怎么能被金钱所困扰，我们应该追求梦想，而不应该追求金钱,兄弟们我们不能天天带薪工作呀 我们是一群为梦想而奋斗的人，怎么能被金钱所困扰，我们应该追求梦想，而不应该追求金钱,兄弟们我们不能天天带薪工作呀 我们是一群为梦想而奋斗的人，怎么能被金钱所困扰，我们应该追求梦想，而不应该追求金钱,兄弟们我们不能天天带薪工作呀 我们是一群为梦想而奋斗的人，怎么能被金钱所困扰，我们应该追求梦想，而不应该追求金钱",
                    type: "CHAT",
                    style: {"background-color": '#2196F3'},
                    date: "2022-09-30 10:05:01"
                },
                {
                    sender: "刘望",
                    content: "",
                    type: "LEAVE",
                    style: {"background-color": '#2196F3'},
                    date: "2022-09-30 10:30:01"
                },

            ],
            stompClient: null,
            timer: '',
            serverUrl: '',
            headers: {},
            connecting: true,
            rules: {
                username: [
                    {required: true, message: '请输入用户名', trigger: 'blur'},
                ]
            }
        }
    },
    computed: {
        noMore() {
            return this.roomList.length >= 20
        },
        disabled() {
            return this.loading || this.noMore
        }
    },
    methods: {
        /**
         * 登录
         */
        login: function () {
            window.location.href = this.serverUrl + "/login2";
        },
        /**
         * 注销登录
         */
        logout: function () {
            this.$message('开发中。。。');
        },
        search: function () {
            if (this.searchText == "") {
                this.$message('消息内容不能为空');
                return;
            }
            this.$message('开发中。。。');
        },
        add: function () {
            this.$message('开发中。。。');
        },
        /**
         * 获取聊天室列表
         */
        loadRoomList: function () {
            setTimeout(() => {
                // var size = this.roomList.length + 1;
                // var room = {name: "群聊" + size};
                // this.roomList.push(room);
                // this.loading = false
            }, 500)
        },
        /**
         * 获取用户列表
         */
        loadUserList: function () {
            return;
        },
        /**
         * 进入聊天室
         * @param formName
         */
        enterChat: function (formName) {
            var _this = this;
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    _this.showUser = false;
                    _this.initWebSocket()
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
            var index = Math.abs(hash % this.colors.length);
            return this.colors[index];
        },
        /**
         * 初始化websocket连接
         */
        initWebSocket: function () {
            this.connect(this.serverUrl);
            let that = this;
            // 断开重连机制,尝试发送消息,捕获异常发生时重连
            this.timer = setInterval(() => {
                try {
                    that.stompClient.send("test");
                } catch (err) {
                    console.log("断线了: " + err);
                    that.connect(that.serverUrl);
                }
            }, 5000);
        }
        ,
        /**
         * 建立ws链接
         * @param event
         */
        connect: function (remoteUrl) {
            // 建立连接对象
            let socket = new SockJS(remoteUrl + '/ws');
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
            this.connecting = false;
            /**
             * 订阅服务器发给topic/public的消息
             */
            this.stompClient.subscribe('/topic/public', this.onMessageReceived);
            this.stompClient.send("/app/chat.addUser",
                this.headers,
                JSON.stringify({sender: this.userInfo.username, type: 'JOIN'})
            )
        }
        ,
        /**
         * 接收服务器消息
         * @param payload
         */
        onMessageReceived: function (payload) {
            let message = JSON.parse(payload.body);
            message.style = {"background-color": this.getAvatarColor(message.sender)};
            this.messageList.push(message);
        }
        ,
        /**
         * 发送websocket消息
         */
        sendMessage: function () {
            if (this.messageContent == "") {
                this.$message('消息内容不能为空');
                return;
            }
            var chatMessage = {
                sender: this.userInfo.username,
                content: this.messageContent,
                type: 'CHAT'
            };
            this.stompClient.send("/app/chat.sendMessage",
                this.headers,
                JSON.stringify(chatMessage)
            )
            this.messageContent = "";
        }
        ,
        /**
         * 连接失败
         */
        onConnectError: function (err) {
            console.log("连接服务器失败,err:" + err);
        },
        /**
         * 断开连接
         */
        disconnect: function () {
            if (this.stompClient) {
                this.stompClient.disconnect();
                console.log("断开和服务其的ws连接")
            }
        },
        /**
         * 获取服务器地址
         */
        getServerUrl: function () {
            let url = window.location.href;
            if (url.lastIndexOf("/") > 0) {
                return url.slice(0, url.lastIndexOf("/"));
            }
            return this.serverUrl = url;
        }
    },
    mounted: function () {
        this.serverUrl = this.getServerUrl();
        console.log("服务器地址:" + this.serverUrl);
    }
    ,
    beforeDestroy: function () {
        // 页面离开时断开连接,清除定时器
        this.disconnect();
        clearInterval(this.timer);
    }
})