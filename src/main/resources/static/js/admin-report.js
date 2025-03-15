document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll(".status").forEach(function (element) {
        let statusText = element.textContent.trim().toLowerCase();
        if (statusText === "resolved") {
            element.classList.add("text-success", "fw-bold");
        } else if (statusText === "pending") {
            element.classList.add("text-warning", "fw-bold");
        }
    });


    document.querySelectorAll(".btn-sort").forEach(function (button) {
        button.addEventListener("click", function () {
            this.classList.toggle("bg-primary");
            this.classList.toggle("text-white");
            this.classList.toggle("btn-outline-primary");
        });
    });

});