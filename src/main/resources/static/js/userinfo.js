for (let btn of document.querySelectorAll(".delete")) {
    btn.onclick = () => {
        let aid = btn.previousElementSibling.value;
        let request = new XMLHttpRequest();
        request.onreadystatechange = () => {
            if (request.readyState === 4) {
                if (request.status === 200) {
                    alert("删除成功");
                    location.reload();
                }else{
                    alert("操作失败");
                }
            }
        };
        request.open("GET", "http://localhost:8080/user/deleteAddress?aid=" + aid);
        request.send();
    }
}

for (let btn of document.querySelectorAll(".add")) {
    btn.onclick = () => {
        let address = btn.previousElementSibling.value;
        if (address) {
            let request = new XMLHttpRequest();
            request.onreadystatechange = () => {
                if (request.readyState === 4) {
                    if (request.status === 200) {
                        alert("添加成功");
                        location.reload();
                    }else{
                        alert("操作失败");
                    }
                }
            };
            request.open("GET", "http://localhost:8080/user/addAddress?address=" + address);
            request.send();
        }
    }
}

for (let btn of document.querySelectorAll(".nullify")) {
    btn.onclick = () => {
        if(window.confirm("注销后账号无法找回，确定要注销吗？")){
            let request = new XMLHttpRequest();
            request.onreadystatechange = () => {
                if (request.readyState === 4) {
                    if (request.status === 200) {
                        location = "http://localhost:8080/";
                    }else{
                        alert("操作失败");
                    }
                }
            };
            request.open("GET", "http://localhost:8080/user/nullify");
            request.send();
        }

    }
}

for (let btn of document.querySelectorAll(".submit")) {
    btn.onclick = () => {
        let request = new XMLHttpRequest();
        let data = new FormData(document.querySelector(".info"));
        request.onreadystatechange = () => {
            if (request.readyState === 4) {
                if (request.status === 200) {
                    alert("修改成功");
                    location.reload();
                }else{
                    alert("操作失败");
                }
            }
        };
        request.open("POST", "http://localhost:8080/user/modify");
        request.send(data);
    }
}
