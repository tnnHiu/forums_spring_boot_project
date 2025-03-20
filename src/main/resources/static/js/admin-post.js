// Existing sidebar code
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

// Add this code for view buttons
document.addEventListener('DOMContentLoaded', function() {
    // Get all view buttons
    const viewButtons = document.querySelectorAll('.btn-view');

    // Add click event to each button
    viewButtons.forEach(button => {
        button.addEventListener('click', function() {
            const postId = this.getAttribute('data-post-id');
            const viewModal = new bootstrap.Modal(document.getElementById('viewPostModal' + postId));
            viewModal.show();
        });
    });
});