document.addEventListener("DOMContentLoaded", init);
let content = null;
let token = "";

function init() {
    content = document.getElementById("content");
    loadLoginForm();
}

function post(urlend, object, func, funcerr) {
    var xhr = new XMLHttpRequest();
    var urlstart = "http://localhost:8087";
    xhr.open("POST", urlstart + urlend, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.setRequestHeader('Csrf-Token', false);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status == 200 || xhr.status == 202){
                func(xhr.responseText);
            }
            else
                funcerr(xhr.responseText)
        }
    };
    var data = JSON.stringify(object);
    xhr.send(data);
}

function loadLoginForm() {
    content.innerHTML = '<h1>Вход</h1>' +
        '<form class="form" method="post">' +
            '<input type="text" id="login" name="nameOrEmail" placeholder="имя пользователя / email">' +
            '<input type="password" id="password" name="password" placeholder="пароль">' +
            '<input type="button" class="submit" id="submit_btn" value="вход">' +
        '</form>' +
        '<div class="links">' +
            '<button id="register">зарегистрироваться</a>' +
        '</div>';
    let submit = document.getElementById("submit_btn");
    submit.onclick = function(e) {
        console.log("sending...");
        let loginModel = {
            login: document.getElementById("login").value,
            password: document.getElementById("password").value
        }
        post("/signin", loginModel, function(response){
            console.log(response);
            post("/" + response + "/shoppinglist",null, function(resp){
                console.log(resp);
                loadItemsList(JSON.parse(resp));
            }, function(error) {
                alert(error);
            });
        }, function(err) {
            alert(err);
        });
    }
}

function loadItemsList(items) {
    content.innerHTML = "<h1>Список</h1>";
    items.forEach(item => {
        let btn = document.createElement("button");
        btn.innerHTML = item.name + " : " + item.count;
        if (item.bought)
            btn.className="item-bought";
        else
            btn.className="item";

        content.appendChild(btn);
    });
}

function loadRegisterForm() {
    content.innerHTML = '<h1>Регистрация</h1>' +
    '<form class="form">' +
        '<input type="text" name="name" placeholder="имя пользователя">' +
        '<input type="email" name="email" placeholder="email">' +
        '<input type="password" name="password" placeholder="пароль">' +
        '<input type="password" name="passwordConfirm" placeholder="подтверждение пароля">' +
        '<input class="submit" id="submit" type="submit" value="регистрация">' +
    '</form>' +
    '<div class="links">' +
        '<button id="register">зарегистрироваться</a>' +
    '</div>';

}