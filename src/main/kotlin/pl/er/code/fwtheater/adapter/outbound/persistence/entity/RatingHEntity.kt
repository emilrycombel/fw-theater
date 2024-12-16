package pl.er.code.fwtheater.adapter.outbound.persistence.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import pl.er.code.fwtheater.adapter.outbound.persistence.annotation.ULIDId

@Entity
@Table(name = "ff_ratings")
open class RatingHEntity : BaseEntity<String>() {
    @Id
    @Size(max = 26)
    @ULIDId
    @Column(name = "ff_rating_id", nullable = false, length = 26)
    override var id: String? = null

    @NotNull
    @Column(name = "rating", nullable = false)
    open var rating: Short? = null

    @Column(name = "comment", length = Integer.MAX_VALUE)
    open var comment: String? = null

    @Size(max = 100)
    @NotNull
    @Column(name = "rating_user", nullable = false, length = 100)
    open var ratingUser: String? = null

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ff_movied_id", nullable = false)
    open var movie: MovieHEntity? = null
}