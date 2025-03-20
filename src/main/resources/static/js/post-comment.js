$(document).ready(function () {
    console.log("jQuery loaded and ready!");

    $('#commentForm').submit(function (event) {
        event.preventDefault();
        console.log("Form submitted: " + $(this).serialize());
        $.ajax({
            url: $(this).attr('action'),
            type: 'POST',
            data: $(this).serialize(),
            success: function (response) {
                $('#commentsContainer').prepend($(response).find('#commentsContainer').html());
                $('#textAreaExample').val('');
                let totalComments = parseInt($('#totalCommentsHidden').val()) + 1;
                $('#totalCommentsHidden').val(totalComments);
            },
            error: function (xhr, status, error) {
                console.error("AJAX error: ", status, error);
                alert("Có lỗi xảy ra khi gửi bình luận: " + error);
            }
        });
    });

    $('#loadMoreBtn').on('click', function () {
        let postId = $('#postIdHidden').val();
        let offset = parseInt($(this).data('offset'));
        $.ajax({
            url: '/post/' + postId + '/load-more-comments',
            type: 'GET',
            data: {offset: offset},
            success: function (data) {
                $('#commentsContainer').append($(data).find('#commentsContainer').html());
                let newOffset = offset + 3;
                $('#loadMoreBtn').data('offset', newOffset);
                let totalComments = parseInt($('#totalCommentsHidden').val());
                if (newOffset >= totalComments) {
                    $('#loadMoreBtn').hide();
                }
            },
            error: function (xhr, status, error) {
                console.error("AJAX error: ", status, error);
            }
        });
    });
});