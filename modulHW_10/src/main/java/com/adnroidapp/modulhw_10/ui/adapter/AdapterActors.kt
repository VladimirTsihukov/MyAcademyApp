package com.adnroidapp.modulhw_10.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.adnroidapp.modulhw_10.R
import com.adnroidapp.modulhw_10.apiCorutine.ApiFactoryCoroutine.BASE_URL_MOVIE_IMAGE
import com.adnroidapp.modulhw_10.pojo.ActorsInfo
import com.bumptech.glide.Glide

class AdapterActors : RecyclerView.Adapter<HolderActors>() {

    private var actors = listOf<ActorsInfo>()

    fun bindActors(newActor: List<ActorsInfo>) {
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

    @SuppressLint("UseCompatLoadingForDrawables")
    fun onBindActor(actor: ActorsInfo) {
        nameActors?.text = actor.nameActor
        imageActor?.let {
            Glide.with(itemView.context)
                .load(BASE_URL_MOVIE_IMAGE + actor.profilePath)
                .error(R.drawable.placeholder)
                .into(it)
        }
    }
}
