document.querySelector("#tologin").onclick = () => {
    document.querySelector("#main").style.opacity = "0.1";
    document.querySelector(".login-panel").style.display = "block";
};

document.querySelector(".login-close").onclick = () => {
    document.querySelector("#main").style.opacity = "1";
    document.querySelector(".login-panel").style.display = "none";
};

document.querySelector("#login-btn").onclick = () => {
    let name = document.getElementById("login-name").value;
    let password = document.getElementById("login-psw").value;
    if (name && password) {
        let request = new XMLHttpRequest();
        request.onreadystatechange = () => {
            if (request.readyState === 4) {
                if (request.status === 200) {
                    document.querySelector("#main").style.opacity = "1";
                    document.querySelector(".login-panel").style.display = "none";
                    location.reload(true);
                } else {
                    window.alert("登录失败：名称或密码错误");
                }
            }
        };
        let data = new FormData();
        data.append("name", name);
        data.append("password", password);
        request.open("Post", "http://localhost:8080/admin/login");
        request.send(data);
    }
};

for (let btn of document.querySelectorAll(".refuse")) {
    btn.onclick = () => {
        let restid = btn.nextElementSibling.nextElementSibling.firstElementChild.firstElementChild.firstElementChild.innerHTML;
        let request = new XMLHttpRequest();
        request.onreadystatechange = () => {
            if (request.readyState === 4) {
                if (request.status === 200) {
                    location.reload(true);
                }else{
                    alert("操作失败");
                }
            }
        };
        request.open("GET","http://localhost:8080/admin/rest/modifyInfo?restid="+restid+"&accept="+"0");
        request.send();
    }
}

for (let btn of document.querySelectorAll(".accept")) {
    btn.onclick = () => {
        let restid = btn.nextElementSibling.firstElementChild.firstElementChild.firstElementChild.innerHTML;
        let request = new XMLHttpRequest();
        request.onreadystatechange = () => {
            if (request.readyState === 4) {
                if (request.status === 200) {
                    location.reload(true);
                }else{
                    alert("操作失败");
                }
            }
        };
        request.open("GET","http://localhost:8080/admin/rest/modifyInfo?restid="+restid+"&accept="+"1");
        request.send();
    }
}
