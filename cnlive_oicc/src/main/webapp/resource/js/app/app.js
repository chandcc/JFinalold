$(document).ready(function() {


    if ($.isFunction($.fn.validate)) {

    	// IP地址验证   
        jQuery.validator.addMethod("ftp_ip", function(value, element) {    
          return this.optional(element) || /^(([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))\.)(([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))\.){2}([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))$/.test(value);    
        }, "请填写正确的IP地址。");
    	
        $('#addApp_validate').validate({
            focusInvalid: false,
            ignore: "",
            rules: {
            	name: {
                    minlength: 2,
                    required: true
                },
                /*http_callbackurl: {
                    required: true,
                    url: true
                },
                ftp_callbackurl: {
                    required: true,
                    url: true
                },
                ftp_name: {
                    minlength: 5,
                    required: true
                },
                ftp_password: {
                    minlength: 5,
                    required: true
                },
                ftp_ip: {
                	required: true,
                    ip: true
                },*/
                t_strategy_group_id: {
                    required:true,
                    min:0
                }
            },

            invalidHandler: function(event, validator) {
                //display error alert on form submit    
            },

            errorPlacement: function(label, element) { // render error placement for each input type   
                console.log(label);
                $('<span class="error"></span>').insertAfter(element).append(label)
                var parent = $(element).parent().parent('.form-group');
                parent.removeClass('has-success').addClass('has-error');
            },

            highlight: function(element) { // hightlight error inputs
                var parent = $(element).parent().parent('.form-group');
                parent.removeClass('has-success').addClass('has-error');
            },

            unhighlight: function(element) { // revert the change done by hightlight

            },

            success: function(label, element) {
                var parent = $(element).parent().parent('.form-group');
                parent.removeClass('has-error').addClass('has-success');
            }
            /*,
            submitHandler: function(form) {
            	alert("添加成功!");
            }*/
        });





    }


});
