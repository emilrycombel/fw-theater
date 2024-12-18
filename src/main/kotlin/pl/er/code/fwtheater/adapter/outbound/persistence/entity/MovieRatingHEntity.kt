package pl.er.code.fwtheater.adapter.outbound.persistence.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import pl.er.code.fwtheater.adapter.outbound.persistence.annotation.ULIDId
import pl.er.code.fwtheater.adapter.outbound.persistence.entity.MovieRatingHEntity.Companion.FIND_BY_MOVIE_ID
import pl.er.code.fwtheater.adapter.outbound.persistence.entity.MovieRatingHEntity.Companion.NO_FILTER
import pl.er.code.fwtheater.domain.model.Movie
import pl.er.code.fwtheater.domain.model.MovieRating

@Entity
@Table(name = "ff_ratings")
@NamedQueries(
    NamedQuery(
        name = FIND_BY_MOVIE_ID,
        query = """FROM MovieRatingHEntity mre 
join fetch MovieHEntity m on m.id = mre.movie.id          
WHERE mre.movie.id = :movie_id"""
    ),
    NamedQuery(
        name = NO_FILTER,
        query = "FROM MovieRatingHEntity"
    )
)
open class MovieRatingHEntity : BaseEntity<String>(), MovieRating {
    companion object {
        const val FIND_BY_MOVIE_ID: String = "MovieRatingHEntity.findByMovieId"
        const val NO_FILTER: String = "MovieRatingHEntity.noFilter"
    }

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