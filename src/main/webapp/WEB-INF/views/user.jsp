<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>List of Products</title>
    <link rel="stylesheet" href="css/user_page.css">
    <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@stomp/stompjs@7.0.0/bundles/stomp.umd.min.js"></script>
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
<script src="js/user_page.js"></script>
</body>
</html>