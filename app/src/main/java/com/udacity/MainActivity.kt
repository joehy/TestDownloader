package com.udacity

import android.app.DownloadManager
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.udacity.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.content_main.view.*


class MainActivity : AppCompatActivity() {

    private var downloadID: Long = 0

    private lateinit var binding:ActivityMainBinding
    private lateinit var uri:Download_URL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main)
        registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
        createChannel(this,getString(R.string.channel_id),"notifi")

        binding.content.downloadRadioGroup.setOnCheckedChangeListener{group, checkedId ->
            uri = when(checkedId){
                R.id.retrofit_radio_button -> Download_URL.RETROFIT_URI
                R.id.udacity_radio_button -> Download_URL.UDACITY_URI
                R.id.glide_radio_button -> Download_URL.GLIDE_URI
                else -> Download_URL.RETROFIT_URI
            }
            binding.content.customButton.setOnClickListener {
                it.custom_button.buttonState=ButtonState.Clicked
            if (this::uri.isInitialized)
                download()
            else
                Toast.makeText(this,getString(R.string.select_option_toast),Toast.LENGTH_SHORT).show()
        }
        }
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            binding.content.customButton.buttonState=ButtonState.Completed
            if(id==downloadID){
                getSystemService(
                    NotificationManager::class.java
                ).createNotification(
                    "Download is completed",
                    applicationContext,
                    uri.title,
                    "Success"
                )
            }else{
                getSystemService(
                    NotificationManager::class.java
                ).createNotification(
                    "Download is completed",
                    applicationContext,
                    uri.title,
                    "Fail"
                )
            }
        }
    }
    private fun download() {
        val request =
            DownloadManager.Request(Uri.parse(uri.uri))
                .setTitle(getString(R.string.app_name))
                .setDescription(getString(R.string.app_description))
                .setRequiresCharging(false)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)

        val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        downloadID =
            downloadManager.enqueue(request)// enqueue puts the download request in the queue.
        binding.content.customButton.buttonState=ButtonState.Loading
    }



}
