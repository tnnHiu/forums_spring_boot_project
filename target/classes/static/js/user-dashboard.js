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

// Xem thêm bài viết
document.addEventListener("DOMContentLoaded", function () {
    let cards = document.querySelectorAll(".card-for-you");
    let btnXemThem = document.getElementById("btnExpandForYou");

    // Ẩn tất cả các card trừ 4 cái đầu
    for (let i = 4; i < cards.length; i++) {
        cards[i].classList.add("hidden");
    }

    btnXemThem.addEventListener("click", function () {
        let isHidden = cards[4].classList.contains("hidden");

        if (isHidden) {
            cards.forEach(card => card.classList.remove("hidden"));
            btnXemThem.innerHTML = "Ẩn bớt <i class='bi bi-arrow-up'></i>";
        } else {
            for (let i = 4; i < cards.length; i++) {
                cards[i].classList.add("hidden");
            }
            btnXemThem.innerHTML = "Xem thêm <i class='bi bi-arrow-down'></i>";
        }
    });
});


// Xem thêm nổi bật
document.addEventListener("DOMContentLoaded", function () {
    let cards = document.querySelectorAll(".card-outstanding");
    let btnXemThem = document.getElementById("btnExpandOutstanding");

    // Ẩn tất cả các card trừ 4 cái đầu
    for (let i = 4; i < cards.length; i++) {
        cards[i].classList.add("hidden");
    }

    btnXemThem.addEventListener("click", function () {
        let isHidden = cards[4].classList.contains("hidden");

        if (isHidden) {
            cards.forEach(card => card.classList.remove("hidden"));
            btnXemThem.innerHTML = "Ẩn bớt <i class='bi bi-arrow-up'></i>";
        } else {
            for (let i = 4; i < cards.length; i++) {
                cards[i].classList.add("hidden");
            }
            btnXemThem.innerHTML = "Xem thêm <i class='bi bi-arrow-down'></i>";
        }
    });
});

