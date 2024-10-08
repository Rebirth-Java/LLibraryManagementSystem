<template>
   <div style="height: 100vh;  overflow: hidden; position: relative;" id="building" >
    <el-card class="cover" v-if="loginAdmin.id">
      <slide-verify :l="42"
                    :r="10"
                    :w="310"
                    :h="155"
                    :accuracy="5"
                    :imgs="puzzleImgList"
                    slider-text="向右滑动"
                    @success="onSuccess"
                    @fail="onFail"
                    @refresh="onRefresh"
      ></slide-verify>
    </el-card>
    <div
      style="width: 500px; height: 400px; background-color: white; border-radius: 10px; margin: 150px auto; padding: 20px;">
      <div style="margin: 30px; text-align: center; font-size: 30px; font-weight: bold; color: dodgerblue;">登录</div>
      <el-form :model="admin" ref="loginForm" :rules="rules" >
        <el-form-item prop="username">
            <el-input  placeholder="请输入账号" size="medium" prefix-icon="el-icon-user" v-model="admin.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
            <el-input  placeholder="请输入密码" show-password size="medium" prefix-icon="el-icon-lock" v-model="admin.password"></el-input>
        </el-form-item>
       <el-form-item >
        <el-button type="primary" style="width: 100%;"  size="medium" @click="loginn">登录</el-button>
       </el-form-item>
      </el-form>
      </div> 
      </div>
</template>
<script>
import request from '@/ultis/request';
import Cookies from 'js-cookie'
export default{
    name:'login',
    data() {
        return {
            puzzleImgList: [
        require('../../assets/logo.png'),
       
         require('../../img/require2.png'),
         require('../../img/require3.png'),
         require('../../img/require4.jpg')
         
      ], 
            loginAdmin:{},
           admin:{},
           rules: {
        username: [{ required: true, message: "请输入用户名", trigger: "blur" },
          {min:2,max:10,trigger:'blur',message: "长度在2-10之间", }
        ],
        password: [{ required: true, message: "请输入密码", trigger: "blur" },
          {min:3,max:10,trigger:'blur',message: "长度在3-10之间", }]
        
      }
        }
    },
    methods:{
        onSuccess() {
      Cookies.set('admin', JSON.stringify(this.loginAdmin))
      this.$router.push('/home')
      this.$notify.success("登录成功")
    },
    onFail() {
      this.$notify.error("验证失败请验证")
    },
    onRefresh() {
      console.log('refresh')
    }
    ,
        loginn(){
            this.$refs['loginForm'].validate(valid => {
        if (valid) {
            request.post('/Admin/login',this.admin).then(res=>{
                if (res.code=="200") {
                    this.loginAdmin=res.data//滑块组件出现了

                   
                   
                   if (res.data!=null) {
                 
                   
                    Cookies.set('admin',JSON.stringify(res.data))
                   }
                  
                }else{
                    this.$notify.error(res.msg)
                    console.log(res.data);
                }
            })
        }
            })
        }
    }
}

</script>

<style>
.cover {
  width: fit-content;
  background-color: white;
  position: absolute;
  top:50%;
  left:50%;
  transform: translate(-50%, -50%);
  z-index: 1000;
}
</style>
