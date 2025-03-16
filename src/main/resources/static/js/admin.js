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

