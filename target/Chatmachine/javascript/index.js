var user = {};
$(document).ready(function() {
	$("#login-btn").click(function() {
		user.username = $("#username").val();
		user.password = $("#password").val();
		$.ajax({
			type : "POST",
			url : "userLoginAuthentication",
			data : JSON.stringify(user),
			contentType : 'application/json'
		}).done(function(msg) {
			if (msg.id == 0) {
				$("#errMsg").html("invalid username/password");
			} else {
				user=msg;
				$(".body").load("home.html");
			}

			/*
			 * $.each(msg, function(index, example1) { alert(example1.username);
			 * //to print object array });
			 */
		});
	});
	$("#newUser").click(function(){
		$(".body").load("register.html");
	});

});