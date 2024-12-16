package pl.er.code.fwtheater.infrastructure.transaction

import org.springframework.transaction.TransactionDefinition


enum class TxPropagation {
    REQUIRED(TransactionDefinition.PROPAGATION_REQUIRED, "REQUIRED"),
    REQUIRES_NEW(TransactionDefinition.PROPAGATION_REQUIRES_NEW, "REQUIRES_NEW"),
    MANDATORY(TransactionDefinition.PROPAGATION_MANDATORY, "MANDATORY"),
    NEVER(TransactionDefinition.PROPAGATION_NEVER, "NEVER"),
    SUPPORTS(TransactionDefinition.PROPAGATION_SUPPORTS, "SUPPORTS"),
    NOT_SUPPORTED(TransactionDefinition.PROPAGATION_NOT_SUPPORTED, "NOT_SUPPORTED"),
    REQUIRED_READ_ONLY(TransactionDefinition.PROPAGATION_REQUIRED, "REQUIRED", true),
    REQUIRES_NEW_READ_ONLY(TransactionDefinition.PROPAGATION_REQUIRES_NEW, "REQUIRES_NEW", true);

    private val propagation: Int
    private val literal: String
    private val readonly: Boolean

    constructor(value: Int, literal: String) {
        this.propagation = value
        this.literal = name
        this.readonly = false
    }

    constructor(value: Int, literal: String, readonly: Boolean) {
        this.propagation = value
        this.literal = name
        this.readonly = readonly
    }

    fun getPropagation(): Int {
        return propagation
    }
}