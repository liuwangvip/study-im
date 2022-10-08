// 历史消息组件
Vue.component("history-message", {
        data: function () {
            return {
                historyMessage: {
                    searchText,
                    data: []
                },
                count: 0
            }
        },
        template: `
            <el-container class="fd-history-chat-container">
            <el-header class="fd-history-chat-header" height="50px">
                <el-input @keyup.enter.native="searchHistoryMessage" size="mini"
                          prefix-icon="el-icon-search"
                          placeholder="搜索" v-model="chat.historyMessage.searchText"
                          class="input-with-select">
                </el-input>
            </el-header>
            <el-main class="fd-history-chat-main" ref="fd_history_chat_main">
                <div class="fd-chat-content-item" v-for="(message,index) in chat.historyMessage.data" :key="index">
                    <div class="fd-chat-notice" v-if="message.type == '2.1'||message.type == '2.2'">
                        <div class="chat-notice-text">
                            <span>{{message.content}}</span>
                            <span style="padding-left: 5px">{{message.createTime}}</span>
                        </div>
                    </div>
                    <div class="fd-chat-message" v-else>
                        <div class="fd-chat-message-left"
                             :style="{'flex-direction':message.senderName!=currentUser.username?'row':'row-reverse'}">
                            <div :style="getAvatarStyle(message)" class="fd-chat-message-avatar">
                                {{message.senderName[0]}}
                            </div>

                            <div class="fd-chat-message-content"
                                 :style="{'align-items': message.senderName!=currentUser.username?'flex-start':'flex-end'}">
                                <div class="fd-chat-message-title"
                                     :style="{'flex-direction':message.senderName!=currentUser.username?'row':'row-reverse'}">
                                    <span>{{message.senderName}}</span>
                                    <span class="fd-chat-message-title-date">{{message.createTime}}</span>
                                </div>
                                <div class="fd-chat-message-content-text">
                                    {{message.content}}
                                </div>
                                <div class="fd-chat-message-content-file"
                                     v-if="message.fileId!='' && message.fileId!=null">
                                    <div class="fd-chat-message-content-file-item">
                                        <i class="el-icon-document"></i>
                                        {{message.fileName}}
                                    </div>
                                    <div class="fd-chat-message-content-file-item"
                                         v-if="canPreviewFile(message.fileName)">
                                        <el-tooltip class="item" effect="dark" content="预览"
                                                    placement="bottom-end">
                                            <el-link :underline="false"
                                                     @click="previewFile(message)"><i
                                                        class="el-icon-view el-icon--right"></i></el-link>
                                        </el-tooltip>
                                    </div>
                                    <div class="fd-chat-message-content-file-item">
                                        <el-tooltip class="item" effect="dark" content="下载"
                                                    placement="bottom-end">
                                            <el-link :underline="false" :href="'file/'+message.fileId"><i
                                                        class="el-icon-download el-icon--right"></i>
                                            </el-link>
                                        </el-tooltip>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </el-main>
            <el-footer class="fd-history-chat-footer" height="50px">
                <el-pagination
                        small="true"
                        @size-change="handleHistoryMessageSizeChange"
                        @current-change="handleHistoryMessagePageChange"
                        :current-page="chat.historyMessage.current"
                        :page-sizes="[10, 20, 50]"
                        :page-size="chat.historyMessage.size"
                        :total="chat.historyMessage.total"
                        layout="total, prev, pager, next, sizes">
                </el-pagination>
            </el-footer>
        </el-container>
        `,
        method: {

        },
        mounted: function () {
        }

    }
)