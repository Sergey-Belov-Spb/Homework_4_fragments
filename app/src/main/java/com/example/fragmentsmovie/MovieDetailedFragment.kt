package com.example.fragmentsmovie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.fragmentsmovie.MainActivity.Companion.imgsArray
import com.google.android.material.appbar.AppBarLayout

class MovieDetailedFragment :Fragment  (){
    companion object{
        const val TAG = "MovieDetailedFragment"

        const val EXTRA_NAME = "EXTRA_NAME"
        const val EXTRA_INDEXPIC = "EXTRA_INDEXPIC"
        const val EXTRA_CONTENS = "EXTRA_CONTENS"

        fun newInstance(name:String,indexPic:Int,contens:String): MovieDetailedFragment{
            val fragment = MovieDetailedFragment()
            val bundle = Bundle()
            bundle.putString(EXTRA_NAME, name)
            bundle.putInt(EXTRA_INDEXPIC, indexPic)
            bundle.putString(EXTRA_CONTENS, contens)
            fragment.arguments = bundle

            return fragment
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_detailed_ext, container,false )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val indecPicture = arguments?.getInt(EXTRA_INDEXPIC)
        view.findViewById<ImageView>(R.id.image).setImageResource(imgsArray[indecPicture!!])
        view.findViewById<TextView>(R.id.description).text =arguments?.getString(EXTRA_CONTENS,"Нет содержания")

        // ЭТО НЕ РАБОТАЕТ view.findViewById<Toolbar>(R.id.toolbar).title = "Hello"
    }

}