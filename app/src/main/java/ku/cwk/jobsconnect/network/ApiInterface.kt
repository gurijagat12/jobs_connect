package ku.cwk.jobsconnect.network

import ku.cwk.jobsconnect.ui.cvadd.model.CVAddExperienceData
import ku.cwk.jobsconnect.ui.cvadd.model.CVAddQualificationData
import ku.cwk.jobsconnect.ui.cvadd.model.CVAddRequestData
import ku.cwk.jobsconnect.ui.employer.model.SearchData
import ku.cwk.jobsconnect.ui.employer.model.SearchRequestData
import ku.cwk.jobsconnect.ui.home.model.CVRequestData
import ku.cwk.jobsconnect.ui.home.model.CVResponseData
import ku.cwk.jobsconnect.ui.onboard.model.LoginRequestData
import ku.cwk.jobsconnect.ui.onboard.model.LoginResponseData
import ku.cwk.jobsconnect.ui.onboard.model.RegisterRequestData
import okhttp3.Request
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiInterface {

    //Login
    @Headers("Accept: application/json")
    @POST("coc_api/v1/login.php")
    suspend fun login(
        @Body request: LoginRequestData
    ): Response<LoginResponseData>

    //Register
    @Headers("Accept: application/json")
    @POST("coc_api/v1/registration.php")
    suspend fun register(
        @Body request: RegisterRequestData
    ): Response<LoginResponseData>

    //Fetch CV details
    @Headers("Accept: application/json")
    @POST("coc_api/v2/fetch_user.php")
    suspend fun fetchCVDetails(
        @Body request: CVRequestData
    ): Response<CVResponseData>

    //Add CV general details
    @Headers("Accept: application/json")
    @POST("coc_api/v1/create_cv.php")
    suspend fun addCVDetails(
        @Body request: CVAddRequestData
    ): Response<ResponseBody>

    //Add CV Experience
    @Headers("Accept: application/json")
    @POST("coc_api/v1/user_job.php")
    suspend fun addCVDetails(
        @Body request: CVAddExperienceData
    ): Response<ResponseBody>

    //Add CV Qualification
    @Headers("Accept: application/json")
    @POST("coc_api/v1/user_qualification.php")
    suspend fun addCVDetails(
        @Body request: CVAddQualificationData
    ): Response<ResponseBody>

    //Add CV Qualification
    @Headers("Accept: application/json")
    @POST("coc_api/v1/agent_search.php")
    suspend fun searchSkills(
        @Body request: SearchRequestData
    ): Response<ArrayList<SearchData>>
}