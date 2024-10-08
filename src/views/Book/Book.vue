<style>
.el-input--small .el-input__inner {
    height: 32px;
    line-height: 32px;
    width: 217px;
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
.el-dialog{
  width: 1200px;
}
.avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
<template>
  <div>
    <div style="margin-bottom: 20px;">
      <el-input style="width: 240px;" placeholder="请输入图书名称" v-model="params.name"></el-input>
      <el-input style="width: 240px;" placeholder="请输入图书标准码" v-model="params.bookNo"></el-input>
      <el-button type="primary" style="margin-left: 5px;" @click="load">
        <i class="el-icon-search"></i>
        搜索
      </el-button>
      <el-button type="warning" style="margin-left: 5px;" @click="reset">
        <i class="el-icon-refresh"></i>
        重置
      </el-button>
    </div>
    <el-button type="warning" style="margin-left: 5px;" @click="AddBook">
      <i class="el-icon-circle-plus-outline"></i>
      图书添加
    </el-button>
    <!-- 数据展示 -->
    <el-table :data="tableDate" stripe style="width: 100%;" row-key="id">
      <el-table-column label="编号" prop="id"></el-table-column>
        <el-table-column label="图书名称" prop="name"></el-table-column>
        <el-table-column label="描述" prop="description"></el-table-column>
        <el-table-column label="出版日期" prop="publishDate"></el-table-column>
        <el-table-column label="作者" prop="author"></el-table-column>
        <el-table-column label="出版社" prop="publisher"></el-table-column>
        <el-table-column label="图书类型" prop="category"></el-table-column>
        <!-- <el-table-column label="标准码" prop="bookNo"></el-table-column> -->
        <el-table-column label="封面" prop="cover">
         <template v-slot="scope">
<el-image :src="scope.row.cover" :preview-src-list="[scope.row.cover]"></el-image>
         </template>
        </el-table-column>

      <el-table-column label="操作" width="370px">
        <template v-slot="scope">
          <el-button type="primary" @click="BookEidt(scope.row)">
            <i class="el-icon-edit-outline"></i>编辑
          </el-button>
          <el-button type="primary" @click="BookDetail(scope.row)">
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
    <el-dialog title="图书新增" :visible.sync="dialogFormVisibleAdd" style="width: 100%;">
      <el-form :inline="true" :model="formAdd" label-width="90px" :rules="rules" ref="ruleFrom">
        <el-form-item label="图书名称" prop="name">
          <el-input v-model="formAdd.name" placeholder="请输入图书名称"></el-input>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input
            style="width: 400px;"
            v-model="formAdd.description"
            type="textarea"
            placeholder="请输入图书名称"
          ></el-input>
        </el-form-item>
        <el-form-item label="出版日期" prop="publishDate">
          <el-date-picker
            v-model="formAdd.publishDate"
            type="datetime"
            value-format="yyyy-MM-dd"
            placeholder="选择日期时间"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="作者" prop="author">
          <el-input v-model="formAdd.author" placeholder="请输入作者名称"></el-input>
        </el-form-item>
        <el-form-item label="出版社名称" prop="publisher">
          <el-input v-model="formAdd.publisher" placeholder="请输入出版社名称"></el-input>
        </el-form-item>
        <el-form-item label="分类" prop="category">
          <el-cascader
          :props="{value:'name',label:'name'}"
    v-model="formAdd.categories"
    :options="categories"
   ></el-cascader>
        </el-form-item>
        <el-form-item label="标准码" prop="bookNo">
          <el-input v-model="formAdd.bookNo" placeholder="请输入图书的标准码"></el-input>
        </el-form-item>
        <el-form-item label="封面" prop="cover">
          <el-upload
  class="avatar-uploader"
  :action="'http://localhost:8090/api/book/file/upload?token=' +this.admin.token"
  :show-file-list="false"
  :on-success="handleCoverSuccess"
  >
  <img v-if="formAdd.cover" :src="formAdd.cover" class="avatar">
  <i v-else class="el-icon-plus avatar-uploader-icon"></i>
</el-upload>
          </el-form-item>
          <el-form-item label="借书积分" prop="score">
            <el-input-number v-model="formAdd.score" :min="10" :max="30" label="所需积分" style="width: 225px;"></el-input-number>

        </el-form-item>
        <el-form-item label="书籍数量" prop="nums">
          <el-input v-model="formAdd.nums" placeholder="请输入书籍数量"></el-input>

        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisibleAdd=false">取 消</el-button>
        <el-button type="primary" @click="SaveAdd ">确 定</el-button>
      </div>
    </el-dialog>
    <!-- 详情弹窗 -->
    <el-dialog title="会员详情" :visible.sync="dialogTableVisibleDeatil" >
      <el-table :data="gridData" stripe >
        <el-table-column label="编号" prop="id"></el-table-column>
        <el-table-column label="图书名称" prop="name"></el-table-column>
        <el-table-column label="描述" prop="description"></el-table-column>
        <el-table-column label="出版日期" prop="publishDate"></el-table-column>
        <el-table-column label="作者" prop="author"></el-table-column>
        <el-table-column label="出版社" prop="publisher"></el-table-column>
        <el-table-column label="图书类型" prop="category"></el-table-column>
        <el-table-column label="标准码" prop="bookNo"></el-table-column>
        <el-table-column label="封面" prop="cover">
          <template v-slot="scope" >
            <el-image :src="scope.row.cover" :preview-src-list="[scope.row.cover]">
              </el-image>
          </template>
          
        </el-table-column>
        <el-table-column label="借书积分" prop="score"></el-table-column>
        <el-table-column label="书籍数量" prop="nums"></el-table-column>
        <el-table-column label="创建时间" prop="createtime"></el-table-column>
        <el-table-column label="修改时间" prop="updatetime"></el-table-column>
      </el-table>
    </el-dialog>
    <!-- 编辑图书信息 -->
    <el-dialog title="编辑图书信息" :visible.sync="dialogFormVisible" style="width: 100%;">
      <el-form :inline="true" :model="form" label-width="100px" :rules="rules" ref="ruleFrom">
        <el-form-item label="图书名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入图书名称"></el-input>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input
            style="width: 400px;"
            v-model="form.description"
            type="textarea"
            placeholder="请输入图书名称"
          ></el-input>
        </el-form-item>
        <el-form-item label="出版日期" prop="publishDate">
          <el-date-picker
            v-model="form.publishDate"
            type="datetime"
            value-format="yyyy-MM-dd"
            placeholder="选择日期时间"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="作者" prop="author">
          <el-input v-model="form.author" placeholder="请输入作者名称"></el-input>
        </el-form-item>
        <el-form-item label="出版社名称" prop="publisher">
          <el-input v-model="form.publisher" placeholder="请输入出版社名称"></el-input>
        </el-form-item>
        <el-form-item label="分类" prop="category">
          <el-cascader
          :props="{value:'name',label:'name'}"
    v-model="form.categories"
    :options="categories"
   ></el-cascader>
        </el-form-item>
        <el-form-item label="标准码" prop="bookNo">
          <el-input v-model="form.bookNo" placeholder="请输入图书的标准码"></el-input>
        </el-form-item>
        <el-form-item label="封面" prop="cover">
          <el-upload
  class="avatar-uploader"
 :action="'http://localhost:8090/api/book/file/upload?token=' +this.admin.token"
  :show-file-list="false"
  :on-success="handleCoverSuccessEdit"
  >
  <img v-if="form.cover" :src="form.cover" class="avatar">
  <i v-else class="el-icon-plus avatar-uploader-icon"></i>
</el-upload>
        </el-form-item>
        <el-form-item label="借书积分" prop="score">
          
          <el-input-number v-model="form.score" :min="10" :max="30" label="所需积分" style="width: 225px;"></el-input-number>
        </el-form-item>
        <el-form-item label="书籍数量" prop="nums">
          <el-input v-model="form.nums" placeholder="请输入书籍数量"></el-input>

        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="SaveChange ">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/ultis/request";
import { del } from "vue";
import Cookies  from "js-cookie";
export default {
  name: "book",
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
    return {
      admin: Cookies.get("admin") ? JSON.parse(Cookies.get("admin")) : {},
      formCategory: {},
      FormEdit: {},
      dialogFormVisibleCategory: false,
      dialogFormVisible: false,
      dialogFormVisibleAdd: false,
      dialogTableVisibleDeatil: false,
      formAdd: {score:10,cover:''},
      categories:[],  
      form: {},
      gridData: [],
      tableDate: [],
      total: 0,
      pid: null,
    
      params: {
        pageNum: 1,
        pageSize: 5,
        name: "",
        bookNo: ""
      },
   
      rules: {
        name: [
          { required: true, message: "请输入图书的名称", trigger: "blur" },
          { min: 3, max: 10, message: "长度在3-10个字符", trigger: "blur" }
        ],
        bookNo: [
          { required: true, message: "请输入图书的标准码", trigger: "blur" },
         
        ],
        score: [
          { required: true, message: "请输入图书的积分", trigger: "blur" },
         
        ],
        nums:[{validator:checknums, trigger:"blur"} ]
      }
    };
  },
  created() {
    this.load();
    request.get("/category/tree").then(res=>{
      this.categories=res.data
  
  });
},
  methods: {
    handleCoverSuccessEdit(res){
     
      if(res.code=='200'){
 
        this.form.cover=res.data
 
}
    },
    handleCoverSuccess(res){
if(res.code=='200'){
  this.formAdd.cover=res.data
  console.log(this.formAdd.cover);
}
    },
       


  
    SaveChange() {
      this.$refs["ruleFrom"].validate(valid => {
        if (valid) {
          request.put("/book/update", this.form).then(res => {
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
          
          
          request.post("/book/save", this.formAdd).then(res => {
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
    BookEidt(row) {
      const id = row.id;
     
      request.get("/book/" + id).then(res => {
        this.form = res.data;
     
        this.form.categories=this.form.category.split('>')
        console.log(this.form.categories);
        
      });
      this.dialogFormVisible = true;
    },
    BookDetail(row) {
      this.dialogTableVisibleDeatil = true;
      request.get("/book/listID/" + row.id).then(res => {
        this.gridData = res.data;

      });
    },
    AddBook() {
      request.get("/category/tree").then(res=>{
      this.categories=res.data
    })
      this.dialogFormVisibleAdd = true;
    },
    load() {
      request.get("/book/page", { params: this.params }).then(res => {
        if (res.code == "200") {
          console.log(res.data);
          this.tableDate = res.data.list;
          
          this.total=res.data.total
        }
      });
    },
    reset() {
      this.params = {
        pageNum: 1,
        pageSize:5,
        name: ""
      };
      this.load();
    },
    handelCurrentChange(pageNum) {
      this.params.pageNum = pageNum;

      this.load();
    },

    del(id) {
      request.delete("book/delete/" + id).then(res => {
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
</script>
