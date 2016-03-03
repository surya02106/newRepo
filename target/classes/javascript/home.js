var getFriendsList = function() {
	$.ajax({
		type : "POST",
		url : "getFriendsList",
		data : JSON.stringify(user),
		contentType : 'application/json'
	}).done(function(msg) {
		var userLists = $("#userChatList").html();
		userLists += "<ul class='userListOrder'>";
		for(var i=0;i<msg.length;i++){
			if($('#user'+msg[i].id).length<1){
				userLists += "<li id='user"+msg[i].id+"' class='allUserCss'>";
				if(msg[i].flag=='0')
					userLists += "<span class='glyphicon glyphicon-question-sign'></span>&nbsp;&nbsp;<span class='wauser'>";
				else{
					if(msg[i].status=='1')
						userLists += "<span class='glyphicon glyphicon-asterisk active-user'></span>&nbsp;&nbsp;<span class='wauser'>";
					else
						userLists += "<span class='glyphicon glyphicon-asterisk'></span>&nbsp;&nbsp;<span class='wauser'>";
				}
				userLists += msg[i].firstname+ " " + msg[i].lastname;
				userLists += "</span><span id='msgSpan"+msg[i].id+"' class='msgCounter'></span></li>";
			}
		}
		userLists += "</ul>";
		$("#userChatList").html(userLists);
	});
};


var getActiveFriendsList = function() {
	$.ajax({
		type : "POST",
		url : "getActiveFriendsList",
		contentType : 'application/json'
	}).done(function(msg) {
		$(".allUserCss").find(".glyphicon-asterisk").removeClass("active-user");
		for(var i=0;i<msg.length;i++){
			$("#user"+msg[i]).find(".glyphicon-asterisk").addClass("active-user");
		}
	});
};

var getRequestList=function(){
	$.ajax({
		type : "POST",
		url : "getFriendsRequestCount",
		data : JSON.stringify(user),
		contentType : 'application/json'
	}).done(function(msg) {
		if(msg>0){
			$("#requestForFriendSpan").css("color","red");
			$("#requestForFriendSpan").html(msg);
		}else{
			$("#requestForFriendSpan").html("");
		}
	});
};
var getMyMsgsForFirstTime = function() {
	$.ajax({
		type : "POST",
		url : "getMyMsgsForFirstTime",
		data : JSON.stringify(user),
		contentType : 'application/json'
	}).done(function(msg) {
		var msgCount=0;
		for(var i=0;i<msg.length;i++){
			if(isNaN($("#msgSpan"+msg[i].frommsg).html()))
				msgCount=0;
			else
				msgCount=$("#msgSpan"+msg[i].frommsg).html();
			
			msgCount++;
			$("#msgSpan"+msg[i].frommsg).html(msgCount)
		}
	});
};


var getMsgOnInterval=function(){
	var arr=new Array();
	var x=$(".inputvalues");
	arr[0]=user.id;
	var i=1;
	$('.inputvalues').each(function() {
	    arr[i]=$(this).val();
	    i++;
	});
	$.ajax({
		type : "POST",
		url : "getMsgOnInterval",
		data : JSON.stringify(arr),
		contentType : 'application/json'
	}).done(function(msg) {
		var mstg="";
		var msgCount=0;
		for(var i=0;i<msg.length;i++){
			
			if($("#input"+msg[i].frommsg).length>0){
				mstg="<div class='toMsgs'><span class='dt'>"+msg[i].timedate+"</span><br><span class='msgS'>"+msg[i].msgs+"</span></div>";
				$('#listFor'+msg[i].frommsg+' .textTab').html($('#listFor'+msg[i].frommsg+' .textTab').html()+mstg);
				$('#listFor'+msg[i].frommsg+' .textTab').animate({ scrollTop: $(document).height() }, "1");
			}else{
				if(isNaN($("#msgSpan"+msg[i].frommsg).html()))
					msgCount=0;
				else
					msgCount=$("#msgSpan"+msg[i].frommsg).html();
				
				msgCount++;
				$("#msgSpan"+msg[i].frommsg).html(msgCount)
			}
		}
	});
}

$(document).ready(function() {
	
	/*$("#sendMail").click(function(){
		$.ajax({
			type : "POST",
			url : "SendMail",
			contentType : 'application/json'
		}).done(function(msg) {
			alert(msg)
		});
	});*/
	
	
	
	
	$(".editProfile").html(user.firstname+" "+user.lastname+"&nbsp;&nbsp;");
	var x = $(window).height();
	x -= 121;
	$(".body-div").css("height", x + "px");
	$("#userChatList").css("height", x + "px");
	
	/*getFriendsList();
	alert("Next getRequestList()");
	getRequestList();
	alert("Next getMyMsgsForFirstTime();");
	getMyMsgsForFirstTime();
	alert("Next getRequestList();");
	getRequestList();
	alert("Next getMsgOnInterval()");
	getMsgOnInterval()*/
	
	getFriendsList();
	getRequestList();
	setTimeout(function(){ getRequestList(); }, 500);
	setTimeout(function(){ getMyMsgsForFirstTime(); }, 1000);
	setInterval(function(){ getRequestList(); }, 30000);
	setTimeout(function(){setInterval(function(){ getMsgOnInterval() }, 1000); }, 2000);
	setInterval(function(){ getActiveFriendsList(); }, 10000);
	
	
	$("#searchFriend").keyup(function(){
		var key=$(this).val();
		if(key!=''){
			key="From User where firstname like '"+key+"%' OR lastname like '"+key+"%' OR username like '"+key+"%' OR email like '"+key+"%'";
			var dd=new Array();
			dd[0]=key;
			dd[1]=user.id;
			$.ajax({
				type : "POST",
				url : "getUsersList",
				data : JSON.stringify(dd),
				contentType : 'application/json'
			}).done(function(msg) {
				var formm="<span class='home-closebtn'>x</span>";
				$("#searchList").css("display","block");
				$.each(	msg,	function(index, u) {
					if (u.id != user.id) {
						formm+="<table class='userDetailsAfterSearch'>";
						formm+="<tr><td>Name : "+u.firstname+" "+u.lastname+"</td></tr>";
						formm+="<tr><td>Email : "+u.email+"</td></tr>";
						formm+="<tr><td><input type='button' value='Send Friend Request' class='sendRequestBtn' id='send"+u.id+"'/>";
						formm+="</td></tr></table><br><br>";
					}
				});
				$(".body-div").html("");
				$("#searchList").html(formm);
			});
		}else{
			$("#searchList").html("");
		}
	});
	
		
	$(document).on("click",".sendRequestBtn",function(){
		var uid=$(this).attr("id");
		var id=uid
		id=id.substring(4);
		id=id.trim();
		var friends={};
		friends.meid=user.id;
		friends.youid=id;
		friends.accepted="0";
		$.ajax({
			type : "POST",
			url : "sendFriendRequest",
			data : JSON.stringify(friends),
			contentType : 'application/json'
		}).done(function(msg) {
			$("#"+uid).val("Request Sent");
			$("#"+uid).attr('disabled','disabled');
			setTimeout(function(){getFriendsList();}, 100);
		});
	});
	
	$("#requestForFriendSpan").click(function(){
		$.ajax({
			type : "POST",
			url : "getFriendsRequestList",
			data : JSON.stringify(user),
			contentType : 'application/json'
		}).done(function(msg) {
			var formm = "<div class='userdetailsList-home'><span class='X'>x</span>";
			$.each(	msg,	function(index, u) {
				formm+="<table class='userDetailsAfterSearch'>";
				formm+="<tr><td>Name : "+u.firstname+" "+u.lastname+"</td></tr>";
				formm+="<tr><td>Email : "+u.email+"</td></tr>";
				formm+="<tr><td><input type='button' value='Accept Request' class='acceptRequestBtn' id='accept"+u.id+"'/>";
				formm+="</td></tr></table><br><br>";
			});
			formm+="</div>";
			$(".body-div").html(formm);
			$("#searchList").css("display","none");
		});
	});
	$(document).on("click",".acceptRequestBtn",function(){
		var id=$(this).attr("id");
		id=id.substring(6);
		id=id.trim();
		var arr=new Array();
		arr[0]=id;
		arr[1]=user.id;
		$.ajax({
			type : "POST",
			url : "setAcceptFriendsRequest",
			data : JSON.stringify(arr),
			contentType : 'application/json'
		}).done(function(msg) {
			if(msg=='000'){
				$("#accept"+id).val("Added To Friend List");
				$("#accept"+id).attr("disabled","disables");
				getFriendsList();
				setTimeout(function(){getRequestList(); getFriendsList();}, 100);
			}
		});
	});
	
	
	$(document).on("click",".X",function(){
		$(this).parent().remove();
	});
	$(document).on("click",".home-closebtn",function(){
		$(this).parent().hide();
	});
	
	$(document).on("click",".allUserCss",function(){
		var id=$(this).attr("id");
		id=id.substring(4);
		id=id.trim();
		//limited 3 chat boxes
		var ncb=$(".chatBoxes li").length;
		if(ncb>2)
			return;
		
		//check chat box for this id  exist
		if($("#listFor"+id).length!=0)
			return;
		
		//create chat box
		//Add LI to UL tag
		$(".chatBoxes").html($(".chatBoxes").html()+"<li id='listFor"+id+"'></li>");
		
		//get predesign table from boxForChat id
		$("#listFor"+id).html($("#boxForChat").html());
		//add id to close X
		//closeCls
		$("#listFor"+id+" .closeCls").attr('id', 'closeCls'+id);
		
		
		var userName=$(this).find(".wauser").html();
		//add username on tab
		$("#listFor"+id+" .chatBox .nameTab .nameTabIn").html(userName);
		
		if($(this).find(".glyphicon-asterisk").hasClass("active-user")){
			$("#listFor"+id+" .chatBox .nameTab").css("backgroundColor","green");
			$("#listFor"+id+" .chatBox .nameTab .nameTabIn").css("color","#FFFFFF");
			$('#closeCls'+id).css("color","#FFFFFF");
		}
		
		$("#allInputIds").html($("#allInputIds").html()+"<input type='hidden' value='"+id+"' id='input"+id+"' class='inputvalues'/>");
		
		//give id to button
		$("#listFor"+id+" .chatBox .typeTab .send").attr('id', 'sendBtn'+id);
		var arr=new Array();
		arr[0]=user.id;
		arr[1]=id;
		$.ajax({
			method: "POST",
			url: "startChatBox",
			data: JSON.stringify(arr),
			contentType: 'application/json'
		}).done(function( mssg ){
			var mstg="";
			for(var i=0;i<mssg.length;i++){
				if(mssg[i].tomsg==user.id){
					mstg="<div class='toMsgs'><span class='dt'>"+mssg[i].timedate+"</span><br><span class='msgS'>"+mssg[i].msgs+"</span></div>";
					$('#listFor'+id+' .textTab').html($('#listFor'+id+' .textTab').html()+mstg);
					$('#listFor'+id+' .textTab').animate({ scrollTop: $(document).height() }, "1");
				}else{
					mstg="<div class='fromMsgs'><span class='dt'>"+mssg[i].timedate+"</span><br><span class='msgS'>"+mssg[i].msgs+"</span></div>";
					$('#listFor'+id+' .textTab').html($('#listFor'+id+' .textTab').html()+mstg);
					$('#listFor'+id+' .textTab').animate({ scrollTop: $(document).height() }, "1");
				}
			}
			$("#msgSpan"+id).html("");
			$('#listFor'+id+' .TtypeTab').focus();
		});
	});
	
	$(document).on("click",".closeCls",function(){
		var id=$(this).attr('id');
		id=id.substring(8);
		$("#listFor"+id).remove();
		$("#input"+id).remove();	
	});
	
	
	
	
	
	
	
	
	
	$(document).on('click','.send',function(){
		var id=$(this).attr('id');
		//get user's id
		id=id.substring(7);
		
		//read from text area
		var msg=$('#listFor'+id+' .TtypeTab').val();
		//ajax call to store msg in database
		var messages={};
		messages.frommsg=user.id;
		messages.tomsg=id;
		messages.msgs=msg;
		messages.flag="0";
		messages.status="0";
		$.ajax({
			method: "POST",
			url: "sendMsgToHashSet",
			data: JSON.stringify(messages),
			contentType: 'application/json'
		}).done(function( mssg ){
			//reset textarea
			var msg=$('#listFor'+id+' .TtypeTab').val();
			var mstg="<div class='fromMsgs'><span class='dt'>"+mssg.timedate+"</span><br><span class='msgS'>"+msg+"</span></div>";
			$('#listFor'+id+' .textTab').html($('#listFor'+id+' .textTab').html()+mstg);
			$('#listFor'+id+' .textTab').animate({ scrollTop: $(document).height() }, "100");
			$('#listFor'+id+' .TtypeTab').val("");					
			$('#listFor'+id+' .TtypeTab').focus();
		});
	});
	$(".logout").click(function(){
		$.ajax({
			method: "POST",
			url: "userLogout",
			data: JSON.stringify(user),
			contentType: 'application/json'
		}).done(function( mssg ){
			if(mssg=="000"){
				user=null;
				location.reload();
			}
		});
	});
});

