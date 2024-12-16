package pl.er.code.fwtheater.infrastructure.transaction

import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.TransactionStatus
import org.springframework.transaction.support.TransactionTemplate
import java.util.concurrent.Callable

class TransactionWrapper(private val transactionManager: PlatformTransactionManager) {
    private var requiredTransactionTemplate: TransactionTemplate? = null
    private val requiredTransactionTemplateName = "TX Wrapper - Required Transaction"
    private var requiresNewTransactionTemplate: TransactionTemplate? = null
    private val requiredNewTransactionTemplateName = "TX Wrapper - Requires New Transaction"
    private var requriesNewReadOnlyTransactionTemplate: TransactionTemplate? = null
    private val requiredNEwReadOnlyTransactionTemplateName = "TX Wrapper - Requires New Read Only Transaction"
    private val lock = Any()

    fun executeInTransaction(action: Runnable) {
        executeInTransaction(action, TxPropagation.REQUIRED)
    }

    fun executeInNewTransaction(action: Runnable) {
        executeInTransaction(action, TxPropagation.REQUIRES_NEW)
    }

    fun executeInNewReadOnlyTransaction(action: Runnable) {
        executeInTransaction(action, TxPropagation.REQUIRES_NEW_READ_ONLY)
    }

    fun <T> executeInTransaction(action: Callable<T>): T? {
        return executeInTransaction(action, TxPropagation.REQUIRED)
    }

    fun <T> executeInNewTransaction(action: Callable<T>): T? {
        return executeInTransaction(action, TxPropagation.REQUIRES_NEW)
    }

    fun <T> executeInNewReadOnlyTransaction(action: Callable<T>): T? {
        return executeInTransaction(action, TxPropagation.REQUIRES_NEW_READ_ONLY)
    }

    fun executeInTransaction(action: Runnable, propagation: TxPropagation) {
        val transactionTemplate = getTransactionTemplate(propagation)
        transactionTemplate.execute<Any> { status: TransactionStatus? ->
            action.run()
            null
        }
    }

    fun <T> executeInTransaction(action: Callable<T>, propagation: TxPropagation): T? {
        val transactionTemplate = getTransactionTemplate(propagation)
        return transactionTemplate.execute<T> { status: TransactionStatus? ->
            try {
                action.call()
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }
    }


    fun getRequiredTransactionTemplate(): TransactionTemplate {
        requiredTransactionTemplate?.let {
            return it // Return if already initialized
        }

        synchronized(lock) {
            if (requiredTransactionTemplate == null) {
                requiredTransactionTemplate = TransactionTemplate(transactionManager!!).apply {
                    propagationBehavior = TxPropagation.REQUIRED.getPropagation()
                    setName(requiredTransactionTemplateName)
                }
            }
            return requiredTransactionTemplate!!
        }
    }

    fun getRequiresNewTransactionTemplate(): TransactionTemplate {
        requiresNewTransactionTemplate?.let {
            return it // Return if already initialized
        }

        synchronized(lock) {
            if (requiresNewTransactionTemplate == null) {
                requiresNewTransactionTemplate = TransactionTemplate(transactionManager!!).apply {
                    propagationBehavior = TxPropagation.REQUIRES_NEW.getPropagation()
                    setName(requiredNewTransactionTemplateName)
                }
            }
            return requiresNewTransactionTemplate!!
        }
    }

    fun getRequiresNewReadOnlyTransactionTemplate(): TransactionTemplate {
        requriesNewReadOnlyTransactionTemplate?.let {
            return it // Return if already initialized
        }

        synchronized(lock) {
            if (requriesNewReadOnlyTransactionTemplate == null) {
                requriesNewReadOnlyTransactionTemplate = TransactionTemplate(transactionManager!!).apply {
                    propagationBehavior = TxPropagation.REQUIRES_NEW_READ_ONLY.getPropagation()
                    setName(requiredNEwReadOnlyTransactionTemplateName)
                }
            }
            return requiresNewTransactionTemplate!!
        }
    }


    private fun getTransactionTemplate(propagation: TxPropagation): TransactionTemplate {
        return when (propagation) {
            TxPropagation.REQUIRED -> getRequiredTransactionTemplate()
            TxPropagation.REQUIRES_NEW -> getRequiresNewTransactionTemplate()
            TxPropagation.REQUIRES_NEW_READ_ONLY -> getRequiresNewReadOnlyTransactionTemplate()
            else -> throw IllegalArgumentException("Unsupported transaction propagation: $propagation")
        }
    }
}