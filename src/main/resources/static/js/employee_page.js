const orderedList = document.getElementsByClassName("listCard")[0];
const inprogressList = document.getElementsByClassName("listCard")[1];
const readyList = document.getElementsByClassName("listCard")[2];

const INPROGRESS = 'INPROGRESS'
const DECLINED = 'DECLINED'
const ORDERED = 'ORDERED'
const READY = 'READY'

const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/gs-guide-websocket'
});

stompClient.onConnect = (frame) => {
    stompClient.subscribe('/topic/orders', (orders) => {
        reloadOrderedList(JSON.parse(orders.body));
    });
    stompClient.subscribe('/topic/inprogress', (orders) => {
        reloadInprogressList(JSON.parse(orders.body));
    });
    stompClient.subscribe('/topic/ready', (orders) => {
        reloadReadyList(JSON.parse(orders.body));
    });
};

stompClient.activate();


initApp();

function initApp(){
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "GET", "http://localhost:8080/employee/orders?status=ORDERED", false );
    xmlHttp.send( null );
    let orders = JSON.parse(xmlHttp.responseText);
    reloadOrderedList(orders)
    xmlHttp.open( "GET", "http://localhost:8080/employee/orders?status=INPROGRESS", false );
    xmlHttp.send( null );
    let inprogress = JSON.parse(xmlHttp.responseText);
    reloadInprogressList(inprogress)
    xmlHttp.open( "GET", "http://localhost:8080/employee/orders?status=READY", false );
    xmlHttp.send( null );
    let ready = JSON.parse(xmlHttp.responseText);
    reloadReadyList(ready)
}

function reloadOrderedList(orders) {
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

function reloadInprogressList(inprogress) {
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

function reloadReadyList(ready) {
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
    xhr.open("PATCH", `http://localhost:8080/orders/${id}?status=${status}`, false);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send();
    listCards = [];
}