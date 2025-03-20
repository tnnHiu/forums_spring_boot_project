document.addEventListener('DOMContentLoaded', () => {
    const commentForm = document.getElementById('commentForm');
    const commentsContainer = document.getElementById('commentsContainer');
    const commentInput = document.getElementById('textAreaExample');
    const loadMoreBtn = document.getElementById('loadMoreBtn');
    const postIdHidden = document.getElementById('postIdHidden');
    const totalCommentsHidden = document.getElementById('totalCommentsHidden');

    if (commentForm) {
        commentForm.addEventListener('submit', async (event) => {
            event.preventDefault();

            const formData = new FormData(commentForm);

            try {
                const response = await fetch(commentForm.action, {
                    method: 'POST',
                    body: formData
                });

                if (!response.ok) throw new Error('Lỗi khi gửi bình luận');

                const html = await response.text();
                const parser = new DOMParser();
                const doc = parser.parseFromString(html, 'text/html');
                const newComments = doc.querySelector('#commentsContainer').innerHTML;

                commentsContainer.insertAdjacentHTML('afterbegin', newComments);
                commentInput.value = '';
                totalCommentsHidden.value = parseInt(totalCommentsHidden.value) + 1;

                // Khởi tạo lại các modal Bootstrap
                const newModals = commentsContainer.querySelectorAll('.modal');
                newModals.forEach(modal => {
                    new bootstrap.Modal(modal);
                });

            } catch (error) {
                console.error('Error:', error);
                alert('Có lỗi xảy ra khi gửi bình luận. Vui lòng thử lại.');
            }
        });
    }

    if (loadMoreBtn) {
        loadMoreBtn.addEventListener('click', async () => {
            const postId = postIdHidden.value;
            const offset = parseInt(loadMoreBtn.dataset.offset);

            try {
                const response = await fetch(`/post/${postId}/load-more-comments?offset=${offset}`, {
                    method: 'GET'
                });

                if (!response.ok) throw new Error('Lỗi khi tải bình luận');

                const html = await response.text();
                const parser = new DOMParser();
                const doc = parser.parseFromString(html, 'text/html');
                const moreComments = doc.querySelector('#commentsContainer').innerHTML;

                commentsContainer.insertAdjacentHTML('beforeend', moreComments);

                const newOffset = offset + 3;
                loadMoreBtn.dataset.offset = newOffset;

                const totalComments = parseInt(totalCommentsHidden.value);
                if (newOffset >= totalComments) {
                    loadMoreBtn.style.display = 'none';
                }

                // Khởi tạo lại các modal Bootstrap
                const newModals = commentsContainer.querySelectorAll('.modal');
                newModals.forEach(modal => {
                    new bootstrap.Modal(modal);
                });

            } catch (error) {
                console.error('Error:', error);
            }
        });
    }
});