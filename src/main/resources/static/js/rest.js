if(document.querySelector("#tologin")) {
    document.querySelector("#tologin").onclick = () => {
        document.querySelector("#main").style.opacity = "0.1";
        document.querySelector(".login-panel").style.display = "block";
    };

    document.querySelector("#toregister").onclick = () => {
        document.querySelector("#main").style.opacity = "0.1";
        document.querySelector(".register-panel").style.display = "block";
    };

    document.querySelector(".login-close").onclick = () => {
        document.querySelector("#main").style.opacity = "1";
        document.querySelector(".login-panel").style.display = "none";
    };

    document.querySelector(".register-close").onclick = () => {
        document.querySelector("#main").style.opacity = "1";
        document.querySelector(".register-panel").style.display = "none";
    };

    document.querySelector("#login-btn").onclick = () => {
        let name = document.getElementById("login-name").value;
        let password = document.getElementById("login-psw").value;
        if (name && password) {
            let request = new XMLHttpRequest();
            request.onreadystatechange = () => {
                if (request.readyState === 4) {
                    if (request.status === 200) {
                        location.reload(true);
                    } else {
                        window.alert("登录失败：商家码或密码错误");
                    }
                }
            };
            let data = new FormData();
            data.append("code", name);
            data.append("password", password);
            request.open("Post", "http://localhost:8080/rest/login");
            request.send(data);
        }
    };

    document.querySelector("#register-btn").onclick = () => {
        let psw = document.getElementById("psw").value;
        let location = document.getElementById("location").value;
        let name = document.getElementById("name").value;
        let phone = document.getElementById("phone").value;
        let request = new XMLHttpRequest();
        request.onreadystatechange = () => {
            if (request.readyState === 4) {
                if (request.status === 200) {
                    document.querySelector("#main").style.opacity = "1";
                    document.querySelector(".register-panel").style.display = "none";
                    alert("注册成功,你的商家码为"+request.responseText+",商家码将用于登录，请将它记下来");
                } else {
                    window.alert("注册失败");
                }
            }
        };
        let data = new FormData();
        data.append("name", name);
        data.append("location", location);
        data.append("password", psw);
        data.append("phone", phone);
        request.open("Post", "http://localhost:8080/rest/register");
        request.send(data);
    };
}