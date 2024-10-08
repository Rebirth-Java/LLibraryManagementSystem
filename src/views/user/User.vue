<style>
.el-input--small .el-input__inner {
  height: 32px;
  line-height: 32px;
  width: 200px;
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
</style>
<template>
  <div>
    <div style="margin-bottom: 20px;">
      <el-input style="width: 240px;" placeholder="请输入名称" v-model="params.name"></el-input>
      <el-input
        style="width: 240px; margin-left: 5px;"
        placeholder="请输入联系方式"
        v-model="params.phone"
      ></el-input>
      <el-button type="primary" style="margin-left: 5px;" @click="load">
        <i class="el-icon-search"></i>
        搜索
      </el-button>
      <el-button type="warning" style="margin-left: 5px;" @click="reset">
        <i class="el-icon-refresh"></i>
        重置
      </el-button>
    </div>
    <el-button type="warning" style="margin-left: 5px;" @click="AddUser">
      <i class="el-icon-circle-plus-outline"></i>
      会员添加
    </el-button>
    <el-table :data="tableDate" stripe style="width: 100%;">
      <el-table-column label="编号" prop="id" width="150px"></el-table-column>
      <el-table-column label="姓名" prop="name" width="180px"></el-table-column>
      <el-table-column label="年龄" prop="age" width="180px"></el-table-column>
      <el-table-column label="性别" prop="sex" :formatter="formatSex" width="180px"></el-table-column>
      <el-table-column label="地址" prop="address" width="180px"></el-table-column>
      <el-table-column label="联系方式" prop="phone" width="180px"></el-table-column>
      <el-table-column label="状态">
        <template v-slot="scope">
          <el-switch v-model="scope.row.status" @change="ChangeStatus(scope.row)" active-color="#13ce66" inactive-color="#ff4949"></el-switch>
        </template>
    </el-table-column>
   
      <el-table-column label="操作" width="400px">
        <template v-slot="scope">
          <el-button type="warning" @click="ADDAccount(scope.row)">
            <i class="el-icon-edit-outline"></i>充值
          </el-button>
          <el-button type="primary" @click="EidtUser(scope.row)">
            <i class="el-icon-edit-outline"></i>编辑
          </el-button>
          <el-button type="primary" @click="DetailUser(scope.row)">
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
    <el-dialog title="会员新增" :visible.sync="dialogFormVisibleAdd" style="width: 100%;">
      <el-form :inline="true" :model="formAdd" label-width="150px" :rules="rules" ref="ruleFrom">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="formAdd.name" placeholder="请输入姓名"></el-input>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input v-model="formAdd.age" placeholder="请输入年龄"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="sex" style="margin-left: 25px;width: 80%;">
          <el-select v-model="formAdd.sex" placeholder="请选择性别">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="地址">
          <el-input v-model="formAdd.address" placeholder="请输入地址"></el-input>
        </el-form-item>
        <el-form-item label="联系方式" prop="phone" style="margin-left: 26px;">
          <el-input v-model="formAdd.phone" placeholder="请输入联系方式"></el-input>
        </el-form-item>
        <el-form-item label="积分" prop="account" style="margin-left: 26px;">
          <el-input v-model="formAdd.account" placeholder="请输入会员积分"></el-input>
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
        <el-table-column label="卡号" prop="username" width="200px"></el-table-column>
        <el-table-column label="姓名" prop="name"></el-table-column>
        <el-table-column label="年龄" prop="age"></el-table-column>
        <el-table-column label="性别" prop="sex" :formatter="formatSex"></el-table-column>
        <el-table-column label="地址" prop="address"></el-table-column>
        <el-table-column label="联系方式" prop="phone"  width="150px"></el-table-column>
        <el-table-column label="用户积分" prop="account"></el-table-column>
        <el-table-column label="创建时间" prop="createtime"></el-table-column>
        <el-table-column label="修改时间" prop="updatetime"></el-table-column>
      </el-table>
    </el-dialog>
    <!-- 编辑会员信息 -->
    <el-dialog title="编辑会员信息" :visible.sync="dialogFormVisible" style="width: 100%;">
      <el-form :inline="true" :model="form" label-width="100px" :rules="rules" ref="ruleFrom">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入姓名"></el-input>
        </el-form-item>
        <el-form-item label="卡号" >
          <el-input v-model="form.username" disabled></el-input>
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
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="SaveChange ">确 定</el-button>
      </div>
    </el-dialog>
    <!-- 充值积分 -->
    <el-dialog title="充值会员积分" :visible.sync="dialogFormVisibleaccount" style="width: 100%;">
      <el-form
        :inline="true"
        :model="formaccount"
        label-width="100px"
        :rules="rules"
        ref="ruleFrom"
      >
        <el-form-item label="当前用户积分" prop="account" style="margin-left: 26px;">
          <el-input v-model="formaccount.account" disabled></el-input>
        </el-form-item>
        <el-form-item label="充值积分" prop="score" style="margin-left: 26px;">
          <el-input v-model="formaccount.score" placeholder="请输入用户积分"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisibleaccount = false">取 消</el-button>
        <el-button type="primary" @click="Saveaccount ">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/ultis/request";
import { del } from "vue";
import AddUser from "./AddUser.vue";
export default {
  name: "User",
  data() {
    const checkAccount = (rule, value, callback) => {
      value = parseInt(value);
      if (value<10||value>200) {
        callback(new Error("请输入大于等于10或小于等于200的数字"));
      }
    
      callback();
    };
    
    const checkAge = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("年龄不能为空"));
      }

      if (!/^[0-9]+$/.test(value)) {
        callback(new Error("请输入数字值"));
      }
      if (parseInt(value) < 0 || parseInt(value) > 120) {
        callback(new Error("请输入正确的年龄"));
      }
      callback();
    };
    const checkPhone = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("联系方式不能为空"));
      }

      if (!/^[1][3,4,5,6,7,8,9][0-9]{9}$/.test(value)) {
        callback(new Error("请输入合法的手机号"));
      }

      callback();
    };
    return {
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
      FormEdit: {},
      dialogFormVisibleaccount: false,
      dialogFormVisible: false,
      dialogFormVisibleAdd: false,
      dialogTableVisibleDeatil: false,
      formAdd: {},
      form: {},
      formaccount: {},
      gridData: [],
      tableDate: [],
      total: 0,
      params: {
        pageNum: 1,
        pageSize: 3,
        name: "",
        phone: ""
      },
      rules: {
        name: [{ required: true, message: "请输入", trigger: "blur" }],
        age: [{ validator: checkAge, trigger: "blur" }],
        phone: [{ validator: checkPhone, trigger: "blur" }],
        score: [{ validator: checkAccount, trigger: "blur" }],
       
      }
    };
  },
  created() {
    this.load();
  },
  methods: {
    ADDAccount(row) {
      this.formaccount = JSON.parse(JSON.stringify(row));
      this.dialogFormVisibleaccount = true;
    },
    ChangeStatus(row){
     
             
               request.put("/user/update", row).then(res => {
            console.log(res.code);
            if (res.code == "200") {
              this.$notify.success("操作成功");
              this.load();
              }
            }
          )}
  
          ,
    Saveaccount() {
      this.$refs["ruleFrom"].validate(valid => {
        if (valid) {
          request.post("/user/account", this.formaccount).then(res => {
            if (res.code == "200") {
              this.$notify.success("充值成功");
              this.dialogFormVisibleaccount = false;
              this.load();
            } else {
              this.$notify.error(res.msg);
            }
          });
        }
      });
    },
    formatSex(row) {
      return row.sex === "0" ? "男" : row.sex === "1" ? "女" : "未填写";
    },
    SaveChange() {
      this.$refs["ruleFrom"].validate(valid => {
        if (valid) {
          request.put("/user/update", this.form).then(res => {
            console.log(this.form);

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
          request.post("/user/save", this.formAdd).then(res => {
            if (res.code == "200") {
              this.$notify.success("新增成功");
              this.load();
              //清空表单
              //   this.$refs[formName].resetFields();
              this.dialogFormVisibleAdd = false;
            } else {
              this.$notify.error(res.msg);
              return false;
            }
          });
        }
      });
    },
    EidtUser(row) {
      const id = row.id;
      request.get("/user/" + id).then(res => {
        this.form = res.data;
      });
      this.dialogFormVisible = true;
    },
    DetailUser(row) {
      this.dialogTableVisibleDeatil = true;
      request.get("/user/listID/" + row.id).then(res => {
        this.gridData = res.data;
        console.log(res.data);
      });
    },
    AddUser() {
      this.dialogFormVisibleAdd = true;
    },
    load() {
      request.get("/user/page", { params: this.params }).then(res => {
        console.log(res);
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
        phone: ""
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
      request.delete("user/delete/" + id).then(res => {
        if (res.code == "200") {
          this.$notify.success("删除成功");
          this.load();
        } else {
          this.$notify.error(res.msg);
        }
      });
    }
  }
}
</script>
