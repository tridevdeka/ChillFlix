<h1 align="center">UFlixOn</h1>
<h4 align="center">
	A Movies guide app with various details and trailers.Discover the most popular and top rated movies playing. Movies data fetched using <a href="https://www.themoviedb.org/">themoviedb.org</a> API.
</h4>

  


## ✨ Screenshots

<img src="https://user-images.githubusercontent.com/49573131/142771233-52e5fe5d-86a5-4997-bbee-785e667060d7.jpg" width="320" height="600">          <img src="https://user-images.githubusercontent.com/49573131/142771983-a1e618d6-bb49-4c2d-830c-814cd39b7dd1.png" width="320" height="600">          <img src="https://user-images.githubusercontent.com/49573131/142771987-d4e97a12-6915-43d5-b127-34c09577295e.png" width="320" height="600">          <img src="https://user-images.githubusercontent.com/49573131/142772051-3674a96c-bd47-4795-9439-d1c9e86681e7.png" width="320" height="600">          <img src="https://user-images.githubusercontent.com/49573131/142772091-312df8f1-4f8a-44b0-a15d-0a35837757c0.png" width="320" height="600">

## 🌟 Features
*   Discover the most popular and the most rated movies
*   User can view and play trailers on video dialogue
*   Shows a list of reviews for each movie
*   Users can mark a movie as favorite in the details view by tapping a plus icon 
*   Advanced uses of Room
*   Navigation Component
*   MVVM with Android Architecture Components(Room, LiveData, ViewModel)
*   Handle network status and network failures
*   ConstraintLayout(guidelines, barriers... etc)
*   Material design.

### App Architecture 
Based on mvvm architecture and repository pattern.

<img src="screenshots/mvvm.png" width="500" style="max-width:500%;">

#### The app includes the following main components:

* A local database that servers as a single source of truth for data presented to the user. 
* A web api service.
* A repository that works with the database and the api service, providing a unified data interface.
* A ViewModel that provides data specific for the UI.
* The UI, which shows a visual representation of the data in the ViewModel.

#### App Packages
* <b>network</b> - contains the api classes to make api calls to MovieDB server, using Retrofit. 
* <b>db</b> - contains the db classes to cache network data.
* <b>repository</b> - contains the repository classes, responsible for triggering api requests and saving the response in the database.
* <b>di</b> - contains dependency injection classes, using Hilt-Dagger.   
* <b>ui</b> - contains classes needed to display Activity and Fragment.
* <b>util</b> - contains classes needed for activity/fragment redirection.

## 🚀 Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.
 
### Prerequisites
*   Android Studio 3.2+
*   Java JDK

### Installing
Follow these steps if you want to get a local copy of the project on your machine.

#### 1. Clone or fork the repository by running the command below	
```
https://github.com/tridevdeka/UFlixOn

```

#### 2. Import the project in AndroidStudio, and add API Key
1.  In Android Studio, go to File -> New -> Import project
2.  Follew the dialog wizard to choose the folder where you cloned the project and click on open.
3.  Android Studio imports the projects and builds it for you.
4.  Add TheMovieDb API Key inside `gradle.properties` file.

```
API_KEY="Your API Key here"
```


## 📃 Libraries used
*   Minimum SDK 17
*   [AndroidX](https://developer.android.com/jetpack/androidx/) - Previously known as 'Android support Library'
*   [Navigation Component](https://developer.android.com/guide/navigation)
*   [Glide](https://github.com/bumptech/glide) - for loading and caching images 
*   [Retrofit 2](https://github.com/square/retrofit) - for API integration. 
*   [RxJava2](https://github.com/ReactiveX/RxJava) for implementing Observable pattern.
*   [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) for dependency injection.
*   [Gson](https://github.com/google/gson) - for serialization/deserialization Java Objects into JSON and back
*   [OkHttp](https://github.com/square/okhttp) - for implementing interceptor, logging and mocking web server.
*   [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
*   [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
*   [DataBinding](https://developer.android.com/topic/libraries/data-binding/)
*   [CircleImageView](https://github.com/hdodenhof/CircleImageView)


## Authors

* **Tridev Deka** - *LinkedIn* - [Tridev Deka](https://www.linkedin.com/in/tridev-deka-5a6691226/)

```
MIT License

Copyright (c) 2021 Tridev Deka
```
