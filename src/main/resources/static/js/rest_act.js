for (let btn of document.querySelectorAll(".deliver")) {
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
        request.open("GET","http://localhost:8080/rest/deliver?oid="+oid);
        request.send();
    }
}

for (let btn of document.querySelectorAll(".done")) {
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
        request.open("GET","http://localhost:8080/rest/done?oid="+oid);
        request.send();
    }
}