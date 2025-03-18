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

document.addEventListener('DOMContentLoaded', function() {
    const hashtagInputs = document.querySelectorAll('.hashtag-input');

    hashtagInputs.forEach(input => {
        input.addEventListener('input', function() {
            const regex = /^(#)?[a-zA-Z0-9_]+$/;

            if (regex.test(this.value)) {
                this.classList.remove('is-invalid');
                this.classList.add('is-valid');
            } else {
                this.classList.remove('is-valid');
                this.classList.add('is-invalid');
            }
        });
    });

    const hashtagForms = document.querySelectorAll('.hashtag-form');
    hashtagForms.forEach(form => {
        form.addEventListener('submit', function(event) {
            const hashtagInput = this.querySelector('.hashtag-input');
            const regex = /^(#)?[a-zA-Z0-9_]+$/;

            if (!regex.test(hashtagInput.value)) {
                event.preventDefault();
                hashtagInput.classList.add('is-invalid');
            }
        });
    });
});