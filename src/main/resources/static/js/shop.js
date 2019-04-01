document.querySelector(".rest-sign").onmouseenter=showShopInfo;
document.querySelector(".rest-sign").onmouseleave=hideShopInfo;
document.querySelector(".rest-name").onmouseenter=showShopInfo;
document.querySelector(".rest-name").onmouseleave=hideShopInfo;
function showShopInfo() {
    document.querySelector(".rest-detail-info").style.display="block";
}
function hideShopInfo() {
    document.querySelector(".rest-detail-info").style.display="none";
}

for(let btn of document.getElementsByClassName("add2cart")){
    btn.onclick = ()=>{
        let id = btn.previousElementSibling.innerHTML;
        let num = btn.previousElementSibling.previousElementSibling.value;
        let request = new XMLHttpRequest();
        request.onreadystatechange = ()=>{
            if(request.readyState===4 && request.status===200){
                window.alert("加入购物车成功！");
            }
        };
        request.open("GET","http://localhost:8080/add2cart?id="+id+"&num="+num);
        request.send();
    }
}
