// Add to your script.js file
function updateNotificationCount() {
    fetch('/api/notifications/count')
        .then(response => response.text())
        .then(count => {
            const badge = document.getElementById('notification-badge');
            if (badge) {
                if (count > 0) {
                    badge.textContent = count;
                    badge.style.display = 'inline-block';
                } else {
                    badge.style.display = 'none';
                }
            }
        });
}

document.addEventListener('DOMContentLoaded', function() {
    // Get all notifications
    const allNotifications = document.querySelectorAll('#all .list-group-item');

    // Category containers
    const postContainer = document.getElementById('post-notifications');
    const commentContainer = document.getElementById('comment-notifications');
    const systemContainer = document.getElementById('system-notifications');

    // Empty messages
    const noPostMsg = document.getElementById('no-post-notifications');
    const noCommentMsg = document.getElementById('no-comment-notifications');
    const noSystemMsg = document.getElementById('no-system-notifications');

    // Counters for each type
    let postCount = 0;
    let commentCount = 0;
    let systemCount = 0;

    // Clone and categorize notifications
    allNotifications.forEach(notification => {
        const type = notification.getAttribute('data-type');
        const clone = notification.cloneNode(true);

        if (type === 'POST') {
            postContainer.appendChild(clone);
            postCount++;
        } else if (type === 'COMMENT') {
            commentContainer.appendChild(clone);
            commentCount++;
        } else {
            // GENERAL or any other type goes to system
            systemContainer.appendChild(clone);
            systemCount++;
        }
    });

    // Show/hide empty messages
    noPostMsg.classList.toggle('d-none', postCount > 0);
    noCommentMsg.classList.toggle('d-none', commentCount > 0);
    noSystemMsg.classList.toggle('d-none', systemCount > 0);
});