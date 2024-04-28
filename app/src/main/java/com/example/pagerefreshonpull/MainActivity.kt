package com.example.pagerefreshonpull


import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.thauvin.erik.jokeapi.JokeApi
import net.thauvin.erik.jokeapi.exceptions.JokeException
import net.thauvin.erik.jokeapi.models.Joke

class MainActivity : AppCompatActivity() {
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var jokeTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        jokeTextView = findViewById(R.id.jokeTextView)

        swipeRefreshLayout.setOnRefreshListener {
            fetchJoke()
        }
        // Initial load
        fetchJoke()
    }

    // used Coroutine so that Network call can be perform on Background Thread
    // will get NetworkOnMainThreadException if perform on Main Thread
    private fun fetchJoke() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val joke: Joke = JokeApi.joke()
                withContext(Dispatchers.Main) {
                    jokeTextView.text = joke.joke.joinToString("\n")
                    swipeRefreshLayout.isRefreshing = false
                }
            } catch (e: JokeException) {
                withContext(Dispatchers.Main) {
                    jokeTextView.text = "Failed to fetch joke: ${e.message}"
                    swipeRefreshLayout.isRefreshing = false
                }
            }
        }
    }
}





