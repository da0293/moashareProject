$(document).on('click', '#btnSave', function(e) {

	e.preventDefault();
	console.log("버튼눌림");
	const title = $("#title").val();
	console.log("title : " + title);
	const content = $("#content").val();
	console.log("content : " + content);
	const params={
		title : title,
		content : content,
	}
	
	// ajax 호출 시 default가 비동기 호출, 아래 코드 실행 가능
	$.ajax({
		url: '../boardApi/writeOk',
		type: 'post',
	 	contentType : 'application/json; charset=utf-8',
        dataType : 'json',
        data : JSON.stringify(params),
        async : false,
		success: function(response) {
			console.log(response);
			alert("새 글 작성이 완료되었습니다.")
			location.href="/board"
		},
		error: function() {
			alert("제목과 내용은 필수입력값입니다.");
		}
	})
	

});
$(document).on('click', '#btnUpdate', function(e) {
	
	
        const id = $("#id").val();
        const content = $("#content").val();
        const title = $("#title").val();
        const params = {
            id : id,
            title : title,
            content : content,
        }

        $.ajax({
            url : '../../boardApi/board/'+id,
            type : 'PUT',
            contentType : 'application/json; charset=utf-8',
            dataType : 'json',
            data : JSON.stringify(params),
            async : false,
            success : function (response) {
                console.log(response);
                location.href="/board"
            },
            error: function() {
			alert("서버요청실패");
		}
    })
});


$(document).on('click', '#btnList', function(e) {

	e.preventDefault();
	location.href = "/board";

});


$(document).on('click', '#btn-reply-content', function(e) {

	e.preventDefault();
	console.log("버튼눌림");
	const rcontent = $("#reply-content").val();
	const board_id = $("#boardId").val();
	const member_id= $("#memberId").val();
	
	const params={
		member_id : member_id,
		board_id : board_id,
		rcontent : rcontent,
	};

	// ajax 호출 시 default가 비동기 호출, 아래 코드 실행 가능
	$.ajax({
		url: '../../boardApi/board/'+board_id+'/reply',
		type: 'post',
		contentType:'application/json; charset=utf-8',
		dataType:'json',
		data : JSON.stringify(params),
		async : false,
		success: function(response) {
			console.log(response);
			alert("댓글 작성이 완료되었습니다.")
			location.href="/board/"+board_id;
		},
		error: function() {
			alert("서버요청실패");
		}
	}) 
});

$(document).on('click', '#btnReplyDelte', function(e) {

	e.preventDefault();
	console.log("버튼눌림");
	var replyId = $("#replyId").val();
	var boardId = $("#boardId").val();
	
	// ajax 호출 시 default가 비동기 호출, 아래 코드 실행 가능
	$.ajax({
		url: '../../boardApi/board/'+boardId+'/reply/'+replyId,
		type: 'DELETE',
		data: {
			JSON
		},
		success: function(response) {
			console.log(response);
			alert("댓글 삭제가 완료되었습니다.")
			location.href="/board/"+boardId;
		},
		error: function() {
			alert("서버요청실패");
		}
	}) 
});

//북마크 
$(document).on('click', '#clickBookmark', function(e) {
	
		console.log("아이콘 누름 ");
       	var id = $("#boardId").val();
		console.log(id);
		return false;
});