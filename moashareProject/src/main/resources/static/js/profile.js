	var check = 0;
	var nickCk=0; 
	var codeCheck=0; 
	$(function() {
		$("#btnnicknameck").on("click", nicknameDuplicateCk); // 아이디중복체크
		$("#updateProfileBtn").on("click", finalCk); // 유효성검사
	});
	function nicknameDuplicateCk() {
		var nickname = $("#usernickname").val().trim();
		if (nickname == '') {
			alert("닉네임을 입력하십시오");
			$("#usernickname").focus();
			return false;
		} else {
			$.ajax({
				url : '../register/nicknameCk',
				type : 'post',
				data : {
					nickname : nickname
				},
				success : function(confirm) {
					var confirm = confirm;
					if (confirm == 1) {
						nickCk = 1;
						$("#nicknameError").html("닉네임이 존재합니다").css('color',
								'red');
					} else {
						nickCk = 2;
						$("#nicknameError").html("닉네임이 사용가능합니다").css('color',
								'green');
					}
				},
				error : function() {
					alert("서버요청실패");
				}
			})
		}
	}
	

	function finalCk() {

		const nickname = $("#usernickname").val();
		const pwd = $("#userpw").val();
		const id = $("#userid").val();
		const email = $("#userEamil").val();
		console.log("id : " + id);
		console.log("pwd : " + pwd);
		console.log("nickname : " + nickname);
       	const params = {
            id : id,
            email : email,
            nickname : nickname,
            password : pwd,
        }



		var pwdPattern = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{6,15}$/;//6~15자 영문,숫자,특수문자 1개 이상 필수
		var nicknamePattern=/^[가-힣a-zA-Z0-9]{2,10}$/;
		
		
		if ( nickname == '') {
			alert("닉네임을 입력하십시오");
			$("#usernickname").focus(); 
			return false; 
		}else if(!nicknamePattern.test(nickname)){
			alert("닉네임 형식에 맞게 입력하십시오");
			$("#usernickname").focus();
			return false;
		}else if ( nickCk == 0){
			//console.log(check);
			alert("닉네임 중복을 확인하십시오.");
			return false;
		}else if ( nickCk == 1 ){
			console.log(check);
			alert("중복된 닉네임입니다. 사용가능한 닉네임으로 바꿔주십시오.");
			return false;
		}else if ( pwd =='' ){
			alert("비밀번호를 입력하십시오");
			$("#userpw").focus();
			return false;
		}else if(!pwdPattern.test(pwd)){
			alert("비밀번호를 형식에 맞게 입력하십시오");
			$("#userpw").focus();
			return false;
		}
		


        $.ajax({
            url : '../memberProfile',
            type : 'PUT',
            contentType : 'application/json; charset=utf-8',
            dataType : 'json',
            data : JSON.stringify(params),
            async : false,
            success : function (response) {
                console.log(response);
                alert("회원수정완료");
                location.href="/home"
            },
            error: function() {
			alert("서버요청실패");
		}
    })
	
	}