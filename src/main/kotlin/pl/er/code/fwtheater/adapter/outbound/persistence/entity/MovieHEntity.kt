package pl.er.code.fwtheater.adapter.outbound.persistence.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import pl.er.code.fwtheater.adapter.outbound.persistence.annotation.ULIDId
import pl.er.code.fwtheater.domain.model.Movie

@Entity
@Table(name = "ff_movies")
@NamedQueries(
    NamedQuery(
        name = MovieHEntity.FIND_BY_TITLE,
        query = "FROM MovieHEntity WHERE title = :title"
    ),
    NamedQuery(
        name = MovieHEntity.NO_FILTER,
        query = "FROM MovieHEntity"
    )
)
open class MovieHEntity : BaseEntity<String>(), Movie {

    companion object {
        const val FIND_BY_TITLE: String = "MovieHEntity.findByTitle"
        const val NO_FILTER: String = "MovieHEntity.noFilter"
    }

    @Id
    @Size(max = 26)
    @ULIDId
    @Column(name = "ff_movie_id", nullable = false, length = 26)
    override var id: String? = null

    @Size(max = 300)
    @NotNull
    @Column(name = "title", nullable = false, length = 300)
    override var title: String? = null

    override fun getEntityId(): String? {
        return id
    }

}