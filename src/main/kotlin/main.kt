fun main(){
    //создание нового поста
    val newPost = Post(
        fromId = 1,
        ownerId = 1,
        date = 1709891079,
        text = "Новый пост",
        friendsOnly = true,
        canPin = true
    )

    //редактирование поста по postId
    val editPost = Post (
        postId = 1,
        fromId = 1,
        ownerId = 1,
        date = 1709891079,
        text = "Откорректированный пост",
        friendsOnly = false,
        canPin = true
    )
    WallService.add(newPost)
    WallService.update(editPost)
}

data class Post(
    val postId: Int = 0,
    val fromId: Int,
    val ownerId: Int,
    val date: Long,
    val text: String,
    val friendsOnly: Boolean = false,
    val postType: String = "post",
    val canPin: Boolean = true,
    val canDelete: Boolean = true,
    val canEdit: Boolean = true,
    val likes: Like = Like(),
    val comments: Comment = Comment(),
)

data class Comment(val count: Int = 0)

data class Like(
    val count: Int = 0,
    val userLikes: Boolean = false
)

object WallService{
    private var posts = emptyArray<Post>()
    private var counter: Int = 0

    //добавление нового поста и присвоение уникального id
    fun add(post: Post): Post {
        counter += 1
        val newPost = post.copy(postId = counter)
        posts += newPost
        return posts.last()
    }

    //обновление поста
    fun update(postToUpdate: Post): Boolean {
        for ((index, post) in posts.withIndex()){
            if (post.postId == postToUpdate.postId) {
                posts.set(index, postToUpdate)
                return true
            }
        }
        return false
    }
}