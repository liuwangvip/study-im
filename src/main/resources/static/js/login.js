var vm = new Vue({
    el: '#app',
    data: function () {
        var validatePass = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请输入密码'));
            } else {
                if (this.ruleForm.password !== '') {
                    this.$refs.ruleForm.validateField('password2');
                }
                callback();
            }
        };
        var validatePass2 = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请再次输入密码'));
            } else if (value !== this.ruleForm.password) {
                callback(new Error('两次输入密码不一致!'));
            } else {
                callback();
            }
        };
        return {
            ruleForm: {
                username: '',
                password: '',
                password2: '',
            },
            config: {
                headers: {"Content-Type": "multipart/form-data"}
            },
            rules: {
                username: [
                    {required: true, message: '请输入昵称', trigger: 'blur'},
                    {min: 1, max: 10, message: '昵称长度限制：1一10', trigger: 'blur'}
                ],
                password: [
                    {validator: validatePass, trigger: 'blur'}
                ],
                password2: [
                    {validator: validatePass2, trigger: 'blur'}
                ],
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
        },
        resetForm: function (formName) {
            this.$refs[formName].resetFields();
        }
        ,
        register: function () {
            //TODO 注册
        }
        ,
    }
    ,
    created: function () {

    }
    ,
    mounted: function () {

    }
})