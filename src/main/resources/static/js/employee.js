const orderedList = document.getElementsByClassName("listCard")[0];
const inprogressList = document.getElementsByClassName("listCard")[1];
const readyList = document.getElementsByClassName("listCard")[2];

let orders = {};
let inprogress = {};
let ready = {};

const INPROGRESS = 'INPROGRESS'
const DECLINED = 'DECLINED'
const ORDERED = 'ORDERED'
const READY = 'READY'

reloadOrderedList()
function reloadOrderedList() {
    const childrenArray = Array.from(orderedList.children);

    childrenArray.forEach(value => {
        let newDiv = document.createElement('div');
        newDiv.innerHTML = `
                <button onclick="updateOrderStatus(${parseInt(value.children[4].outerText)}, ${INPROGRESS})">✓</button>
                <button onclick="updateOrderStatus(${parseInt(value.children[4].outerText)}, ${DECLINED})">x</button>
            `
        value.replaceChild(newDiv, value.children[3]);
    })
}

reloadInprogressList()
function reloadInprogressList() {
    const childrenArray = Array.from(inprogressList.children);

    childrenArray.forEach(value => {
        let newDiv = document.createElement('div');
        newDiv.innerHTML = `

                <button onclick="updateOrderStatus(${parseInt(value.children[4].outerText)}, ${READY})">✓</button>
                <button onclick="updateOrderStatus(${parseInt(value.children[4].outerText)}, ${ORDERED})">x</button>
            `
        value.replaceChild(newDiv, value.children[3]);
    })
}

reloadReadyList()
function reloadReadyList() {
    const childrenArray = Array.from(readyList.children);

    childrenArray.forEach(value => {
        let newDiv = document.createElement('div');
        newDiv.innerHTML = `
                <button onclick="updateOrderStatus(${parseInt(value.children[4].outerText)}, ${INPROGRESS})">x</button>
            `
        value.replaceChild(newDiv, value.children[3]);
    })
}

initApp();

function initApp(){
    for (let i = 0; i < orderedList.children.length; i++) {
        let productImageUrl = orderedList.children[i].children[0].children[0].src;
        let userName = orderedList.children[i].children[1].outerText;
        let productName = orderedList.children[i].children[2].outerText;
        let id = parseInt(orderedList.children[i].children[4].outerText);
        orders[id] = {
            image: productImageUrl,
            userName: userName,
            productName: productName,
            id: id
        };
    }

    for (let i = 0; i < inprogressList.children.length; i++) {
        let productImageUrl = inprogressList.children[i].children[0].children[0].src;
        let userName = inprogressList.children[i].children[1].outerText;
        let productName = inprogressList.children[i].children[2].outerText;
        let id = parseInt(inprogressList.children[i].children[4].outerText);
        inprogress[id] = {
            image: productImageUrl,
            userName: userName,
            productName: productName,
            id: id
        };
    }

    for (let i = 0; i < readyList.children.length; i++) {
        let productImageUrl = readyList.children[i].children[0].children[0].src;
        let userName = readyList.children[i].children[1].outerText;
        let productName = readyList.children[i].children[2].outerText;
        let id = parseInt(readyList.children[i].children[4].outerText);
        ready[id] = {
            image: productImageUrl,
            userName: userName,
            productName: productName,
            id: id
        };
    }

}

function updateOrderStatus(id, status){
    const xhr = new XMLHttpRequest();
    xhr.open("PATCH", `http://localhost:8080/updateOrderStatus/${id}?status=${status}`, false);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send();
    listCards = [];
    reloadOrderedList();
    reloadInprogressList();
    reloadReadyList();
}