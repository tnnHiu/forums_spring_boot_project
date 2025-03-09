
// Lưu trạng thái sidebar
const sidebar = document.querySelector('.sidebar');
const toggleSidebarButton = document.getElementById('toggleSidebar');
const isSidebarCollapsed = localStorage.getItem('sidebarCollapsed') === 'true';

if (isSidebarCollapsed) {
    sidebar.classList.add('collapsed');
}

toggleSidebarButton.addEventListener('click', () => {
    sidebar.classList.toggle('collapsed');
    localStorage.setItem('sidebarCollapsed', sidebar.classList.contains('collapsed'));
});





// Hiển thị thông báo với Toast
function showToast(message, type = "success") {
    const toast = new bootstrap.Toast(document.getElementById("toast"));
    const toastBody = document.getElementById("toastBody");
    const toastHeader = document.querySelector(".toast-header strong");

    toastHeader.textContent = type === "success" ? "Thành công" : "Lỗi";
    toastBody.textContent = message;
    document.getElementById("toast").classList.remove("bg-success", "bg-danger");
    if (type === "success") document.getElementById("toast").classList.add("bg-success");
    else document.getElementById("toast").classList.add("bg-danger");
    toast.show();
}


// Thêm Danh Mục
document.getElementById('addCategoryForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const categoryName = document.getElementById('categoryName').value;
    const categoryDescription = document.getElementById('categoryDescription').value;

    const category = {
        categoryName: categoryName,
        description: categoryDescription
    };

    fetch('/admin/category/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(category)
    })
        .then(response => response.json())
        .then(data => {
            if (data) {



                // Hiển thị thông báo thành công
                showToast('Thêm danh mục thành công!', 'success');


            } else {
                showToast('Có lỗi xảy ra khi thêm danh mục.', 'error');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            showToast('Có lỗi xảy ra khi thêm danh mục.', 'error');
        });
});

// Xóa Danh Mục
function deleteCategory(categoryId) {
    if (confirm("Bạn có chắc chắn muốn xóa danh mục này?")) {
        fetch(`/admin/categories/delete/${categoryId}`, {
            method: 'DELETE'
        })
            .then(response => {
                if (response.ok) {
                    showToast('Xóa danh mục thành công!', 'success');
                    setTimeout(() => {
                        location.reload();
                    }, 2000);
                } else {
                    showToast('Có lỗi xảy ra khi xóa danh mục.', 'error');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                showToast('Có lỗi xảy ra khi xóa danh mục.', 'error');
            });
    }
}

// Sửa danh mục
function openEditModal(categoryId) {
    fetch(`/admin/categories/edit/${categoryId}`)
        .then(response => response.json())
        .then(data => {
            if (data) {
                document.getElementById("editCategoryId").value = data.categoryId;
                document.getElementById("editCategoryName").value = data.categoryName;
                document.getElementById("editCategoryDescription").value = data.description;

                const editCategoryModal = new bootstrap.Modal(document.getElementById("editCategoryModal"));
                editCategoryModal.show();
            } else {
                showToast('Không tìm thấy thông tin danh mục.', 'error');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            showToast('Có lỗi xảy ra khi tải danh mục.', 'error');
        });
}

function saveCategory() {
    const categoryId = document.getElementById("editCategoryId").value;
    const categoryName = document.getElementById("editCategoryName").value;
    const categoryDescription = document.getElementById("editCategoryDescription").value;

    const updatedCategory = {
        categoryName: categoryName,
        description: categoryDescription
    };

    fetch(`/admin/categories/update/${categoryId}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(updatedCategory)
    })
        .then(response => {
            if (response.ok) {
                showToast('Cập nhật danh mục thành công!', 'success');
            } else {
                response.text().then(text => showToast(text, 'error'));
            }
        })
        .catch(error => {
            console.error('Error:', error);
            showToast('Có lỗi xảy ra khi lưu thay đổi danh mục.', 'error');
        });
}






