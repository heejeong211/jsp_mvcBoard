<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <!-- c 태그 라이브러리 사용!!(jstl), EL사용 -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
		<title>Insert title here</title>
	</head>
	<body>
		<table width="500" cellpadding="0" cellspacing="0" border="1">
			<tr>
				<td>번호</td>
				<td>이름</td>
				<td>제목</td>
				<td>날짜</td>
				<td>히트</td>
			</tr>
			<!-- 핵심 코드!!!!!! -->
			<c:forEach items="${list}" var="dto"> <!-- jsp 파일에 new해서 dto, dao의 객체생성하면 mvc 모델1 버전임. 모델2 버전에서는 var="dto" 처럼 객체 또는 데이터만 전달해야 한다. BListCommand클래스에서 setAttribute이용해 저장된 list를 가져온것.(list = ArrayList<BDto> dtos) -->
			<tr>
				<td>${dto.bId}</td> <!-- dto.getBId -->
				<td>${dto.bName}</td>
				<td>
					<c:forEach begin="1" end="${dto.bIndent}">></c:forEach> <!-- 댓글 달때 붙는 친구... -->
					<a href="content_view.do?bId=${dto.bId}">${dto.bTitle}</a></td> <!-- href="content_view.do?bId=${dto.bId}" 게시글 상세보기, Get방식 ? key value 가 들어감. -->
				<td>${dto.bDate}</td>
				<td>${dto.bHit}</td>
			</tr>
			</c:forEach>
			<tr>
				<td colspan="5"> <a href="write_view.do">글작성</a> </td>
			</tr>
		</table>
	</body>
</html>