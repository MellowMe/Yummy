for (let input of document.getElementsByClassName("dish-num")) {
    input.onchange = () => {
        let id = input.previousElementSibling.previousElementSibling.innerHTML;
        let request = new XMLHttpRequest();
        request.onreadystatechange = () => {
            if (request.readyState === 4) {
                if (request.status === 200){
                    alert("修改菜品数量成功");
                    location.reload(true);
                }else{
                    alert("操作失败");
                }
            }
        };
        request.open("GET", "http://localhost:8080/rest/dish/modifyNum?did=" + id + "&num=" + input.value);
        request.send();
    }
}

for (let btn of document.getElementsByClassName("delete")) {
    btn.onclick = () => {
        if(window.confirm("确定要删除这道菜品吗？")){
            let id = btn.previousElementSibling.innerHTML;
            let request = new XMLHttpRequest();
            request.onreadystatechange = () => {
                if (request.readyState === 4) {
                    if (request.status === 200) {
                        alert("删除菜品成功");
                        location.reload(true);
                    } else {
                        alert("操作失败");
                    }
                }
            };
            request.open("GET", "http://localhost:8080/rest/dish/delete?did=" + id);
            request.send();
        }
    }
}