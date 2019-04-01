for (let btn of document.getElementsByClassName("rest")) {
    btn.onmouseenter = () => {
        btn.style.backgroundColor = "#f7f7f7";
    };
    btn.onmouseleave = () => {
        btn.style.backgroundColor = "#fff";
    };
    btn.onclick = () => {
        window.open("http://localhost:8080/restaurant?id=" + btn.firstElementChild.innerHTML);
    }
}

for (let btn of document.getElementsByClassName("rest-cate")) {
    btn.onclick = () => {
        for (let btn2 of document.getElementsByClassName("rest-cate")) {
            btn2.style.backgroundColor = "#fff";
        }
        btn.style.backgroundColor = "#f7f7f7";
        let cate = btn.innerHTML;
        let request = new XMLHttpRequest();
        request.onreadystatechange = () => {
            if (request.readyState === 4 && request.status===200) {
                document.querySelector(".rests").innerHTML = request.responseText;
            }
        };
        request.open("Get","http://localhost:8080/restaurants?cate="+cate);
        request.send();
    }
}

