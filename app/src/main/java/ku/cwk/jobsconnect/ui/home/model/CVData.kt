package ku.cwk.jobsconnect.ui.home.model

import com.google.gson.annotations.SerializedName


data class CVData(

    @SerializedName("personal") var personal: Personal? = Personal(),
    @SerializedName("cv") var cvProfile: CvProfile? = CvProfile(),
    @SerializedName("qualification") var qualification: ArrayList<Qualification> = arrayListOf(),
    @SerializedName("has_job") var experience: ArrayList<Experience> = arrayListOf()

)