fun main(){
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
    val replyOwnerId: Int?,
    val replyPostId: Post?,
    val attachment: Array<Attachment>?
)

data class Comment(
    val id: Int,
    val fromId: Int,
    val date: Long,
    val text: String,
    val post: Post?
)

data class Like(
    val count: Int = 0,
    val userLikes: Boolean = false
)

data class reportComment(
    val id: Int = 0,
    val ownerId: Int,
    val commentId: Int?,
    val reason: Int
)

object WallService{
    private var posts = emptyArray<Post>()
    private var comments = emptyArray<Comment>()
    private var reportsComment = emptyArray<reportComment>()
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

    //поиск поста по Id
    fun findPostById (postId: Int): Post {
        for ((index, post) in posts.withIndex()) {
            if (post.postId == postId) {
                return post
            }
        }
        return throw PostNotFoundException("No post with postId = $postId")
    }

    //добавление комментария
    fun addComment(postId: Int, comment: Comment): Comment {
        val newComment: Comment = comment.copy(post = findPostById(postId))
        comments += newComment
        return newComment
    }

    fun findCommentById (commentId: Int): Comment {
        for ((index, comment) in comments.withIndex()) {
            if (comment.id == commentId) {
                return comment
            }
        }
        return throw CommentNotFoundException("No post with postId = $commentId")
    }

    fun getReasonForReport (reason: Int): Int {
        if (reason < 0 || reason == 7 || reason > 8) {
            return throw ReasonNotFoundException ("No reason with Id = $reason")
        } else {
            return reason
        }
    }

    fun addReportComment(id: Int, ownerId: Int, commentId: Int, reason: Int): reportComment {
        var newReport: reportComment = reportComment (
            id = id,
            ownerId = ownerId,
            commentId = findCommentById(commentId).id,
            reason = getReasonForReport(reason)
        )
        reportsComment += newReport
        return newReport
    }

    fun clear() {
        posts = emptyArray()
        comments = emptyArray()
        counter = 0
    }
}

class PostNotFoundException(message: String): RuntimeException(message)
class CommentNotFoundException(message: String): RuntimeException(message)
class ReasonNotFoundException(message: String): RuntimeException(message)