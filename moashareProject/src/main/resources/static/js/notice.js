$(document).on('click', '#btnNoticeSave', function(e) {

	e.preventDefault();
	console.log("버튼눌림");
	
	const ntitle = $("#ntitle").val();
	const ncontent = $("#ncontent").val();
	console.log("title : " + ntitle);
	console.log("content : " + ncontent);
	const params = {
		ntitle: ntitle,
		ncontent: ncontent,
	}

	// ajax 호출 시 default가 비동기 호출, 아래 코드 실행 가능
	$.ajax({
		url: '../NoticeApi/writeOk',
		type: 'post',
		contentType: 'application/json; charset=utf-8',
		dataType: 'json',
		data: JSON.stringify(params),
		async: false,
		success: function(response) {
			console.log(response);
			alert("새 글 작성이 완료되었습니다.")
			location.href = "/home"
		},
		error: function() {
			alert("제목과 내용은 필수입력값입니다.");
		}
	})


});




$(document).on('click', '#btn-reply-content', function(e) {

	e.preventDefault();
	console.log("버튼눌림");
	const rcontent = $("#reply-content").val();
	const board_id = $("#boardId").val();
	const member_id = $("#memberId").val();

	const params = {
		member_id: member_id,
		board_id: board_id,
		rcontent: rcontent,
	};

	// ajax 호출 시 default가 비동기 호출, 아래 코드 실행 가능
	$.ajax({
		url: '../../boardApi/' + board_id + '/reply',
		type: 'post',
		contentType: 'application/json; charset=utf-8',
		data: JSON.stringify(params),
		dataType: 'json',
		async: false,
		success: function(response) {
			console.log(response);
			console.log(board_id);
			alert("댓글 작성이 완료되었습니다.");
			$.ajax(getReplyList(board_id));
			$('#reply-content').val('');
		},
		error: function() {
			alert("댓글은 필수 입력값이며 최대 200자끼지 가능합니다.");
		}
	})
});
$(document).on('click', '#deleteNotice', function(e) {

	e.preventDefault();
	console.log("버튼눌림");
	var id = $("#id").val();
	console.log("id : " + id);

	// ajax 호출 시 default가 비동기 호출, 아래 코드 실행 가능
	$.ajax({
		url: '../noticeApi/notice/'+id,
		type: 'DELETE',
		data: {
			JSON
		},
		success: function(response) {
			console.log(response);
			alert("삭제가 완료되었습니다.")
			location.href="/notice"
		},
		error: function() {
			alert("서버요청실패");
		}
	})
	

});