<style>
.el-input--small .el-input__inner {
    height: 32px;
    line-height: 32px;
    width: 200px;
}
.el-form-item .is-error .el-form-item--small{
  margin-left: -59px
}
.el-form-item--mini.el-form-item, .el-form-item--small.el-form-item {
    margin-left: 23px;
    margin-bottom: 18px;
} 
/* element 滚动条的宽度 */
/* 自定义滚动条样式 */
.el-table__body-wrapper::-webkit-scrollbar {
  width: 8px;
  height: 7px;
}

.el-table__body-wrapper::-webkit-scrollbar-thumb {
  background-color: #c0c0c0;
  border-radius: 4px;
}

.el-table__body-wrapper::-webkit-scrollbar-track {
  background-color: #f5f5f5;
}

.el-select .el-input .el-select__caret {
    display: none;
    color: #C0C4CC;
    font-size: 14px;
    transition: transform .3s;
    transform: rotateZ(180deg);
    cursor: pointer;
}

</style>
<template>
 
  <div>
    
    <div style="margin-bottom: 20px;">
      <el-input style="width: 240px;" placeholder="请输入分类名称" v-model="params.name"></el-input>
     
      <el-button type="primary" style="margin-left: 5px;" @click="load">
        <i class="el-icon-search"></i>
        搜索
      </el-button>
      <el-button type="warning" style="margin-left: 5px;" @click="reset">
        <i class="el-icon-refresh"></i>
        重置
      </el-button>
    </div>
    <el-button type="warning" style="margin-left: 5px;"  @click="AddUser">
        <i class="el-icon-circle-plus-outline"></i>
        分类添加
      </el-button>
      <!-- 数据展示 -->
 <el-table :data="tableDate" stripe  style="width: 100%;" row-key="id">
  <el-table-column label="编号" prop="id" ></el-table-column>
   
   <el-table-column label="名称" prop="name" ></el-table-column>
   <el-table-column label="备注" prop="remark" ></el-table-column>
   <el-table-column label="创建时间" prop="createtime" ></el-table-column>
   <el-table-column label="修改时间" prop="updatetime" ></el-table-column>

   <el-table-column label="操作" width="370px">
    <template v-slot="scope">
      <el-button type="success" @click="CategoryAdd(scope.row)" v-if="!scope.row.pid"><i class="el-icon-edit-outline"></i>添加二级分类</el-button>
          <el-button type="primary" @click="EidtUser(scope.row)"><i class="el-icon-edit-outline"></i>编辑</el-button>
      <el-button type="primary" @click="DetailUser(scope.row)"><i class="el-icon-message"></i>详情</el-button>
      <el-popconfirm
      style="margin-left: 5px;"
  title="你确定删除这行数据吗？"
  @confirm="del(scope.row.id)"
>
  <el-button slot="reference" type="danger"><i class="el-icon-delete-solid"></i>删除</el-button>
</el-popconfirm>
     
    </template>
  </el-table-column>
 </el-table>
 <div>
<!-- 分页 -->
  <el-pagination style="margin-top: 20px;"
  background
  :page-size="params.pageSize"
  :current-page="params.pageNum"
    layout="prev, pager, next"
    @current-change="handelCurrentChange"
    :total="total">
  </el-pagination>
 </div>
 <!-- 添加书籍分类 -->
 <el-dialog title="分类新增" :visible.sync="dialogFormVisibleAdd" style="width: 100%;" >
      <el-form :inline="true" :model="formAdd" label-width="90px" :rules="rules" ref="ruleFrom">
      <el-form-item label="分类名称" prop="name">
        <el-input v-model="formAdd.name" placeholder="请输入分类名称"></el-input>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="formAdd.remark" placeholder="请输入备注"></el-input>
      </el-form-item>
  </el-form>
  <div slot="footer" class="dialog-footer">
    <el-button @click="dialogFormVisibleAdd=false">取 消</el-button>
    <el-button type="primary" @click="SaveAdd ">确 定</el-button>
  </div>
  </el-dialog>
    <!-- 详情弹窗 -->
    <el-dialog title="会员详情" :visible.sync="dialogTableVisibleDeatil" >
      <el-table :data="gridData" stripe>
        <el-table-column label="编号" prop="id" ></el-table-column>
  
   <el-table-column label="名称" prop="name" ></el-table-column>
   <el-table-column label="备注" prop="remark" ></el-table-column>
   <el-table-column label="父级id" prop="pid"></el-table-column>
  
  
   <el-table-column label="创建时间" prop="createtime" ></el-table-column>
   <el-table-column label="修改时间" prop="updatetime" ></el-table-column>

   </el-table>
</el-dialog>
<!-- 编辑会员信息 -->
<el-dialog  title="编辑分类信息" :visible.sync="dialogFormVisible" style="width: 100%;">
  
        <el-form :inline="true" :model="form" label-width="100px" :rules="rules" ref="ruleFrom">
    <el-form-item label="分类名称" prop="name">
        <el-input v-model="form.name" placeholder="请输入姓名" ></el-input>
    </el-form-item>
    <el-form-item label="备注">
        <el-input v-model="form.remark" ></el-input>
    </el-form-item>
    
</el-form>
    <div slot="footer" class="dialog-footer">
    <el-button @click="dialogFormVisible = false">取 消</el-button>
    <el-button type="primary" @click="SaveChange ">确 定</el-button>
  </div>
  </el-dialog>
  <!-- 添加二级分类 -->
  <el-dialog title="新增分类信息" :visible.sync="dialogFormVisibleCategory" style="width: 100%;" >
      <el-form :inline="true" :model="formCategory" label-width="150px" :rules="rules" ref="ruleFrom">
      <el-form-item label="分类名称" prop="name">
        <el-input v-model="formCategory.name" placeholder="请输入分类名称"></el-input>
      </el-form-item>
      <el-form-item label="分类备注" prop="remark">
        <el-input v-model="formCategory.remark" placeholder="请输入分类备注"></el-input>
      </el-form-item>
     
    
      
  </el-form>
  
  <div slot="footer" class="dialog-footer">
    <el-button @click="dialogFormVisibleCategory=false">取 消</el-button>
    <el-button type="primary" @click="CategoryADD() ">确 定</el-button>
  </div>
</el-dialog>

 

  </div>

</template>

<script>
import request from '@/ultis/request';
import { del } from 'vue';

export default {
  name: 'Category',
  data(){
    return{
      formCategory:{},
      FormEdit:{},
      dialogFormVisibleCategory:false,
      dialogFormVisible:false,
      dialogFormVisibleAdd:false,
      dialogTableVisibleDeatil:false,
      formAdd: {},
      form:{},
      gridData:[],
      tableDate:[],
      total:0,
      pid:null,
      params:{
  pageNum:1,
pageSize:5,
name:'',
phone:''
      },
      rules: {
        name: [{ required: true, message: "请输入分类的名称", trigger: "blur" },
          {min:3,max:10,message:'长度在3-10个字符',trigger:"blur"}
        ],
     
      }
    }
  },
  created(){
this.load();

  },
  methods:{
    CategoryAdd(row){
     this.pid=row.id
     this.dialogFormVisibleCategory=true
    },
    
    CategoryADD(){
      this.$refs['ruleFrom'].validate(valid => {

        if (valid) {

        this.formCategory.pid=this.pid
 
        
          request.post("/category/save", this.formCategory).then(res => {
            if (res.code == "200") {
              this.$notify.success("新增二级分类成功");
              this.formCategory={}
              
              this.dialogFormVisibleCategory=false
//清空表单
this.load()
            //   this.$refs[formName].resetFields();
             
            } else {
              this.$notify.error(res.msg);
              return false;
            }
          });
        }
      });
    },
    SaveChange(){
  this.$refs['ruleFrom'].validate(valid => {
            if (valid) {
        request.put('/category/update',this.form).then(res=>
            {
                if (res.code=='200') {
                    this.$notify.success('更新成功')
                  this.dialogFormVisible=false
                  this.load()
                }else{
                    this.$notify.error(res.msg);
                }
            }
        )
    }
});
},
    SaveAdd(){
      this.$refs['ruleFrom'].validate(valid => {
        if (valid) {
          request.post("/category/save", this.formAdd).then(res => {
            if (res.code == "200") {
              this.$notify.success("新增成功");
             this.load();
//清空表单
            //   this.$refs[formName].resetFields();
             this.dialogFormVisibleAdd=false
            } else {
              this.$notify.error(res.msg);
              return false;
            }
          });
        }
      });
    },
    EidtUser(row){
      const id= row.id
request.get("/category/"+id).then(res=>{
    this.form=res.data
})
this.dialogFormVisible=true
    },
    DetailUser(row){
      this.dialogTableVisibleDeatil=true
      request.get("/category/listID/"+row.id).then(res=>{
   this.gridData=res.data
})
    },
    AddUser(){
this.dialogFormVisibleAdd=true
    },
load(){
request.get('/category/page',{params:this.params}).then(res=>{
  console.log(res);
  if(res.code=='200'){
  console.log(res.data);
  
    this.tableDate= res.data.list
this.total=res.data.total

  }

})
},
reset(){
  this.params={
    pageNum:1,
pageSize:5,
name:'',
phone:''
  }
  this.load()
},
handelCurrentChange(pageNum){
 this.params.pageNum=pageNum


this.load()
},

del(id){
    request.delete('category/delete/'+id).then(res=>{
      if (res.code=="200") {
        this.$notify.success("删除成功")
        this.load()
      }else{
        this.$notify.error(res.msg)
      }
    })
  }

},

  }




</script>
