var vm = new Vue({
    el: '#app',
    data: function () {
        return {
            drawer: false,
            navType: "",
            /**
             * 聊天室列表
             */
            roomList: [{name: "群聊", src: "img/chat/chatroom.png"}],
            /**
             * 用户列表
             */
            userList: [
                {name: "超管", src: "img/chat/user-blue.png"}
            ],
            /**
             * 搜索聊天室
             */
            searchChatRoomText: "",
            /**
             * 搜索人员
             */
            searchUserListText: "",
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
                    sender: "超管",
                    content: "用户行为规范：用户的言行不得违反《计算机信息网络国际联网安全保护管理办法》、《互联网信息服务管理办法》、《维护互联网安全的决定》、《互联网新闻信息服务管理规定》、《长江保护法》、《中华人民共和国测绘法》、《地图管理条例》、《网络安全法》、《未成年人保护法》、《互联网宗教信息服务管理办法》等相关法律法规规定；由于用户言行导致的法律问题与平台无关，平台保留追诉权力。",
                    type: "CHAT",
                    style: {"background-color": '#2196F3'},
                    date: moment().format('YYYY-MM-DD HH:mm:ss')
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
    watch: {
        navType: function (newVal, oldVal) {
            console.log(newVal, oldVal);
            switch (newVal) {
                case 'userList':
                    this.loadUserList();
                    return;
                case 'chatRoom':
                    this.loadRoomList();
                    return;
            }
        }
    },
    methods: {
        /**
         * 打开设置
         */
        openSetting: function () {
            this.$message('开发中。。。');
        },
        /**
         * 改变导航
         * @param type
         */
        changeNav: function (type) {
            this.navType = type;
        },
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
        searchChatRoom: function () {
            if (this.searchChatRoomText == "") {
                this.$message('搜索内容不能为空');
                return;
            }
            this.$message('开发中。。。');
        },
        searchUserList: function () {
            if (this.searchUserListText == "") {
                this.$message('搜索内容不能为空');
                return;
            }
            this.$message('开发中。。。');
        },
        /**
         * 添加用户
         */
        addUser: function () {
            this.$message('开发中。。。');
        },
        /**
         * 添加聊天室
         */
        addChatRoom: function () {
            this.$message('开发中。。。');
        },
        /**
         * 获取聊天室列表
         */
        loadRoomList: function () {
            this.roomList.splice(0);
            for (let i = 0; i < 10; i++) {
                var size = this.roomList.length + 1;
                var room = {name: "群聊" + size, src: "img/chat/chatroom.png"};
                this.roomList.push(room);
            }
        },
        /**
         * 获取用户列表
         */
        loadUserList: function () {
            this.userList.splice(0);
            for (let i = 0; i < 10; i++) {
                var size = this.userList.length + 1;
                var room = {name: "用户" + size, src: "img/chat/user-blue.png"};
                this.userList.push(room);
            }
        },
        /**
         * 打开表情面板
         */
        openEmo: function () {
            this.$message('开发中。。。');
        },
        /**
         * 打开文件
         */
        openFile: function () {
            this.$message('开发中。。。');
        },
        /**
         * 打开消息历史
         */
        openMsgHistory: function () {
            this.$message('开发中。。。');
        },
        /**
         * 打开语音聊天
         */
        openVoiceChat: function () {
            this.$message('开发中。。。');
        },
        /**
         * 打开视频聊天
         */
        openVideoChat: function () {
            this.$message('开发中。。。');
        },
        showMore: function () {
            this.drawer = !this.drawer;
            if (this.drawer) {
                this.loadUserList();
            }
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
                    this.$message("用户名不能为空");
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
                    this.$message("断线重连");
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
                JSON.stringify({
                    sender: this.userInfo.username,
                    type: 'NOTICE',
                    content: this.userInfo.username + " 上线",
                    date: moment().format('YYYY-MM-DD HH:mm:ss')
                })
            )
        }
        ,
        /**
         * 接收服务器消息
         * @param payload
         */
        onMessageReceived: function (payload) {
            var _this = this;
            let message = JSON.parse(payload.body);
            message.style = {"background-color": this.getAvatarColor(message.sender)};
            this.messageList.push(message);
            this.$nextTick(() => {
                _this.$refs.fd_chat_main.scrollTop = _this.$refs.fd_chat_main.scrollHeight
            })
        }
        ,
        /**
         * 发送websocket消息
         */
        sendMessage: function () {
            if (this.messageContent == "") {
                this.$notify({title: '提示', message: '消息内容不能为空', type: 'warning'});
                return;
            }
            var chatMessage = {
                sender: this.userInfo.username,
                content: this.messageContent,
                date: moment().format('YYYY-MM-DD HH:mm:ss'),
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
            this.$notify({title: '提示', message: '连接服务器失败', type: 'error'});
        },
        /**
         * 断开连接
         */
        disconnect: function () {
            if (this.stompClient) {
                this.stompClient.disconnect();
                this.$notify({title: '提示', message: '断开连接', type: 'info'});
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
        },
        init: function () {
            this.navType = "chatRoom";
            this.serverUrl = this.getServerUrl();
            console.log("服务器地址:" + this.serverUrl);
        }
    },
    mounted: function () {
        this.init();
    }
    ,
    beforeDestroy: function () {
        // 页面离开时断开连接,清除定时器
        this.disconnect();
        clearInterval(this.timer);
    }
})