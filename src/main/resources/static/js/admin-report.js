 // Dữ liệu mẫu
 let categories = [
    { id: 1, name: 'Công nghệ', postCount: 10, description: 'Các bài viết về công nghệ' },
    { id: 2, name: 'Giáo dục', postCount: 5, description: 'Các bài viết về giáo dục' },
    { id: 3, name: 'Kinh doanh', postCount: 8, description: 'Các bài viết về kinh doanh' },
    { id: 4, name: 'Thể thao', postCount: 12, description: 'Các bài viết về thể thao' },
    { id: 5, name: 'Giải trí', postCount: 15, description: 'Các bài viết về giải trí' },
    { id: 6, name: 'Sức khỏe', postCount: 7, description: 'Các bài viết về sức khỏe' },
    { id: 7, name: 'Du lịch', postCount: 9, description: 'Các bài viết về du lịch' },
    { id: 8, name: 'Ẩm thực', postCount: 11, description: 'Các bài viết về ẩm thực' },
    { id: 9, name: 'Thời trang', postCount: 6, description: 'Các bài viết về thời trang' },
    { id: 10, name: 'Xe cộ', postCount: 4, description: 'Các bài viết về xe cộ' },
    { id: 11, name: 'Nhà đất', postCount: 3, description: 'Các bài viết về nhà đất' },
    { id: 12, name: 'Tài chính', postCount: 8, description: 'Các bài viết về tài chính' },
    { id: 13, name: 'Pháp luật', postCount: 5, description: 'Các bài viết về pháp luật' },
    { id: 14, name: 'Văn hóa', postCount: 7, description: 'Các bài viết về văn hóa' },
    { id: 15, name: 'Khoa học', postCount: 9, description: 'Các bài viết về khoa học' }
];

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


// Thêm danh mục
function addCategory(event) {
    event.preventDefault();
    const categoryName = document.getElementById('categoryName').value;
    const categoryDescription = document.getElementById('categoryDescription').value;
    if (!categoryName) {
        showToast('Vui lòng nhập tên danh mục!', 'danger');
        return;
    }
    const newCategory = {
        id: categories.length + 1,
        name: categoryName,
        postCount: 0,
        description: categoryDescription
    };
    categories.push(newCategory);
    renderTable(categories);
    renderPagination(categories.length); // Cập nhật phân trang sau khi thêm
    // Đóng modal
    const modal = bootstrap.Modal.getInstance(document.getElementById('addCategoryModal'));
    modal.hide();
    document.getElementById('categoryForm').reset();
    showToast('Thêm danh mục thành công!');
}

// Xóa danh mục
function deleteCategory(id) {
    const categoryToDelete = categories.find(category => category.id === id);
    if (!categoryToDelete) return;
    if (confirm(`Bạn có chắc chắn muốn xóa danh mục "${categoryToDelete.name}"?`)) {
        categories = categories.filter(category => category.id !== id);
        renderTable(categories);
        renderPagination(categories.length); // Cập nhật phân trang sau khi xóa
        showToast('Xóa danh mục thành công!');
    }
}

function editCategory(id) {
    const categoryToEdit = categories.find(category => category.id === id);
    if (!categoryToEdit) return;

    // Reset form trước khi hiển thị và set giá trị
    document.getElementById('editCategoryForm').reset();
    document.getElementById('editCategoryId').value = categoryToEdit.id;
    document.getElementById('editCategoryName').value = categoryToEdit.name;
    document.getElementById('editCategoryDescription').value = categoryToEdit.description;

    // Show modal
    const modal = new bootstrap.Modal(document.getElementById('editCategoryModal'));
    modal.show();
}

// Add saveCategory function to handle saving edited category
function saveCategory() {
const id = parseInt(document.getElementById('editCategoryId').value);
const name = document.getElementById('editCategoryName').value;
const description = document.getElementById('editCategoryDescription').value;

if (!name) {
    showToast('Vui lòng nhập tên danh mục!', 'danger');
    return;
}

// Find and update category
const categoryIndex = categories.findIndex(c => c.id === id);
if (categoryIndex !== -1) {
    categories[categoryIndex] = {
        ...categories[categoryIndex],
        name: name,
        description: description
    };
    
    // Close modal
    const modal = bootstrap.Modal.getInstance(document.getElementById('editCategoryModal'));
    modal.hide();
    
    // Re-render table and show success message
    renderTable(categories);
    renderPagination(categories.length);
    showToast('Cập nhật danh mục thành công!');
}
}
 




// Hiển thị toast
function showToast(message, type = 'success') {
    const toast = new bootstrap.Toast(document.getElementById('toast'));
    const toastBody = document.getElementById('toastBody');
    toastBody.textContent = message;
    toastBody.className = `toast-body ${type === 'success' ? 'bg-success text-white' : 'bg-danger text-white'}`;
    toast.show();
}


// Phân trang 5 bảng ghi mỗi trang
const itemsPerPage = 5;
const pagination = document.getElementById('pagination');
let currentPage = 1;

function renderPagination(totalItems) {
    const totalPages = Math.ceil(totalItems / itemsPerPage);
    pagination.innerHTML = '';
    for (let i = 1; i <= totalPages; i++) {
        const li = document.createElement('li');
        li.className = `page-item ${i === currentPage ? 'active' : ''}`;
        li.innerHTML = `<a class="page-link" href="#">${i}</a>`;
        li.addEventListener('click', () => {
            currentPage = i;
            renderTable(categories);
            renderPagination(categories.length);
        });
        pagination.appendChild(li);
    }
}

// Tìm kiếm danh mục
function filterTable() {
    const searchText = document.getElementById('searchInput').value.toLowerCase();
    const filteredCategories = categories.filter(category => {
        return category.name.toLowerCase().includes(searchText) ||
               category.description.toLowerCase().includes(searchText);
    });
    renderTable(filteredCategories);
    renderPagination(filteredCategories.length);
}

// Xóa bộ lọc tìm kiếm
function clearSearch() {
    document.getElementById('searchInput').value = '';
    renderTable(categories);
    renderPagination(categories.length);
}


let currentSortOrder = null; // null: no sort, 'asc': A-Z, 'desc': Z-A

// Sắp xếp danh mục
function sortTable() {
    const tbody = document.querySelector('#categoryTable tbody');
    const sortButtons = document.querySelectorAll('.sort-btn');

    if (currentSortOrder === 'asc' || currentSortOrder === null) {
        categories.sort((a, b) => b.name.toLowerCase().localeCompare(a.name.toLowerCase())); // Z-A
        currentSortOrder = 'desc';
    } else {
        categories.sort((a, b) => a.name.toLowerCase().localeCompare(b.name.toLowerCase())); // A-Z
        currentSortOrder = 'asc';
    }
    renderTable(categories);
    renderPagination(categories.length); // Cập nhật phân trang sau khi sắp xếp
}


function renderTable(data) {
    const tbody = document.querySelector('#categoryTable tbody');
    tbody.innerHTML = '';
    const start = (currentPage - 1) * itemsPerPage;
    const end = start + itemsPerPage;
    const paginatedData = data.slice(start, end);

    if (paginatedData.length === 0) {
        tbody.innerHTML = `<tr><td colspan="4">Không có danh mục nào.</td></tr>`;
        return;
    }


    paginatedData.forEach(category => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>${category.name}</td>
            <td>${category.postCount}</td>
            <td>${category.description}</td>
            <td>
                <button class="btn btn-outline-primary btn-sm btn-action btn-edit" data-category-id="${category.id}"><i class="bi bi-pencil"></i></button>
                <button class="btn btn-outline-primary btn-sm btn-action btn-delete" data-category-id="${category.id}"><i class="bi bi-trash"></i></button>
            </td>
        `;
        tbody.appendChild(tr);
    });

    // Gắn sự kiện cho nút sửa và xóa sau khi render bảng
    attachTableEvents();
}
function attachTableEvents() {
    const tbody = document.querySelector('#categoryTable tbody');

    // **Quan trọng: Xóa bỏ event listener cũ trước khi gắn mới**
    tbody.replaceWith(tbody.cloneNode(true)); // Clone tbody để loại bỏ event listeners cũ
    const newTbody = document.querySelector('#categoryTable tbody');


    newTbody.addEventListener('click', function(event) {
        if (event.target.closest('.btn-delete')) {
            const id = parseInt(event.target.closest('.btn-delete').dataset.categoryId);
            deleteCategory(id);
        } else if (event.target.closest('.btn-edit')) {
            const id = parseInt(event.target.closest('.btn-edit').dataset.categoryId);
            editCategory(id);
        }
    });
}


// Render dữ liệu ban đầu
renderTable(categories);
renderPagination(categories.length);

// Gắn sự kiện submit cho form Thêm Danh Mục
document.getElementById('categoryForm').addEventListener('submit', addCategory);