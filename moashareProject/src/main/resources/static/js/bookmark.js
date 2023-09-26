function updateBookmarkCk(boardId) {

	console.log("보드아이디" + boardId);

	$.ajax({
		url: '../../board/' + boardId + '/bookmark',
		type: 'post',
		data : {
			boardId : boardId
		},
		success: function(data) {
			console.log(data);
			if(data=='y'){
				alert("북마크에 추가되었습니다.");
				$('#'+ boardId +'star').css({
					"color" : "yellow"
				})
			}else{
				alert("북마크가 취소되었습니다.");
				$('#'+ boardId +'star').css({
					"color" : "#858796"
				})
			}
		},
		error: function() {
			alert("서버요청실패");
		}
	})

}
