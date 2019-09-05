package id.haadii.alodoktersubmission.data

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class User(

    @Id var id: Long = 0,

    var uid: String = "",

    var email: String = "",

    var username: String = ""

)