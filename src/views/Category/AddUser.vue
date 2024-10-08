<template>
  <div style="width: 80%;">
    <h2 style="margin-bottom: 30px;">新增用户</h2>
    <el-form :inline="true" :model="form" label-width="100px" :rules="rules" ref="ruleFrom">
      <el-form-item label="姓名" prop="name">
        <el-input v-model="form.name" placeholder="请输入姓名"></el-input>
      </el-form-item>
      <el-form-item label="年龄" prop="age">
        <el-input v-model="form.age" placeholder="请输入年龄"></el-input>
      </el-form-item>
      <el-form-item label="性别" prop="sex">
        <el-select v-model="form.sex" placeholder="请选择性别">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="地址">
        <el-input v-model="form.address" placeholder="请输入地址"></el-input>
      </el-form-item>
      <el-form-item label="联系方式" prop="phone">
        <el-input v-model="form.phone" placeholder="请输入联系方式"></el-input>
      </el-form-item>
    </el-form>
    <div style="text-align: center; margin-top: 30px;">
      <el-button type="primary" @click="save">
        <i class="el-icon-success"></i>提交
      </el-button>
      <el-button type="danger" @click="$router.push('/userList')">
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
  name: "AddUser",
  data() {
    const checkAge = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('年龄不能为空'));
        }
            
          if (!/^[0-9]+$/.test(value)) {
            callback(new Error('请输入数字值'));
          } 
          if (parseInt(value)<0||parseInt(value)>120) {
            callback(new Error('请输入正确的年龄'));
          }
          callback()
        }
        const checkPhone = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('联系方式不能为空'));
        }
            
          if (!/^[1][3,4,5,6,7,8,9][0-9]{9}$/.test(value)) {
            callback(new Error('请输入合法的手机号'));
          } 
         
          callback()
        }
return {
      form: {},
      options: [
        {
          value: "0",
          label: "男"
        },
        {
          value: "1",
          label: "女"
        }
      ],
      rules: {
        name: [{ required: true, message: "请输入", trigger: "blur" }],
        age:[{ validator: checkAge, trigger: 'blur'}],
        phone:[{ validator: checkPhone, trigger: 'blur'}]
      }
    }
},
  methods: {
    save() {
      this.$refs['ruleFrom'].validate(valid => {
        if (valid) {
          request.post("/user/save", this.form).then(res => {
            if (res.code == "200") {
              this.$notify.success("新增成功");
              this.form = {};
//清空表单
            //   this.$refs[formName].resetFields();
              this.$router.push("/userList");
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

