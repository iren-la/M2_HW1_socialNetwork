sealed class Attachment (val type: String)
data class PhotoAttachment (val photo: Photo) : Attachment("photo")
data class AudioAttachment (val audio: Audio) : Attachment("audio")
data class VideoAttachment (val video: Video) : Attachment("video")
data class LinkAttachment (val link: Link) : Attachment("link")
data class DocAttachment (val doc: Doc) : Attachment("doc")

//классы объектов разных файлов
data class Photo(
    val id: Int,
    val ownerId: Int,
    val userId: Int,
    val text: String,
    val size: Int,
    val width: Int,
    val height: Int
)
data class Audio(
    val id: Int,
    val ownerId: Int,
    val artis: String,
    val title: String,
    val duration: Int
)
data class Video(
    val id: Int,
    val ownerId: Int,
    val title: String,
    val description: String,
    val duration: Int
)
data class Link(
    val url: String,
    val title: String,
    val caption: String?,
    val description: String
)
data class Doc(
    val id: Int,
    val ownerId: Int,
    val title: String,
    val size: Int,
    val ext: String
)