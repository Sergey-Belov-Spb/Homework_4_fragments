package com.example.fragmentsmovie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_movies_list.*

class MainActivity : AppCompatActivity(), MoviesListFragments.MoviesListListener {
    companion object{
        const val TAG = "MainActivity"
        val imgsArray = arrayOf(R.drawable.film_0,
            R.drawable.film_1,
            R.drawable.film_2,
            R.drawable.film_3,
            R.drawable.film_4,
            R.drawable.film_5,
            R.drawable.film_6,
            R.drawable.film_7,
            R.drawable.film_8,
            R.drawable.film_9,
            R.drawable.film_10,
            R.drawable.film_11,
            R.drawable.film_12)

        var AllMovies =listOf(
            MoviesItem(0, 1,"Film 0", "Содержание фильма 0 \n\tЭто очень длинный текст описания, который вставлен сюда доя проверки работы ....",false),
            MoviesItem(1, 2,"Film 1", "Содержание фильма 1",false),
            MoviesItem(2, 3,"Film 2", "Содержание фильма 2",false),
            MoviesItem(3, 4,"Film 3", "Содержание фильма 3",false),
            MoviesItem(4, 5,"Film 4", "Содержание фильма 4",false),
            MoviesItem(5, 6,"Film 5", "Содержание фильма 5",false),
            MoviesItem(6, 7,"Film 6", "Содержание фильма 6",false),
            MoviesItem(7, 8,"Film 7", "Содержание фильма 7",false),
            MoviesItem(8, 9,"Film 8", "Содержание фильма 8",false)
        )
        var FavoriteMovies: MutableList<MoviesItem> = ArrayList()
        var LastFragmentAttached: Fragment = Fragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        openAllMoviesList()
        initButtonListener()
    }
    private fun openAllMoviesList(){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer,MoviesListFragments(), MoviesListFragments.TAG  )
            .addToBackStack(null)
            .commit()
    }

    private fun openFavoriteMoviesList(){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer,MoviesListFavoriteFragments(), MoviesListFavoriteFragments.TAG  )
            .addToBackStack(null)
            .commit()
    }

    private fun openDetailedFragment(moviesItem: MoviesItem) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer,MovieDetailedFragment.newInstance(moviesItem.name,moviesItem.indexPic,moviesItem.contents),MovieDetailedFragment.TAG)
            .addToBackStack(null)
            .commit()
    }

    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)
        LastFragmentAttached = fragment
        if (fragment is MoviesListFragments){
            fragment.listener = this
            Log.d(TAG,"onAttachFragment -> MoviesListFragments")
        }
        if (fragment is MoviesListFavoriteFragments){
            fragment.listener = this
            Log.d(TAG,"onAttachFragment -> MoviesListFavoriteFragments")
        }
    }

    private fun initButtonListener(){
        findViewById<BottomNavigationView>(R.id.navigationBottom).setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_allMovies -> {
                    Log.d(TAG,"action_allMovies")
                    openAllMoviesList()
                }
                R.id.action_favoriteMovies -> {
                    Log.d(TAG,"action_allMovies")
                    openFavoriteMoviesList()
                }
            }
            false
        }
    }
    fun View.snack(message: String, duration: Int = Snackbar.LENGTH_LONG) {
        Snackbar.make(this, message, duration).show()
    }
    override fun onMoviesSelected(moviesItem: MoviesItem, add: Int, position : Int) {
        Log.d(TAG,"onMoviesSelected MoviesItem=$moviesItem add=$add")

        if (add==0) {openDetailedFragment(moviesItem)}
        else {
            moviesItem.inFavorite=!moviesItem.inFavorite
            if (FavoriteMovies.indexOf(moviesItem) == -1){
                //Toast.makeText(applicationContext,"Фильм:${moviesItem.name}. Добавлен в спиок избранных.",Toast.LENGTH_SHORT).show()
                val AddedMovieItem = moviesItem
                val AddedPosition = position
                FavoriteMovies.add(moviesItem)
                Snackbar.make(this.findViewById(R.id.fragmentContainer), "Отменить последнюю операцию?", Snackbar.LENGTH_LONG
                ).setAction(
                    "Отмена",
                    {
                        AddedMovieItem.inFavorite=!AddedMovieItem.inFavorite
                        FavoriteMovies.remove(AddedMovieItem)
                        if ((LastFragmentAttached is MoviesListFragments)||(LastFragmentAttached is MoviesListFavoriteFragments)) {
                            LastFragmentAttached.recyclerView.adapter?.notifyItemChanged(AddedPosition)
                        }
                    }
                ).show()
            }
            else
            {
                //Toast.makeText(applicationContext,"Фильм:${moviesItem.name}. Удален из спиока избранных.",Toast.LENGTH_SHORT).show()
                FavoriteMovies.remove(moviesItem)
                val DeletedMovieItem = moviesItem
                val DeletedPosition = position
                Snackbar.make(this.findViewById(R.id.fragmentContainer), "Отменить последнюю операцию?", Snackbar.LENGTH_LONG
                ).setAction(
                    "Отмена",
                    {
                        DeletedMovieItem.inFavorite=!DeletedMovieItem.inFavorite
                        FavoriteMovies.add(DeletedMovieItem)
                        if ((LastFragmentAttached is MoviesListFragments)||(LastFragmentAttached is MoviesListFavoriteFragments)) {
                            LastFragmentAttached.recyclerView.adapter?.notifyItemChanged(DeletedPosition)
                        }
                    }
                ).show()
            }
        }
    }
}
