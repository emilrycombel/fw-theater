package pl.er.code.fwtheater.adapter.outbound.persistence.repository

import net.datafaker.Faker
import org.springframework.transaction.annotation.Transactional
import pl.er.code.fwtheater.domain.model.DomainInstance
import pl.er.code.fwtheater.domain.model.search.PageSearchCriteria
import pl.er.code.fwtheater.infrastructure.persistence.DatabaseNeededTestBase
import java.io.Serializable
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

abstract class AbstractBaseHRepositoryImplTest<D : DomainInstance<ID>, E : D, ID : Serializable, S : PageSearchCriteria, R : AbstractDomainRepository<D, E, ID, S>> :
    DatabaseNeededTestBase() {
    protected val faker: Faker = Faker()
    protected abstract fun repository(): R
    abstract fun newNonPersistentInstance(): D
    abstract fun updatePersistedInstance(instance: D): D

    @Transactional(readOnly = false)
    @Test
    fun `Should persist entity`() {
        val instance = newNonPersistentInstance()
        repository().saveAndFlush(instance)
        assertTrue(instance.id != null, "Entity should have recived an ID")

        val stored = repository().findById(instance.id!!)
        assertEquals(instance, stored, "Instances should be the same !")
    }

    @Transactional(readOnly = false)
    @Test
    fun `Should delete persisted entity`() {
        val theater = newNonPersistentInstance()
        repository().saveAndFlush(theater)
        assertTrue(theater.id != null, "Entity should have recived an ID")

        val stored = repository().findById(theater.id!!)
        assertEquals(theater, stored, "Instances should be the same !")

        repository().delete(stored!!)
        assertNull(repository().findById(theater.id!!), "Item should have been deleted and not reachable here.")
    }

    @Transactional(readOnly = false)
    @Test
    fun `Should delete persisted entity by id`() {
        val theater = newNonPersistentInstance()
        repository().saveAndFlush(theater)
        assertTrue(theater.id != null, "Entity should have recived an ID")

        val stored = repository().findById(theater.id!!)
        assertEquals(theater, stored, "Instances should be the same !")

        repository().deleteById(stored!!.id!!)
        assertNull(repository().findById(theater.id!!), "Item should have been deleted and not reachable here.")
    }
}