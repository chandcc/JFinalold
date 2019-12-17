$(document).ready(function() {


    if ($.isFunction($.fn.validate)) {

        $('#addTrategy_validate').validate({
            focusInvalid: false,
            ignore: "",
            rules: {
            	name: {
                    minlength: 2,
                    required: true
                },
               width: {
                	 minlength: 1,
                     required: true
                },
                height: {
                	 minlength: 1,
                     required: true
                },
                command: {
                    minlength: 5,
                    required: true
                },
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
