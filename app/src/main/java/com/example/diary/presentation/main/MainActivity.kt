package com.example.diary.presentation.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diary.R
import com.example.diary.databinding.ActivityMainBinding
import com.example.diary.model.DiaryDatabase
import com.example.diary.presentation.write.WriteActivity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnWrite.setOnClickListener {
            val intent = Intent(this, WriteActivity::class.java)
            startActivity(intent)
        }

        val db = DiaryDatabase.getDatabase(this)
        val dao = db.diaryDao()

        // TODO : 데이터베이스 연결
        // TODO : 리사이클러뷰 연결
        // TODO : 삭제 이벤트

        val adapter = DiaryRecyclerAdapter { selectedDiary ->
            lifecycleScope.launch {
                dao.deleteDiary(selectedDiary)
                Toast.makeText(this@MainActivity, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.rvDiary.adapter = adapter
        binding.rvDiary.layoutManager = LinearLayoutManager(this)
    }
}