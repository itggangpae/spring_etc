<%@ page contentType="text/html; charset=UTF-8"
	trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>채팅</title>
<style>
#chatArea {
	width: 200px;
	height: 100px;
	overflow-y: auto;
	border: 1px solid black;
	overflow-y: auto;
}
</style>
</head>
<body>
	이름:
	<input type="text" id="nickname">
	<input type="button" id="enterBtn" value="입장">
	<input type="button" id="exitBtn" value="나가기">

	<h1>대화 영역</h1>
	<div id="chatArea">
		<div id="chatMessageArea"></div>
	</div>
	<br />
	<input type="text" id="message">
	<input type="button" id="sendBtn" value="전송">
</body>
<script type="text/javascript">
	var wsocket;

	function connect() {
		wsocket = new WebSocket("ws://localhost:8080/adamsoft/chat-ws");
		wsocket.onopen = onOpen;
		wsocket.onmessage = onMessage;
		wsocket.onclose = onClose;
	}
	function disconnect() {
		wsocket.close();
	}
	function onOpen(evt) {
		appendMessage("연결되었습니다.");
	}
	
	function onMessage(evt) {
		var data = evt.data;
		appendMessage(data);

	}
	function onClose(evt) {
		appendMessage("연결을 끊었습니다.");
	}

	function send() {
		var nickname = document.getElementById("nickname").value;
		var msg = document.getElementById("message").value;
		wsocket.send("msg:" + nickname + ":" + msg);
		document.getElementById("message").value = "";
	}
	
		function appendMessage(msg) {
		document.getElementById("chatMessageArea").innerHTML = msg + "<br />" + document.getElementById("chatMessageArea").innerHTML;
	}

	document.getElementById('message').addEventListener('keypress',
			function(e) {
				event = e || window.event
				var keycode = (event.keyCode ? event.keyCode : event.which);
				if (keycode == '13') {
					send();
				}
				event.stopPropagation();
			});
	
		document.getElementById('sendBtn').addEventListener('click', function() {
		send();
	});
	document.getElementById('enterBtn').addEventListener('click', function() {
		connect();
	});
	document.getElementById('exitBtn').addEventListener('click', function() {
		disconnect();
	});
</script>

</html>
