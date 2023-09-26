function updateBookmarkCk(boardId) {
	console.log("클릭");
	console.log("보드아이디" + boardId);
	var url=window.location.href;
	console.log("url :" + url);
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
function deleteBookmarkCk(boardId) {
	console.log("클릭");
	console.log("보드아이디" + boardId);
	var url=window.location.href;
	console.log("url :" + url);
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
				location.href=url;
			}
		},
		error: function() {
			alert("서버요청실패");
		}
	})
}