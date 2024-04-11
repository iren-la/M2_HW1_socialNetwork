import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class NoteServiceTest {

    @Before
    fun clearBeforeTest() {
        NoteService.clear()
    }
    @Test
    fun addNewNote() {
        val result = NoteService.addNote("Title", "Text for new note")
        assertEquals(result>=0, true)
    }

    @Test
    fun deleteCorrectNote() {
        NoteService.addNote("Title", "Text for new note")
        val result = NoteService.deleteNote(0)
        assertEquals(1, result)
    }

    @Test
    fun deleteNotFoundNote() {
        val result = NoteService.deleteNote(5)
        assertEquals(-1,result)
    }

    @Test
    fun deleteNotActiveNote() {
        NoteService.addNote("Title", "Text for new note")
        NoteService.deleteNote(0)
        val result = NoteService.deleteNote(0)
        assertEquals(-2, result)
    }

    @Test
    fun editCorrectNote() {
        NoteService.addNote("Title", "Text for new note")
        val result = NoteService.editNote(0, "New title", "New text")
        assertEquals(1,result)
    }

    @Test
    fun editNotFoundNote() {
        val result = NoteService.editNote(0, "New title", "New text")
        assertEquals(-1,result)
    }

    @Test
    fun editNotActiveNote() {
        NoteService.addNote("Title", "Text for new note")
        NoteService.deleteNote(0)
        val result = NoteService.editNote(0, "New title", "New text")
        assertEquals(-2,result)
    }

    @Test
    fun getCorrectNotes() {
        NoteService.addNote("Title 1", "Text for 1 note")
        NoteService.addNote("Title 2", "Text for 2 note")
        NoteService.addNote("Title 3", "Text for 3 note")
        val noteIds = arrayOf(0, 2)
        val result = NoteService.getNotes(noteIds)
        assertEquals(result.size == 2,true)
    }

    @Test
    fun getNotFoundNotes() {
        NoteService.addNote("Title 1", "Text for 1 note")
        val noteIds = arrayOf(0, 2)
        val result = NoteService.getNotes(noteIds)
        assertEquals(result.size == 0,true)
    }

    @Test
    fun getNotActiveNotes() {
        NoteService.addNote("Title 1", "Text for 1 note")
        NoteService.addNote("Title 2", "Text for 2 note")
        NoteService.deleteNote(0)
        val noteIds = arrayOf(0, 1)
        val result = NoteService.getNotes(noteIds)
        assertEquals(result.size == 0,true)
    }

    @Test
    fun getNoteById() {
        NoteService.addNote("Title 1", "Text for 1 note")
        val result = NoteService.getNoteById(0)
        assertEquals(result.id >=0, true)
    }

    @Test(expected = NoteNotFoundException::class)
    fun getNotFoundNoteById() {
        NoteService.getNoteById(0)
    }

    @Test(expected = NoteIsNotActiveException::class)
    fun getNotActiveNotById() {
        NoteService.addNote("Title 1", "Text for 1 note")
        NoteService.deleteNote(0)
        NoteService.getNoteById(0)
    }

    @Test
    fun createCorrectComment() {
        NoteService.addNote("Title 1", "Text for 1 note")
        val result = NoteService.createComment(0, "Comment")
        assertEquals(result>=0, true)
    }
    @Test
    fun createCommentForNotFoundNote() {
        val result = NoteService.createComment(0, "Comment")
        assertEquals(-1, result)
    }

    @Test
    fun createCommentForNotActiveNote() {
        NoteService.addNote("Title 1", "Text for 1 note")
        NoteService.deleteNote(0)
        val result = NoteService.createComment(0, "Comment")
        assertEquals(-2, result)
    }

    @Test
    fun deleteCorrectComment() {
        NoteService.addNote("Title 1", "Text for 1 note")
        NoteService.createComment(0, "Comment")
        val result = NoteService.deleteComment(0)
        assertEquals(1, result)
    }

    @Test
    fun deleteNotFoundComment() {
        val result = NoteService.deleteComment(0)
        assertEquals(-1, result)
    }
    @Test
    fun deleteNotActiveComment() {
        NoteService.addNote("Title 1", "Text for 1 note")
        NoteService.createComment(0, "Comment")
        NoteService.deleteComment(0)
        val result = NoteService.deleteComment(0)
        assertEquals(-2, result)
    }

    @Test
    fun editCorrectComment() {
        NoteService.addNote("Title 1", "Text for 1 note")
        NoteService.createComment(0, "Comment")
        val result = NoteService.editComment(0, "Edit comment")
        assertEquals(1, result)
    }

    @Test
    fun editNotFoundComment() {
        val result = NoteService.editComment(0, "Edit comment")
        assertEquals(-1, result)
    }

    @Test
    fun editNotActiveComment() {
        NoteService.addNote("Title 1", "Text for 1 note")
        NoteService.createComment(0, "Comment")
        NoteService.deleteComment(0)
        val result = NoteService.editComment(0, "Edit comment")
        assertEquals(-2, result)
    }

    @Test
    fun getCorrectComments() {
        NoteService.addNote("Title 1", "Text for 1 note")
        NoteService.addNote("Title 2", "Text for 2 note")
        NoteService.createComment(0, "Comment 1")
        NoteService.createComment(1, "Comment 2")
        NoteService.createComment(0, "Comment 3")
        val result = NoteService.getComments(0)
        assertEquals(result.size == 2, true)
    }

    @Test
    fun getCommentsNotFoundNote() {
        val result = NoteService.getComments(0)
        assertEquals(result.size == 0, true)
    }

    @Test
    fun getCommentsNotActiveNote() {
        NoteService.addNote("Title 1", "Text for 1 note")
        NoteService.createComment(0, "Comment 1")
        NoteService.createComment(0, "Comment 2")
        NoteService.deleteNote(0)
        val result = NoteService.getComments(0)
        assertEquals(result.size == 0, true)
    }

    @Test
    fun restoreCorrectComment() {
        NoteService.addNote("Title 1", "Text for 1 note")
        NoteService.createComment(0, "Comment 1")
        NoteService.deleteComment(0)
        val result = NoteService.restoreComment(0)
        assertEquals(1,result)
    }

    @Test
    fun restoreNotFoundComment() {
        val result = NoteService.restoreComment(0)
        assertEquals(-1,result)
    }

    @Test
    fun restoreActiveComment() {
        NoteService.addNote("Title 1", "Text for 1 note")
        NoteService.createComment(0, "Comment 1")
        val result = NoteService.restoreComment(0)
        assertEquals(-2,result)
    }
}