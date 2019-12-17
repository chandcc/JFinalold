$(document).ready(function() {

    if ($.isFunction($.fn.validate)) {
        $('#profileform').validate({
            focusInvalid: false,
            ignore: "",
            rules: {
            	name: {
                    minlength: 3,
                    required: true
                },
                password: {
                    required: true
                },
                newpwd: {
                    required: true
                },
                conpwd: {
                    required: true,
                    equalTo: "#newpwd"
                },
                description: {
                    required: true
                },
                company: {
                    minlength: 5,
                    required: true
                },
                contacts: {
                    minlength: 5,
                    required: true
                }
                

            },

            invalidHandler: function(event, validator) {
                //display error alert on form submit    
            },

            errorPlacement: function(label, element) { // render error placement for each input type   
                console.log(label);
                $('<span class="error" style="color:red;"></span>').insertAfter(element).append(label);
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
