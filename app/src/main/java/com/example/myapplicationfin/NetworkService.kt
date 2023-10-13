package com.example.myapplicationfin

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface NetworkService {
    //https://openapi.gg.go.kr/Ggmindmedinst
    @GET("Ggmindmedinst")
    fun getList(
        @Query("Key") apikey:String,
        @Query("pIndex") page:Long,
        @Query("pSize") pageSize:Int,
        @Query("Type") returnType:String,
        @Query("SIGUN_NM") sigunName:String,
        ) : Call<MyModel> // 서버로부터 전달받겠다
}