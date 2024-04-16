import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class ChatServiceTest {

    @Before
    fun clearBeforeTest() {
        ChatService.clear()
    }
    @Test
    fun addUser() {
        val user = ChatService.addUser("user")
        assertEquals(user.id > 0, true)
    }
    @Test
    fun addMessageAndChat() {
        val user1 = ChatService.addUser("user1")
        val user2 = ChatService.addUser("user2")
        val result = ChatService.addMessage(user1, user2, "New message")
        assertEquals(result.id==1, true)
    }

    @Test
    fun addMessageForCreatedChat() {
        val user1 = ChatService.addUser("user1")
        val user2 = ChatService.addUser("user2")
        ChatService.addMessage(user1, user2, "New message for new chat")
        val result = ChatService.addMessage(user2, user1, "New message for created chat")
        assertEquals(result.id==2, true)
    }

    @Test
    fun getChatsForUser() {
        val user1 = ChatService.addUser("user1")
        val user2 = ChatService.addUser("user2")
        val user3 = ChatService.addUser("user2")
        ChatService.addMessage(user1, user3, "Message for chat 1")
        ChatService.addMessage(user2, user3, "Message for chat 2")
        val result = ChatService.getChats(user3)
        assertEquals(result.size == 2, true)
    }

    @Test
    fun getUnreadChatsCount() {
        val user1 = ChatService.addUser("user1")
        val user2 = ChatService.addUser("user2")
        val user3 = ChatService.addUser("user3")
        ChatService.addMessage(user1, user3, "Message for chat 1")
        ChatService.addMessage(user2, user3, "Message for chat 2")
        val result = ChatService.getUnreadChatsCount(user3)
        assertEquals(result == 2, true)
    }

    @Test
    fun getMessagesChat() {
        val user1 = ChatService.addUser("user1")
        val user2 = ChatService.addUser("user2")
        ChatService.addMessage(user1, user2, "First message")
        ChatService.addMessage(user2, user1, "Second message")
        val result = ChatService.getMessagesChat(setOf(user1, user2), 5)
        assertEquals(result.size == 2, true)
    }

    @Test
    fun getLastMessages() {
        val user1 = ChatService.addUser("user1")
        val user2 = ChatService.addUser("user2")
        val user3 = ChatService.addUser("user3")
        ChatService.addMessage(user1, user3, "Message for chat 1")
        ChatService.addMessage(user2, user3, "Message for chat 2")
        val result = ChatService.getLastMessages(user3)
        assertEquals(result.size == 2, true)
    }

    @Test
    fun deleteChat() {
        val user1 = ChatService.addUser("user1")
        val user2 = ChatService.addUser("user2")
        ChatService.addMessage(user1, user2, "Message for chat 1")
        val result = ChatService.deleteChat(setOf(user1, user2))
        assertEquals(result == 1, true)
    }

    @Test
    fun deleteUnrealChat() {
        val user1 = ChatService.addUser("user1")
        val user2 = ChatService.addUser("user2")
        val result = ChatService.deleteChat(setOf(user1, user2))
        assertEquals(result == -1, true)
    }

    @Test
    fun deleteMessage() {
        val user1 = ChatService.addUser("user1")
        val user2 = ChatService.addUser("user2")
        ChatService.addMessage(user1, user2, "Message for chat 1")
        val result = ChatService.deleteMessage(setOf(user1, user2), 1)
        assertEquals(result == 1, true)
    }

    @Test
    fun deleteUnrealMessage() {
        val user1 = ChatService.addUser("user1")
        val user2 = ChatService.addUser("user2")
        val result = ChatService.deleteMessage(setOf(user1, user2), 1)
        assertEquals(result == -1, true)
    }
}