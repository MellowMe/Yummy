for (let btn of document.querySelectorAll(".do-delete")) {
    btn.onclick = () => {
        let oid = btn.parentElement.previousElementSibling.value;
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
        request.open("GET","http://localhost:8080/user/deleteOrder?oid="+oid);
        request.send();
    }
}

for (let btn of document.querySelectorAll(".do-pay")) {
    btn.onclick = () => {
        let oid = btn.parentElement.previousElementSibling.value;
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
        request.open("GET","http://localhost:8080/user/pay?oid="+oid);
        request.send();
    }
}

for (let btn of document.querySelectorAll(".do-cancel")) {
    btn.onclick = () => {
        let oid = btn.parentElement.previousElementSibling.value;
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
        request.open("GET","http://localhost:8080/user/cancelOrder?oid="+oid);
        request.send();
    }
}

for (let btn of document.querySelectorAll(".charge-back")) {
    btn.onclick = () => {
        let oid = btn.parentElement.previousElementSibling.value;
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
        request.open("GET","http://localhost:8080/user/chargeBack?oid="+oid);
        request.send();
    }
}
