var vm = new Vue({
    el: '#app',
    data: function () {
        return {
            ruleForm: {
                username: '',
                password: '',
                rememberMe: false
            },
            config: {
                headers: {"Content-Type": "multipart/form-data"}
            },
            rules: {
                username: [
                    {required: true, message: '请输入昵称', trigger: 'blur'}
                ],
                password: [
                    {required: true, message: '请输入密码', trigger: 'blur'}
                ]
            }
        }
    },
    methods: {
        login: function (formName) {
            var _this = this;
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    axios.post("login", _this.ruleForm, _this.config).then(function (res) {
                        console.log(res);
                        if (res.data.success) {
                            _this.$message({type: 'success', message: res.data.message});
                            window.location.href = "im";
                        } else {
                            _this.$message.error(res.data.message);
                        }

                    }).catch(function (e) {
                        _this.$message("登录失败");
                    });
                } else {
                    _this.$message("参数校验失败");
                    return false;
                }
            });
        }
    },
    created: function () {

    },
    mounted: function () {

    }
})