package com.adnroidapp.modulhw_8.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.adnroidapp.modulhw_8.R
import com.adnroidapp.modulhw_8.apiRxJava.ApiFactoryRxJava
import com.adnroidapp.modulhw_8.pojo.Cast

import com.bumptech.glide.Glide

class AdapterActors : RecyclerView.Adapter<HolderActors>() {

    private var actors = listOf<Cast>()

    fun bindActors(newActor: List<Cast>) {
        actors = newActor
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderActors {
        return HolderActors(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_actor, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HolderActors, position: Int) {
        holder.onBindActor(actors[position])
    }

    override fun getItemCount(): Int = actors.size
}

class HolderActors(item: View) : RecyclerView.ViewHolder(item) {
    private val imageActor: ImageView? = item.findViewById(R.id.holder_actor_image)
    private val nameActors: TextView? = item.findViewById(R.id.holder_actor_name)

    fun onBindActor(actor: Cast) {
        nameActors?.text = actor.name
        imageActor?.let { Glide.with(itemView.context).load(ApiFactoryRxJava.BASE_URL_MOVIE_IMAGE + actor.profilePath).into(it) }
    }
}
