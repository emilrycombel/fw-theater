package pl.er.code.fwtheater.adapter.outbound.persistence.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import pl.er.code.fwtheater.adapter.outbound.persistence.annotation.ULIDId
import pl.er.code.fwtheater.domain.model.Movie
import pl.er.code.fwtheater.domain.model.Rating

@Entity
@Table(name = "ff_ratings")
open class RatingHEntity : BaseEntity<String>(), Rating {
    @Id
    @Size(max = 26)
    @ULIDId
    @Column(name = "ff_rating_id", nullable = false, length = 26)
    override var id: String? = null

    @NotNull
    @Column(name = "rating", nullable = false)
    override var rating: Short? = null

    @Column(name = "comment", length = Integer.MAX_VALUE)
    override var comment: String? = null

    @Size(max = 100)
    @NotNull
    @Column(name = "rating_user", nullable = false, length = 100)
    override var ratingUser: String? = null

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false, targetEntity = MovieHEntity::class)
    @JoinColumn(name = "ff_movied_id", nullable = false)
    override var movie: Movie? = null

    override fun getEntityId(): String? {
        return id
    }
}