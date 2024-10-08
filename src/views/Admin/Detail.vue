
<template>
   
    <div>   
        <h1>用户详情</h1> 
   <el-table :data="tableDate" stripe>
   <el-table-column label="编号" prop="id" ></el-table-column>
   <el-table-column label="用户名" prop="username" width="200px"></el-table-column>
   <el-table-column label="密码" prop="password" width="200px"></el-table-column>
  
   <el-table-column label="联系方式" prop="phone" ></el-table-column>
   <el-table-column label="创建时间" prop="createtime" ></el-table-column>
   <el-table-column label="修改时间" prop="updatetime" ></el-table-column>
    <el-table-column label="操作"  ><el-button type="primary" @click="$router.push('/adminList')"><i class="el-icon-back"></i>返回</el-button>
</el-table-column>
   </el-table>

   <div style="text-align: center; margin-top: 30px;">
  
    
</div>
</div>

</template>

<script>
import request from '../../ultis/request'
export default {
    name:'DetailAdmin',
    data(){
return {
    tableDate:[],
} 
},
created(){
const id= this.$route.query.id
request.get("/Admin/listID/"+id).then(res=>{
   this.tableDate=res.data
})
},
    methods:{
    
    }
}

</script>

