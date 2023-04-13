package com.example.cameragallery.adapter

import android.content.Context
import android.media.MediaMetadataRetriever
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cameragallery.databinding.ItemviewBinding
import com.example.cameragallery.model.Datamodel

class recyclerAdapter(var uriList:ArrayList<Datamodel>,var context: Context) : RecyclerView.Adapter<recyclerAdapter.viewHolder>(){
    class viewHolder(var binding:ItemviewBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(datamodel: Datamodel, context: Context) {

            Log.d("videourl","${datamodel.video},${datamodel.uri}")
            if(datamodel.video) {
                val mMMR = MediaMetadataRetriever();
                mMMR.setDataSource(context, datamodel.uri);
                val bitmap = mMMR.getFrameAtTime()
                binding.thumbnail.setImageBitmap(bitmap)
            }else{
                binding.thumbnail.setImageBitmap(datamodel.bitmap)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(ItemviewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return uriList.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.bind(uriList[position],context)
    }

}