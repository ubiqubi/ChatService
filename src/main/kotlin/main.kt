import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


// Описание класса пользователя
data class User(
    val id: Int, // идентификатор пользователя
    val firstName: String, // имя пользователя
    val lastName: String, // фамилия пользователя
    var incomingMessages: List<Message>, // входящие сообщения пользователя
    var outgoingMessages: List<Message> // исходящие сообщения пользователя
) {
    override fun toString(): String {
        return "UserID = $id, $firstName $lastName"
    }
}

// Описание класса сообщения
data class Message(
    val id: Int = 0, // идентификатор сообщения
    val dateTime: String, // дата и время отправки сообщения
    val text: String?, // текст сообщения
    val readStatus: Boolean = false, // статус прочтения сообщения
    val senderID: Int, // идентификатор отправителя
    val recipientID: Int // идентификатор автора сообщения
) {
    override fun toString(): String {
        return text.toString()
    }
}

// Описание класса сервиса чата
class ChatService {
    private var chats: MutableMap<List<Int>, MutableList<Message>> = mutableMapOf() // список чатов
    private var usersData = mutableListOf<User>() // данные пользователей
    private var lastID = 1 // последний идентификатор сообщения

    // Функция добавления пользователя
    fun addUserToData(user: User): Boolean {
        return if (usersData.contains(user)) {
            println("Пользователь с таким ID уже существует!")
            false
        } else {
            usersData.add(user)
            true
        }
    }

    fun chatList111(): MutableMap<List<Int>, MutableList<Message>> {
        return chats
    }

    // Функция добавления сообщения в чат
    fun addMessage(message: Message): Int {
        val key: List<Int> = listOf(message.senderID, message.recipientID)
        val newMessage = message.copy(id = lastID++)

        if (!chats.containsKey(key) && !chats.containsKey(key.reversed())) {
            chats[key] = mutableListOf(newMessage)
        } else {
            chats.forEach { (k, v) ->
                if (k.contains(message.senderID) && k.contains(message.recipientID)) {
                    chats[k] = v.plus(newMessage) as MutableList<Message>
                }
            }
        }
        return chats.size
    }

    // Функция удаления сообщения
    fun deleteMessage(messageID: Int): Boolean {
        val externalIterator = chats.iterator()
        externalIterator.forEach { entry ->
            val interiorIterator = entry.value.iterator()
            interiorIterator.forEach { message ->
                if (message.id == messageID) {
                    val newList = entry.value.filterNot { it.id == messageID } as MutableList<Message>
                    chats[entry.key] = newList
                    if (entry.value.isEmpty()) {
                        externalIterator.remove()
                    }
                    return true
                }
            }
        }
        println("Сообщение с таким ID не существует!")
        return false
    }

    // Функция удаления чата по ID
    fun deleteChatByID(chatID: List<Int>): Boolean {
        val iterator = chats.iterator()
        iterator.forEach {
            if (it.key == chatID || it.key == chatID.reversed()) {
                iterator.remove()
                return true
            }
        }
        println("Чата с данным ID не существует!")
        return false
    }

    // Функция редактирования сообщения
    fun editMessage(updatedMessage: Message): Boolean {
        chats.forEach { (_, value) ->
            value.forEach { message ->
                if (message.id == updatedMessage.id) {
                    val newMessage = message.copy(
                        dateTime = updatedMessage.dateTime,
                        text = updatedMessage.text,
                        readStatus = true
                    )
                    value[value.indexOf(message)] = newMessage
                    return true
                }
            }
        }
        println("Сообщение с таким ID не существует!")
        return false
    }

    // Функция получения списка чатов
    fun getChatList(): MutableMap<Int, MutableList<Message>> {
        val chatList = mutableMapOf<Int, MutableList<Message>>()
        var count = 1
        chats.forEach { (_, value) ->
            chatList[count] = value
            count += 1
        }
        return chatList
    }

    // Функция получения сообщений из чата (id отправителя и получателя, id сообщения, кол-во сообщений для показа после id)
    fun getMessagesFromChat(chatID: List<Int>, lastMessageID: Int, numberOfMessages: Int): List<Message> {
        var chatMessageList = listOf<Message>()
        var num = numberOfMessages
        chats.forEach { (key, value) ->
            if (key == chatID) {
                val size = value.filter { it.id >= lastMessageID }.size
                if (numberOfMessages >  size ) {
                    num = size
                }
                chatMessageList =
                    value.filter { it.id >= lastMessageID }.subList(0, num)

                chatMessageList.forEach { message ->
                    value[value.indexOf(message)] = message.copy(readStatus = true)
                }
            }
        }
        return chatMessageList
    }

    // Функция получения непрочитанных чатов пользователя
    fun getUnreadChats(userID: Int): MutableList<List<Message>> {
        val unreadChatList = mutableListOf<List<Message>>()

        chats.forEach { (key, value) ->
            if (key.contains(userID)) {
                val newList = value.filter { it.recipientID == userID && !it.readStatus }
                unreadChatList += newList
            }
        }

        val iterator = unreadChatList.iterator()
        iterator.forEach {
            if (it.isEmpty()) {
                iterator.remove()
            }
        }
        return unreadChatList
    }
}

fun main() {
    val time: LocalDateTime = LocalDateTime.now()
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
    val formatted: String = time.format(formatter)
    val chatService = ChatService()

    val ivan = User(
        id = 1,
        firstName = "Ivan",
        lastName = "Popov",
        emptyList(),
        emptyList()
    )

    val dima = User(
        id = 2,
        firstName = "Dima",
        lastName = "Petrov",
        emptyList(),
        emptyList()
    )
    val kola = User(
        id = 2,
        firstName = "Kola",
        lastName = "Petrov",
        emptyList(),
        emptyList()
    )


    val message1 = Message(
        dateTime = formatted,
        text = "сообщение1",
        senderID = 2,
        recipientID = 1
    )
    val message2 = Message(
        dateTime = formatted,
        text = "сообщение2",
        senderID = 2,
        recipientID = 1
    )
    val message3 = Message(
        dateTime = formatted,
        text = "сообщение3",
        senderID = 2,
        recipientID = 1
    )
    val message4 = Message(
        dateTime = formatted,
        text = "сообщение4",
        senderID = 1,
        recipientID = 3
    )
    val updatedMessage = Message(
        id = 1,
        dateTime = formatted,
        text = "сообщение 1 отредактировано",
        senderID = 2,
        recipientID = 1
    )

    // Добавляем пользователей
    chatService.addUserToData(ivan)
    chatService.addUserToData(dima)
    chatService.addUserToData(kola)

    // Добавляем сообщения
    chatService.addMessage(message1)
    chatService.addMessage(message2)
    chatService.addMessage(message3)
    chatService.addMessage(message4)
    // Редактируем сообщение
    chatService.editMessage(updatedMessage)
    // Получаем непрочитанные сообщения
    chatService.getUnreadChats(1)

    println(
        " Получаем сообщение из чата \n" +
                chatService.getMessagesFromChat(listOf(2, 1), 1, 3)
    )
    println()
    // Удаляем сообщение
    chatService.deleteMessage(2)
    println()
    println(
        " Удаляем сообщение \n" +
                chatService.getMessagesFromChat(listOf(2, 1), 1, 5)
    )
    println()
    println(
        "Получаем список чатов \n" +
                chatService.getChatList().keys)
    // Удаляем чат
    chatService.deleteChatByID(listOf(1,2))
    println(
        "Удаляем чат \n" +
                chatService.getChatList().keys)
    println()
    println(
        "Получаем список чатов \n" +
                chatService.getChatList().keys)
}
