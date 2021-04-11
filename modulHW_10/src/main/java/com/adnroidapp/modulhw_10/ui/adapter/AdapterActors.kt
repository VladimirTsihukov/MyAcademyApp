package com.adnroidapp.modulhw_10.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adnroidapp.modulhw_10.R
import com.adnroidapp.modulhw_10.api.ApiFactory.BASE_URL_MOVIE_IMAGE
import com.adnroidapp.modulhw_10.pojo.ActorsInfo
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.view_item_holder_actor.view.*


class AdapterActors : RecyclerView.Adapter<HolderActors>() {

    var actors: List<ActorsInfo> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderActors {
        return HolderActors(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_item_holder_actor, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HolderActors, position: Int) {
        holder.onBindActor(actors[position])
    }

    override fun getItemCount(): Int = actors.size
}

class HolderActors(item: View) : RecyclerView.ViewHolder(item) {
    fun onBindActor(actor: ActorsInfo) {
        with(itemView) {
            tv_holder_actor_name.text = actor.nameActor

            Glide.with(context)
                .load(BASE_URL_MOVIE_IMAGE + actor.profilePath)
                .error(R.drawable.ic_placeholder_actor)
                .placeholder(R.drawable.ic_placeholder_actor)
                .into(img_holder_actor_image)
        }
    }
}
