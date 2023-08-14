const orderedList = document.getElementsByClassName("listCard")[0];
const inprogressList = document.getElementsByClassName("listCard")[1];
const readyList = document.getElementsByClassName("listCard")[2];

let orders = [];
let inprogress = [];
let ready = [];

const INPROGRESS = 'INPROGRESS'
const DECLINED = 'DECLINED'
const ORDERED = 'ORDERED'
const READY = 'READY'

initApp();

function initApp(){
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "GET", "http://localhost:8080/getUserOrders?status=ORDERED", false ); // false for synchronous request
    xmlHttp.send( null );
    orders = JSON.parse(xmlHttp.responseText);
    xmlHttp.open( "GET", "http://localhost:8080/getUserOrders?status=INPROGRESS", false ); // false for synchronous request
    xmlHttp.send( null );
    inprogress = JSON.parse(xmlHttp.responseText);
    xmlHttp.open( "GET", "http://localhost:8080/getUserOrders?status=READY", false ); // false for synchronous request
    xmlHttp.send( null );
    ready = JSON.parse(xmlHttp.responseText);
}

reloadOrderedList()
function reloadOrderedList() {
    orderedList.innerHTML="";
    orders.forEach(value => {
        let newRow = document.createElement('li');
        newRow.innerHTML = `
                <div><img src="${value.productImageUrl}" alt=""></div>
                <div>${value.userName}</div>
                <div>${value.productName}</div>
                <div>
                    <button onclick="updateOrderStatus(${value.id}, ${INPROGRESS})">✓</button>
                    <button onclick="updateOrderStatus(${value.id}, ${DECLINED})">x</button>
                </div>    
            `
        orderedList.appendChild(newRow);
    })
}

reloadInprogressList()
function reloadInprogressList() {
    inprogressList.innerHTML="";
    inprogress.forEach(value => {
        let newRow = document.createElement('li');
        newRow.innerHTML = `
                <div><img src="${value.productImageUrl}" alt=""></div>
                <div>${value.userName}</div>
                <div>${value.productName}</div>
                <div>
                    <button onclick="updateOrderStatus(${value.id}, ${READY})">✓</button>
                    <button onclick="updateOrderStatus(${value.id}, ${ORDERED})">x</button>
                </div>    
            `
        inprogressList.appendChild(newRow);
    })
}

reloadReadyList()
function reloadReadyList() {
    readyList.innerHTML="";
    ready.forEach(value => {
        let newRow = document.createElement('li');
        newRow.innerHTML = `
                <div><img src="${value.productImageUrl}" alt=""></div>
                <div>${value.userName}</div>
                <div>${value.productName}</div>
                <div>
                    <button onclick="updateOrderStatus(${value.id}, ${INPROGRESS})">x</button>
                </div>    
            `
        readyList.appendChild(newRow);
    })
}

function updateOrderStatus(id, status){
    const xhr = new XMLHttpRequest();
    xhr.open("PATCH", `http://localhost:8080/updateOrderStatus/${id}?status=${status}`, false);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send();
    listCards = [];
    initApp();
    reloadOrderedList();
    reloadInprogressList();
    reloadReadyList();
}