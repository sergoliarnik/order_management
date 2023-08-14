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
    <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@stomp/stompjs@7.0.0/bundles/stomp.umd.min.js"></script>
    <script src="/webjars/stomp-websocket/stomp.min.js"></script>
</head>
<body>
<div class="container">
    <div>
        <h1>Ordered</h1>
        <ul class="listCard"></ul>
    </div>
    <div>
        <h1>Inprogress</h1>
        <ul class="listCard"></ul>
    </div>
    <div>
        <h1>Ready</h1>
        <ul class="listCard"></ul>
    </div>
</div>
<script src="js/employee.js"></script>
</body>
</html>