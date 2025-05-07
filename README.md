# MangaFace

Manga Face is a feature-rich Android application that leverages modern Android technologies, including Jetpack Compose, MVVM architecture, Room Database, Retrofit, and MediaPipe. The app enables users to authenticate using local storage, browse manga with pagination from the Manga API (via RapidAPI), and perform real-time face detection using Google MediaPipe.

## Features
1. Authentication using Room Database with email and password.
2. Dependency injection with Hilt.
3. Manga Data Fetching via the Manga API from RapidAPI.
4. Pagination Support with offline caching using Room Database and automatic refresh when internet becomes available.
5. Real-Time Face Detection using Google MediaPipe.
6. Follows a Single-Activity Architecture and adheres to Clean Architecture principles.



## Screenshot


|                                                                                                                         |                                                                                                               |                                                                                                                |
|-------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------|
| ![login](https://github.com/user-attachments/assets/a2d855d5-3cce-44ea-a0a9-5a3a09e018ef)                               | ![Manga List](https://github.com/user-attachments/assets/2c9786b5-c7a5-49bb-8dee-c8a890b01a10)                | ![Manga Detail](https://github.com/user-attachments/assets/57ad7425-58c0-44b7-a028-ce4c93464885)               |
| ![Face Inside](https://github.com/user-attachments/assets/0d9289ab-04a1-40c2-8848-2bb7f4632b9a)                         | ![Face Out](https://github.com/user-attachments/assets/74d82ab9-5471-4d55-8f91-ed26b956ca62)                  |                                                                                                                |




## Package Structure

* [`data`](app/src/main/java/com/example/shopkaro/data): Responsible for producing data. Contains Api, Entity, Database, repository, and mappers.
* [`di`](app/src/main/java/com/example/shopkaro/di): Hilt modules.
* [`domain`](app/src/main/java/com/example/shopkaro/di): Contains Interfaces.
* [`ui`](app/src/main/java/com/example/shopkaro/ui): UI layer of the app.
    * `components`: Common components used in Ui.
    * `navigation`: Contains app navigation and destinations.
    * `screens`: Contains UI components.
    * `theme`: Material3 theme.
* [`utils`](app/src/main/java/com/example/shopkaro/utils): Utility classes used across the app.


## Build With

[Kotlin](https://kotlinlang.org/):
As the programming language.

[Jetpack Compose](https://developer.android.com/jetpack/compose) :
To build UI.

[Jetpack Navigation](https://developer.android.com/jetpack/compose/navigation) :
For navigation between screens.

[Retrofit](https://square.github.io/retrofit/):
Used for making network requests to fetch product data from the Fakestore API.

[Room](https://developer.android.com/training/data-storage/room):
Used for local data storage and caching.

[Hilt](https://developer.android.com/training/dependency-injection/hilt-android) :
For injecting dependencies.

[Coil](https://coil-kt.github.io/coil/compose/) :
To load image asynchronously.

[Paging3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) :
To load Data in chunks.

[Media Pipe](https://ai.google.dev/edge/mediapipe/solutions/vision/face_detector) :
To detect face in realtime.


## 🔧 Installation

1. **Clone** this repository and open it in **Android Studio**.
2. **Get an API key** for the Manga API from [RapidAPI - Mangaverse API](https://rapidapi.com/sagararofie/api/mangaverse-api).
3. **Add the following entries** to your `local.properties` file:

   ```
   API_KEY=your_rapidapi_key_here
   API_URL=https://mangaverse-api.p.rapidapi.com/
   ```



### 📦 Download APK

Click the link below to download the latest release of the app:

[**Download APK (v1.0.0)**](https://github.com/devAchint/Manga-Face/releases/download/v1.0.0/app-release.apk)

