<template>
<div>
  <div class="aa">
  <el-card>
    <img src="../assets/logo.png" width="100px" height="100px">
    <p>id:<span id="111111" name="id" ref="user">111111</span></p>
    <p>昵称:骑驴吃西瓜</p>
    <p>年龄:18</p>
    <p>个性签名:一jio人字拖闯天下............</p>
    <el-button size="small" >注销</el-button>
  </el-card>
  </div>
  <div style="float:left width:auto">
  <el-table
    :data="files"
    border
    style="width:auto"
    >
    <el-table-column
      prop="name"
      label="文件名"
      width="400">
    </el-table-column>
    <el-table-column
      prop="lenght"
      label="文件大小/KB"
      width="200">
    </el-table-column>
    <el-table-column
      prop="date"
      label="上传时间"
      width="220">
    </el-table-column>
    <el-table-column
      prop="right"
      label="操作"
      width="260">
      <template slot-scope="scope">
                            <!--下载按钮-->
                            <el-button type="warning" icon="el-icon-download" @click="downloadModel(scope.row.name)"></el-button>
                            <!--编辑按钮-->
                            <el-button type="primary" icon="el-icon-edit" ></el-button>
                            <!--删除按钮-->
                            <el-button type="danger" icon="el-icon-delete" @click="deleteModel(scope.row.name)"></el-button>
                      </template>
    </el-table-column>
  </el-table>
  </div>
  <el-upload
  class="upload-demo"
  action=""
  :http-request="UPlocadFile"
  :on-preview="handlePreview"

  :on-success="handleSuccess"
  :before-upload="beforUpload"
  :on-change="handleChange"
  multiple
  :show-file-list="false"
  :on-exceed="handleExceed"
  :file-list="fileList">
  <el-button size="small" type="primary">点击上传</el-button>
  <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
    </el-upload>
    <!--<el-button style="width: 140px;" type="success"><a style="color: #ffffff;" class='download' href='http://localhost:8443/api/download' download=""  title="excel模板下载">excel模板下载</a></el-button>
    -->
</div>
</template>
<script>
  export default {
    inject:['reload'],
   mounted:function(){
      this.loadFiles();
    },
    methods:{
      loadFiles(){
        var _this =this;
        _this.$axios.get('/filelist').then(resp=>{
          if(resp&&resp.status==200){
            _this.files=resp.data
          }
        })
      },
      handleRemove(file, fileList) {
        this.deleteModel(fileList.filename)
        console.log(file, fileList);
      },
      handlePreview(file) {
        console.log(file);
      },
      handleExceed(files, fileList) {
        this.$message.warning(`当前限制选择1个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
      },
      beforeRemove(file, fileList) {
        var id=this.$refs.user.getAttribute("id")
        return this.$confirm(`确定移除 `+id);
      },
      handleSuccess(response){
          this.url=response
          this.$emit('onUpload')
          this.$message.warning('上传成功')
      },
      beforUpload(file){
       // const isJPG = file.type === 'image/jpeg';
        const isLt2M = file.size / 1024 / 1024 < 2;

        //if (!isJPG) {
          //this.$message.error('上传头像图片只能是 JPG 格式!');
        //}
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!');
        }
        return isLt2M;
      },
      handleChange(file, fileList) {
        this.fileList = fileList.slice(-3);
      },
      downloadModel(row) {
            this.$axios.post('http://localhost:8443/api/download',{'filename':row}
                ,{'responseType':'blob'})
                .then(response =>{
                  if(response.data==null){
                    this.$message.err("2222222")
                    return
                  }
                var  blob = new Blob([response.data],{type:response.headers['Content-type']})
                let url = window.URL.createObjectURL(blob)
                let a = document.createElement('a')
                a.href = url
                var file=response.headers["content-disposition"].split(";")[1].split("fileName=")[1]
                a.setAttribute('download',file)
                document.body.appendChild(a)
                
                //点击下载
                a.click()

                // 下载完成移除元素
                document.body.removeChild(a)
                // 释放掉blob对象
                window.URL.revokeObjectURL(url)
                console.log("下载完成")
                
                })
                .catch(function (error) {
                console.log("11111")
            });
      },
      deleteModel(row) {
            this.$axios.post('http://localhost:8443/api/delete',{'filename':row}
                )
                .then(response =>{
                  console.log(response.data)
                  this.loadFiles()
                  this.reload()
                }
                ).catch((err)=>{
                })
      },
      UPlocadFile(param){
         let form = new FormData()
      var that = this
      form.append('file', param.file)
      form.append('dir', 'temp1')
      that.$axios.post('/upload', form, {
        headers: {
          'Content-Type': 'multipart/form-data'
        },
        onUploadProgress: progressEvent => {
          that.uploadPercent = (progressEvent.loaded / progressEvent.total * 100) | 0
        }
      }).then((res) => {
        console.log('上传结束')
        this.loadFiles()
        console.log(res)
      }).catch((err) => {
        console.log('上传错误')
        console.log(err)
      })
      }
    },

    data() {
      return {
         files: [],
         fileList:[],
      }
    }
  }
</script>
<style scoped>
  .aa {
    width:429px;
    height: 100%;
    float: left;
    margin-right: 10px;
  }
 
</style>