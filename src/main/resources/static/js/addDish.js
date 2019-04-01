let picture = document.querySelector(".picture");
let picture_input = document.querySelector("#picture");
picture.onclick = () => {
    sign_input.click();
};

picture_input.onchange = () => {
    let reader = new FileReader();
    reader.readAsDataURL(picture_input.files[0]);
    reader.onload = () => {
        picture.src = reader.result;
    }
};

document.getElementById("submit").onclick = ()=>{
    let data = new FormData(document.querySelector(".dish"));
    let request = new XMLHttpRequest();
    let good = data.get("name")&&data.get("picture")&&data.get("price")&&data.get("cate");
    if(good){
        request.onreadystatechange = () => {
            if (request.readyState === 4) {
                if (request.status === 200) {
                    alert("添加成功");
                    location.reload(true);
                } else {
                    alert("操作失败");
                }
            }
        };
        request.open("POST", "http://localhost:8080/rest/dish/add");
        request.send(data);
    }else{
        alert("信息不全");
    }

};