package pl.er.code.fwtheater.adapter.outbound.persistence.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import pl.er.code.fwtheater.adapter.outbound.persistence.annotation.ULIDId
import pl.er.code.fwtheater.domain.model.Auditorium
import pl.er.code.fwtheater.domain.model.Movie
import pl.er.code.fwtheater.domain.model.MovieSchedule
import java.time.OffsetDateTime

@Entity
@Table(name = "ff_movie_schedules")
open class MovieScheduleHEntity : BaseEntity<String>(), MovieSchedule {
    @Id
    @Size(max = 26)
    @ULIDId
    @Column(name = "ff_movie_schedule_id", nullable = false, length = 26)
    override var id: String? = null

    @NotNull
    @Column(name = "screen_time", nullable = false)
    override var screenTime: OffsetDateTime? = null

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false, targetEntity = MovieHEntity::class)
    @JoinColumn(name = "ff_movie_id", nullable = false)
    override var movie: Movie? = null

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false, targetEntity = AuditoriumHEntity::class)
    @JoinColumn(name = "ff_auditorium_id", nullable = false)
    override var auditorium: Auditorium? = null

    override fun getEntityId(): String? {
        return id
    }

}