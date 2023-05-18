<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<form name="item_form" action="demo" method="post">
		<label>Text to store</label>
		<input type="text" name="item" />
		<input type="submit" value="Submit" />
</form>
<p>Current text: <%= request.getAttribute("current_text") %>. </p>

</body>
</html>