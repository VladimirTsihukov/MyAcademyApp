package com.adnroidapp.modulhw_5.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.adnroidapp.modulhw_5.R
import com.adnroidapp.modulhw_5.data.Movie

class AdapterMovies(
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<HolderMovies>() {

    private var movies = listOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderMovies {
        return HolderMovies(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_movies, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HolderMovies, position: Int) {
        holder.onBind(movies[position])
        holder.imageFilm?.setOnClickListener {
            onItemClickListener.onItemClick(movies[position])
        }
    }

    override fun getItemCount(): Int = movies.size

    fun bindMovies(newMovies: List<Movie>) {
        movies = newMovies
    }
}

class HolderMovies(item: View) : RecyclerView.ViewHolder(item) {
     val imageFilm: ImageView? = item.findViewById(R.id.holder_image_film)
    private val ageCategory: TextView? = item.findViewById(R.id.holder_age_category)
    private val movieGenre: TextView? = item.findViewById(R.id.holder_movie_genre)
    private val star1: ImageView? = item.findViewById(R.id.holder_star_level_1)
    private val star2: ImageView? = item.findViewById(R.id.holder_star_level_2)
    private val star3: ImageView? = item.findViewById(R.id.holder_star_level_3)
    private val star4: ImageView? = item.findViewById(R.id.holder_star_level_4)
    private val star5: ImageView? = item.findViewById(R.id.holder_star_level_5)
    private val reviews: TextView? = item.findViewById(R.id.holder_reviews)
    private val filName: TextView? = item.findViewById(R.id.holder_film_name)
    private val min: TextView? = item.findViewById(R.id.holder_list_min)
    private val iconLike: ImageView? = item.findViewById(R.id.holder_icon_like)

    @SuppressLint("SetTextI18n")
    fun onBind(film: Movie) {


        ageCategory?.text = "${film.ageCategory}+"
        movieGenre?.text = film.movieGenre
        if (film.star1) star1?.setImageResource(R.drawable.star_icon_on) ?: star1?.setImageResource(
            R.drawable.star_icon_off
        )
        if (film.star2) star2?.setImageResource(R.drawable.star_icon_on) ?: star2?.setImageResource(
            R.drawable.star_icon_off
        )
        if (film.star3) star3?.setImageResource(R.drawable.star_icon_on) ?: star3?.setImageResource(
            R.drawable.star_icon_off
        )
        if (film.star4) star4?.setImageResource(R.drawable.star_icon_on) ?: star4?.setImageResource(
            R.drawable.star_icon_off
        )
        if (film.star5) star5?.setImageResource(R.drawable.star_icon_on) ?: star5?.setImageResource(
            R.drawable.star_icon_off
        )
        reviews?.text = "${film.reviews} Reviews"
        filName?.text = film.poster
        min?.text = "${film.ratings} min"
        if (film.iconLike) iconLike?.setImageResource(R.drawable.icon_like_off)
            ?: iconLike?.setImageResource(R.drawable.icon_like_on)
    }
}
