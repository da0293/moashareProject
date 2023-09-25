$(document).on('click', '#btnSave', function(e) {

	e.preventDefault();
	console.log("버튼눌림");
	const title = $("#title").val();
	console.log("title : " + title);
	const content = $("#content").val();
	console.log("content : " + content);
	const params = {
		title: title,
		content: content,
	}

	// ajax 호출 시 default가 비동기 호출, 아래 코드 실행 가능
	$.ajax({
		url: '../boardApi/writeOk',
		type: 'post',
		contentType: 'application/json; charset=utf-8',
		dataType: 'json',
		data: JSON.stringify(params),
		async: false,
		success: function(response) {
			console.log(response);
			alert("새 글 작성이 완료되었습니다.")
			location.href = "/board"
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
		id: id,
		title: title,
		content: content,
	}

	$.ajax({
		url: '../../boardApi/board/' + id,
		type: 'PUT',
		contentType: 'application/json; charset=utf-8',
		dataType: 'json',
		data: JSON.stringify(params),
		async: false,
		success: function(response) {
			console.log(response);
			location.href = "/board"
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
			

function getReplyList() {
	console.log("여기까징옴");
	console.log(arguments[0]);
	var board_id = arguments[0];
	$.ajax({
		type: "POST",
		url: "../../reply/list/" + board_id,
		async: false,
		success: function(fragment) {
			$('#replyTable').replaceWith(fragment);
		},
		error: function() {
			alert("서버요청실패");
		}
	})
}

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



function updateReplyCk(boardId, replyId, replyContent) {

	console.log("id : " + replyId);
	console.log("id : " + replyContent);
	$("#reply_text").val(replyContent);
	$("#reply_id").val(replyId);
	$("#board_id").val(boardId);
}

function updateReply() {

	const replyId = $("#reply_id").val();
	const rcontent = $("#reply_text").val();
	const boardId = $("#board_id").val();
	const params = {
		id: replyId,
		board_id: boardId,
		rcontent: rcontent,
	}

	$.ajax({
		url: '../../boardApi/board/' + boardId + '/reply/' + replyId,
		type: 'PUT',
		contentType: 'application/json; charset=utf-8',
		dataType: 'json',
		data: JSON.stringify(params),
		async: false,
		success: function(response) {
			console.log(response);
			$.ajax(getReplyList(boardId));
			$('#updateReply').modal('hide');
		},
		error: function() {
			alert("서버요청실패");
		}
	})

}

function deleteReply() {

	console.log("삭제버튼눌림");
	var replyId = $("#reply_id").val();
	var boardId = $("#board_id").val();
	console.log("보드번호" + boardId);
	console.log("댓글번호" + replyId);// ajax 호출 시 default가 비동기 호출, 아래 코드 실행 가능
	$.ajax({
		url: '../../boardApi/board/' + boardId + '/reply/' + replyId,
		type: 'DELETE',
		data: {
			JSON
		},
		success: function(response) {
			console.log(response);
			alert("댓글 삭제가 완료되었습니다.")
			$.ajax(getReplyList(boardId));
			$('#updateReply').modal('hide');
		},
		error: function() {
			alert("서버요청실패");
		}
	})
}