package com.whyadnanshah.mockly.HomeScreen.data


import retrofit2.http.Body
import retrofit2.http.POST

interface APIService {
    @POST("/generate/test")
    suspend fun generateTest(@Body request: TestRequest) : TestResponse
}