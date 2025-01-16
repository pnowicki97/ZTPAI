const menuIcon = document.querySelector("#menu");
const menu = document.querySelector(".sidebar");


menuIcon.addEventListener("click", () => {
    menu.classList.toggle("show");
});