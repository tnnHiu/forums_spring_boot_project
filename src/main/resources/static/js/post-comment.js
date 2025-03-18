$(document).ready(function () {
    console.log("jQuery loaded and ready!");
    $('#commentForm').submit(function (event) {
        event.preventDefault();
        console.log("Form submitted: " + $(this).serialize());
        $.ajax({
            url: $(this).attr('action'), type: 'POST', data: $(this).serialize(), success: function (response) {
                console.log("Response received: ", response);
                $('#commentList').html(response);
                $('#textAreaExample').val('');
            }, error: function (xhr, status, error) {
                console.error("AJAX error: ", status, error);
                alert("Có lỗi xảy ra khi gửi bình luận: " + error);
            }
        });
    });
});

$(document).ready(function () {
    $('#loadMoreBtn').on('click', function () {
        let postId = $('input[name="postId"]').val();
        let offset = parseInt($(this).data('offset'));
        $.ajax({
            url: `/post/${postId}/load-more-comments`, type: 'GET', data: {offset: offset}, success: function (data) {
                $('#commentList').append(data);
                let newOffset = offset + 3;
                $('#loadMoreBtn').data('offset', newOffset);
                let totalComments = parseInt($('#totalCommentsHidden').val());
                if (newOffset >= totalComments) {
                    $('#loadMoreWrapper').hide();
                }
            }
        });
    });
});