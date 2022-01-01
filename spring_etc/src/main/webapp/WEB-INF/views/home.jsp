<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Home</title>
</head>
<body>
	<a href="echo">에코</a>
	<br />
	<a href="chat">채팅</a>
	<br />
	<a href="#" id="push">웹 푸시 시작</a><br/>
	<a href="#" id="proxy">프록시를 이용한 요청 처리</a><br />
	<span id="disp"></span>
	<br />

	<a href="textmail">텍스트 메일 보내기</a>
	<br />
	<a href="htmlmail">HTML 메일 보내기</a>
	<br />

</body>

<script>
	window.addEventListener('load', function(e) {
		document.getElementById("push").addEventListener("click", function(e){
			var eventSource = new EventSource("push");
			eventSource.onmessage = function(event) {
				document.getElementById('disp').innerHTML = event.data + "<br />";
			};
		})
		
		document.getElementById("proxy").addEventListener("click", function(e){
			var request = new XMLHttpRequest();
			request.open('GET', 'proxy?addr=http://www.kma.go.kr/weather/forecast/mid-term-xml.jsp?stnId=108');
			request.send('');
			request.onreadystatechange = function() {
				if (request.readyState == 4) {
					if (request.status >= 200 && request.status < 300) {
						var resultXML = request.responseXML
						var cities = resultXML.getElementsByTagName('city');
						var datas = resultXML.getElementsByTagName('data');
						for (var i = 0; i < datas.length; i++) {
							var data = datas[i];
							var output = '';
							if (i % 6 == 0) {
								output += '<div>';
								output += '<h2>' + cities[i/6].childNodes[0].nodeValue + '</h2>';
							}
							output += '<span>' + data.getElementsByTagName('tmEf')[0].childNodes[0].nodeValue + '</span>';
							output += ':<span>' + data.getElementsByTagName('wf')[0].childNodes[0].nodeValue + '</span><br/>';
							if (i % 6 == 0) {
								output += '</div>';
							}
							// 출력
							document.getElementById('disp').innerHTML += output
						}
					}
					else if(request.status >= 400 && request.status < 500)
					{
						alert(request.status);
					}
				}
			}
		})

	})
</script>
</html