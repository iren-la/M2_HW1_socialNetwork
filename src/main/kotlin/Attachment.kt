interface Attachment {
    val type: String
}
class PhotoAttachment (
    override val type: String,
    val photo: Photo
) : Attachment

class AudioAttachment (
    override val type: String,
    val audio: Audio
) : Attachment

class VideoAttachment (
    override val type: String,
    val video: Video
) : Attachment

class LinkAttachment (
    override val type: String,
    val link: Link
) : Attachment

class DocAttachment (
    override val type: String,
    val doc: Doc
) : Attachment

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