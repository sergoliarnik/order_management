let openOrder = document.querySelector('.order');
let closeOrder = document.querySelector('.closeOrder');
let openInprogress = document.querySelector('.inprogress');
let openReady = document.querySelector('.ready');
let list = document.querySelector('.list');
let listCard = document.querySelector('.listCard');
let body = document.querySelector('body');
let total = document.querySelector('.total');
let quantity = document.querySelector('.quantity');
let makeOrder = document.querySelector('.makeOrder')

makeOrder.addEventListener('click', () => {
    body.classList.remove('active')
    listCards = [];
    reloadCard();
})
openOrder.addEventListener('click', () => {
    body.classList.add('active');
})
openInprogress.addEventListener('click', () => {
    body.classList.add('active');
})
openReady.addEventListener('click', () => {
    body.classList.add('active');
})
closeOrder.addEventListener('click', () => {
    body.classList.remove('active')
})
let products = [
    {
        id: 1,
        name: 'Product name 1',
        image: 'shopping-cart.svg',
        price: 200
    },
    {
        id: 2,
        name: 'Product name 2',
        image: 'shopping-cart.svg',
        price: 200
    },
    {
        id: 3,
        name: 'Product name 3',
        image: 'shopping-cart.svg',
        price: 200
    },
    {
        id: 4,
        name: 'Product name 4',
        image: 'shopping-cart.svg',
        price: 200
    },
    {
        id: 5,
        name: 'Product name 5',
        image: 'shopping-cart.svg',
        price: 200
    },
    {
        id: 6,
        name: 'Product name 2',
        image: 'shopping-cart.svg',
        price: 200
    },
    {
        id: 7,
        name: 'Product name 3',
        image: 'shopping-cart.svg',
        price: 200
    },
    {
        id: 8,
        name: 'Product name 4',
        image: 'shopping-cart.svg',
        price: 200
    },
    {
        id: 9,
        name: 'Product name 5',
        image: 'shopping-cart.svg',
        price: 200
    },
];

let listCards = [];
function initApp(){
    products.forEach((value, key) => {
        let newDiv = document.createElement('div');
        newDiv.classList.add('item');
        newDiv.innerHTML = `
            <img src="image/${value.image}"/>
            <div class="title">${value.name}</div>
            <div class="price">${value.price.toLocaleString()}</div>
            <button onclick="addToCard(${key})">Add To Card</button>
        `
        list.appendChild(newDiv)
    })
}
initApp();

function addToCard(key){
    if(listCards[key] == null){
        listCards[key] =structuredClone(products[key]);
        listCards[key].quantity = 1;
    }
    reloadCard();
}

function reloadCard(){
    listCard.innerHTML = '';
    let count = 0;
    let totalPrice = 0;
    listCards.forEach((value, key) => {
        totalPrice = totalPrice + value.price;
        count = count + value.quantity;

        if (value != null){
            let newDiv = document.createElement('li');
            newDiv.innerHTML = `
            <div><img src="image/${value.image}"></div>
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
    quantity.innerText = count;
}
function changeQuantity(key, quantity){
    if (quantity === 0){
        delete listCards[key];
    }else{
        listCards[key].quantity = quantity;
        listCards[key].price = quantity * products[key].price;
    }
    reloadCard();
}
