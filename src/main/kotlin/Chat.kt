object ChatService {
    private var chats = mutableMapOf<Set<User>, Chat>()
    private var counterMessage = 0
    private var users = mutableListOf<User>()

    //добавление юзера
    fun addUser (name: String): User {
        val user = User (id = users.size + 1, name = name)
        users.add(user)
        return user
    }

    //добавление нового сообщения и создание чата
    fun addMessage(fromUserId: User, toUserId: User, text: String): Message {
        val message = Message(
            id = counterMessage + 1,
            fromUserId = fromUserId,
            toUserId = toUserId,
            text = text
        )
        counterMessage += 1
        chats.getOrPut(setOf(fromUserId, toUserId)) {Chat(chats.size + 1, mutableListOf())}.messages += message
        return message
    }

    //получение списка чатов пользователя
    fun getChats(user: User) = chats.filterKeys { it: Set<User> -> it.contains(user)}

    //получение количества непрочитанных сообщений для пользователя
    fun getUnreadChatsCount (user: User) = chats.asSequence()
        .filter { it.key.contains(user) && it.value.messages.any { message: Message -> !message.isRead } }
        .count ()

    //получить сообщения из чата с определенным пользователем
    fun getMessagesChat (users: Set<User>, count: Int) = chats
        .getValue(users).messages
        .asSequence()
        .filter { it.isActive }
        .take(count)
        .onEach { message: Message -> message.isRead = true}
        .toList()

    //получить последнии собщения из всех чатов пользователя
    fun getLastMessages (user: User) = chats.asSequence()
        .filter {  it: Map.Entry<Set<User>, Chat> -> it.key.contains(user) }
        .map { it: Map.Entry<Set<User>, Chat> -> it.value.messages.last().text }
        .toList()
        .ifEmpty { listOf("No message") }


    //удалить чат
    fun deleteChat (users: Set<User>): Int {
        try {
            val chat = chats[users] ?: throw ChatNotFoundException ("No chat")
            chat.isActive = false
            chat.messages.onEach { it.isActive = false }
            return 1
        } catch (e: ChatNotFoundException) {
            return -1
        }
    }

    //удалить сообщение в чате
    fun deleteMessage (users: Set<User>, messageId: Int): Int {
        try {
            val chat = chats[users] ?: throw MessagesNotFoundException ("No message or message is deleted")
            chat.messages
                .asSequence()
                .filter {it.id == messageId && it.isActive }
                .onEach { it.isActive = false }
            return 1
        } catch (e: MessagesNotFoundException) {
            return -1
        }
    }

    fun clear() {
        chats.clear()
        users.clear()
        counterMessage = 0
    }
}

data class Chat (
    val id: Int,
    val messages: MutableList<Message>,
    var isActive: Boolean = true
)

data class Message (
    val id: Int,
    val fromUserId: User,
    val toUserId: User,
    var text: String,
    var isActive: Boolean = true,
    var isRead: Boolean = false
)

data class User (
    val id: Int,
    val name: String
)

class ChatNotFoundException(message: String): RuntimeException(message)
class MessagesNotFoundException(message: String): RuntimeException(message)