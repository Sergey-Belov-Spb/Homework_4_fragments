package com.example.fragmentsmovie

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fragmentsmovie.MainActivity.Companion.imgsArray

class MoviesViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: MoviesItem){
        itemView.findViewById<TextView>(R.id.nameMovieInAll).text  = item.name
        itemView.findViewById<ImageView>(R.id.imageMovieInAll).setImageResource(imgsArray[item.indexPic])

        if (item.inFavorite== true) {itemView.findViewById<ImageView>(R.id.imageFavoriteAll).setImageResource(R.drawable.ic_favorite_black_24dp)}
        else {itemView.findViewById<ImageView>(R.id.imageFavoriteAll).setImageResource(R.drawable.ic_favorite_border_black_24dp)}
        //(itemView as TextView).text = item.name

    }


}