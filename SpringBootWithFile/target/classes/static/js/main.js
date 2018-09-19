/**
 * 
 */
$(document).ready(function(){
	$("#submitbutton").click(function(event){
		event.preventDefault();
		ajaxSubmitForm();
	})
});

function ajaxSubmitForm(){
	
//	var form = $("#uploadformid")[0];
//	var data = new FormData(form);
	
	$("#submitbutton").prop("disabled",true);
	
	$.ajax({
		type:"POST",
		enctype: 'multipart/form-data',
		url: '/rest/uploadmultifiles',
		data: new FormData($("#uploadformid")[0]),
		
        processData: false,
        contentType: false,
        cache: false,
        timeout: 1000000,
        success: function(data, textStatus, jqXHR) {
 
            $("#result").html(data);
            console.log("SUCCESS : ", data);
            $("#submitbutton").prop("disabled", false);
            $('#uploadformid')[0].reset();
        },
        error: function(jqXHR, textStatus, errorThrown) {  
 
            $("#result").html(jqXHR.responseText);
            console.log("ERROR : ", jqXHR.responseText);
            $("#submitbutton").prop("disabled", false);
 
        }
	});
}