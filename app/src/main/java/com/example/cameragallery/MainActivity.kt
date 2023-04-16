package com.example.cameragallery
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.cameragallery.adapter.recyclerAdapter
import com.example.cameragallery.databinding.ActivityMainBinding
import com.example.cameragallery.databinding.ItemviewBinding
import com.example.cameragallery.model.Datamodel


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val pic_id = 1
    private val vid_id = 2
    private lateinit var binding: ActivityMainBinding
    private lateinit var binding_item: ItemviewBinding

    private var imageList = ArrayList<Datamodel>()
    private var videoList = ArrayList<Datamodel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding_item = ItemviewBinding.inflate(layoutInflater)

        setContentView(binding.root)
        getPermission()
    }

    // to get permission
    private fun getPermission() {
        var permissionslist = mutableListOf<String>()
        if (checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) permissionslist.add(
            android.Manifest.permission.CAMERA
        )
        if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) permissionslist.add(
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )
        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) permissionslist.add(
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        if (permissionslist.size > 0) {
            requestPermissions(permissionslist.toTypedArray(), 101)
        }

    }

    override fun onClick(view: View) {
        when (view) {
            binding.btnCamera -> {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE,)
                if (intent.resolveActivity(packageManager) != null) {
                    startActivityForResult(intent, pic_id)
                }
            }
            binding.btnVideocam -> {
                val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE,)
                if (intent.resolveActivity(packageManager) != null) {
                    startActivityForResult(intent, vid_id)
                }
            }

            binding.imgGalleryBtn ->{
                val recyclerAdapter = recyclerAdapter(imageList, this)
                binding.recyclerView.adapter = recyclerAdapter
            }
            binding.vidGalleryBtn -> {
                val recyclerAdapter = recyclerAdapter(videoList, this)
                binding.recyclerView.adapter = recyclerAdapter
            }
        }
    }

    // for data fetch from api
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == pic_id) {
            val bitmap = data?.extras?.get("data") as Bitmap
            Log.d("videourl","${bitmap}")
            imageList.add(Datamodel(null,false, bitmap))

        } else if (resultCode == Activity.RESULT_OK && requestCode == vid_id) {
            val videoUri = data?.data
        Log.d("videourl","${videoUri}")
            if (videoUri != null) {
                videoList.add(Datamodel(data.data!!,true,null))
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}