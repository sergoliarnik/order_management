let openOrder = document.querySelector('.order');
let closeOrder = document.querySelector('.closeOrder');
let openInprogress = document.querySelector('.inprogress');
let openReady = document.querySelector('.ready');
let list = document.querySelector('.list');
let listCard = document.querySelector('.listCard');
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

makeOrder.addEventListener('click', () => {
    body.classList.remove('active')
    const xhr = new XMLHttpRequest();
    xhr.open("POST", "http://localhost:8080/makeOrder", true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    const filteredList = listCards.filter(item => item !== null);
    xhr.send(JSON.stringify(filteredList));
    listCards = [];
    reloadCard();
})
openOrder.addEventListener('click', () => {
    reloadCard();
    sideMenuStatus = sideMenuStatusesEnum.Order;
    body.classList.add('active');
})

function reloadInprogress() {
    listCard.innerHTML = '';
    sideMenuName.textContent='Inprogress';
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "GET", "http://localhost:8080/products_in_order/INPROGRESS", false ); // false for synchronous request
    xmlHttp.send( null );
    let inprogressList = JSON.parse(xmlHttp.responseText);
    inprogressList.forEach(value => {
        if (value != null){
            let newDiv = document.createElement('li');
            newDiv.innerHTML = `
            <div><img src="${value.imageUrl}"></div>
            <div>${value.name}</div>
            <div>${value.price.toLocaleString()}</div>
            `
            listCard.appendChild(newDiv);
        }
    })
    inprogressQuantity.innerText = inprogressList.length;
}

openInprogress.addEventListener('click', () => {
    reloadInprogress();
    sideMenuStatus = sideMenuStatusesEnum.Inprogress;
    body.classList.add('active');
})

function reloadReady() {
    listCard.innerHTML = '';
    sideMenuName.textContent='Ready';
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "GET", "http://localhost:8080/products_in_order/READY", false ); // false for synchronous request
    xmlHttp.send( null );
    let readyList = JSON.parse(xmlHttp.responseText);
    readyList.forEach(value => {
        if (value != null){
            let newDiv = document.createElement('li');
            newDiv.innerHTML = `
            <div><img src="${value.imageUrl}"></div>
            <div>${value.name}</div>
            <div>${value.price.toLocaleString()}</div>
            `
            listCard.appendChild(newDiv);
        }
    })
    readyQuantity.innerText = readyList.length;
}

openReady.addEventListener('click', () => {
    reloadReady();
    sideMenuStatus = sideMenuStatusesEnum.Ready;
    body.classList.add('active');
})
closeOrder.addEventListener('click', () => {
    sideMenuStatus = sideMenuStatusesEnum.None;
    body.classList.remove('active')
})
let products = {};

let listCards = [];
function initApp(){
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "GET", "http://localhost:8080/products_in_order/INPROGRESS", false ); // false for synchronous request
    xmlHttp.send( null );
    let inprogressList = JSON.parse(xmlHttp.responseText);
    inprogressQuantity.innerText = inprogressList.length;

    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "GET", "http://localhost:8080/products_in_order/READY", false ); // false for synchronous request
    xmlHttp.send( null );
    let readyList = JSON.parse(xmlHttp.responseText);
    readyQuantity.innerText = readyList.length;

    for (let i = 0; i < list.children.length; i++) {
        let image = list.children[i].children[0].src;
        let name = list.children[i].children[1].outerText;
        let price = parseInt(list.children[i].children[2].outerText);
        let id = parseInt(list.children[i].children[3].outerText);
        products[id] = {
            image: image,
            name: name,
            price: price,
            id: id
        };
    }
}

initApp();

function addToCard(key){
    if(!(key in listCards)){
        listCards[key] = structuredClone(products[key]);
        listCards[key].quantity = 1;
    }
    if(sideMenuStatus === sideMenuStatusesEnum.Order){
        reloadCard();
    }
    orderQuantity.innerText = parseInt(orderQuantity.innerText) + 1;
}
function reloadCard(){
    listCard.innerHTML = '';
    sideMenuName.textContent='Cart';
    let count = 0;
    let totalPrice = 0;
    listCards.forEach((value, key) => {
        totalPrice = totalPrice + value.price;
        count = count + value.quantity;

        if (value != null){
            let newDiv = document.createElement('li');
            newDiv.innerHTML = `
            <div><img src="${value.image}"></div>
            <div>${value.name}</div>
            <div>${value.price.toLocaleString()}</div>
            <div>
                <button onclick="changeQuantity(${key}, ${value.quantity-1})">-</button>
                <div class="count">${value.quantity}</div>
                <button onclick="changeQuantity(${key}, ${value.quantity+1})">+</button>
            </div>
            `
            listCard.appendChild(newDiv);
        }
    })
    total.innerText = totalPrice.toLocaleString();
    orderQuantity.innerText = count;
}
function changeQuantity(key, q){
    if (q === 0){
        delete listCards[key];
    }else{
        listCards[key].quantity = q;
        listCards[key].price = q * products[key].price;
    }
    orderQuantity.innerText = q;
    if(sideMenuStatus === sideMenuStatusesEnum.Order){
        reloadCard();
    }
}
