package com.adnroidapp.modulhw_10.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.adnroidapp.modulhw_10.R
import com.adnroidapp.modulhw_10.ui.data.MovieData
import com.bumptech.glide.Glide
import kotlin.math.roundToInt

class AdapterMovies(
    private val onItemClickListener: OnItemClickListener,
) : RecyclerView.Adapter<HolderMovies>() {

    private var movies = listOf<MovieData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderMovies {
        return HolderMovies(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_movies, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HolderMovies, position: Int) {
        holder.onBind(movies[position], holder.itemView.context)
        holder.imageFilm.setOnClickListener {
            onItemClickListener.onItemClick(movies[position].id)
        }
        holder.iconLike.setOnClickListener {
            if (movies[position].likeMovies) {
                movies[position].likeMovies = false
                onItemClickListener.deleteLikeMovies(movies[position])
            } else {
                movies[position].likeMovies = true
                onItemClickListener.onClickLikeMovies(movies[position])
            }
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = movies.size

    fun bindMovies(newMovies: List<MovieData>?) {
        if (newMovies != null) {
            movies = newMovies
            notifyDataSetChanged()
        }
    }
}

class HolderMovies(item: View) : RecyclerView.ViewHolder(item) {
    val imageFilm: ImageView = item.findViewById(R.id.holder_image_film)
    private val ageCategory: TextView? = item.findViewById(R.id.holder_age_category)
    private val star1: ImageView? = item.findViewById(R.id.holder_star_level_1)
    private val star2: ImageView? = item.findViewById(R.id.holder_star_level_2)
    private val star3: ImageView? = item.findViewById(R.id.holder_star_level_3)
    private val star4: ImageView? = item.findViewById(R.id.holder_star_level_4)
    private val star5: ImageView? = item.findViewById(R.id.holder_star_level_5)
    private val listStar: List<ImageView> = listOfNotNull(star1, star2, star3, star4, star5)
    private val reviews: TextView? = item.findViewById(R.id.holder_reviews)
    private val filName: TextView? = item.findViewById(R.id.holder_film_name)
    val iconLike: ImageView = item.findViewById(R.id.holder_icon_like)

    @SuppressLint("SetTextI18n")
    fun onBind(movie: MovieData, context: Context) {

        setPosterIcon(movie.poster, context)
        ageCategory?.text = "${movie.minimumAge}+"
        setImageStars((movie.ratings / 2).roundToInt())
        reviews?.text = "${movie.numberOfRatings} Reviews"
        filName?.text = movie.title
        iconLike.setImageResource(if (movie.likeMovies) {
            R.drawable.icon_like_off
        } else {
            R.drawable.icon_like_on
        })
    }

    private fun setPosterIcon(poster: String, context: Context) {
        Glide.with(context)
            .load(poster)
            .placeholder(R.drawable.ph_movie_grey_200)
            .into(imageFilm)
    }

    private fun setImageStars(current: Int) {
        listStar.forEachIndexed { index, _ ->
            if (index < current) {
                (listStar[index] as? ImageView)?.setImageResource(R.drawable.star_icon_on)
            } else {
                (listStar[index] as? ImageView)?.setImageResource(R.drawable.star_icon_off)
            }
        }
    }
}
