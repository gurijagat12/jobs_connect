package ku.cwk.jobsconnect.ui.cvadd.model

import com.google.gson.annotations.SerializedName
import ku.cwk.jobsconnect.ui.home.model.Experience

data class CVAddExperienceData(
    var data: Experience? = Experience(),
    var user: User? = User()
)

data class User(
    @SerializedName("id") var userId: Int? = 0
)
