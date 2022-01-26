package com.udacity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.udacity.databinding.ActivityDetailBinding
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_detail)
        setSupportActionBar(toolbar)
        if(intent?.extras!=null){
            binding.content.fileNameTextView.text=intent.getStringExtra("fileName")
            binding.content.statusTextView.text=intent.getStringExtra("status")
        }
        binding.content.okButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

}
