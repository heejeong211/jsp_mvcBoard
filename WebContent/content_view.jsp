<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="EUC-KR">
		<title>Insert title here</title>
	</head>
	<body>
		<table width="500" cellpadding="0" cellspacing="0" border="1">
		<form action="modify.do" method="post">
			<input type="hidden" name="bId" value="${ content_view.bId }"> <!-- hidden, 유저가 글번호를 보이지 않게끔 해주는 것. BContentCommand클래스에서 setAttribute이용해 저장된 content_view를 가져온것. -->
			<tr>
				<td> 번호 </td>
				<td> ${ content_view.bId } </td>
			</tr>
			<tr>
				<td> 히트 </td>
				<td> ${ content_view.bHit } </td>
			</tr>
			<tr>
				<td> 이름 </td>
				<td> <input type="text" name="bName" value="${ content_view.bName }"> </td>
			</tr>
			<tr>
				<td> 제목 </td>
				<td> <input type="text" name="bTitle" value="${ content_view.bTitle }"> </td>
			</tr>
			<tr>
				<td> 내용 </td>
				<td> <textarea name="bContent" rows="10" > ${ content_view.bContent } </textarea> </td>
			</tr>
			<tr >
				<td colspan="2"> <input type="submit" value="수정"> &nbsp;&nbsp; <a href="list1.do">목록보기</a> &nbsp;&nbsp; <a href="delete.do?bId=${ content_view.bId }">삭제</a> &nbsp;&nbsp; <a href="reply_view.do?bId=${ content_view.bId }">답변</a></td> <!-- &nbsp; 띄어쓰기 -->
			</tr>
		</form>
	</table>  
	</body>
</html>