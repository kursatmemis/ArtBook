package com.kursatmemis.artbook.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.kursatmemis.artbook.R
import com.kursatmemis.artbook.databinding.ItemArtBinding
import com.kursatmemis.artbook.model.Art
import dagger.hilt.android.qualifiers.ActivityContext
import java.lang.reflect.Array
import javax.inject.Inject

class ArtAdapter @Inject constructor(
    context: Context,
    private val artList: ArrayList<Art>
) : ArrayAdapter<Art>(context, R.layout.item_art, artList) {

    private lateinit var binding: ItemArtBinding

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        if (convertView == null) {
            val layoutInflater = LayoutInflater.from(context)
            binding = ItemArtBinding.inflate(layoutInflater, parent, false)
            binding.root.tag = binding
        } else {
            binding = convertView.tag as ItemArtBinding
        }

        val art = artList[position]
        binding.artNameTextView.text = art.artName

        return binding.root
    }

    fun updateAdapter(newArtList: ArrayList<Art>) {
        clear()
        addAll(newArtList)
        notifyDataSetChanged()
    }

    fun getArt(position: Int) : Art {
        return artList[position]
    }

}