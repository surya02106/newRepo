$(document).ready(function(){
	$("#register-btn").click(function(){
		user={};
		user.firstname=$("#firstname").val();
		user.lastname=$("#lastname").val();
		user.username=$("#username").val();
		user.password=$("#password").val();
		user.contact="0";
		user.flag="0";
		user.status="0";
		user.emailstatus="0";
		user.email=$("#email").val();
		$.ajax({
			type : "POST",
			url : "userRegistration",
			data : JSON.stringify(user),
			contentType : 'application/json'
		}).done(function(msg) {
			if (!isNaN(msg)) {
				$(".profile").html("<span class='get-login-p'>Registration successfull. Please Confirm the Email and Then login<span>");
				$("#sendMail").click(function(){
					$.ajax({
						type : "POST",
						url : "SendMail",
						data : JSON.stringify(msg),
						contentType : 'application/json'
					}).done(function(msg) {
						//alert(msg)
					});
				});
			} else {
				$(".get-login-p").html(msg);
			}
		});
	});
	$(document).on('click','.glyphicon-arrow-left',function(){
		window.location.reload();
	});
});