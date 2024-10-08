<template>
  <div>
    <div style="margin-bottom: 20px;">
      <el-input style="width: 240px;" placeholder="请输入用户名" v-model="params.username"></el-input>
      <el-input
        style="width: 240px; margin-left: 5px;"
        placeholder="请输入联系方式"
        v-model="params.phone"
      ></el-input>
      <el-input style="width: 240px; margin-left: 5px;" placeholder="请输入邮箱" v-model="params.email"></el-input>
      <el-button type="primary" style="margin-left: 5px;" @click="load">
        <i class="el-icon-search"></i>
        搜索
      </el-button>
      <el-button type="warning" style="margin-left: 5px;" @click="reset">
        <i class="el-icon-refresh"></i>
        重置
      </el-button>
    </div>
    <div style="margin-bottom: 20px;">
      <el-button type="warning" style="margin-left: 5px;" @click="AddAdmin">
        <i class="el-icon-circle-plus-outline"></i>
        管理员添加
      </el-button>
    </div>
    <!-- 展示数据 -->
    <div>
    <el-table :data="tableDate" stripe style="width: 100%;">
      <el-table-column label="编号" prop="id"></el-table-column>
      <el-table-column label="用户名" prop="username"></el-table-column>
      <el-table-column label="邮箱" prop="email"></el-table-column>
      <el-table-column label="创建时间" prop="createtime"></el-table-column>
      <el-table-column label="修改时间" prop="updatetime"></el-table-column>
      <el-table-column label="联系方式" prop="phone"></el-table-column>
      <el-table-column label="状态">
        <template v-slot="scope">
          <el-switch v-model="scope.row.status" @change="ChangeStatus(scope.row)" active-color="#13ce66" inactive-color="#ff4949"></el-switch>
        </template>
    </el-table-column>
   

       <el-table-column label="操作" width="430px">
        <template v-slot="scope">
      <el-button type="primary" @click="EditAdmin(scope.row)">
    <i class="el-icon-edit-outline"></i>编辑
   </el-button>
  <el-button type="primary" @click="DetailAdmin(scope.row)">
    <i class="el-icon-message"></i>详情
  </el-button>
  <el-popconfirm style="margin-left: 5px;" title="你确定删除这行数据吗？" @confirm="del(scope.row.id)">
    <el-button slot="reference" type="danger">
      <i class="el-icon-delete-solid"></i>删除
    </el-button>
  </el-popconfirm>
  <el-button style="margin-left: 5px;" type="warning" @click="handleChangePass(scope.row)">
    <i class="el-icon-delete-solid"></i>忘记密码
  </el-button>
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
    <!-- 新增弹窗 -->
    <el-dialog title="新增管理员" :visible.sync="dialogFormVisibleAdd" style="width: 100%;" >
      <el-form :inline="true" :model="formAdd" label-width="100px" :rules="rulesAdd" ref="ruleFrom">
    
      <el-form-item label="用户名" prop="username">
        <el-input v-model="formAdd.username" placeholder="请输入用户名"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input v-model="formAdd.password" placeholder="请输入密码 "></el-input>
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="formAdd.email" placeholder="请输入邮箱"></el-input>
      </el-form-item>
      
      <el-form-item label="联系方式" prop="phone">
        <el-input v-model="formAdd.phone" placeholder="请输入联系方式"></el-input>
      </el-form-item>
  </el-form>
  <div slot="footer" class="dialog-footer">
    <el-button @click="dialogFormVisibleAdd=false">取 消</el-button>
    <el-button type="primary" @click="SaveAdd ">确 定</el-button>
  </div>
  </el-dialog>
    <!-- 详情弹窗 -->
    <el-dialog title="管理员详情" :visible.sync="dialogTableVisible" >
      
  <el-table :data="gridData" style="width: 100%;">
    <el-table-column label="编号" prop="id" ></el-table-column>
   <el-table-column label="用户名" prop="username" width="200px"></el-table-column>
   <el-table-column label="密码" prop="password" width="200px"></el-table-column>
   <el-table-column label="联系方式" prop="phone" ></el-table-column>
   <el-table-column label="创建时间" prop="createtime" ></el-table-column>
   <el-table-column label="修改时间" prop="updatetime" ></el-table-column>
   <el-table-column label="管理员当前状态" prop="status" :formatter="formatStatus"></el-table-column>
  </el-table>
</el-dialog>
<!-- 修改管理员信息弹窗 -->
<el-dialog  title="编辑管理员信息" :visible.sync="dialogFormVisible" style="width: 100%;">
 
  <el-form :inline="true" :model="FormEdit" label-width="100px" :rules="rulesAdd" ref="ruleFromEdit">
    <el-form-item label="用户名" prop="username">
        <el-input v-model="FormEdit.username" placeholder="请输入用户名" ></el-input>
    </el-form-item>
    <el-form-item label="密码">
        <el-input v-model="FormEdit.password" placeholder="请输入密码" show-password></el-input>
    </el-form-item>
    <el-form-item label="联系方式" prop="phone">
        <el-input v-model="FormEdit.phone" placeholder="请输入联系方式" ></el-input>
    </el-form-item>
    <el-form-item label="邮箱" prop="email">
        <el-input v-model="FormEdit.email" placeholder="请输入邮箱"></el-input>
    </el-form-item>
   
    </el-form>
    <div slot="footer" class="dialog-footer">
    <el-button @click="dialogFormVisible = false">取 消</el-button>
    <el-button type="primary" @click="SaveChange ">确 定</el-button>
  </div>
  </el-dialog>
  <!-- 重置密码 -->
  <el-dialog title="重置密码" :visible.sync="dialogFormVisiblereset" style="width: 100%;">
 
  <el-form :inline="true" :model="FormRest" label-width="100px" :rules="rulesAdd" ref="ruleFromEdit">
    <el-form-item label="用户名" prop="username">
        <el-input v-model="FormRest.username" placeholder="请输入用户名" ></el-input>
    </el-form-item>
    
    <el-form-item label="联系方式" prop="phone">
        <el-input v-model="FormRest.phone" placeholder="请输入联系方式" ></el-input>
    </el-form-item>

   
    </el-form>
    <div slot="footer" class="dialog-footer">
    <el-button @click="dialogFormVisiblereset = false">取 消</el-button>
    <el-button type="primary" @click="RestSave ">确 定</el-button>
  </div>
  </el-dialog>
</div>
</div>

</template>

<script>
import request from "@/ultis/request";
import { del } from "vue";
import Cookies from "js-cookie";
export default {
  name: "ListAdmin",
  data() {
    const checkPhone = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("联系方式不能为空"));
      }

      if (!/^[1][3,4,5,6,7,8,9][0-9]{9}$/.test(value)) {
        callback(new Error("请输入合法的手机号"));
      }

      callback();
    };
    const checkemail = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("邮箱方式不能为空"));
      }

      if (!/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/.test(value)) {
        callback(new Error("请输入合法的邮箱号"));
      }

      callback();
    };
    return {
      formAdd: {},
      rulesAdd: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          { min: 3, max: 10, trigger: "blur", message: "长度在3-10之间" }
        ],
        password:[{ required: true, message: "请输入密码", trigger: "blur" },
        { min: 3, max: 10, trigger: "blur", message: "长度在3-10之间" }],
        email: [{ validator: checkemail, trigger: "blur" }],
        phone: [{ validator: checkPhone, trigger: "blur" }]
      },
      admin: Cookies.get("admin") ? JSON.parse(Cookies.get("admin")) : {},
      dialogFormVisiblereset: false,
      dialogFormVisible: false,
      dialogTableVisible: false,
      dialogFormVisibleAdd: false,

      tableDate: [],
      FormEdit: {},
      FormRest: {},
      gridData: [{}],
      total: 0,
      Restid: 0,
      params: {
        pageNum: 1,
        pageSize: 5,
        name: "",
        phone: ""
      },
      rules: {
        newPass: [
          { required: true, message: "请输入新密码", trigger: "blur" },
          { min: 3, max: 10, trigger: "blur", message: "长度在3-10之间" }
        ]
      }
    };
  },
  created() {
    this.load();
  },
  methods: {
    formatStatus(row){
 
 return row.status === true ? "已开启" : row.status === false ? "已禁用" : "未知状态";
    },
    ChangeStatus(row){
      const ID=row.id
      
      const adminID=this.admin.id
              if (ID == adminID) {
                row.status=true
                
                this.$notify.warning("不能禁用当前用户");
              } else if(ID != adminID) {
               request.put("/Admin/update", row).then(res => {
            console.log(res.code);
            if (res.code == "200") {
              this.$notify.success("操作成功");
              this.load();
              }
            }
          )}else {
              this.$notify.error("修改失败");
              this.dialogFormVisible = false;
            }
       
    },
    handleChangePass(row) {
      const id = row.id;
      this.Restid = id;

      this.dialogFormVisiblereset = true;
    },
    RestSave() {
      request.put("/Admin/resetpassword", this.FormRest).then(res => {
        if (res.code == "200" && this.Restid != this.admin.id) {
          console.log(this.Restid);
          this.$notify.success("重置密码成功,初识密码为123");
          this.dialogFormVisiblereset = false;
          this.FormRest = {};
        } else if (res.code == "200" && this.Restid == this.admin.id) {
          //当前修改的用户ID等于当前登录的用户ID ，用户需要重新登录
          Cookies.remove("admin");
          this.$notify.success("重置密码为123,请重新登录");
          this.dialogFormVisiblereset = false;
          this.$router.push("/login");
        } else {
          this.$notify.error("用户名或手机号输入错误");
        }
      });
    },
    EditAdmin(row) {
      request.get("/Admin/" + row.id).then(res => {
        if (res.code == "200") {
          this.FormEdit = res.data;
          this.dialogFormVisible = true;
        } else {
          this.$notify.error(res.msg);
        }
      });
    },
    DetailAdmin(row) {
      request.get("/Admin/listID/" + row.id).then(res => {
        if (res.code == "200") {
          this.dialogTableVisible = true;

          this.gridData = res.data;
          console.log(this.gridData);
        } else {
          this.$notify.error(res.msg);
        }
      });
    },
    SaveAdd() {
      this.$refs["ruleFrom"].validate(valid => {
        if (valid) {
          request.post("/Admin/save", this.formAdd).then(res => {
            if (res.code == "200") {
              this.$notify.success("新增成功");
              this.formAdd = {};
              //清空表单
              this.dialogFormVisibleAdd = false;
              this.load();
              //   this.$refs[formName].resetFields();
            } else {
              this.$notify.error(res.msg);
              return false;
            }
          });
        }
      });
    },
    AddAdmin() {
      this.dialogFormVisibleAdd = true;
    },
    handleChangeAdmin(row) {
      this.form = JSON.parse(JSON.stringify(row));
      this.dialogFormVisible = true;
    },
    load() {
      //   fetch('http://localhost:8090/user/list').then(res=>res.json()).then(res=>{
      //     console.log(res);
      //     this.tableDate=res;
      //   })
      request.get("/Admin/page", { params: this.params }).then(res => {
        if (res.code == "200") {
          this.tableDate = res.data.list;
          this.total = res.data.total;
        }
      });
    },
    reset() {
      this.params = {
        pageNum: 1,
        pageSize: 5,
        name: "",
        phone: "",
        email:""
      };
      this.load();
    },
    handelCurrentChange(pageNum) {
      this.params.pageNum = pageNum;

      this.load();
    },
    formatSex(row) {
      return row.sex === "0" ? "男" : row.sex === "1" ? "女" : "未填写";
    },
    del(id) {
      const ID = id;
      const adminID = this.admin.id;

      if (ID == adminID) {
        this.$notify.error("禁止删除当前登录的账号");
      } else if (ID != adminID) {
        request.delete("Admin/delete/" + ID).then(res => {
          if (res.code == "200") {
            this.$notify.success("删除成功");
            this.load();
          } else {
            this.$notify.error(res.msg);
          }
        });
      }
    },
    SaveChange() {
      this.$refs["ruleFromEdit"].validate(valid => {
        if (valid) {
          request.put("/Admin/update", this.FormEdit).then(res => {
            console.log(res.code);
            if (res.code == "200") {
              this.load();
              this.$notify.success("数据更新成功");
              this.dialogFormVisible = false;
              if (this.FormEdit.id == this.admin.id) {
                //当前修改的用户ID等于当前登录的用户ID ，用户需要重新登录
                Cookies.remove("admin");
                this.$notify.success("修改成功请重新登录");
              } else {
                this.load();
                this.dialogFormVisible = false;
              }
            } else {
              this.$notify.error(res.msg);
              this.dialogFormVisible = false;
            }
          });
        }
      });
    }
  }
};
</script>
