//七牛再次封裝JS 

var UploadBean = {
     opt:{
         dataUrl:"http://oicc.cnlive.com/cnlive_oicc/getToken_test/",
         file:null,
         UploadProgress:function(rs){
                 
         },
         FileUploaded:function(rs){
                
         },
         BeforeUpload:function(f,rs){

         },
         Error: function(up, err, errTip) {
         },
         UploadComplete:function(files){} 
      },
      init:function(config){
      opt=$.extend(this.opt,config);

      $.post(opt.dataUrl,function(rs){
         if(rs.code == 0){
             var resultData=rs.data;
             UploadBean.opt.domian=resultData.domian;
             UploadBean.opt.token=resultData.token;
             UploadBean.opt.filePath=resultData.filePath;
             UploadBean.opt.uploader =  Qiniu.uploader({
             disable_statistics_report: true,
             runtimes: 'html5,flash,html4',
             browse_button: 'pickfiles',
             container: 'container',
             drop_element: 'container',
             dragdrop: true,
             chunk_size: '4mb',
             filters: {
                 mime_types: [
                     {
                         title: "zip files",
                         extensions: "zip,rar,rar5,7z"
                     } 
                 ]
             }, 
             uptoken :  UploadBean.opt.token,
             domain:  UploadBean.opt.domian, 
             auto_start: false,
             init:{
                  'Key':function(uploader,file){
                	  return UploadBean.opt.BeforeUpload(uploader,file); 
                  },
                  'UploadProgress':function(up,rs){
                            UploadBean.opt.UploadProgress(rs);
                          },
                     'FileUploaded':function(up,rs){
                          UploadBean.opt.FileUploaded(rs); 
                     },
                     'Error': function(up, err, errTip) {
                    	 UploadBean.opt.Error(rs); 
                  },
                  'UploadComplete':function(files){
                	  UploadBean.opt.UploadComplete(files); 
                  }
              }
           });
         }else{
            opt.fileBefore(rs.code);
         }
      });
      
    },
    start:function(){
        UploadBean.opt.uploader.start();
    },
    stop:function(){
        UploadBean.opt.uploader.stop();
    }
}



 