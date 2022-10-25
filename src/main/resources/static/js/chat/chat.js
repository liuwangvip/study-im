var vm = new Vue({
    el: '#app',
    data: function () {
        return {
            notification: null,
            /**
             * 控制显示
             */
            visible: {
                userDialog: true,
                fileDialog: false,
                showMoreDialog: false,
                historyMessageDialog: false,
                showPic: false,
            },
            navType: "",
            /**
             * 聊天室列表
             */
            roomList: {
                searchText: "",
                data: [{name: "群聊", src: "img/chat/chatroom.png"}]
            },
            /**
             * 用户列表
             */
            userList: {
                searchText: "",
                /**
                 * 所有用户
                 */
                all: []
            },
            /**
             * 当前用户信息
             */
            currentUser: {
                username: ''
            },
            rules: {
                username: [
                    {required: true, message: '请输入用户名', trigger: 'blur'},
                ]
            },
            stompClient: null,
            timer: '',
            maxFailNum: 5,
            serverUrl: '',
            headers: {},
            connectStatus: "",
            emoji: {
                activeIndex: '0',
                panes: [],
                buttons: [],
                data: [[
                    "😄", "😃", "😁", "😊", "😘", "😚", "😗", "😙", "😭", "😂",
                    "😳", "😒", "😏", "😔", "😣", "😠", "😡", "😖", "😷", "😉",
                    "😍", "😥", "😪", "😌", "😜", "😝", "😓", "😲", "😞", "😨",
                    "😰", "😅", "😆", "😋", "😫", "😤", "😩", "😵", "😁", "🤓",
                    "😎", "😑", "😮", "😴", "😕", "🤭", "🤯", "🧐", "🤣", "🤤",
                    "🤥", "🤧", "😇", "😐", "😶", "😈", "👿", "👹", "👻", "💀"
                ], [
                    "🐱", "🐵", "🐯", "🐶", "🦁", "🐮", "🐷", "🐭", "🐻", "🐨",
                    "🐰", "🐹", "🐽", "🐸", "🐴", "🐺", "🐗", "🦄", "🐔", "🐦",
                    "🐤", "🐧", "🐣", "🐍", "🐛", "🐉", "🐎", "🐈", "🐑", "🐅",
                    "🦌", "🐙", "🐓", "🐇", "🐿", "🐼", "🐒", "🦃", "🐩", "🐊",
                    "🐡", "🐲", "🦈", "🦉", "🦇", "🦕", "🦔", "🦒", "🦓", "🦑",
                    "🦏", "🦍", "🐋", "🐬", "🐳", "🐌", "🐜", "🐝", "🐞", "🦋"
                ], [
                    "🍇", "🍈", "🍉", "🍊", "🍋", "🍌", "🍍", "🍎", "🍏", "🍐",
                    "🍑", "🍒", "🍞", "🍔", "🍙", "🍚", "🍧", "🍜", "🍨", "🍲",
                    "🍝", "🍵", "☕", "🍣", "🌯", "🌮", "🍺", "🍻", "🍸", "🍷",
                    "🧀", "🍰", "🍡", "🍢", "🍛", "🍘", "🍱", "🍭", "🍳", "🍿",
                    "🍟", "🎂", "🍦", "🌭", "🍼", "🍾", "🍹", "🍖", "🍗", "🍕",
                    "🍤", "🍥", "🍩", "🍪", "🍬", "🍮", "🍯", "🥟", "🥤", "🥩"
                ], [
                    "⚽", "🏀", "🎾", "⚾", "🏈", "🎳", "🎱", "🏐", "🏉", "🏒",
                    "🏑", "🏏", "🏓", "🏹", "🎿", "⛸", "⛳", "🎶", "🎵", "🎼",
                    "🎧", "🎤", "🎬", "🎰", "🏆", "🎣", "🚵", "🚴", "🏇", "🏄",
                    "🏂", "🏋", "🏌", "🏊", "🎹", "🎷", "🎸", "👾", "🎮", "🀄",
                    "🃏", "🎴", "🕹", "🎯", "🏁", "🎢", "⛵", "🥇", "🥈", "🥉",
                    "🛸", "🎲", "🎠", "🎡", "🚣", "🚤", "🎪", "🎭", "⛹", "💿"
                ], [
                    "💋", "💌", "💘", "❤", "💗", "💙", "💚", "💛", "💜", "💟",
                    "♣", "♦", "♥", "🔱", "🔗", "🚺", "💟", "🚮", "🚻", "🏧",
                    "🛂", "🛃", "🛄", "🛅", "🚹", "♿", "🚰", "🚾", "🚭", "⛔",
                    "🚫", "🔞", "📵", "🚯", "🚱", "🚳", "🚷", "0️⃣", "1️⃣", "2️⃣",
                    "3️⃣", "4️⃣", "5️⃣", "6️⃣", "7️⃣", "8️⃣", "9️⃣", "🔟", "⏩", "◀",
                    "⏪", "🔼", "⏫", "🔽", "⏬", "🉐", "🔠", "🔡", "🔢", "🔣",
                ]]
            },
            chat: {
                openNotice: false,
                unread: 0,
                loading: false,
                loadingText: "",
                preview: {
                    pictureUrl: "",
                    pdfUrl: ""
                },
                colors: [
                    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
                    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
                ],
                //消息列表
                message: {
                    tip: {
                        senderName: "超管",
                        content: "用户行为规范：用户的言行不得违反《计算机信息网络国际联网安全保护管理办法》、《互联网信息服务管理办法》、《维护互联网安全的决定》、《互联网新闻信息服务管理规定》、《长江保护法》、《中华人民共和国测绘法》、《地图管理条例》、《网络安全法》、《未成年人保护法》、《互联网宗教信息服务管理办法》等相关法律法规规定；由于用户言行导致的法律问题与平台无关，平台保留追诉权力。",
                        type: "1",
                        createTime: moment().format('YYYY-MM-DD HH:mm:ss')
                    },
                    data: [],
                },
                /**
                 * 用户列表
                 */
                userList: {
                    searchText: "",
                    onlineNum: 0,
                    onlineList: []
                },
                /**
                 * 历史消息搜索
                 */
                searchHistoryMessageText: "",
                /**
                 * 历史消息列表
                 */
                historyMessage: {
                    /**
                     * 搜索内容
                     */
                    searchText: "",
                    /**
                     * 当前页码
                     */
                    current: 1,
                    /**
                     * 每页显示的条数
                     */
                    size: 10,
                    total: 0,
                    data: []
                },
                /**
                 * 待发送消息的消息内容
                 */
                msgContent: "",
                /**
                 * 待发送的文件
                 */
                sendFile: {
                    disabled: false,
                    show: false,
                    fileName: "",
                    fileId: ""
                },

            }
        }
    },
    watch: {
        navType: function (newVal, oldVal) {
            switch (newVal) {
                case 'userList':
                    this.loadUserList();
                    return;
                case 'chatRoom':
                    this.loadRoomList();
                    this.return;
            }
        }
    },
    methods: {
        handleCommand(command) {
            switch (command) {
                case 'logout':
                    window.location.href = "logout";
                    break;
                case 'login':
                    window.location.href = "login";
                    break;
            }
        },
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
            window.location.href = "logout";
        },
        /**
         * 搜索聊天室
         */
        searchChatRoom: function () {
            if (this.roomList.searchText == "") {
                this.$message('搜索内容不能为空');
                return;
            }
            this.$message('开发中。。。');
        },
        /**
         * 搜索用户
         */
        searchUserList: function () {
            if (this.userList.searchText == "") {
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
         * 清除未读消息
         */
        clearUnread: function () {
            this.chat.unread = 0;
        },
        /**
         * 获取聊天室列表
         */
        loadRoomList: function () {
            //TODO 获取聊天室列表
        },
        /**
         * 获取在线人数
         */
        loadOnlineUserCount: function () {
            var _this = this;
            axios.get("user/count/1").then(function (res) {
                if (res.data.success) {
                    console.log("获取在线人数", res.data.result);
                    _this.chat.userList.onlineNum = res.data.result;
                }
            }).catch(function (err) {
                _this.$message.error("获取在线人数失败");
            });
        },
        /**
         * 获取用户列表
         */
        loadUserList: function () {
            var _this = this;
            axios.post("user/list", {}).then(function (res) {
                _this.userList.all = res.data.result;
            }).catch(function (e) {
                console.log("获取用户列表失败", e);
                _this.$message.error("获取用户列表失败");
            });
        },
        /**
         * 获取聊天室在线用户列表
         */
        loadChatOnlineUserList: function () {
            var _this = this;
            var param = {"onlineStatus": "1"};
            axios.post("user/list", param).then(function (res) {
                _this.chat.userList.onlineList = res.data.result;
            }).catch(function (e) {
                console.log("获取用户列表失败", e);
                _this.$message.error("获取用户列表失败");
            });
        },
        /**
         * 搜索用户
         */
        searchChatUserList: function () {
            if (this.chat.userList.searchText == "") {
                this.$message('搜索内容不能为空');
                return;
            }
            this.$message('开发中。。。');
        },
        /**
         * 获取消息列表，获取最近的条消息
         * @param size
         */
        loadMessageList: function (size) {
            var _this = this;
            var param = {current: 1, size: size};
            axios.post("chat/page", param).then(function (res) {
                if (res.data.result && res.data.result.records) {
                    _this.chat.message.data = res.data.result.records.reverse();
                    _this.chat.message.data.push(_this.chat.message.tip);
                    _this.scrollToChatMessageBottom();
                }
            }).catch(function (e) {
                console.log(e);
                _this.$message.error("获取最近消息失败");
            });
        },
        /**
         * 是否可以预览文件
         * @param fileName 文件名
         */
        canPreviewFile: function (fileName) {
            if (/\.(png|jpg|jpeg)$/.test(fileName)) {
                return true;
            }
            return false;
        },
        /**
         * 预览文件
         * @param fileId 文件id
         */
        previewFile: function (file) {
            if (/\.(png|jpg|jpeg)$/.test(file.fileName)) {
                this.visible.showPic = true;
                this.chat.preview.pictureUrl = "file/" + file.fileId;
                return;
            }
            this.$message("暂不支持该文件预览");
        },
        /**
         * 打开表情面板
         */
        openEmo: function () {
            this.choseEmojiTab(0);
        },
        /**
         * 改变表情tab
         * @param index
         */
        choseEmojiTab: function (index) {
            if (index == this.emoji.activeIndex) {
                return
            }
            // 修改 按钮样式
            this.emoji.buttons[index].classList.add('tab-active')
            this.emoji.buttons[this.emoji.activeIndex].classList.remove('tab-active')
            // 修改 显示的选项卡
            this.emoji.panes[this.emoji.activeIndex].classList.add('tab-hide')
            this.emoji.panes[index].classList.remove('tab-hide')
            // 更新 当前所在选项卡索引
            this.emoji.activeIndex = index
        },
        choseEmoji: function (emo) {
            this.chat.msgContent = this.chat.msgContent + emo;
            this.$refs['chat-input'].focus();
        },
        /**
         * 打开文件
         */
        openFile: function () {
            this.visible.fileDialog = true;
        },
        /**
         * 对上传的文件进行限制
         * @param file
         */
        checkUploadFile: function (file) {
            console.log("处理文件之前的校验", file);
            var size200M = 1024 * 1024 * 200;
            if (file.size > size200M) {
                this.$message.error('上传文件大小不能超过 200MB!');
                return false;
            }
            this.chat.loading = true;
            this.chat.loadingText = "文件上传中,请稍等";
            return true;
        },
        /**
         * 文件上传成功
         * @param res
         * @param file
         * @param fileList
         */
        handleFileSuccess: function (res, file, fileList) {
            this.chat.loading = false;
            this.$message({type: "success", message: "文件上传成功"});
            this.showChatSendFile(res);
        },
        /**
         * 显示上传的文件
         * @param res
         */
        showChatSendFile: function (res) {
            this.chat.sendFile.show = true;
            this.chat.sendFile.disabled = true;
            this.chat.sendFile.fileName = res.result.fileName;
            this.chat.sendFile.fileId = res.result.fileId;
        },
        /**
         * 处理文件上传失败
         * @param res
         * @param file
         * @param fileList
         */
        handleFileFail: function (res, file, fileList) {
            this.chat.loading = false;
            this.$message.error(res.message || "文件上传失败，请联系管理员");
        },
        /**
         * 删除待发送的文件
         */
        deleteSendFile: function () {
            var _this = this;
            axios.delete("file/" + this.chat.sendFile.fileId).then(function (res) {
                console.log("文件删除成功", res);
                _this.clearSendFile();
                _this.$message({message: "文件删除成功", type: 'success'});
            }).catch(function (e) {
                console.log("文件删除失败", e);
                _this.$message.error("文件删除失败，请联系管理员");
            });
        },
        /**
         * 清空待发送的文件
         */
        clearSendFile: function () {
            this.chat.sendFile.show = false;
            this.chat.sendFile.disabled = false;
            this.chat.sendFile.fileName = "";
            this.chat.sendFile.fileId = "";
        },
        /**
         * 打开消息历史
         */
        openMsgHistory: function () {
            this.visible.historyMessageDialog = true;
            this.chat.historyMessage.current = 1;
            this.chat.historyMessage.size = 10;
            this.chat.historyMessage.searchText = "";
            this.loadHistoryMessageList();
        },
        /**
         * 获取消息列表，获取最近的条消息
         * @param size
         */
        loadHistoryMessageList: function () {
            var _this = this;
            var param = {
                current: this.chat.historyMessage.current,
                size: this.chat.historyMessage.size,
                searchText: this.chat.historyMessage.searchText
            };
            axios.post("chat/page", param).then(function (res) {
                _this.chat.historyMessage.data = res.data.result.records.reverse();
                _this.chat.historyMessage.total = res.data.result.total;
            }).catch(function (e) {
                console.log(e);
                _this.$message.error("获取历史消息失败");
            });
        },
        /**
         * 历史消息搜索
         */
        searchHistoryMessage: function () {
            if (this.chat.historyMessage.searchText == "") {
                this.$message("搜索内容不能为空");
                return;
            }
            this.chat.historyMessage.current = 1;
            this.chat.historyMessage.size = 10;
            var param = {
                current: this.chat.historyMessage.current,
                size: this.chat.historyMessage.size,
                searchText: this.chat.historyMessage.searchText
            };
            var _this = this;
            axios.post("chat/page", param).then(function (res) {
                _this.chat.historyMessage.data = res.data.result.records.reverse();
                _this.chat.historyMessage.total = res.data.result.total;
            }).catch(function (e) {
                console.log(e);
                _this.$message.error("获取历史消息失败");
            });
        },
        /**
         * 改变每页显示条数
         * @param val
         */
        handleHistoryMessageSizeChange(val) {
            this.chat.historyMessage.current = 1;
            this.chat.historyMessage.size = val;
            this.loadHistoryMessageList();
        },
        /**
         * 改变页码
         * @param val
         */
        handleHistoryMessagePageChange(val) {
            this.chat.historyMessage.current = val;
            this.loadHistoryMessageList();
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
        /**
         * 显示更多
         */
        showMore: function () {
            this.visible.showMoreDialog = !this.visible.showMoreDialog;
            if (this.visible.showMoreDialog) {
                this.loadChatOnlineUserList();
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
                    _this.visible.userDialog = false;
                    _this.initWebSocket()
                } else {
                    this.$message("用户名不能为空");
                    return false;
                }
            });
        },
        /**
         * 获取头像的颜色
         * @param messageSender
         * @returns {string}
         */
        getAvatarColor: function (messageSender) {
            var hash = 0;
            for (var i = 0; i < messageSender.length; i++) {
                hash = 31 * hash + messageSender.charCodeAt(i);
            }
            var index = Math.abs(hash % this.chat.colors.length);
            return this.chat.colors[index];
        },
        /**
         * 获取头像的样式
         * @param messageSender
         * @returns {{"background-color": string}}
         */
        getAvatarStyle: function (message) {
            var style = {};
            if (this.currentUser.username != message.senderName) {
                style['left'] = "10px";
            } else {
                style['right'] = "10px";
            }
            style['background-color'] = this.getAvatarColor(message.senderName);
            return style;
        },
        /**
         * 初始化websocket连接
         */
        initWebSocket: function () {
            this.connect(this.serverUrl);
            let that = this;
            // 断开重连机制,尝试发送消息,捕获异常发生时重连
            this.timer = setInterval(() => {
                if (that.maxFailNum < 0) {
                    that.$notify.error({title: '提示', message: '尝试断线重连失败，已达最大失败次数', duration: 0});
                    clearInterval(that.timer);
                    return;
                }
                if (that.connectStatus == "3" || that.connectStatus == "4") {
                    that.$message("尝试断线重连");
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
            this.connectStatus = "1";
            // 建立连接对象
            let socket = new SockJS(remoteUrl + '/ws');
            // 获取STOMP子协议的客户端对象
            this.stompClient = Stomp.over(socket);
            // 定义客户端的认证信息,按需求配置
            this.headers = {}
            // 向服务器发起websocket连接
            this.stompClient.connect(this.headers, this.onConnectSuccess, this.onConnectError);
        },
        /**
         * 链接成功
         */
        onConnectSuccess: function () {
            this.maxFailNum = 5;
            this.connectStatus = "2";
            /**
             * 订阅服务器发给topic/public的消息
             */
            this.stompClient.subscribe('/topic/public', this.onMessageReceived);
            /**
             * 订阅服务器发给topic/online的消息
             */
            this.stompClient.subscribe('/topic/online', this.onMessageOnline);
            this.stompClient.send("/app/chat.online", this.headers,
                JSON.stringify({senderName: this.currentUser.username})
            );
        },
        /**
         * 接收服务器消息
         * @param payload
         */
        onMessageReceived: function (payload) {
            let message = JSON.parse(payload.body);
            if (message && !this.chat.openNotice && (message.type == '2.1' || message.type == '2.2')) {
                this.scrollToChatMessageBottom();
                return;
            }
            if (message && message.type == '1') {
                this.chat.unread = this.chat.unread + 1;
            }
            this.chat.message.data.push(message);
            this.scrollToChatMessageBottom();
            this.noticeMessage();
        },
        /**
         * 接收服务器在线消息
         * @param payload
         */
        onMessageOnline: function (payload) {
            this.chat.userList.onlineNum = payload.body;
        },
        /**
         * 滚动到最新消息处
         * @param ref
         */
        scrollToChatMessageBottom: function () {
            var _this = this;
            this.$nextTick(() => {
                if (_this.$refs.fd_chat_main.scrollHeight == _this.$refs.fd_chat_main.scrollTop) {
                    return;
                }
                if (_this.$refs.fd_chat_main.scrollHeight) {
                    _this.$refs.fd_chat_main.scrollTop = _this.$refs.fd_chat_main.scrollHeight
                }
            }, 400);
        },
        /**
         * 处理粘贴
         * @param event
         */
        handlePaste: function (event) {
            var _this = this;
            // 读取图片
            let items = event.clipboardData && event.clipboardData.items;
            let file = null;
            if (items && items.length) {
                // 检索剪切板items
                for (let i = 0; i < items.length; i++) {
                    if (items[i].type.indexOf('image') !== -1) {
                        file = items[i].getAsFile();
                        break;
                    }
                }
            }
            if (file == null) {
                return;
            }
            var reader = new FileReader();
            reader.onloadend = function (event) {
                var imgBase64 = event.target.result;
                if (imgBase64 == 'data:' || imgBase64 == "") {
                    return;
                }
                var dataURI = imgBase64;
                var blob = _this.dataURItoBlob(dataURI);
                _this.uploadFile(blob, "截图.png");
            };
            reader.readAsDataURL(file);
        },
        /**
         * base64  to blob二进制
         * @param dataURI
         * @returns {Blob}
         */
        dataURItoBlob: function (dataURI) {
            var mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0]; // mime类型
            var byteString = atob(dataURI.split(',')[1]); //base64 解码
            var arrayBuffer = new ArrayBuffer(byteString.length); //创建缓冲数组
            var intArray = new Uint8Array(arrayBuffer); //创建视图
            for (var i = 0; i < byteString.length; i++) {
                intArray[i] = byteString.charCodeAt(i);
            }
            return new Blob([intArray], {type: mimeString});
        },

        /**
         * 上传文件
         * @param file
         */
        uploadFile: function (file, fileName) {
            var formData = new FormData();
            formData.append('file', file, fileName);
            var _this = this;
            axios.post("file", formData, {headers: {"Content-Type": "multipart/form-data"}}).then(function (res) {
                _this.$message("文件上传成功");
                _this.showChatSendFile(res.data);
            }).catch(function (e) {
                _this.$message("文件上传失败");
            });
        },
        /**
         * 发送websocket消息
         */
        sendMessage: function () {
            if (this.chat.msgContent == "" && this.chat.sendFile.fileId == "") {
                this.$notify({title: '提示', message: '消息内容不能为空', type: 'warning'});
                return;
            }
            var chatMessage = {
                senderId: this.currentUser.id,
                senderName: this.currentUser.username,
                fileId: this.chat.sendFile.fileId,
                fileName: this.chat.sendFile.fileName,
                content: this.chat.msgContent,
                type: "1"
            };
            this.stompClient.send("/app/public.sendMessage",
                this.headers,
                JSON.stringify(chatMessage)
            )
            this.chat.msgContent = "";
            this.clearSendFile();
        },
        /**
         * 发送文件
         */
        sendFile: function () {
            this.$message("开发中。。。")
        },
        /**
         * 连接失败
         */
        onConnectError: function (err) {
            this.connectStatus = "3";
            this.maxFailNum = this.maxFailNum - 1;
            this.$notify({title: '提示', message: '连接服务器失败', type: 'error'});
        },
        /**
         * 断开连接
         */
        disconnect: function () {
            if (this.stompClient) {
                this.stompClient.disconnect();
                this.connectStatus = "4";
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
        /**
         * 获取当前登录用户
         */
        getLoginUser: function () {
            var _this = this;
            axios.get("user/name").then(function (res) {
                if (res.data.success) {
                    console.log("获取登录用户成功", res.data.result.username);
                    _this.addWaterMark(res.data.result.username);
                    _this.visible.userDialog = false;
                    _this.currentUser = res.data.result;
                    _this.initWebSocket();
                }
            }).catch(function (err) {
                console.log("获取登录用户失败", err);
                _this.$message("获取登录用户失败");
            });
        },
        /**
         * 添加水印
         * @param content
         */
        addWaterMark: function (content) {
            var container = document.body;
            var width = '200px';
            var height = '150px';
            var textAlign = 'center';
            var textBaseline = 'top';
            var font = "16px Microsoft Yahei";
            var fillStyle = 'rgba(89, 91, 93,0.05)';
            // var fillStyle = 'rgba(184, 184, 184, 0.8)';
            var rotate = '30';
            var zIndex = 1000;
            var canvas = document.createElement('canvas');

            canvas.setAttribute('width', width);
            canvas.setAttribute('height', height);
            var ctx = canvas.getContext("2d");

            ctx.textAlign = textAlign;
            ctx.textBaseline = textBaseline;
            ctx.font = font;
            ctx.fillStyle = fillStyle;
            ctx.rotate(Math.PI / 180 * rotate);
            ctx.fillText(content, parseFloat(width) / 2, parseFloat(height) / 2);

            var base64Url = canvas.toDataURL();
            const watermarkDiv = document.createElement("div");
            watermarkDiv.setAttribute('style',
                `position:absolute;
                  top:0;
                  left:0;
                  width:100%;
                  height:100%;
                  z-index:${zIndex};
                  pointer-events:none;
                  background-repeat:repeat;
                  background-image:url('${base64Url}')`
            );
            container.style.position = 'relative';
            container.insertBefore(watermarkDiv, container.firstChild);
        },
        noticeMessage: function () {
            var unread = this.chat.unread;
            var _this = this;
            Notification.requestPermission().then(function (permission) {
                if (permission === 'granted') {
                    if (_this.notification && _this.notification.close) {
                        _this.notification.close();
                    }
                    _this.notification = new Notification('消息提醒', {
                        body: `您有${unread}条未读消息`
                    });
                } else if (permission === 'denied') {
                    console.log('用户拒绝通知');
                }
            });
        },
        init: function () {
            this.getLoginUser();
            this.loadMessageList();
            this.navType = "chatRoom";
            this.serverUrl = this.getServerUrl();
            console.log("服务器地址:" + this.serverUrl);
        }
    },
    mounted: function () {
        this.init();
        // 当前所在选项卡索引
        this.emoji.activeIndex = 0
        // 页面
        this.emoji.panes = document.querySelectorAll('.emoji-tabs .tab-pane')
        // 按钮
        this.emoji.buttons = document.querySelectorAll('.emoji-tabs .tab-button')
    },
    beforeDestroy: function () {
        // 页面离开时断开连接,清除定时器
        this.disconnect();
        clearInterval(this.timer);
    }
})