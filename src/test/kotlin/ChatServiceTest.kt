import org.junit.Test

import org.junit.Assert.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ChatServiceTest {

    // Добавляем пользователя успешно
    @Test
    fun addUserToData() {
        val ivan = User(
            id = 2,
            firstName = "Ivan",
            lastName = "Popov",
            emptyList(),
            emptyList()
        )
        val chatService = ChatService()

        // Проверяем результат
        val result = chatService.addUserToData(ivan)
        assertTrue(result)
    }

    // Добавляем пользователя неуспешно
    @Test
    fun addUserToData_False() {
        val ivan = User(
            id = 2,
            firstName = "Ivan",
            lastName = "Popov",
            emptyList(),
            emptyList()
        )
        val ivan1 = User(
            id = 2,
            firstName = "Ivan",
            lastName = "Popov",
            emptyList(),
            emptyList()
        )
        val chatService = ChatService()

        // Проверяем результат
        chatService.addUserToData(ivan)
        val result = chatService.addUserToData(ivan1)
        assertFalse(result)
    }

    // Отправляем сообщение
    @Test
    fun addMessage() {
        val time: LocalDateTime = LocalDateTime.now()
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
        val formatted: String = time.format(formatter)
        val chatService = ChatService()

        // Добавляем отправителя и получателя
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
        val message1 = Message(
            dateTime = formatted,
            text = "сообщение",
            senderID = 1,
            recipientID = 2
        )

        // Проверяем результат
        chatService.addUserToData(ivan)
        chatService.addUserToData(dima)
        val result = chatService.addMessage(message1)

        
        assertEquals(result, 1)
    }

    // Удаляем сообщение успешно
    @Test
    fun deleteMessage_True() {
        
        val time: LocalDateTime = LocalDateTime.now()
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
        val formatted: String = time.format(formatter)
        val chatService = ChatService()
        // Добавляем отправителя и получателя
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
        val message1 = Message(
            id = 1,
            dateTime = formatted,
            text = "сообщение",
            senderID = 1,
            recipientID = 2
        )

        
        chatService.addUserToData(ivan)
        chatService.addUserToData(dima)
        chatService.addMessage(message1)
        val result = chatService.deleteMessage(1)

        
        assertTrue(result)
    }

    // Удаляем сообщение неуспешно
    @Test
    fun deleteMessage_False() {
        
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
        val message1 = Message(
            id = 1,
            dateTime = formatted,
            text = "сообщение",
            senderID = 1,
            recipientID = 2
        )

        
        chatService.addUserToData(ivan)
        chatService.addUserToData(dima)
        chatService.addMessage(message1)
        val result = chatService.deleteMessage(5)

        
        assertFalse(result)
    }

    // Удаляем чат успешно
    @Test
    fun deleteChatById_True() {
        
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
        val message1 = Message(
            id = 1,
            dateTime = formatted,
            text = "сообщение",
            senderID = 1,
            recipientID = 2
        )

        
        chatService.addUserToData(ivan)
        chatService.addUserToData(dima)
        chatService.addMessage(message1)
        val chatKey = listOf(1, 2)
        val result = chatService.deleteChatByID(chatKey)

        
        assertTrue(result)
    }

    // Удаляем чат неуспешно
    @Test
    fun deleteChatById_False() {
        
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
        val message1 = Message(
            id = 1,
            dateTime = formatted,
            text = "сообщение",
            senderID = 1,
            recipientID = 2
        )

        
        chatService.addUserToData(ivan)
        chatService.addUserToData(dima)
        chatService.addMessage(message1)
        val chatKey = listOf(5, 2)
        val result = chatService.deleteChatByID(chatKey)
        assertFalse(result)
    }

    // Редактируем сообщение успешно
    @Test
    fun editingMessage_True() {
        
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
        val message1 = Message(
            id = 1,
            dateTime = formatted,
            text = "сообщение",
            senderID = 1,
            recipientID = 2
        )

        val updatedMessage = Message(
            id = 1,
            dateTime = formatted,
            text = "сообщение отредактировано",
            senderID = 1,
            recipientID = 2
        )

        chatService.addUserToData(ivan)
        chatService.addUserToData(dima)
        chatService.addMessage(message1)
        val result = chatService.editMessage(updatedMessage)
        assertTrue(result)
    }

    // Редактируем сообщение неуспешно
    @Test
    fun editingMessage_False() {
        
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
        val message1 = Message(
            id = 1,
            dateTime = formatted,
            text = "сообщение",
            senderID = 1,
            recipientID = 2
        )

        val updatedMessage = Message(
            id = 2,
            dateTime = formatted,
            text = "сообщение отредактировано",
            senderID = 1,
            recipientID = 2
        )

        chatService.addUserToData(ivan)
        chatService.addUserToData(dima)
        chatService.addMessage(message1)
        val result = chatService.editMessage(updatedMessage)
        assertFalse(result)
    }


// Получить список чатов
    @Test
    fun getChatList() {

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
    val message1 = Message(
        id = 1,
        dateTime = formatted,
        text = "сообщение",
        senderID = 1,
        recipientID = 2
    )

        chatService.addUserToData(ivan)
        chatService.addUserToData(dima)
        chatService.addMessage(message1)
        val result = chatService.getChatList()
        assertEquals(result, mutableMapOf(Pair((1), mutableListOf(message1))))
    }


    // Получаем сообщения из чата
    @Test
    fun getMessagesFromChat() {
        
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
        val message1 = Message(
            id = 1,
            dateTime = formatted,
            text = "сообщение",
            senderID = 1,
            recipientID = 2
        )

        chatService.addUserToData(ivan)
        chatService.addUserToData(dima)
        chatService.addMessage(message1)
        val result = chatService.getMessagesFromChat(listOf(1, 2), 1, 1)
        assertEquals(result, listOf(message1))
    }

    // Получаем непрочитанные сообщения из чата
    @Test
    fun getUnreadChats() {
        
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
        val message1 = Message(
            id = 1,
            dateTime = formatted,
            text = "сообщение",
            senderID = 1,
            recipientID = 2
        )

        chatService.addUserToData(ivan)
        chatService.addUserToData(dima)
        chatService.addMessage(message1)
        val result = chatService.getUnreadChats(2)

        
        assertTrue(result.size == 1)
    }
}