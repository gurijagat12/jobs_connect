package ku.cwk.jobsconnect.ui.employer.model

import com.google.gson.annotations.SerializedName
import ku.cwk.jobsconnect.ui.home.model.Personal

data class SearchData(
    val personal: Personal,
    @SerializedName("has_skills")
    val skills: String
)