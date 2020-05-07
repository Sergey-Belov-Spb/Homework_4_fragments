package com.example.fragmentsmovie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.fragmentsmovie.MainActivity.Companion.AllMovies
import kotlinx.android.synthetic.main.fragment_movies_list.*
import java.lang.Exception

class MoviesListFragments : Fragment (){
    companion object{
        const val TAG = "MoviesListFragments"
    }
    var listener : MoviesListListener? = null

   override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity is MoviesListListener){
            listener = activity as MoviesListListener
        } else {
            throw Exception("Activity must implement MoviesListListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return  inflater.inflate(R.layout.fragment_movies_list, container, false)
        //return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<RecyclerView>(R.id.recyclerView).adapter = MoviesAdapter(
            LayoutInflater.from(activity),
            AllMovies) { moviesItem: MoviesItem, long: Int, position: Int ->

            recyclerView.adapter?.notifyItemChanged(position)
            listener?.onMoviesSelected(moviesItem,long,position)
        }

        /*view.findViewById<RecyclerView>(R.id.recyclerView).adapter = MoviesAdapter(LayoutInflater.from(activity), AllMovies){
            listener?.onMoviesSelected(it)
        }*/
    }

    interface MoviesListListener {
        fun onMoviesSelected(moviesItem: MoviesItem,add : Int,position : Int)
    }
}