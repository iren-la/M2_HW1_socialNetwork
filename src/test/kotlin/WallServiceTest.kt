import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class WallServiceTest {
    @Before
    fun clearBeforeTest() {
        WallService.clear()
    }

    @Test
    fun addNewPost() {
        val newPost = Post(
            fromId = 1,
            ownerId = 1,
            date = 1709891079,
            text = "Новый пост",
            friendsOnly = true,
            canPin = true,
            replyOwnerId = null,
            replyPostId = null,
            attachment = null,
        )

        val result: Int = WallService.add(newPost).postId

        assertEquals(result > 0, true)
    }

    @Test
    fun updateWithRealPost() {
        //добавление постов в массив
        WallService.add(Post(fromId = 1, ownerId = 1, date = 1709891079, text = "Новый пост", friendsOnly = true, canPin = true, replyOwnerId = null, replyPostId = null, attachment = null))
        WallService.add(Post(fromId = 2, ownerId = 1, date = 1709891095, text = "Новый пост 2", friendsOnly = true, canPin = true, replyOwnerId = null, replyPostId = null, attachment = null))

        //новый пост для изменений
        val editPost = Post(
            postId = 1,
            fromId = 1,
            ownerId = 1,
            date = 1709891097,
            text = "Изменённый текст",
            friendsOnly = true,
            canPin = true,
            replyOwnerId = null,
            replyPostId = null,
            attachment = null
        )

        val result = WallService.update(editPost)

        assertEquals(true, result)
    }

    @Test
    fun updateIncorrectPost() {
        //добавление постов в массив
        WallService.add(Post(fromId = 1, ownerId = 1, date = 1709891079, text = "Новый пост", friendsOnly = true, canPin = true, replyOwnerId = null, replyPostId = null, attachment = null))
        WallService.add(Post(fromId = 2, ownerId = 1, date = 1709891095, text = "Новый пост 2", friendsOnly = true, canPin = true, replyOwnerId = null, replyPostId = null, attachment = null))

        //новый пост с несуществующим id
        val editPost = Post(
            postId = 5,
            fromId = 1,
            ownerId = 1,
            date = 1709891097,
            text = "Изменённый текст",
            friendsOnly = true,
            canPin = true,
            replyOwnerId = null,
            replyPostId = null,
            attachment = null
        )

        val result = WallService.update(editPost)

        assertEquals(false, result)
    }

    @Test
    fun addCommentForRealPost () {
        //добавление нового поста
        WallService.add(Post(fromId = 1, ownerId = 1, date = 1709891079, text = "Новый пост", friendsOnly = true, canPin = true, replyOwnerId = null, replyPostId = null, attachment = null))

        //добавление нового комментария
        val newComment = Comment(
            id = 1,
            fromId = 1,
            date = 132828,
            text = "new comment",
            post = null
        )

        WallService.addComment(1,newComment)
        val result = true

        assertEquals(true, result)
    }

    @Test(expected = PostNotFoundException::class)
    fun shouldThrow() {
        //добавление нового комментария
        val newComment = Comment(
            id = 1,
            fromId = 1,
            date = 132828,
            text = "new comment",
            post = null
        )

        WallService.addComment(1, newComment)
    }

    @Test
    fun addNewReport() {
        //добавление нового поста
        WallService.add(Post(fromId = 1, ownerId = 1, date = 1709891079, text = "Новый пост", friendsOnly = true, canPin = true, replyOwnerId = null, replyPostId = null, attachment = null))

        //добавление нового комментария
        WallService.addComment(1, Comment(id = 1, fromId = 1, date = 132828, text = "new comment", post = null))

        WallService.addReportComment(1, 1, 1, 2)
        val result = true

        assertEquals(true, result)
    }

    @Test(expected = CommentNotFoundException::class)
    fun addReportForIncorrectComment() {
        WallService.addReportComment(1, 1, 1, 2)
    }

    @Test(expected = ReasonNotFoundException::class)
    fun addReportWithIncorrectReason() {
        //добавление нового поста
        WallService.add(Post(fromId = 1, ownerId = 1, date = 1709891079, text = "Новый пост", friendsOnly = true, canPin = true, replyOwnerId = null, replyPostId = null, attachment = null))

        //добавление нового комментария
        WallService.addComment(1, Comment(id = 1, fromId = 1, date = 132828, text = "new comment", post = null))

        WallService.addReportComment(1, 1, 1, 10)
    }
}