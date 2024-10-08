<template>
  <div style="width: 80%;">
    <h2 style="margin-bottom: 30px;">新增管理员</h2>
    <el-form :inline="true" :model="form" label-width="100px" :rules="rules" ref="ruleFrom">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="form.username" placeholder="用户名"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input v-model="form.password" placeholder="请输入密码 "></el-input>
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="form.email" placeholder="请输入邮箱"></el-input>
      </el-form-item>
      
      <el-form-item label="联系方式" prop="phone">
        <el-input v-model="form.phone" placeholder="请输入联系方式"></el-input>
      </el-form-item>
    </el-form>
    <div style="text-align: center; margin-top: 30px;">
      <el-button type="primary" @click="save">
        <i class="el-icon-success"></i>提交
      </el-button>
      <el-button type="danger" @click="$router.push('/adminList')">
        <i class="el-icon-error"></i>取消
      </el-button>
    </div>
  </div>
</template>
<script>
import { Message } from "element-ui";
import request from "../../ultis/request";
import { triggerRef } from "vue";
export default {
  name: "addAdmin",
  data() {
        const checkPhone = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('联系方式不能为空'));
        }
            
          if (!/^[1][3,4,5,6,7,8,9][0-9]{9}$/.test(value)) {
            callback(new Error('请输入合法的手机号'));
          } 
         
          callback()
        }
        const checkemail = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('邮箱方式不能为空'));
        }
            
          if (!/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/.test(value)) {
            callback(new Error('请输入合法的邮箱号'));
          } 
         
          callback()
        }
return {
      form: {},
      rules: {
        username: [{ required: true, message: "请输入用户名", trigger: "blur" },
          {min:3,max:10,trigger:'blur',message: "长度在3-10之间", }
        ],
        email:[{ validator: checkemail, trigger: 'blur'}],
        phone:[{ validator: checkPhone, trigger: 'blur'}]
      }
    }
},
  methods: {
    save() {
      this.$refs['ruleFrom'].validate(valid => {
        if (valid) {
          request.post("/Admin/save", this.form).then(res => {
            if (res.code == "200") {
              this.$notify.success("新增成功");
              this.form = {};
//清空表单
            //   this.$refs[formName].resetFields();
              this.$router.push("/adminList");
            } else {
              this.$notify.error(res.msg);
              return false;
            }
          });
        }
      });
    }
  }
}
</script>

