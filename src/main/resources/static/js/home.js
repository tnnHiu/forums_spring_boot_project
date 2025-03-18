// Lấy phần tử button
const scrollToTopBtn = document.getElementById("scrollToTopBtn");

// Khi cuộn xuống 300px thì hiển thị nút
window.onscroll = function () {
    if (document.documentElement.scrollTop > 1000) {
        scrollToTopBtn.style.display = "block";
    } else {
        scrollToTopBtn.style.display = "none";
    }
};

// Khi click vào nút, cuộn lên đầu trang
scrollToTopBtn.addEventListener("click", function () {
    window.scrollTo({
        top: 0,
        behavior: "smooth"
    });
});

document.addEventListener("DOMContentLoaded", function () {
    const likeButtons = document.querySelectorAll(".like-btn");
    const saveButtons = document.querySelectorAll(".save-btn");

    likeButtons.forEach(button => {
        button.addEventListener("click", function () {
            this.classList.toggle("liked");
        });
    });

    saveButtons.forEach(button => {
        button.addEventListener("click", function () {
            this.classList.toggle("saved");
        });
    });
});