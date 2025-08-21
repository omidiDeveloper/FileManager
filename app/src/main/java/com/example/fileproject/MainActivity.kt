package com.example.fileproject

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fileproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        var ourViewType = 0
        var ourSpanCount = 1
    }

    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainToolbar)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val file = getExternalFilesDir(null)!!
        val path = file.path

        val trnasaction = supportFragmentManager.beginTransaction()
        trnasaction.add(R.id.frame_main_container , FragmentFile(path))
        trnasaction.commit()


    }
}