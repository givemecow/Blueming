package com.example.myapplicationfin

import android.app.Application
import androidx.multidex.MultiDexApplication
import com.bumptech.glide.Glide.init
import com.example.myapplicationfin.MyApplication.Companion.auth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// 전역 응용 프로그램 상태를 유지하기 위한 기본 클래스
//첫번째 액티비티(메인액티비티)가 표시되기 전에 전역 상태 초기화
// Application 코드는 MainActivity보다 더 먼저 실행됨
// 전역변수 관리를 위해(전역변수 초기화)

class MyApplication: MultiDexApplication(){
    companion object{
        lateinit var db : FirebaseFirestore
        lateinit var storage : FirebaseStorage

        lateinit var auth: FirebaseAuth
        var email:String? = null

        fun checkAuth():Boolean{
            var currentUser = auth.currentUser
            return currentUser?.let{
                email=currentUser.email
                if(currentUser.isEmailVerified) true
                else false
            } ?: false
        }

        var networkService : NetworkService
        val retrofit : Retrofit
            get() = Retrofit.Builder()
                .baseUrl("https://openapi.gg.go.kr/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        init{
            networkService = retrofit.create(NetworkService::class.java)// 초기화
        }
    }

    override fun onCreate() {
        super.onCreate()
        auth = Firebase.auth

        db = FirebaseFirestore.getInstance()
        storage = Firebase.storage
    }
}