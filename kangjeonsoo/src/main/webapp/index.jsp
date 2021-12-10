<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TodoList</title>
<style>
	#cover {
	padding: 30px;
	width: 260px;
	background-color: rgb(187, 187, 235);
	border: 1px solid red;
	margin-bottom: 5px;
	}
</style>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js">
</script>

<script>
	$(document).ready(onreadyFnc);
	
	
	function onreadyFnc() {
		$.ajax({
			url: 'TodoServlet',
			method: 'get',
			dataType:'json',
			success: loadTodoList,
			error: function(error) {
				console.log(error);
				window.alert(`Error: ${error.status}, Message: ${error.statusText}`);
			}
		});
	}
	
	//성공시 가져오는 콜백함수
	function loadTodoList(result){
		console.log(result);
		if(result.retCode =='Success') {
			var data = result.retVal;
			console.log(data);
			data.forEach(item => {
				
				$('#todoLists').append(makeListView(item));	
			});	
		}else {
			window.alert(result.retVal);
		}
	}
	
	//data 한건 가져와서 출력.
	function makeListView(todo) {
		var div = $('<div>');
		
		$(div).attr('id',todo.content);

		$(div).data('content',todo.content);
		
		//
		$(div).addClass('content');
		
		$(div).append(//
				$('<strong>').text(todo.content),
					
				$('<input>').attr('type','button').val('삭제').click(confirmDeletion)
				
		);
						
		return div;
	}
	

	//삭제버튼 관련 함수
	function confirmDeletion(e) {
		if(confirm('삭제하시겠습니까?')) {
			var content = $(e.target.parentNode).attr('id');
			$.ajax({
				url: 'TodoServlet',
				method:'post',
				data:{
					content: content,
					cmd:'delete'
				},
				dataType: 'json',
				success: function(result) {
					alert(`content: ${result.retVal} 삭제되었습니다!!`);
					$('#' + content).remove();

				},
				error: function(error) {
					console.log(error);
					alert('삭제 처리 중 에러 발생했습니다.');

				}
				
			});
			
		}
	}
	
	//게시판 등록.
	function addContent() {
		$.ajax({
			url: 'TodoServlet',
			method: 'post',
			data: {
				cmd: 'insert',
				content: $('form[name="addForm"]>input[name="content"]').val()
			},
			dataType: 'json',
			success: addResult,
			error: function(error) {
				console.log(error);
				window.alert(`Error: ${error.status}, Message: ${error.statusText}`);
			}		
		});	
	}
	
	// 등록 콜백함수.
	
	function addResult(result) {
		var obj = result.retVal;
		$('#todoLists').append(makeListView(obj));
		// 초기화.
		$('form[name="addForm"]>input[type="text"]').val("");
		
	}
	
	// 변경 버튼 클릭 시. 디비반영. 화면 반영.
	

</script>
</head>
<body>
<div id="cover">
<h1>TODO List</h1>

	<!-- 글 등록 화면. -->
	<div id="contentAdd">
		<form action="" name="addForm">
			<input type="text" name="content" placeholder="What do you want to do..">
			<input type="button" value="+" onclick="addContent()">
		</form>
	</div>

		<div id="todoLists"></div>

	
</div>
</body>
</html>