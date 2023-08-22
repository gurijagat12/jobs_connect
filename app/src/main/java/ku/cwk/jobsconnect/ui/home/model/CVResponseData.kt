package ku.cwk.jobsconnect.ui.home.model

import com.google.gson.annotations.SerializedName

data class CVResponseData(
    @SerializedName("data")
    val cvData: CVData
)
