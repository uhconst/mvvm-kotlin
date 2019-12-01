# MVVM-KOTLIN
Android application using MVVM to display a response code and times fetched.
Times fetched are persisted for offline viewing

## Libraries used in this project
- [Gson](https://github.com/google/gson)
- [Koin](https://insert-koin.io/)
- [Room](https://developer.android.com/topic/libraries/architecture/room.html)
- [LiveData](https://developer.android.com/topic/libraries/architecture/livedata.html)
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel.html)
- [Retrofit](https://square.github.io/retrofit/)
- [RxJava and RxAndroid](https://github.com/ReactiveX/RxAndroid)
- [Robolectric](https://github.com/robolectric/robolectric)
- [Constraint layout](https://developer.android.com/training/constraint-layout/index.html)
- [OkHttp](https://github.com/square/okhttp)

## Main Screen
- Response code
- Times fetched

## What you should expect
- MVVM architecture pattern
- The database is inserted from the API results
- Works offline
- DEBUG and RELEASE mode
- Displaying loading while fetching from API
- As it is fetching the data locally, maybe it is too fast to see the loading
- The next path is being fetched again every time on *Resume*
- Project hosted on [GitHub](https://github.com/uhconst/mvvm-kotlin.git)

## Changes necessary before testing
- Localhost is using *http://10.0.2.2:8080/* so it can work in the emulator
- In case is necessary, change Localhost just replacing the *API_URL* in the *build.gradle* 

## Developed by
Uryel Constancio - [uryelhenrique.c@gmail.com](uryelhenrique.c@gmail.com)
