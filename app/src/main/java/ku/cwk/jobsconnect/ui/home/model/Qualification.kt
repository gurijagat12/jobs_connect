package ku.cwk.jobsconnect.ui.home.model

import com.google.gson.annotations.SerializedName


data class Qualification(

    @SerializedName("institute_name") var instituteName: String? = null,
    @SerializedName("course_name") var courseName: String? = null,
    @SerializedName("grades") var grades: String? = null,
    @SerializedName("from_date") var fromDate: String? = null,
    @SerializedName("to_date") var toDate: String? = null

)