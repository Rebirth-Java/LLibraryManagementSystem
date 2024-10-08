<template>
    <div>    
        <h2 style="margin-bottom: 30px;">会员详情</h2>
   <el-table :data="tableDate" stripe>
   <el-table-column label="编号" prop="id" ></el-table-column>
   <el-table-column label="卡号" prop="username" width="200px"></el-table-column>
   <el-table-column label="姓名" prop="name" ></el-table-column>
   <el-table-column label="年龄" prop="age" ></el-table-column>
   <el-table-column label="性别" prop="sex" :formatter="formatSex"></el-table-column>
   <el-table-column label="地址" prop="address" ></el-table-column>
   <el-table-column label="联系方式" prop="phone" ></el-table-column>
   <el-table-column label="创建时间" prop="createtime" ></el-table-column>
   <el-table-column label="修改时间" prop="updatetime" ></el-table-column>
    <el-table-column label="操作"  ><el-button type="primary" @click="$router.push('/userList')"><i class="el-icon-back"></i>返回</el-button>
</el-table-column>
   </el-table>

   <div style="text-align: center; margin-top: 30px;">
  
    
</div>
</div>

</template>

<script>
import request from '../../ultis/request'
export default {
    name:'AddUser',
    data(){
return {
    tableDate:[],
} 
},
created(){
const id= this.$route.query.id
request.get("/user/listID/"+id).then(res=>{
   this.tableDate=res.data
})
},
    methods:{
        formatSex(row){
 
 return row.sex === "0" ? "男" : row.sex === "1" ? "女" : "未填写";

},
    }
}

</script>

