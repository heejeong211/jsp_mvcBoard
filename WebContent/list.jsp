<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <!-- c �±� ���̺귯�� ���!!(jstl), EL��� -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
		<title>Insert title here</title>
	</head>
	<body>
		<table width="500" cellpadding="0" cellspacing="0" border="1">
			<tr>
				<td>��ȣ</td>
				<td>�̸�</td>
				<td>����</td>
				<td>��¥</td>
				<td>��Ʈ</td>
			</tr>
			<!-- �ٽ� �ڵ�!!!!!! -->
			<c:forEach items="${list}" var="dto"> <!-- jsp ���Ͽ� new�ؼ� dto, dao�� ��ü�����ϸ� mvc ��1 ������. ��2 ���������� var="dto" ó�� ��ü �Ǵ� �����͸� �����ؾ� �Ѵ�. BListCommandŬ�������� setAttribute�̿��� ����� list�� �����°�.(list = ArrayList<BDto> dtos) -->
			<tr>
				<td>${dto.bId}</td> <!-- dto.getBId -->
				<td>${dto.bName}</td>
				<td>
					<c:forEach begin="1" end="${dto.bIndent}">></c:forEach> <!-- ��� �޶� �ٴ� ģ��... -->
					<a href="content_view.do?bId=${dto.bId}">${dto.bTitle}</a></td> <!-- href="content_view.do?bId=${dto.bId}" �Խñ� �󼼺���, Get��� ? key value �� ��. -->
				<td>${dto.bDate}</td>
				<td>${dto.bHit}</td>
			</tr>
			</c:forEach>
			<tr>
				<td colspan="5"> <a href="write_view.do">���ۼ�</a> </td>
			</tr>
		</table>
	</body>
</html>