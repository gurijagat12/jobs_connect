package ku.cwk.jobsconnect.ui.home.model

import com.google.gson.annotations.SerializedName


data class Paper(

    @SerializedName("paper_title") var paperTitle: Int? = null,
    @SerializedName("paper_url") var paperUrl: Int? = null,
    @SerializedName("paper_summary") var paperSummary: Int? = null

)