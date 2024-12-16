package pl.er.code.fwtheater.adapter.outbound.persistence.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Entity
@Table(name = "ff_fb_platform_movies")
open class FilmBaseMovieHEntity : BaseEntity<String>() {

    @Id
    @Size(max = 26)
    @Column(name = "ff_fb_platform_movie_id", nullable = false, length = 26)
    override var id: String? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ff_fb_platform_id", nullable = false)
    open var filmBasePlatform: pl.er.code.fwtheater.adapter.outbound.persistence.entity.FilmBasePlatformHEntity? = null
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ff_movie_id", nullable = false)
    open var movie: pl.er.code.fwtheater.adapter.outbound.persistence.entity.MovieHEntity? = null

    @Size(max = 250)
    @NotNull
    @Column(name = "movie_id", nullable = false, length = 250)
    open var platformMovieId: String? = null
}