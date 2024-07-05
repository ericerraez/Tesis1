import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Multipart
    @POST("api/convert_api/")
    fun uploadAudio(@Part audio: MultipartBody.Part): Call<ResponseBody>

    @FormUrlEncoded
    @POST("api/verify_token/")
    fun sendToken(@FieldMap token: Map<String, String>): Call<Void>

    @GET("api/get_last_transcription/")
    fun getLastTranscription(): Call<ResponseBody>
}
