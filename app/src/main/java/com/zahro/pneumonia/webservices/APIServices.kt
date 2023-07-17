package com.zahro.pneumonia.webservices

import com.zahro.pneumonia.model.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import com.zahro.pneumonia.response.WrappedListResponse
import com.zahro.pneumonia.response.WrappedObject
import com.zahro.pneumonia.response.WrappedResponse

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
        @Field("presentase")presentase:String
    ):Call<WrappedResponse<History>>
    @GET("historyByUser/{id}")
    fun history(
        @Path("id") id: Int
    ):Call<WrappedListResponse<History>>
    @GET("news")
    fun news():Call<WrappedListResponse<News>>
    @GET("article")
    fun article():Call<WrappedListResponse<Article>>
    @FormUrlEncoded
    @POST("signup")
    fun register(
        @Field("name") name:String,
        @Field("email") email:String,
        @Field("password") password:String
    ):Call<WrappedResponse<User>>
    @GET("version/{id}")
    fun version(@Path("id")id:Int):Call<WrappedObject>
}