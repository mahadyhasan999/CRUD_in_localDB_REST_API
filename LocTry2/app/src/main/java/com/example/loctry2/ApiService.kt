package com.example.loctry2

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ApiService {
    @GET("read.php")
    fun getData(): Call<List<Data>>

    @PUT("update.php")
    fun updateData(@Query("id") id: Int,
               @Query("name") name: String,
               @Query("age") age: Int,
               @Query("email") email: String)
                : Call<Void>

    @POST("create.php")
    fun createData(@Body data: Data): Call<Void>

    @DELETE("delete.php")
    fun deleteData(@Query("id") id: Int): Call<Void>
}
