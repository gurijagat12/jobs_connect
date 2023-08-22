package ku.cwk.jobsconnect.ui.home.model

import com.google.gson.annotations.SerializedName


data class Experience(

    @SerializedName("company_name") var companyName: String? = null,
    @SerializedName("job_title") var jobTitle: String? = null,
    @SerializedName("job_role") var jobRole: String? = null,
    @SerializedName("from_date") var fromDate: String? = null,
    @SerializedName("to_date") var toDate: String? = null

)