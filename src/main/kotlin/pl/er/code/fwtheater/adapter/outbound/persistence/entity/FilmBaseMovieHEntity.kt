package pl.er.code.fwtheater.adapter.outbound.persistence.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import pl.er.code.fwtheater.adapter.outbound.persistence.annotation.ULIDId
import pl.er.code.fwtheater.adapter.outbound.persistence.entity.FilmBaseMovieHEntity.Companion.FIND_ONE_BY_FILM_BASE_AND_MOVIE
import pl.er.code.fwtheater.domain.model.FilmBaseMovie
import pl.er.code.fwtheater.domain.model.FilmBasePlatform
import pl.er.code.fwtheater.domain.model.Movie

@Entity
@Table(name = "ff_fb_platform_movies")
@NamedNativeQueries(
    NamedNativeQuery(
        name = FIND_ONE_BY_FILM_BASE_AND_MOVIE,
        query = """select ffpm.*
from ff_fb_platform_movies ffpm
         join ff_fb_platforms ffp on ffpm.ff_fb_platform_id = ffp.ff_fb_platform_id
    and ffp.code = :filmBaseCode and ffpm.ff_movie_id = :movie_id""",
        resultClass = FilmBaseMovieHEntity::class
    )
)
open class FilmBaseMovieHEntity : BaseEntity<String>(), FilmBaseMovie {

    companion object {
        const val FIND_ONE_BY_FILM_BASE_AND_MOVIE = "FilmBaseMovieHEntity.findOneByFilmBaseAndMovie"
    }

    @Id
    @Size(max = 26)
    @ULIDId
    @Column(name = "ff_fb_platform_movie_id", nullable = false, length = 26)
    override var id: String? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false, targetEntity = FilmBasePlatformHEntity::class)
    @JoinColumn(name = "ff_fb_platform_id", nullable = false)
    override var filmBasePlatform: FilmBasePlatform? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false, targetEntity = MovieHEntity::class)
    @JoinColumn(name = "ff_movie_id", nullable = false)
    override var movie: Movie? = null

    @Size(max = 250)
    @NotNull
    @Column(name = "movie_id", nullable = false, length = 250)
    override var platformMovieId: String? = null

    override fun getEntityId(): String? {
        return id
    }

}