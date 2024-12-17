package pl.er.code.fwtheater.adapter.outbound.persistence.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import pl.er.code.fwtheater.adapter.outbound.persistence.annotation.ULIDId
import pl.er.code.fwtheater.adapter.outbound.persistence.entity.MovieScheduleHEntity.Companion.FIND_OVERLAPPING_SCHEDULES_FOR_AUDITORIUM_AND_MOVIE
import pl.er.code.fwtheater.adapter.outbound.persistence.entity.MovieScheduleHEntity.Companion.FIND_SCHEDULES_FOR_GIVEN_DAY_IN_AUDITORIUM
import pl.er.code.fwtheater.domain.model.Auditorium
import pl.er.code.fwtheater.domain.model.Movie
import pl.er.code.fwtheater.domain.model.MovieSchedule
import java.math.BigDecimal
import java.time.OffsetDateTime

@Entity
@Table(name = "ff_movie_schedules")
@NamedNativeQueries(
    NamedNativeQuery(
        name = FIND_OVERLAPPING_SCHEDULES_FOR_AUDITORIUM_AND_MOVIE,
        query = """select fms.*
from ff_movie_schedules fms
where fms.screen_time < (:planned_start_time + INTERVAL '1 minute' * :planned_start_time)
  and (fms.screen_time + INTERVAL '1 minute' * :movie_lenght) > :planned_start_time
  and fms.ff_auditorium_id = :auditorium_id""",
        resultClass = MovieScheduleHEntity::class
    ),
    NamedNativeQuery(
        name = FIND_SCHEDULES_FOR_GIVEN_DAY_IN_AUDITORIUM,
        query = """select fms.*
from ff_movie_schedules fms
where cast(fms.screen_time as date) = cast(:day as date)
and fms.ff_auditorium_id = :auditorium_id""",
        resultClass = MovieScheduleHEntity::class
    )
)
open class MovieScheduleHEntity : BaseEntity<String>(), MovieSchedule {

    companion object {
        const val FIND_OVERLAPPING_SCHEDULES_FOR_AUDITORIUM_AND_MOVIE =
            "MovieScheduleHEntity.findOverlappingSchedulesForAuditoriumAndMovie"
        const val FIND_SCHEDULES_FOR_GIVEN_DAY_IN_AUDITORIUM =
            "MovieScheduleHEntity.findScheduleForGivenDayInAuditorium"
    }

    @Id
    @Size(max = 26)
    @ULIDId
    @Column(name = "ff_movie_schedule_id", nullable = false, length = 26)
    override var id: String? = null

    @NotNull
    @Column(name = "screen_time", nullable = false)
    override var screenTime: OffsetDateTime? = null

    @NotNull
    @Column(name = "price", nullable = false)
    override var price: BigDecimal = BigDecimal.ZERO

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