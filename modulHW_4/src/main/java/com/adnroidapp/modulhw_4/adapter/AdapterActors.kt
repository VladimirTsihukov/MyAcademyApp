package com.adnroidapp.modulhw_4.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.adnroidapp.modulhw_4.R
import com.adnroidapp.modulhw_4.data.Actor
import com.adnroidapp.modulhw_4.data.ActorsName.*

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
    private val view: View = itemView
    private val imageActor: ImageView? = item.findViewById(R.id.holder_actor_image)
    private val nameActors: TextView? = item.findViewById(R.id.holder_actor_name)

    fun onBindActor(actor: Actor) {
        when (actor.imageActor) {
            ROBERT_DOWNEY -> imageActor?.setImageResource(R.drawable.actor_1)
            CHRIS_EVANS -> imageActor?.setImageResource(R.drawable.actor_2)
            MARK_RUFFALO -> imageActor?.setImageResource(R.drawable.actor_3)
            CHRIS_HAMSWORTH -> imageActor?.setImageResource(R.drawable.actor_4)
        }

        when (actor.nameActors) {
            ROBERT_DOWNEY -> nameActors?.text = view.resources.getString(R.string.str_name_actor_1)
            CHRIS_EVANS -> nameActors?.text = view.resources.getString(R.string.str_name_actor_2)
            MARK_RUFFALO -> nameActors?.text = view.resources.getString(R.string.str_name_actor_3)
            CHRIS_HAMSWORTH -> nameActors?.text =
                view.resources.getString(R.string.str_name_actor_4)
        }
    }
}