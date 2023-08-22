package ku.cwk.jobsconnect.ui.cvadd.model

import com.google.gson.annotations.SerializedName

data class CVAddRequestData(
    @SerializedName("address") var address: String? = null,
    @SerializedName("gender") var gender: String? = null,
    @SerializedName("age") var age: String? = null,
    @SerializedName("has_skills") var hasSkills: String? = null,
    @SerializedName("languages") var languages: String? = null,
    @SerializedName("hobbies") var hobbies: String? = null,
    @SerializedName("userid") var userId: Int? = null
)