package com.example.progr3ss.network

import com.example.progr3ss.model.AuthRequest
import com.example.progr3ss.model.AuthResponse
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @POST("auth/local/signin")
    suspend fun login(@Body request: AuthRequest): Response<AuthResponse>

    // A Swagger űrlapja fájlfeltöltést is tartalmaz (profileImage), ezért multipart
    @Multipart
    @POST("auth/local/signup")
    suspend fun register(
        @Part("username") username: RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody
    ): Response<ResponseBody>
}