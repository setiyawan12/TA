package yayang.setiyawan.pneumonia.webservices

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import yayang.setiyawan.pneumonia.model.History
import yayang.setiyawan.pneumonia.model.Results
import yayang.setiyawan.pneumonia.model.User
import yayang.setiyawan.pneumonia.response.WrappedListResponse
import yayang.setiyawan.pneumonia.response.WrappedListResponseArray
import yayang.setiyawan.pneumonia.response.WrappedResponse

interface APIServices {
    @Multipart
    @POST("predict")
    fun predict(
        @Part file:MultipartBody.Part
    ):Call<WrappedResponse<Results>>
    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email:String,
        @Field("password") password:String
    ):Call<WrappedResponse<User>>
    @FormUrlEncoded
    @POST("addHistory")
    fun addHistory(
        @Field("name")name:String,
        @Field("indicated")indicated:String,
        @Field("alamat")alamat:String,
        @Field("umur")umur:String,
        @Field("no_hp")no_hp:String,
        @Field("user_id")user_id:Int,
        @Field("image")image:String,
    ):Call<WrappedResponse<History>>
    @GET("historyByUser/{id}")
    fun history(
        @Path("id") id: Int
    ):Call<WrappedListResponse<History>>
    @FormUrlEncoded
    @POST("signup")
    fun register(
        @Field("name") name:String,
        @Field("email") email:String,
        @Field("password") password:String
    ):Call<WrappedResponse<User>>

}