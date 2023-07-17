package com.zahro.pneumonia.util

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.zahro.pneumonia.webservices.APIServices
import java.util.concurrent.TimeUnit

class APIclient {
    companion object{
        private var retrofit: Retrofit? = null
        private var okHttpClient = OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS).build()
        fun APIService(): APIServices = getClient().create(APIServices::class.java)
        private fun getClient(): Retrofit {
            return if (retrofit == null){
                retrofit = Retrofit.Builder().baseUrl(Constants.API_ENDPOINT)
                    .client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build()
                retrofit!!
            }else{
                retrofit!!
            }
        }
    }
}
class Constants{
    companion object{
//        const val API_ENDPOINT = "http://192.168.50.13:5000/"
        const val API_ENDPOINT = "https://apipy.dev-a-team.com/"
        fun getToken(context: Context): String {
            val pref = context.getSharedPreferences("TOKEN", Context.MODE_PRIVATE)
            val token = pref?.getString("TOKEN", "UNDEFINED")
            return token!!
        }
        fun setToken(context: Context, token: String) {
            val pref = context.getSharedPreferences("TOKEN", Context.MODE_PRIVATE)
            pref.edit().apply {
                putString("TOKEN", token)
                apply()
            }
        }
        fun clearToken(context: Context) {
            val pref = context.getSharedPreferences("TOKEN", Context.MODE_PRIVATE)
            pref.edit().clear().apply()
        }
        fun getName(context: Context): String {
            val pref = context.getSharedPreferences("NAME", Context.MODE_PRIVATE)
            val token = pref?.getString("NAME", "JhonDoe")
            return token!!
        }

        fun setName(context: Context, name: String) {
            val pref = context.getSharedPreferences("NAME", Context.MODE_PRIVATE)
            pref.edit().apply {
                putString("NAME", name)
                apply()
            }
        }

        fun clearName(context: Context) {
            val pref = context.getSharedPreferences("NAME", Context.MODE_PRIVATE)
            pref.edit().clear().apply()
        }

        fun getId(context: Context): Int {
            val pref = context.getSharedPreferences("ID", Context.MODE_PRIVATE)
            val id = pref?.getInt("ID", 0)
            return id!!
        }

        fun setId(context: Context, id: Int) {
            val pref = context.getSharedPreferences("ID", Context.MODE_PRIVATE)
            pref.edit().apply {
                putInt("ID", id)
                apply()
            }
        }

        fun clearId(context: Context) {
            val pref = context.getSharedPreferences("ID", Context.MODE_PRIVATE)
            pref.edit().clear().apply()
        }
    }
    
}