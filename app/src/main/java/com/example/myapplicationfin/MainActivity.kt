package com.example.myapplicationfin

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager

import com.example.myapplicationfin.MyApplication.Companion.checkAuth
import com.example.myapplicationfin.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    lateinit var binding: ActivityMainBinding
    var datas: MutableList<String>? = null
    lateinit var adapter: MyAdapter

    lateinit var homeFragment: HomeFragment
    lateinit var reservationFragment: ReservationFragment
    lateinit var quetionFragment: QuestionFragment
    lateinit var diaryFragment: DiaryFragment
    lateinit var mypageFragment: MypageFragment

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        adapter = MyAdapter(datas)
        //binding.recyclerView.adapter = adapter

        setContentView(binding.root)

        binding.bottomNavigationView.setOnNavigationItemSelectedListener(this)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        homeFragment = HomeFragment()
        reservationFragment = ReservationFragment()
        quetionFragment = QuestionFragment()
        diaryFragment = DiaryFragment()
        mypageFragment = MypageFragment()

        val intent = Intent(this, AuthActivity::class.java)
        if (!checkAuth()) {
            intent.putExtra("data", "logout")
            startActivity(intent)
        } else {
            intent.putExtra("data", "login")
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.activity_content, homeFragment)
            .commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.activity_content, diaryFragment)
            .hide(diaryFragment)
            .commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.activity_content, mypageFragment)
            .hide(mypageFragment)
            .commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.activity_content, quetionFragment)
            .hide(quetionFragment)
            .commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.activity_content, reservationFragment)
            .hide(reservationFragment)
            .commit()

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            // 액션바 타이틀 설정
            title = "My App"

            // 홈 버튼 표시
            //setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onResume() {
        super.onResume()

        val isDarkTheme = sharedPreferences.getBoolean("theme", false)
        if (isDarkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        // 테마에 따라 텍스트 색상 변경
        val txColor = if (isDarkTheme) {
            "#FFFFFF" // 어두운 테마에서는 흰색으로 설정
        } else {
            "#000000" // 밝은 테마에서는 검은색으로 설정
        }
        adapter.setTextColor(txColor)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_actionbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //val file: File = File(filesDir, "todo_list.txt")
        Log.d("mobileApp", "onOptionsItemSelected")

        when(item.itemId){
            R.id.settingBtn -> {
                Log.d("mobileApp", "settingBtn")
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.page_home -> {
                supportFragmentManager.beginTransaction()
                    .show(homeFragment)
                    .hide(reservationFragment)
                    .hide(quetionFragment)
                    .hide(diaryFragment)
                    .hide(mypageFragment)
                    .commit()
                return true
            }
            R.id.page_history -> {
                supportFragmentManager.beginTransaction()
                    .show(reservationFragment)
                    .hide(homeFragment)
                    .hide(quetionFragment)
                    .hide(diaryFragment)
                    .hide(mypageFragment)
                    .commit()
                return true
            }
            R.id.page_qna -> {
                supportFragmentManager.beginTransaction()
                    .show(quetionFragment)
                    .hide(reservationFragment)
                    .hide(homeFragment)
                    .hide(diaryFragment)
                    .hide(mypageFragment)
                    .commit()
                return true
            }
            R.id.page_diary -> {
                supportFragmentManager.beginTransaction()
                    .show(diaryFragment)
                    .hide(reservationFragment)
                    .hide(homeFragment)
                    .hide(quetionFragment)
                    .hide(mypageFragment)
                    .commit()
                return true
            }
            R.id.page_my -> {
                supportFragmentManager.beginTransaction()
                    .show(mypageFragment)
                    .hide(reservationFragment)
                    .hide(homeFragment)
                    .hide(quetionFragment)
                    .hide(diaryFragment)
                    .commit()
                return true
            }
        }

        return false
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        //outState.putStringArrayList("datas", ArrayList(datas))
    }
}