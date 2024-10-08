
<template>
  <div id="app">

    <router-view />
  </div>
</template>
<style>

.el-dialog__title {
  font-weight: bold;
}

/* 强制居中弹窗 */
.el-dialog{
  display: flex;
  display: -ms-flex; /* 兼容IE */
  flex-direction: column;
  -ms-flex-direction: column; /* 兼容IE */
  margin:0 !important;
  position:absolute;
  top:50%;
  left:50%;
  transform:translate(-50%,-50%);
  max-height:calc(100% - 30px);
  max-width:calc(100% - 30px);
}
.el-dialog .el-dialog__body{
  max-height: 100%;
  flex: 1;
  -ms-flex: 1 1 auto; /* 兼容IE */
  overflow-y: auto;
  overflow-x: hidden;
}

.el-dialog__wrapper {
  /*隐藏ie和edge中遮罩的滚动条*/
  overflow: hidden;
}
</style>
