let openCart = document.querySelector('.order');
let closeCart = document.querySelector('.closeOrder');
let openInprogress = document.querySelector('.inprogress');
let openReady = document.querySelector('.ready');
let productList = document.querySelector('.list');
let listCart = document.querySelector('.listCard');
let body = document.querySelector('body');
let total = document.querySelector('.total');
let orderQuantity = document.getElementsByClassName("quantity")[0];
let inprogressQuantity = document.getElementsByClassName("quantity")[1];
let readyQuantity = document.getElementsByClassName("quantity")[2];
let makeOrder = document.querySelector('.makeOrder');
let sideMenuName = document.querySelector('.sideMenuName');

const sideMenuStatusesEnum = {
    None: "none",
    Order: "order",
    Inprogress: "inprogress",
    Ready: "ready"
}

let sideMenuStatus = sideMenuStatusesEnum.None;

let products = [];
let listCards = [];

const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/gs-guide-websocket'
});

stompClient.onConnect = (frame) => {
    stompClient.subscribe('/topic/current_user_inprogress', (productsInput) => {
        let inprogressProducts = JSON.parse(productsInput.body);
        if(sideMenuStatus === sideMenuStatusesEnum.Inprogress){
            reloadInprogress(inprogressProducts);
        }
        inprogressQuantity.innerText = inprogressProducts.length;
    });
    stompClient.subscribe('/topic/current_user_ready', (productsInput) => {
        let readyProducts = JSON.parse(productsInput.body);
        if(sideMenuStatus === sideMenuStatusesEnum.Ready){
            reloadReady(readyProducts);
        }
        readyQuantity.innerText = readyProducts.length;
    });
};

stompClient.activate();

initApp();

function initApp() {
    fetchAndReloadProducts();
    reloadInprogressQuantity()
    reloadReadyQuantity()
}

function reloadInprogressQuantity(){
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", "http://localhost:8080/products_in_order?status=INPROGRESS", false);
    xmlHttp.send(null);
    let inprogressList = JSON.parse(xmlHttp.responseText);
    inprogressQuantity.innerText = inprogressList.length;
}

function reloadReadyQuantity() {
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", "http://localhost:8080/products_in_order?status=READY", false); // false for synchronous request
    xmlHttp.send(null);
    let readyList = JSON.parse(xmlHttp.responseText);
    readyQuantity.innerText = readyList.length;
}

function fetchAndReloadProducts() {
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", "http://localhost:8080/products", false);
    xmlHttp.send(null);
    let allProducts = JSON.parse(xmlHttp.responseText);
    reloadProducts(allProducts);
}

function reloadProducts(allProducts) {
    products = allProducts;
    productList.innerHTML = "";
    products.forEach(value => {
        let newDiv = document.createElement('div');
        newDiv.classList.add("item");
        newDiv.innerHTML = `
                <img src="${value.imageUrl}" alt="photo"/>
                <div class="title">${value.name}</div>
                <div class="price">${value.price}</div>
                <button onclick="addToCart(${value.id})">Add To Cart</button>
            `
        productList.appendChild(newDiv);
    })
}

function reloadCart() {
    listCart.innerHTML = '';
    sideMenuName.textContent = 'Cart';

    let totalPrice = 0;
    let totalQuantity = 0;
    listCards.forEach((value, key) => {
        totalPrice += value.price;
        totalQuantity += value.quantity;

        let newDiv = document.createElement('li');
        newDiv.innerHTML = `
            <div><img src="${value.imageUrl}"></div>
            <div>${value.name}</div>
            <div>${value.price}</div>
            <div>
                <button onclick="changeQuantity(${key}, ${value.quantity - 1})">-</button>
                <div class="count">${value.quantity}</div>
                <button onclick="changeQuantity(${key}, ${value.quantity + 1})">+</button>
            </div>
            `
        listCart.appendChild(newDiv);
    })
    total.innerText = totalPrice;
    orderQuantity.innerText = totalQuantity;
}

openCart.addEventListener('click', () => {
    reloadCart();
    sideMenuStatus = sideMenuStatusesEnum.Order;
    makeOrder.style.visibility = "visible";
    total.style.visibility = "visible";
    body.classList.add('active');
})

function fetchAndReloadInprogress() {
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", "http://localhost:8080/products_in_order?status=INPROGRESS", false);
    xmlHttp.send(null);
    let inprogressList = JSON.parse(xmlHttp.responseText);
    reloadInprogress(inprogressList);
}

function reloadInprogress(inprogressList) {
    listCart.innerHTML = '';
    sideMenuName.textContent = 'Inprogress';
    inprogressList.forEach(value => {
        if (value != null) {
            let newDiv = document.createElement('li');
            newDiv.innerHTML = `
            <div><img src="${value.imageUrl}"></div>
            <div>${value.name}</div>
            <div>${value.price}</div>
            `
            listCart.appendChild(newDiv);
        }
    })
    inprogressQuantity.innerText = inprogressList.length;
}

openInprogress.addEventListener('click', () => {
    fetchAndReloadInprogress();
    sideMenuStatus = sideMenuStatusesEnum.Inprogress;
    makeOrder.style.visibility = "hidden";
    total.style.visibility = "hidden";
    body.classList.add('active');
})

function fetchAndReloadReady() {
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", "http://localhost:8080/products_in_order?status=READY", false);
    xmlHttp.send(null);
    let readyList = JSON.parse(xmlHttp.responseText);
    reloadReady(readyList);
}

function reloadReady(readyList) {
    listCart.innerHTML = '';
    sideMenuName.textContent = 'Ready';

    readyList.forEach(value => {
        if (value != null) {
            let newDiv = document.createElement('li');
            newDiv.innerHTML = `
            <div><img src="${value.imageUrl}"></div>
            <div>${value.name}</div>
            <div>${value.price}</div>
            `
            listCart.appendChild(newDiv);
        }
    })
    readyQuantity.innerText = readyList.length;
}

openReady.addEventListener('click', () => {
    fetchAndReloadReady();
    sideMenuStatus = sideMenuStatusesEnum.Ready;
    makeOrder.style.visibility = "hidden";
    total.style.visibility = "hidden";
    body.classList.add('active');
})


makeOrder.addEventListener('click', () => {
    body.classList.remove('active')
    const xhr = new XMLHttpRequest();
    xhr.open("POST", "http://localhost:8080/orders", true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    const filteredList = listCards.filter(item => item !== null);
    xhr.send(JSON.stringify(filteredList));
    listCards = [];
    orderQuantity.innerText = 0;
})

closeCart.addEventListener('click', () => {
    sideMenuStatus = sideMenuStatusesEnum.None;
    body.classList.remove('active')
})

function addToCart(key) {
    if (!(key in listCards)) {
        listCards[key] = structuredClone(products.find(product => product.id === key));
        listCards[key].quantity = 1;
        orderQuantity.innerText = parseInt(orderQuantity.innerText) + 1;
    }
    if (sideMenuStatus === sideMenuStatusesEnum.Order) {
        reloadCart();
    }
}

function changeQuantity(key, q) {
    if (q === 0) {
        delete listCards[key];
    } else {
        listCards[key].quantity = q;
        listCards[key].price = q * products.find(product => product.id === key).price;
    }
    orderQuantity.innerText = q;
    if (sideMenuStatus === sideMenuStatusesEnum.Order) {
        reloadCart();
    }
}
