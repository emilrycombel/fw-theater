package pl.er.code.fwtheater.adapter.outbound.persistence.repository


import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import pl.er.code.fwtheater.adapter.outbound.persistence.entity.TheaterHEntity
import pl.er.code.fwtheater.domain.model.Theater
import pl.er.code.fwtheater.domain.model.search.PageSearchCriteria
import pl.er.code.fwtheater.infrastructure.transaction.TransactionWrapper
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class TheaterHRepositoryImplTest :
    AbstractBaseHRepositoryImplTest<Theater, TheaterHEntity, String, PageSearchCriteria, TheaterHRepositoryImpl>() {

    @Autowired
    lateinit var repository: TheaterHRepositoryImpl

    @Autowired
    lateinit var transactionWrapper: TransactionWrapper

    override fun repository(): TheaterHRepositoryImpl {
        return repository
    }

    override fun newNonPersistentInstance(): Theater {
        val newItem: Theater = repository().newInstance()

        newItem.name = faker.finalFantasyXIV().character()

        return newItem
    }

    override fun updatePersistedInstance(instance: Theater): Theater {
        instance.name = faker.movie().name()

        return instance
    }

    @Order(1)
    @Test
    fun `Should have the main theater`() {
        val theater = repository().findById("01HYYC1D19ABCDEF1234567890")

        assertEquals("Fast and Furious Cinema", theater?.name)
    }

    @Test
    fun `Should update persisted entity`() {
        val id = "01HYYC1D19ABCDEF1234567890"
        transactionWrapper.executeInTransaction {
            val theater = repository().findById(id)
            assertEquals("Fast and Furious Cinema", theater?.name)
            theater?.name = faker.zelda().character()
            assertNotEquals("Fast and Furious Cinema", theater?.name)
        }

        repository.cleanUp()

        transactionWrapper.executeInTransaction {
            val theater = repository().findById(id)
            assertNotEquals("Fast and Furious Cinema", theater?.name)
        }
    }
}