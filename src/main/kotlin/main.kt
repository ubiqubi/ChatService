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

    // Функция добавления сообщения в чат
    fun addMessage(message: Message): Int {
        val key: List<Int> = listOf(message.senderID, message.recipientID)
        val newMessage = message.copy(id = lastID++)

        if (!chats.containsKey(key) && !chats.containsKey(key.reversed())) {
            chats[key] = mutableListOf(newMessage)
        } else {
            chats.asSequence()
                .filter { it.key.contains(message.senderID) && it.key.contains(message.recipientID) }
                .forEach { (k, v) -> chats[k] = v.plus(newMessage).toMutableList() }
        }
        return chats.size
    }

    // Функция удаления сообщения
    fun deleteMessage(messageID: Int): Boolean {
        // Ищем сообщение
        val findet = chats.values
            .asSequence()
            .flatMap { it.asSequence() }
            .filter { it.id == messageID }
            .toMutableList()
            .firstOrNull()
        if (findet == null) {
            // Если не найдено возвращаем false
            return false
        } else {
            val up = chats.values
                .asSequence()
                .flatMap { it.asSequence() }
                .filter { it.id != messageID }
                .groupBy { listOf(it.senderID, it.recipientID) }
                .mapValues { it.value.toMutableList() }
            chats = up.toMutableMap()
            // Если найдено возвращаем true
            return true
        }
    }


    // Функция удаления чата по ID
    fun deleteChatByID(chatID: List<Int>): Boolean {
        val filteredChats = chats.asSequence()
            .filter { it.key == chatID || it.key == chatID.reversed() }
            .firstOrNull()
        if (filteredChats != null) {
            chats.remove(filteredChats.key)
            return true
        }
        println("Чата с данным id не существует!")
        return false
    }

    // Функция редактирования сообщения
    fun editMessage(updatedMessage: Message): Boolean {
        chats.asSequence()
            .forEach { (_, value) ->
                value.asSequence()
                    .forEach { message ->
                        if (message.id == updatedMessage.id) {
                            // Обновляем сообщение с указанным идентификатором
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
        // Если ID не найдено
        println("Сообщение с таким id не существует!")
        return false
    }

    // Функция получения списка чатов
    fun getChatList(): MutableMap<Int, MutableList<Message>> {
        return chats.entries
            .asSequence()
            .mapIndexed { index, (_, value) -> (index + 1) to value }
            .toMap()
            .toMutableMap()
    }

    // Функция получения сообщений из чата (id отправителя и получателя, id сообщения, кол-во сообщений для показа после id)
    fun getMessagesFromChat(chatID: List<Int>, lastMessageID: Int, numberOfMessages: Int): List<Message> {
        val chatMessageList = chats[chatID] ?: return emptyList() // Проверяем наличие чата с указанным id
        val filteredMessages = chatMessageList.asSequence()
            .dropWhile { it.id < lastMessageID } // Пропускаем сообщения с меньшим id
            .take(numberOfMessages) // Берем необходимое количество сообщений

        filteredMessages.forEach {
            it.copy(readStatus = true) // Помечаем сообщение как прочитанное
        }
        return filteredMessages.toList()
    }

    // Функция получения непрочитанных чатов пользователя
    fun getUnreadChats(userID: Int): MutableList<List<Message>> {
        return chats
            .filter { it.key.contains(userID) } // отфильтровать список чатов, содержащий указанного пользователя
            .values
            .filter { messages -> messages.any { !it.readStatus } } // отфильтровать список сообщений с readstatus = false
            .toMutableList() // преобразовать результат в mutableList
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
        id = 3,
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
    val message5 = Message(
        dateTime = formatted,
        text = "сообщение5",
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
    chatService.addMessage(message5)
    // Редактируем сообщение
    chatService.editMessage(updatedMessage)
    println()
    println(
        "Получаем список непрочитанных чатов \n" +
                chatService.getUnreadChats(1)
    )
    println()
    println(
        " Получаем сообщение из чата 1 2 \n" +
                chatService.getMessagesFromChat(listOf(2, 1), 1, 3)
    )
    println()
    // Удаляем сообщение
    chatService.deleteMessage(2)
    println(chatService.deleteMessage(5))
    println(
        " Удаляем сообщение 2 \n" +
                chatService.getMessagesFromChat(listOf(2, 1), 1, 3)
    )
    println()
    println(
        "Получаем список чатов \n" +
                chatService.getChatList().keys
    )
    // Удаляем чат
    chatService.deleteChatByID(listOf(1, 2))
    println(
        "Удаляем чат \n" +
                chatService.getChatList().keys
    )
}

