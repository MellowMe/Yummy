for (let input of document.getElementsByClassName("quantity")) {
    input.onchange = () => {
        let num = input.value;
        let restid = input.parentElement.parentElement.parentElement.firstElementChild.firstElementChild.innerHTML;
        if (num <= 0)
            input.value = 1;
        else if (num > 100)
            input.value = 100;
        else {
            if (!Number.isInteger(num)) {
                input.value = Math.round(num);
            }
            let id = input.previousElementSibling.value;
            let request = new XMLHttpRequest();
            request.onreadystatechange = () => {
                if (request.readyState === 4) {
                    location.reload(true);
                }
            };
            request.open("GET", "http://localhost:8080/resetCart?did=" + id + "&num=" + input.value+"&restid="+restid);
            request.send();
        }
    }
}

for (let btn of document.getElementsByClassName("delete-btn")) {
    btn.onclick = () => {
        let id = btn.parentElement.previousElementSibling.previousElementSibling.firstElementChild.value;
        let restid = btn.parentElement.parentElement.parentElement.firstElementChild.firstElementChild.innerHTML;
        let request = new XMLHttpRequest();
        request.onreadystatechange = () => {
            if (request.readyState === 4) {
                    location.reload(true);
            }
        };
        request.open("GET", "http://localhost:8080/resetCart?did=" + id+"&restid="+restid);
        request.send();
    }
}

for (let btn of document.querySelectorAll(".pay-btn")) {
    btn.onclick = () => {
        if (document.getElementById("tologin")) {
            alert("请先登录");
        } else {
            let money  = btn.nextElementSibling.innerHTML.substring(4) - document.querySelector(".exempt").innerHTML;
            document.querySelector(".money").innerHTML = money;
            document.querySelector("#money").innerHTML = money;
            document.querySelector(".restid").value = btn.parentElement.parentElement.firstElementChild.firstElementChild.innerHTML;
            document.querySelector("#main").style.opacity = "0.1";
            document.querySelector("#order-panel").style.display = "block";
        }
    };
}

document.querySelector(".order-close").onclick = () => {
    document.querySelector("#main").style.opacity = "1";
    document.querySelector("#order-panel").style.display = "none";
};

document.getElementById("address-write").onchange = ()=>{
    document.getElementById("address-write").nextElementSibling.checked = true;
};

document.querySelector(".make-order").onclick = () => {
    let aid,address="";
    for (let address of document.querySelector(".addresses").children) {
        if (address.lastElementChild.checked) {
            console.log(address);
            aid = address.firstElementChild.value;
        }
    }
    if(!parseInt(aid)){
        aid=0;
        address = document.getElementById("address-write").value;
        if(!address)
            return;
    }
    let restid = document.querySelector(".restid").value;
    let money = document.querySelector(".money").innerHTML;
    let request = new XMLHttpRequest();
    request.onreadystatechange = () => {
        if (request.readyState === 4) {
            if (request.status === 200) {
                document.querySelector(".oid").value = request.responseText;
                document.querySelector("#main").style.opacity = "0.1";
                document.querySelector("#pay-panel").style.display = "block";
                document.querySelector("#order-panel").style.display = "none";
            } else
                window.alert("下单失败");
        }
    };
    request.open("GET", "http://localhost:8080/makeOrder?restid=" + restid + "&money=" + money + "&aid=" + aid+"&address="+address);
    request.send();
};

document.getElementById("do-pay").onclick = () => {
    let oid = document.querySelector(".oid").value;
    let request = new XMLHttpRequest();
    request.onreadystatechange = () => {
        if (request.readyState === 4) {
            if (request.status === 200) {
                window.alert("支付成功");
                location= "/user/orders";
            }
        }
    };
    request.open("GET", "http://localhost:8080/user/pay?oid=" + oid);
    request.send();
};

document.querySelector(".pay-close").onclick = () => {
    document.querySelector("#main").style.opacity = "1";
    document.querySelector("#pay-panel").style.display = "none";
};
