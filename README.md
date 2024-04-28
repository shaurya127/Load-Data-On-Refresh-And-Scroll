# Load Data on Refresh and Scroll

- ** This Android application demonstrates a dynamic data loading feature where jokes are fetched from a remote API and displayed in a list. The app utilizes two primary user interactions for data loading:

- ** 1. Pull to Refresh: Users can refresh the list of jokes by pulling down at the top of the list.
- ** 2. Scroll to Load More: Additional jokes are fetched automatically as the user scrolls to the bottom of the list.

## Features
- ** Initial Data Load: When the app is launched, it automatically fetches 10 jokes to populate the list.
- ** Pull to Refresh: Allows the user to refresh the joke list, fetching 10 new jokes.
- ** Infinite Scrolling: As the user scrolls to the bottom of the list, 5 new jokes are added incrementally to the list.


## Technologies Used
- **Kotlin: The primary programming language for the application.
- **Coroutine: Used for handling asynchronous tasks such as API requests to ensure smooth UI performance.
- **Retrofit: A type-safe HTTP client for Android and Java which makes it easier to consume RESTful web services.
- **RecyclerView: An efficient component that provides a flexible way of displaying a collection of data.
- **SwipeRefreshLayout: Supports the pull-to-refresh gesture for refreshing content.
- **JokeAPI: A free service that provides random jokes. This API is used to fetch jokes for the application
