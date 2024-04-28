package com.example.pagerefreshonpull


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class JokesAdapter(private val jokesList: List<String>) :
    RecyclerView.Adapter<JokesAdapter.JokeViewHolder>() {

    class JokeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val jokeTextView: TextView = itemView.findViewById(R.id.jokeTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.joke_item, parent, false)
        return JokeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        holder.jokeTextView.text = jokesList[position]
    }

    override fun getItemCount() = jokesList.size
}
