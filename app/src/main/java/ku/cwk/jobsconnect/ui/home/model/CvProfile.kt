package ku.cwk.jobsconnect.ui.home.model

import com.google.gson.annotations.SerializedName


data class CvProfile(

    @SerializedName("address") var address: String? = null,
    @SerializedName("gender") var gender: String? = null,
    @SerializedName("age") var age: String? = null,
    @SerializedName("qualification") var qualification: String? = null,
    @SerializedName("has_job") var hasJob: String? = null,
    @SerializedName("has_skills") var hasSkills: String? = null,
    @SerializedName("languages") var languages: String? = null,
    @SerializedName("hobbies") var hobbies: String? = null,
    @SerializedName("papers_published") var papersPublished: String? = null,
    @SerializedName("latest_date") var latestDate: String? = null,
    @SerializedName("userid") var userId: Int? = null

)