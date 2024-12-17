package pl.er.code.fwtheater.application.port.dto.response

import pl.er.code.fwtheater.domain.model.Page
import java.io.Serializable

enum class MessageType {
    INFO,
    WARNING,
    ERROR
}

enum class PaginationType {
    PAGE_BASE,
    ID_OFFSET
}

data class Paging<ID : Serializable>(
    val page: Int? = null,
    val pageSize: Int? = null,
    val totalItems: Int? = null,
    val totalPages: Int? = null,

    val startId: ID? = null,
    val limit: Int? = null,

    val type: PaginationType? = PaginationType.PAGE_BASE
)

data class ResponseEnvelope<T, ID : Serializable>(
    val status: String,
    val message: String? = null,
    val messageType: MessageType? = null,
    val data: T? = null,
    val messagesPerItem: Map<String, List<Message>>? = null,
    val paging: Paging<ID>? = null
) {

    data class Message(
        val text: String,
        val type: MessageType
    )

    fun hasMessages(): Boolean {
        return !messagesPerItem.isNullOrEmpty()
    }

    fun addMessagesForItem(itemKey: String, messages: List<Message>) {
        val updatedMessages = messagesPerItem?.toMutableMap() ?: mutableMapOf()
        updatedMessages[itemKey] = messages
        this.copy(messagesPerItem = updatedMessages)
    }

    fun addMessage(message: String, type: MessageType) {
        this.copy(message = message, messageType = type)
    }

    companion object {
        fun <D, ID : Serializable> fromPage(items: Page<D>): ResponseEnvelope<List<D>, ID> {
            return ResponseEnvelope(
                status = "success",
                data = items.content,
                paging = Paging(
                    page = items.number,
                    pageSize = items.size,
                    totalItems = items.totalElements.toInt(),
                    totalPages = items.totalPages
                )
            )
        }
    }
}
