object NoteService{
    private var notes = mutableListOf<Note>()
    private var noteComments = mutableListOf<NoteComment>()

    fun addNote(title: String, text: String): Int{
        val newNote = Note (
            id = notes.size,
            title = title,
            text = text,
            isActive = true
        )
        notes.add(newNote)
        return newNote.id
    }

    fun deleteNote(noteId: Int): Int {
        try {
            val note = getNoteById(noteId)
            val commentsNote = getComments(note.id)
            for (comment in commentsNote) {
                comment.isActive = false
            }
            note.isActive = false
            return 1
        } catch (e: NoteNotFoundException) {
            return -1
        } catch (e: NoteIsNotActiveException) {
            return -2
        }
    }

    fun editNote(noteId: Int, title: String, text: String): Int {
        try {
            val note = getNoteById(noteId)
            note.title = title
            note.text = text
            return 1
        } catch (e: NoteNotFoundException) {
            return -1
        } catch (e: NoteIsNotActiveException) {
            return -2
        }
    }

    fun getNotes(noteIds: Array<Int>): List<Note>{
        var findNotes = mutableListOf<Note>()
        try {
            for (noteId in noteIds) {
                var note = getNoteById(noteId)
                findNotes.add(note)
            }
            return findNotes
        } catch (e: NoteNotFoundException) {
            findNotes.clear()
            return findNotes
        } catch (e: NoteIsNotActiveException) {
            findNotes.clear()
            return findNotes
        }
    }

    fun getNoteById(noteId: Int): Note {
        for (note in notes) {
            if (note.id == noteId && note.isActive) {
                return note
            } else if (note.id == noteId && !note.isActive) {
                return throw NoteIsNotActiveException("Note is deleted")
            }
        }
        return throw NoteNotFoundException("Note not found with id = $noteId")
    }

    fun createComment(noteId: Int, message: String): Int {
        try {
            val newComment = NoteComment (
                id = noteComments.size,
                note = getNoteById(noteId),
                message = message,
                isActive = true
            )
            noteComments.add(newComment)
            return newComment.id
        } catch (e: NoteNotFoundException) {
            return -1
        } catch (e: NoteIsNotActiveException) {
            return -2
        }
    }

    fun deleteComment(commentId: Int): Int {
        try {
            val comment = getCommentById(commentId)
            comment.isActive = false
            return 1
        } catch (e: CommentNoteNotFoundException) {
            return -1
        } catch (e: CommentNoteIsNotActiveException) {
            return -2
        }
    }

    fun editComment(commentId: Int, message: String): Int {
        try {
            val comment = getCommentById(commentId)
            comment.message = message
            return 1
        } catch (e: CommentNoteNotFoundException) {
            return -1
        } catch (e: CommentNoteIsNotActiveException) {
            return -2
        }
    }

    fun getComments(noteId:Int): List<NoteComment> {
        var commentsList = mutableListOf<NoteComment>()
        try {
            for (comment in noteComments) {
                if (comment.note.id == noteId) {
                    commentsList.add(comment)
                }
            }
            return commentsList
        } catch (e: NoteNotFoundException) {
            commentsList.clear()
            return commentsList
        } catch (e: NoteIsNotActiveException) {
            commentsList.clear()
            return commentsList
        }
    }
    private fun getCommentById(commentId: Int): NoteComment {
        for (comment in noteComments) {
            if (comment.id == commentId && comment.isActive) {
                return comment
            } else if (comment.id == commentId && !comment.isActive) {
                return throw CommentNoteIsNotActiveException("Comment is deleted")
            }
        }
        return throw CommentNoteNotFoundException("Comment not found with id = $commentId")
    }

    fun restoreComment(commentId: Int): Int {
        try {
            for (comment in noteComments) {
                if (comment.id == commentId && !comment.isActive) {
                    comment.isActive = true
                    return 1
                } else if (comment.id == commentId && comment.isActive) {
                    return throw CommentNoteIsNotActiveException("Comment is active")
                }
            }
            return throw CommentNoteNotFoundException("Comment not found with id = $commentId")
        } catch (e: CommentNoteNotFoundException) {
            return -1
        } catch (e: CommentNoteIsNotActiveException) {
            return -2
        }
    }

    fun clear() {
        notes.clear()
        noteComments.clear()
    }
}

data class Note (
    val id: Int,
    var title: String,
    var text: String,
    var isActive: Boolean
)
data class NoteComment (
    val id: Int,
    val note: Note,
    var message: String,
    var isActive: Boolean
)

class NoteNotFoundException(message: String): RuntimeException(message)
class NoteIsNotActiveException(message: String): RuntimeException(message)
class CommentNoteNotFoundException(message: String): RuntimeException(message)
class CommentNoteIsNotActiveException(message: String): RuntimeException(message)
