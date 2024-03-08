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
            canPin = true
        )

        val result: Int = WallService.add(newPost).postId

        assertEquals(result > 0, true)
    }

    @Test
    fun updateWithRealPost() {
        //добавление постов в массив
        WallService.add(Post(fromId = 1, ownerId = 1, date = 1709891079, text = "Новый пост", friendsOnly = true, canPin = true))
        WallService.add(Post(fromId = 2, ownerId = 1, date = 1709891095, text = "Новый пост 2", friendsOnly = true, canPin = true))

        //новый пост для изменений
        val editPost = Post(
            postId = 1,
            fromId = 1,
            ownerId = 1,
            date = 1709891097,
            text = "Изменённый текст",
            friendsOnly = true,
            canPin = true
        )

        val result = WallService.update(editPost)

        assertEquals(true, result)
    }

    @Test
    fun updateIncorrectPost() {
        //добавление постов в массив
        WallService.add(Post(fromId = 1, ownerId = 1, date = 1709891079, text = "Новый пост", friendsOnly = true, canPin = true))
        WallService.add(Post(fromId = 2, ownerId = 1, date = 1709891095, text = "Новый пост 2", friendsOnly = true, canPin = true))

        //новый пост с несуществующим postId
        val editPost = Post(
            postId = 5,
            fromId = 1,
            ownerId = 1,
            date = 1709891097,
            text = "Изменённый текст",
            friendsOnly = true,
            canPin = true
        )

        val result = WallService.update(editPost)

        assertEquals(false, result)
    }
}