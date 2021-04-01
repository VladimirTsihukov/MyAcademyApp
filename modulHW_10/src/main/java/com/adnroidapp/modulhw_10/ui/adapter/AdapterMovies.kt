package com.adnroidapp.modulhw_10.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.adnroidapp.modulhw_10.R
import com.adnroidapp.modulhw_10.database.dbData.DataDBMovies
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.view_holder_movies.view.*
import kotlin.math.roundToInt

class AdapterMovies(
    private val onItemClickListener: OnItemClickListener,
) : RecyclerView.Adapter<HolderMovies>() {

    var movies: List<DataDBMovies> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderMovies {
        return HolderMovies(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_movies, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HolderMovies, position: Int) {
        holder.onBind(movies[position])
        holder.imageFilm.setOnClickListener {
            onItemClickListener.onItemClick(movies[position].id)
        }
        holder.iconLike.setOnClickListener {
            onItemClickListener.onClickLikeMovies(movies[position].likeMovie, movies[position])
            movies[position].likeMovie = !movies[position].likeMovie
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = movies.size
}

class HolderMovies(item: View) : RecyclerView.ViewHolder(item) {

    val imageFilm: ImageView = item.findViewById(R.id.holder_image_film)

    private val star1: ImageView = item.findViewById(R.id.holder_star_level_1)
    private val star2: ImageView = item.findViewById(R.id.holder_star_level_2)
    private val star3: ImageView = item.findViewById(R.id.holder_star_level_3)
    private val star4: ImageView = item.findViewById(R.id.holder_star_level_4)
    private val star5: ImageView = item.findViewById(R.id.holder_star_level_5)
    private val listStar: List<ImageView> = listOfNotNull(star1, star2, star3, star4, star5)
    val iconLike: ImageView = item.findViewById(R.id.holder_icon_like)


    fun onBind(movie: DataDBMovies) {
        with(itemView) {
            holder_reviews.text = context.resources.getString(R.string.fragment_reviews).let {
                String.format(it, "${movie.numberOfRatings}")
            }
            holder_age_category.text = context.getString(R.string.fragment_age_category).let {
                String.format(it, "${movie.minimumAge}")
            }
            holder_film_name.text = movie.title
        }
        setPosterIcon(movie.posterPath)
        setImageStars((movie.ratings / 2).roundToInt())
        iconLike.setImageResource(if (movie.likeMovie) R.drawable.icon_like_off
        else R.drawable.icon_like_on)
    }


    private fun setPosterIcon(poster: String) {
        Glide.with(itemView.context)
            .load(poster)
            .placeholder(R.drawable.ph_movie_grey_200)
            .error(R.drawable.ph_movie_grey_200)
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
