if(document.querySelector("#tologin")){
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
                        window.alert("登录失败：邮箱或密码错误");
                    }
                }
            };
            let data = new FormData();
            data.append("email", name);
            data.append("password", password);
            request.open("Post", "http://localhost:8080/user/login");
            request.send(data);
        }
    };

    document.querySelector("#register-btn").onclick = () => {
        let psw = document.getElementById("psw").value;
        let email = document.getElementById("email").value;
        let username = document.getElementById("register-name").value;
        let phone = document.getElementById("phone").value;
        let request = new XMLHttpRequest();
        request.onreadystatechange = () => {
            if (request.readyState === 4) {
                if (request.status === 200) {
                    document.querySelector("#main").style.opacity = "1";
                    document.querySelector(".register-panel").style.display = "none";
                    alert("注册成功，请尽快前往您的邮箱激活账户");
                } else {
                    window.alert("注册失败：该邮箱已被占用");
                }
            }
        };
        let data = new FormData();
        data.append("email", email);
        data.append("username", username);
        data.append("password", psw);
        data.append("phone", phone);
        request.open("Post", "http://localhost:8080/user/register");
        request.send(data);
    };
}


