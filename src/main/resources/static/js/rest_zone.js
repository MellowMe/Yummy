let sign = document.querySelector(".rest-sign");
let sign_input = document.querySelector("#sign");
sign.onclick = () => {
    sign_input.click();
};

sign_input.onchange = () => {
    let reader = new FileReader();
    reader.readAsDataURL(sign_input.files[0]);
    reader.onload = () => {
        sign.src = reader.result;
    }
};

document.querySelector(".reset").onclick = () => {
    document.querySelector(".reset-old").click();
    sign.src = "/img/" + sign.previousElementSibling.innerHTML;
};

let name = document.getElementById("rest-name").getAttribute("value");
let password = document.getElementById("password").getAttribute("value");
let address = document.getElementById("rest-location").getAttribute("value");
let phone = document.getElementById("rest-phone").getAttribute("value");
let openhours = document.getElementById("openhours").getAttribute("value");
let type = document.getElementById("type").previousElementSibling.innerHTML;

document.querySelector(".submit").onclick = () => {
    let data = new FormData(document.querySelector(".info"));
    let isSame = name === data.get("name")&&sign_input.files.length===0
        && address === data.get("location") && phone === data.get("phone")
        && openhours === data.get("openhours") && type === data.get("type");
    if (isSame && password === data.get("password")) {
        alert("没有改动");
    } else if(isSame){
        alert("暂时不能修改密码");
    }
    else {
        let request = new XMLHttpRequest();
        request.onreadystatechange = () => {
            if (request.readyState === 4) {
                if (request.status === 200) {
                    alert("保存成功，等待yummy通过");
                    location.reload(true);
                } else {
                    alert("操作失败");
                }
            }
        };
        request.open("POST", "http://localhost:8080/rest/modify");
        request.send(data);
    }
};