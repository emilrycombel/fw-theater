package pl.er.code.fwtheater.adapter.outbound.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.Size
import pl.er.code.fwtheater.adapter.outbound.persistence.annotation.ULIDId

@Entity
@Table(name = "ff_fb_platforms")
open class FilmBasePlatformHEntity : BaseEntity<String>() {
    @Id
    @Size(max = 26)
    @ULIDId
    @Column(name = "ff_fb_platform_id", nullable = false, length = 26)
    override var id: String? = null

    //TODO [Reverse Engineering] generate columns from DB
}