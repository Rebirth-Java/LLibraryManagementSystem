<style>
.el-input--small .el-input__inner {
  height: 32px;
  line-height: 32px;
  width: 180px;
  margin-left: 2px;
}

.el-form-item .is-error .el-form-item--small {
  margin-left: -59px;
}
.el-form-item--mini.el-form-item,
.el-form-item--small.el-form-item {
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
  color: #c0c4cc;
  font-size: 14px;
  transition: transform 0.3s;
  transform: rotateZ(180deg);
  cursor: pointer;
}
.el-dialog {
  width: 62%;
}
</style>
<template>
  <div>
    <div style="margin-bottom: 40px; ">
      <el-input
        style="width: 40px;
     margin-right: 160px"
        placeholder="请输入图书名称"
        v-model="params.bookName"
      ></el-input>
      <el-input
        style="width: 40px;
         margin-right: 160px"
        placeholder="请输入图书标准码"
        v-model="params.bookNo"
      ></el-input>
      <el-input
        style="width:10px;
      margin-right: 180px"
        placeholder="请输入用户名称"
        v-model="params.userName"
      ></el-input>
      <el-button type="primary"  @click="load">
        <i class="el-icon-search"></i>
        搜索
      </el-button>
      <el-button type="warning"  @click="reset">
        <i class="el-icon-refresh"></i>
        重置
      </el-button>
    </div>
    <el-button type="warning" style="margin-left: 5px;" @click="AddBorrow">
      <i class="el-icon-circle-plus-outline"></i>
      新增借书记录
    </el-button>
    <!-- 数据展示 -->
    <el-table :data="tableDate" stripe style="width: 100%;" row-key="id">
      <el-table-column label="编号" prop="id"></el-table-column>
      <el-table-column label="图书名称" prop="bookName"></el-table-column>
      <el-table-column label="图书标准码" prop="bookNo" ></el-table-column>
     
      <el-table-column label="用户名称" prop="userName"></el-table-column>
      <el-table-column label="用户联系方式" prop="userPhone"></el-table-column>
      <el-table-column label="所用积分" prop="score"></el-table-column>
      <el-table-column label="剩余书籍" prop="nums"></el-table-column>
      
      <el-table-column label="借出状态" prop="status"></el-table-column>
      <el-table-column label="借出天数" prop="days"></el-table-column>
      <el-table-column label="提醒状态" prop="note">
        <template v-slot="scope">
<el-tag type="success" v-if="scope.row.note=='正常'">{{ scope.row.note }}</el-tag>
<el-tag type="primary"  v-if="scope.row.note=='即将到期'">{{ scope.row.note }}</el-tag>
<el-tag type="warning" v-if="scope.row.note=='已到期'">{{ scope.row.note }}</el-tag>
<el-tag type="danger" v-if="scope.row.note=='已过期'">{{ scope.row.note }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="管理">
        <template v-slot="scope">
        <el-button type="primary" @click="reterunBooks(scope.row)" v-if="scope.row.status=='已借出'">还书</el-button>
      </template>
      </el-table-column>
     
      <el-table-column label="操作"  width="180px" >
        <template v-slot="scope">
         
          <el-button type="primary" @click="BorrowDetail(scope.row)">
            <i class="el-icon-message"></i>详情
          </el-button>
          <el-popconfirm style="margin-left: 5px;" title="你确定删除这行数据吗？" @confirm="del(scope.row.id)">
            <el-button slot="reference" type="danger">
              <i class="el-icon-delete-solid"></i>删除
            </el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div>
      <!-- 分页 -->
      <el-pagination
        style="margin-top: 20px;"
        background
        :page-size="params.pageSize"
        :current-page="params.pageNum"
        layout="prev, pager, next"
        @current-change="handelCurrentChange"
        :total="total"
      ></el-pagination>
    </div>
    <!-- 添加书籍图书 -->
    <el-dialog title="新增借书记录" :visible.sync="dialogFormVisibleAdd" style="width: 100%;">
      <el-form :inline="true" :model="formAdd" label-width="96px" :rules="rules" ref="ruleFrom">
        <el-form-item label="图书标准码" prop="bookNo">
          <el-select v-model="formAdd.bookNo" filterable placeholder="请选择" @change="selBook">
            <el-option
              v-for="item in books"
              :key="item.id"
              :label="item.bookNo"
              :value="item.bookNo"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="图书名称" prop="bookName">
          <el-input v-model="formAdd.bookName" disabled></el-input>
        </el-form-item>

        <el-form-item label="会员码" prop="userNo">
          <el-select v-model="formAdd.userNo" filterable placeholder="请选择" @change="selUserID">
            <el-option
              v-for="item in users"
              :key="item.id"
              :label="item.username"
              :value="item.username"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="用户名称" prop="userName">
          <el-input v-model="formAdd.userName" disabled></el-input>
        </el-form-item>
        <el-form-item label="用户联系方式" prop="userPhone">
          <el-input v-model="formAdd.userPhone" disabled></el-input>
        </el-form-item>
        <el-form-item label="借书积分" prop="score">
          <el-input v-model="formAdd.score" disabled></el-input>
        </el-form-item>
        <el-form-item label="用户剩余积分" prop="account">
          <el-input v-model="formAdd.account" disabled></el-input>
        </el-form-item>
        <el-form-item label="剩余数量" prop="nums">
          <el-input v-model="formAdd.nums" disabled></el-input>
        </el-form-item>
        <el-form-item label="借出天数" prop="days">
          <el-input-number v-model="formAdd.days" :min="1" :max="30" label="借出天数" style="width: 183px;"></el-input-number>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisibleAdd=false">取 消</el-button>
        <el-button type="primary" @click="SaveAdd ">确 定</el-button>
      </div>
    </el-dialog>
    <!-- 详情弹窗 -->
    <el-dialog title="会员详情" :visible.sync="dialogTableVisibleDeatil">
      <el-table :data="gridData" stripe>
        <el-table-column label="编号" prop="id"></el-table-column>
      <el-table-column label="图书名称" prop="bookName"></el-table-column>
      <el-table-column label="图书标准码" prop="bookNo"></el-table-column>
      <el-table-column label="用户会员码" prop="userNo"  width="150px"></el-table-column>
      <el-table-column label="用户名称" prop="userName"></el-table-column>
      <el-table-column label="用户联系方式" prop="userPhone"  width="100px"></el-table-column>
      <el-table-column label="借书积分" prop="score"></el-table-column>
      <el-table-column label="书籍数量" prop="nums"></el-table-column>
      <el-table-column label="借出时间" prop="createtime"></el-table-column>
      <el-table-column label="归还日期" prop="returnDate"></el-table-column>
      </el-table>
    </el-dialog>
   
  </div>
</template>

<script>
import request from "@/ultis/request";
import { del } from "vue";

export default {
  name: "Borrow",
  data() {
    const checknums = (rule, value, callback) => {
      value=parseInt(value)
      if (value==null||value==undefined||value>9999) {
        callback(new Error('请输入0-9999的数字'));
        }
        if (!Number.isInteger(value) ||value<0) {
          callback(new Error('请输入大于等于0的整数'));
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
      users: [],
      books: [],
      formCategory: {},
      FormEdit: {},
      dialogFormVisibleCategory: false,
      dialogFormVisible: false,
      dialogFormVisibleAdd: false,
      dialogTableVisibleDeatil: false,
      formAdd: {days:1},
      categories: [],
      form: {},
      gridData: [],
      tableDate: [],
      total: 0,
      pid: null,

      params: {
        pageNum: 1,
        pageSize: 5,
        name: "",
        bookName: "",
        bookNo: "",
        
        userName: ""
      },

      rules: {
        name: [
          { required: true, message: "请输入图书的名称", trigger: "blur" },
          { min: 3, max: 10, message: "长度在3-10个字符", trigger: "blur" }
        ],
        nums:[{validator:checknums, trigger:"blur"} ],
        score:[{validator:checknums, trigger:"blur"}],
        userPhone:[{validator:checkPhone, trigger:"blur"}]
      }
    };
  },
  created() {
   
    request.get("/book/list").then(res => {
      this.books = res.data;
    });
    request.get("/user/list").then(res => {
      this.users = res.data.filter(v=>v.status);
    });

    this.load();
  },
  methods: {
    reterunBooks(row){
      console.log(row);
      
request.post("/Borrow/saveRetur",row).then(res=>{
  console.log(row);
  
if(res.code=='200'){
  this.$notify.success("还书成功")
  this.load()
}else{
  this.$notify.error(res.msg)
}
});
},
    
    selUserID() {
      const user = this.users.find(v => v.username == this.formAdd.userNo);
      request.get("/user/"+user.id).then(res => {
        this.$set(this.formAdd,'userName',res.data.name)
        
      this.formAdd.userPhone = res.data.phone;
     this.formAdd.account=res.data.account;
    });
    },
    selBook() {
      const book = this.books.find(v => v.bookNo == this.formAdd.bookNo);
      request.get("/book/"+book.id).then(res => {
     this.$set(this.formAdd,'bookName',res.data.name)
  this.formAdd.score = res.data.score;
  this.formAdd.nums=res.data.nums
});
   
      
     
    },
    SaveChange() {
      this.$refs["ruleFrom"].validate(valid => {
        if (valid) {
          request.put("/Borrow/update", this.form).then(res => {
            if (res.code == "200") {
              this.$notify.success("更新成功");
              this.dialogFormVisible = false;
              this.load();
            } else {
              this.$notify.error(res.msg);
            }
          });
        }
      });
    },
    SaveAdd() {
      this.$refs["ruleFrom"].validate(valid => {
        if (valid) {
         
          
          request.post("/Borrow/save", this.formAdd).then(res => {
            if (res.code == "200") {
              this.$notify.success("新增成功");
              this.load();
              this.formAdd = {};

              this.dialogFormVisibleAdd = false;
            } else {
              this.$notify.error(res.msg);
              return false;
            }
          });
        }
      });
    },
    BorrowEidt(row) {
      const id = row.id;

      request.get("/Borrow/" + id).then(res => {
        this.users=res.data.filter(v=>v.status)
        this.form = res.data;


      });
      this.dialogFormVisible = true;
    },
    BorrowDetail(row) {
      this.dialogTableVisibleDeatil = true;
      request.get("/Borrow/listID/" + row.id).then(res => {
        this.gridData = res.data;
      });
    },
    AddBorrow() {
    
      this.dialogFormVisibleAdd = true;
    },
    load() {
      request.get("/Borrow/page", { params: this.params }).then(res => {
        if (res.code == "200") {
       
          this.tableDate = res.data.list;
          this.total = res.data.total;
         console.log(this.params);
         
          
        }
      });
    },
    reset() {
      this.params = {
        pageNum: 1,
        pageSize: 5,
        name: ""
      };
      this.load();
    },
    handelCurrentChange(pageNum) {
      this.params.pageNum = pageNum;

      this.load();
    },

    del(id) {
      request.delete("Borrow/delete/" + id).then(res => {
        if (res.code == "200") {
          this.$notify.success("删除成功");
          this.load();
        } else {
          this.$notify.error(res.msg);
        }
      });
    }
  }
};
</script>./Borrow.vue
