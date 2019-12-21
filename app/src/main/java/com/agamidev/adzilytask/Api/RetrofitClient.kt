package com.agamidev.adzilytask.Api

import okhttp3.ConnectionSpec
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

object RetrofitClient {

    val client: ApiServices
        get(){
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("api_key", API_KEY)
                    .build()

                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .connectionSpecs(
                    Arrays.asList(
                    ConnectionSpec.MODERN_TLS,
                    ConnectionSpec.COMPATIBLE_TLS))
                .followRedirects(true)
                .followSslRedirects(true)
                .retryOnConnectionFailure(true)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .cache(null)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiServices::class.java)

        }

}


//NewsFeed
//object RetrofitClient {
//    private var retrofit: Retrofit? = null
//
//
//    val client: Retrofit?
//        get(){
//            if (retrofit == null) {
//                val loggingInterceptor = HttpLoggingInterceptor()
//                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
//                val okHttpClient = OkHttpClient.Builder()
//                    .addInterceptor(loggingInterceptor)
//    .connectionSpecs(
//Arrays.asList(
//ConnectionSpec.MODERN_TLS,
//ConnectionSpec.COMPATIBLE_TLS))
//.followRedirects(true)
//.followSslRedirects(true)
//.retryOnConnectionFailure(true)
//                    .readTimeout(60, TimeUnit.SECONDS)
//                    .connectTimeout(60, TimeUnit.SECONDS)
//                    .writeTimeout(60, TimeUnit.SECONDS)
//                    .build()
//                retrofit = Retrofit.Builder()
//                    .client(okHttpClient)
//                    .baseUrl(BASE_URL)
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build()
//            }
//            return retrofit
//        }
//
//}


//Fawzy
//object RetrofitClient {
//    private lateinit var interceptor: HttpLoggingInterceptor
//    private lateinit var okHttpClient: OkHttpClient
//    private var retrofit: Retrofit? = null
//
//
//    val client: Retrofit
//        get() {
//            interceptor = HttpLoggingInterceptor()
//            interceptor.level = HttpLoggingInterceptor.Level.BODY
//            okHttpClient = OkHttpClient.Builder()
//                .addInterceptor(interceptor)
//                .connectionSpecs(
//                    Arrays.asList(
//                    ConnectionSpec.MODERN_TLS,
//                    ConnectionSpec.COMPATIBLE_TLS))
//                .followRedirects(true)
//                .followSslRedirects(true)
//                .retryOnConnectionFailure(true)
//                .connectTimeout(20, TimeUnit.SECONDS)
//                .readTimeout(20, TimeUnit.SECONDS)
//                .writeTimeout(20, TimeUnit.SECONDS)
//                .cache(null)
//                .build()
//
//            if (retrofit == null) {
//                retrofit = Retrofit.Builder()
//                    .baseUrl(BASE_URL)
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .client(okHttpClient)
//                    .build()
//            }
//            return retrofit!!
//
//        }
//
//
//}
