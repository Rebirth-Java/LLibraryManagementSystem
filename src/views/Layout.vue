<template>
    <div>
     <!-- 头部区域 -->
     <div style="height:60px; line-height:60px; background-color: white; margin-bottom: 2px; display: flex;">
      <div style="width: 300px;"><img src="@/assets/logo.png" style="width: 40px;position: relative; top: 10px; left: 20px;" />
      <span style="margin-left: 25px; font-size: 24px;">图书管理系统</span></div>
      <div style="flex: 1; text-align: right; padding-right:20px ;">
        <el-dropdown>
  <span class="el-dropdown-link" style="cursor: pointer;">欢迎
    {{ admin.username }}<i class="el-icon-arrow-down el-icon--right"></i>
  </span>
  <el-dropdown-menu slot="dropdown" style="margin-top: -5px;">
    <el-dropdown-item><div style="width: 50px; text-align: center;" @click="LogiNout">退出</div></el-dropdown-item>
  </el-dropdown-menu>
</el-dropdown>
      </div>
      <!--  -->
    </div>
    <!-- 侧边栏和主体 -->
    <div style="display: flex;">
      <!-- 侧边栏导航 -->
      <div
        style="width: 200px; min-height:calc(100vh - 62px); overflow:hidden; margin-right: 2px;  background-color: white;"
      >
        <el-menu
          :default-active="$route.path"
          router
          class="el-menu-demo"
          :default-openeds="['User','Admin','Category','Book','Borrow','Retur']"
          style="margin-bottom: 10px;"
        >
          <el-menu-item index="home">
            <i class="el-icon-eleme"></i>
            <span>首页</span>
          </el-menu-item>
          <el-submenu index="User">
            <template slot="title">
              <i class="el-icon-message-solid"></i>
              <span>会员管理</span>
            </template>
            
            <el-menu-item index="/userList">
              <i class="el-icon-s-opportunity"></i>会员列表
            </el-menu-item>
          </el-submenu>
          <el-submenu index="Admin">
            <template slot="title">
              <i class="el-icon-user"></i>
              <span>管理员管理</span>
            </template>
           
            <el-menu-item index="/adminList">
              <i class="el-icon-s-opportunity"></i>管理员列表
            </el-menu-item>
          </el-submenu>
          <el-submenu index="Category">
            <template slot="title">
              <i class="el-icon-s-operation"></i>
              <span>图书分类管理</span>
            </template>
           
            <el-menu-item index="/category">
              <i class="el-icon-s-operation"></i>分类管理列表
            </el-menu-item>
          </el-submenu>
          <el-submenu index="Book">
            <template slot="title">
              <i class="el-icon-notebook-1"></i>
              <span>图书管理</span>
            </template>
           
            <el-menu-item index="/book">
              <i class="el-icon-notebook-2"></i>图书管理列表
            </el-menu-item>
          </el-submenu>
          <el-submenu index="Borrow">
            <template slot="title">
              <i class="el-icon-document-copy"></i>
              <span>借书管理</span>
            </template>
           
            <el-menu-item index="/borrow">
              <i class="el-icon-document-copy"></i>借书管理列表
            </el-menu-item>
          </el-submenu>
          <el-submenu index="Retur">
            <template slot="title">
              <i class="el-icon-document-copy"></i>
              <span>还书管理</span>
            </template>
           
            <el-menu-item index="/Retur">
              <i class="el-icon-document-copy"></i>还书管理列表
            </el-menu-item>
          </el-submenu>
        </el-menu>
      </div>
      <!-- width:0px可以限制容器的宽度，不被子元素撑开 -->
      <!-- 主体数据 -->
      <div style="flex: 1;width: 0px; background-color: white; padding: 10px;">
        <router-view />
      </div>
    </div>
</div>
</template>
<script>
import Cookies from 'js-cookie'
export default {
    name:'Layout',
    data() {
      return {
        admin:Cookies.get('admin')?JSON.parse(Cookies.get('admin')):{}
      }
    },
    methods:{
      LogiNout(){
        Cookies.remove('admin')
       
        this.$router.push('/login') 
       
        this.$notify.success("退出成功")
       
       
      }
    }
}
</script>