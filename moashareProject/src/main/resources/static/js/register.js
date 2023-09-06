	var check = 0;
	var nickCk=0; 
	var codeCheck=0; 
	$(function() {
		$("#btnnicknameck").on("click", nicknameDuplicateCk); // 아이디중복체크
		$("#btnemailck").on("click", emailDuplicateCk); // 이메일 아이디 중복체크
		$("#btnemailverifty").on("click", emailVerifyCk); // 이메일 인증코드 보내기 버튼
		$("#btncodeck").on("click", codeCk); // 이메일 인증코드 확인 체크
		$("#btnfinck").on("click", finalCk); // 유효성검사
		$("select").on("change", selectEmail); // 이메일도메인
	});
	function emailDuplicateCk() {
		console.log("버튼눌림");
		var email1 = $("#email1").val().trim();
		var email2 = $("#email2").val().trim();
		var email = email1 + '@' + email2;
		console.log(email);
		// 확인
		if (email1 == '') {
			alert("이메일 앞자리를 입력하십시오");
			$("#email1").focus();
			return false;
		} else if (email2 == '') {
			alert("이메일 뒷자리를 입력하십시오");
			$("#email2").focus();
			return false;
		} else {
			$
					.ajax({
						url : '../register/emailCk',
						type : 'post',
						data : {
							email : email
						},
						success : function(confirm) {
							var confirm = confirm;
							if (confirm == 1) {
								check = 1;
								$("#emailError").html("이메일이 존재합니다").css(
										'color', 'red');
							} else {
								check = 2;
								$("#emailError").html("이메일이 사용가능합니다").css(
										'color', 'green');
							}
						},
						error : function() {
							alert("서버요청실패");
						}
					})
		}

	}
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
	function emailVerifyCk() {
		var email1 = $("#email1").val();
		var email2 = $("#email2").val();
		var email = email1 + '@' + email2;

		console.log(email);
		if(check==2){
			verificationError
			$.ajax({
				url : '../register/emailVerify',
				type : 'get',
				data : {
					email : email
				},
				success : function(data) {
					console.log("data : " + data);
					alert('인증번호가 전송되었습니다.');
					$("#codeConfirm").attr("value", data);
				},
				error : function() {
					alert("관리자에게 문의하십시오.");
				}
			});
		} else{
			alert("중복확인을 먼저 바르게 진행해 주십시오.");
		}
		
	}
	function codeCk() {
		var codeInput = $("#codeInput").val();
		var codeConfirm = $("#codeConfirm").val();
		if (codeInput == codeConfirm) {
			codeCheck=1;
			alert("인증되었습니다.");
		} else {
			codeCheck=2; 
			alert("번호가 다릅니다.");
		}
	}

	function selectEmail() {
		$("#selectEmail option:selected").each(function() {
			if ($(this).val() == '1') {
				$("#email2").val('');
				$("#email2").attr("disabled", false);
			} else {
				$("#email2").val($(this).text());
				$("#email2").attr("disabled", true);
			}
		});
	}
	function finalCk() {
		//console.log("버튼눌림"); 
		var email1 = $("#email1").val();
		var email2 = $("#email2").val();
		var middle = $("#middle").text();
		$("#id").val(email1+middle+email2);
		var id = $("#id").val();
		var pwd = $("#userpw").val();
		var repwd = $("#userrepw").val();
		var nickname = $("#usernickname").val();
		var codeInput = $("#codeInput").val();
		var codeConfirm = $("#codeConfirm").val();

		var pwdPattern = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{6,15}$/;//6~15자 영문,숫자,특수문자 1개 이상 필수
		var nicknamePattern=/^[가-힣a-zA-Z0-9]{2,10}$/;
		
		
		
		if ( email1 == '' || email2 =='') {
			alert("이메일을 입력하십시오.");
			return false; 
		}else if ( check == 0){
			//console.log(check);
			alert("이메일 중복을 확인하십시오.");
			return false;
		}else if ( check == 1 ){
			console.log(check);
			alert("중복된 이메일입니다. 사용가능한 이메일로 바꿔주십시오.");
			return false;
		} else if (codeConfirm=''){
			alert("인증메일을 받으십시오.")
			return false;
		}else if(codeCheck==0){
			alert("이메일 인증번호를 입력하시고 확인버튼을 누르십시오.");
			return false;
		}else if(codeCheck==2){
			alert("이메일 인증번호가 다릅니다.");
			return false;
		}else if ( nickname == '') {
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
		}else if ( repwd=='' ){
			alert("확인비밀번호를 입력하십시오"); 
			return false; 
		}else if ( pwd != repwd ){
			console.log(pwd);
			console.log(repwd);
			alert("비밀번호가 일치하지 않습니다"); 
			$("#userrepw").focus();
			return false; 
		}
		alert("회원가입완료");
	}