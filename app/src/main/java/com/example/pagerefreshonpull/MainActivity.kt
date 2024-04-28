package com.example.pagerefreshonpull


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.thauvin.erik.jokeapi.JokeApi
import net.thauvin.erik.jokeapi.models.Joke

class MainActivity : AppCompatActivity() {
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private var jokesList = mutableListOf<String>()
    private lateinit var adapter: JokesAdapter
    private var isLoading = false
    private var initialLoad = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        recyclerView = findViewById(R.id.joke_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = JokesAdapter(jokesList)
        recyclerView.adapter = adapter

        setupScrollListener()

        swipeRefreshLayout.setOnRefreshListener {
            if (!isLoading) {
                jokesList.clear() // Optionally clear old data
                adapter.notifyDataSetChanged()
                initialLoad = true
                fetchJokes(10) // Fetch initial 10 jokes on refresh
            }
        }

        fetchJokes(10) // Fetch initial 10 jokes on start
    }

    private fun setupScrollListener() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) { // Check if scrolling down
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val totalItemCount = layoutManager.itemCount
                    val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                    // Check if the end of the list is reached and data is not currently loading
                    if (!isLoading && totalItemCount <= (lastVisibleItem + 3)) {
                        fetchJokes(5) // Fetch additional jokes when scrolling down
                    }
                }
            }
        })
    }

    private fun fetchJokes(amount: Int) {
        if (isLoading) return
        isLoading = true
        swipeRefreshLayout.isRefreshing = true

        CoroutineScope(Dispatchers.IO).launch {
            try {
                repeat(amount) {
                    val joke: Joke = JokeApi.joke()
                    withContext(Dispatchers.Main) {
                        jokesList.add(joke.joke.joinToString("\n"))
                        adapter.notifyDataSetChanged()
                    }
                }
                withContext(Dispatchers.Main) {
                    isLoading = false
                    swipeRefreshLayout.isRefreshing = false
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    isLoading = false
                    swipeRefreshLayout.isRefreshing = false
                }
            }
        }
    }
}









