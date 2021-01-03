package com.adnroidapp.modulhw_7.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.adnroidapp.modulhw_7.R
import com.adnroidapp.modulhw_7.data.Actor

import com.bumptech.glide.Glide

class AdapterActors : RecyclerView.Adapter<HolderActors>() {

    private var actors = listOf<Actor>()

    fun bindActors(newActor: List<Actor>) {
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

    fun onBindActor(actor: Actor) {
        nameActors?.text = actor.name
        imageActor?.let { Glide.with(itemView.context).load(actor.picture).into(it) }
    }
}