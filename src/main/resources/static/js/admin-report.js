document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll(".status").forEach(function (element) {
        let statusText = element.textContent.trim().toLowerCase();
        if (statusText === "resolved") {
            element.classList.add("text-success", "fw-bold");
        } else if (statusText === "pending") {
            element.classList.add("text-warning", "fw-bold");
        }
    });
});

// function toggleOldestFilter() {
//     const oldestCheckbox = document.getElementById('oldestCheckbox');
//     const oldestInput = document.getElementById('oldest');
//
//     if (oldestCheckbox.checked) {
//         // Nếu checkbox được chọn, thiết lập giá trị ngày tháng cụ thể
//         const now = new Date();
//         const formattedDate = now.toISOString().split('T')[0]; // Định dạng YYYY-MM-DD
//         oldestInput.value = formattedDate;
//     } else {
//         // Nếu checkbox không được chọn, xóa giá trị
//         oldestInput.value = '';
//     }
// }

function clearFilters() {
    document.querySelectorAll('input[type="checkbox"], input[type="radio"]').forEach(input => {
        input.checked = false;
    });
    // document.getElementById('oldest').value = ''; // Xóa giá trị trường ẩn
    document.querySelector('form').submit(); // Gửi form sau khi xóa bộ lọc
}