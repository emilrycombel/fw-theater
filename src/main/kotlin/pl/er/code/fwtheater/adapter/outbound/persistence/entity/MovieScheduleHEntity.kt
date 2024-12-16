package pl.er.code.fwtheater.adapter.outbound.persistence.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import pl.er.code.fwtheater.adapter.outbound.persistence.annotation.ULIDId
import java.time.OffsetDateTime

@Entity
@Table(name = "ff_movie_schedules")
open class MovieScheduleHEntity : BaseEntity<String>() {
    @Id
    @Size(max = 26)
    @ULIDId
    @Column(name = "ff_movie_schedule_id", nullable = false, length = 26)
    override var id: String? = null

    @NotNull
    @Column(name = "screen_time", nullable = false)
    open var screenTime: OffsetDateTime? = null

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ff_movie_id", nullable = false)
    open var movie: pl.er.code.fwtheater.adapter.outbound.persistence.entity.MovieHEntity? = null

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ff_auditorium_id", nullable = false)
    open var auditorium: AuditoriumHEntity? = null
}