<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.example.order_management.dto.ProductDto" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>List of Products</title>
    <link rel="stylesheet" href="css/employee.css">
    <script src="/webjars/jquery/jquery.min.js"></script>
    <script src="/webjars/sockjs-client/sockjs.min.js"></script>
    <script src="/webjars/stomp-websocket/stomp.min.js"></script>
</head>
<body>
<div class="container">
    <div>
        <h1>Ordered</h1>
        <ul class="listCard">
            <c:forEach var="value" items="${orderedList}">
                <li>
                    <div><img src="${value.productImageUrl}"></div>
                    <div>${value.userName}</div>
                    <div>${value.productName}</div>
                    <div></div>
                    <div hidden="hidden"><c:out value="${value.id}"/></div>
                </li>
            </c:forEach>
        </ul>
    </div>
    <div>
        <h1>Inprogress</h1>
        <ul class="listCard">
            <c:forEach var="value" items="${inprogressList}">
                <li>
                    <div><img src="${value.productImageUrl}"></div>
                    <div>${value.userName}</div>
                    <div>${value.productName}</div>
                    <div></div>
                    <div hidden="hidden"><c:out value="${value.id}"/></div>
                </li>
            </c:forEach>
        </ul>
    </div>
    <div>
        <h1>Ready</h1>
        <ul class="listCard">
            <c:forEach var="value" items="${readyList}">
                <li>
                    <div><img src="${value.productImageUrl}"></div>
                    <div>${value.userName}</div>
                    <div>${value.productName}</div>
                    <div></div>
                    <div hidden="hidden"><c:out value="${value.id}"/></div>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>
<script src="js/employee.js"></script>
</body>
</html>