package pl.er.code.fwtheater.infrastructure.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import pl.er.code.fwtheater.application.port.dto.response.MessageType
import pl.er.code.fwtheater.application.port.dto.response.ResponseEnvelope
import pl.er.code.fwtheater.domain.exception.BusinessRuleException
import pl.er.code.fwtheater.domain.exception.InvalidStateException
import pl.er.code.fwtheater.infrastructure.common.ULIDGenerator
import pl.er.code.fwtheater.infrastructure.common.extensions.logger
import java.time.LocalDateTime

@RestControllerAdvice
class GlobalExceptionHandler() {
    companion object {
        val log = logger()
        val ulidGenerator: ULIDGenerator = ULIDGenerator()
    }

    @ExceptionHandler(InvalidStateException::class)
    fun handleBusinessRuleException(
        ex: InvalidStateException,
        request: WebRequest
    ): ResponseEntity<ResponseEnvelope<String, String>> {
        return ResponseEntity(
            ResponseEnvelope<String, String>(
                status = "failed",
                message = ex.message,
                messageType = MessageType.ERROR
            ), HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(BusinessRuleException::class)
    fun handleBusinessRuleException(
        ex: BusinessRuleException,
        request: WebRequest
    ): ResponseEntity<ResponseEnvelope<String, String>> {
        return ResponseEntity(
            ResponseEnvelope<String, String>(
                status = "failed",
                message = ex.message,
                messageType = MessageType.ERROR
            ), HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleAllExceptions(ex: Exception, request: WebRequest): ResponseEntity<ResponseEnvelope<String, String>> {
        val issueMarker = ulidGenerator.generate()
        log.error("Request has failed [${issueMarker}] ${request.getDescription(true)} -> ${ex.message}")
        return ResponseEntity(
            ResponseEnvelope<String, String>(
                status = "failed",
                message = "There has been a technical issue, please reach out to the administrator and hand him the issue code ${issueMarker}",
                messageType = MessageType.ERROR
            ), HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

    data class ErrorResponse(
        val timestamp: LocalDateTime,
        val message: String,
        val details: String
    )
}