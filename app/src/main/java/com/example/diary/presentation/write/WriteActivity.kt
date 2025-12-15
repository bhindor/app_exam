package com.example.diary.presentation.write

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.diary.R
import com.example.diary.databinding.ActivityWriteBinding
import com.example.diary.model.Diary
import com.example.diary.model.DiaryDatabase
import kotlinx.coroutines.launch

class WriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val db = DiaryDatabase.getDatabase(this)
        val dao = db.diaryDao()

        binding.btnDone.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val body = binding.etBody.text.toString()

            if (title.isEmpty() || body.isEmpty()) {
                Toast.makeText(this@WriteActivity, "제목과 내용을 작성해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                lifecycleScope.launch {
                    val newDiary = Diary(title = title, content = body)
                    dao.insertDiary(newDiary)
                }

                Toast.makeText(this@WriteActivity, "일기가 저장되었습니다", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}