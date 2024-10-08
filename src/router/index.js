import Vue from 'vue'
import VueRouter from 'vue-router'
// import HomeView from '../views/Home/HomeView.vue'
import Layout from "../views/Layout.vue";
import Cookies from 'js-cookie';
Vue.use(VueRouter)

const routes = [
  //====登录===
  {
    path: '/login',
    name: 'Login',
    component:()=>import('@/views/Login/login.vue')
  },
  //主页
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect:'/login',
    children:[
      {
        path: 'home',
        name: 'Home',
        component:()=>import ('@/views/Home/HomeView.vue')
      },
      {
        path: 'userlist',
        name: 'Userlist',
        component:()=>import ('@/views/user/User.vue')
      },
      // {
      //   path: 'addUser',
      //   name: 'AddUser',
      //   component:()=>import ('@/views/user/AddUser.vue')
      // },
      // {
      //   path: 'edituser',
      //   name: 'EditUser',
      //   component:()=>import ('@/views/user/EditUser.vue')
      // },
      // {
      //   path: 'detail',
      //   name: 'Detail',
      //   component:()=>import ('@/views/user/Detail.vue')
      // },
      // Admin
      {
        path: 'adminlist',
        name: 'Adminlist',
        component:()=>import ('@/views/Admin/List.vue')
      },
      // {
      //   path: 'addAdmin',
      //   name: 'AddAdmin',
      //   component:()=>import ('@/views/Admin/Add.vue')
      // },
      // {
      //   path: 'editAdmin',
      //   name: 'EditAdmin',
      //   component:()=>import ('@/views/Admin/Edit.vue')
      // },
      // {
      //   path: 'detailAdmin',
      //   name: 'DetailAdmin',
      //   component:()=>import ('@/views/Admin/Detail.vue')
      // },
      {
        path: 'category',
        name: 'category',
        component:()=>import ('@/views/Category/Category.vue')
      },
      {
        path: 'Book',
        name: 'book',
        component:()=>import ('@/views/Book/Book.vue')
      },
      {
        path: 'Borrow',
        name: 'borrow',
        component:()=>import ('@/views/Borrow/borrow.vue')
      },
      {
        path: 'Retur',
        name: 'retur',
        component:()=>import ('@/views/retur/Retur.vue')
      }
    ]
  },
  // 
  {
    path:"*",
    component:()=>import('@/views/404.vue')
  }
  //===user====
  
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})
//路由守卫
router.beforeEach((to, from, next) => {
  if (to.path === '/login') next()
  const admin = Cookies.get("admin")
  if (!admin && to.path !== '/login') return next("/login")
  next()
})
export default router
