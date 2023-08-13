<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.example.order_management.dto.ProductDto" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>List of Products</title>
    <link rel="stylesheet" href="css/product_style.css">
    <script src="/webjars/jquery/jquery.min.js"></script>
    <script src="/webjars/sockjs-client/sockjs.min.js"></script>
    <script src="/webjars/stomp-websocket/stomp.min.js"></script>
</head>
<body>
<div class="container">
    <header>
        <h1>Your shopping list</h1>
        <div class="order">
            <img src="image/shopping-cart.svg" alt="">
            <span class="quantity">0</span>
        </div>
        <div class="inprogress">
            <img src="image/in-progress.svg" alt="">
            <span class="quantity">0</span>
        </div>
        <div class="ready">
            <img src="image/ready.png" alt="">
            <span class="quantity">0</span>
        </div>
    </header>
    <div class="list">
        <c:forEach var="rrrrrr" items="${products}">
            <div class="item">
                <img src="<c:out value="${rrrrrr.imageUrl}"/>" alt="photo"/>
                <div class="title"><c:out value="${rrrrrr.name}"/></div>
                <div class="price"><c:out value="${rrrrrr.price}"/></div>
                <div hidden="hidden"><c:out value="${rrrrrr.id}"/></div>
                <button onclick="addToCard(<c:out value="${rrrrrr.id}"/>)">Add To Card</button>
            </div>
        </c:forEach>
    </div>
</div>
<div class="card">
    <h1 class="sideMenuName">Cart</h1>
    <ul class="listCard">
    </ul>
    <div class="checkOut" style="flex: auto">
        <div class="total">0</div>
        <div class="makeOrder">Order</div>
        <div class="closeOrder">Close</div>
    </div>
</div>
<script src="js/app.js"></script>
</body>
</html>