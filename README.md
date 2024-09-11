# My Cash Task

My Cash Task is an Android application built using Kotlin and the MVVM architecture pattern. The project was developed as part of a task for a job application with the company "My Cash". The app demonstrates various features, including user authentication and a home page with multiple sections.

## Features

- **Splash Screen**: Implemented using `androidx.core:core-splashscreen:1.0.0`.
- **Glide**: For image loading.
- **Dagger - Hilt**: For dependency injection.
- **Retrofit2**: For network operations.
- **Activity KTX**: For convenient access to ViewModels.
- **Coroutines**: For asynchronous programming.
- **Navigation Component**: For managing app navigation.
- **ViewBinding**: For binding views in activities and fragments.

## Architecture

The project follows the MVVM (Model-View-ViewModel) architecture pattern. It features two main activities, each managing its own set of fragments:

1. **Login Activity**: Manages user login.
2. **Sign Up Activity**: Manages user registration.

The application uses the Navigation Component to handle navigation between different parts of the app.

## Features

- **Login Page**:
  - Requests:
    - `email: String`
    - `password: String`

- **Sign Up Page**:
  - Requests:
    - `name: String`
    - `email: String`
    - `password: String`
    - `phone: String`

- **Home Page**:
  - Sections:
    - **Categories**: Displays a list of categories.
    - **Popular Now**: Shows popular items.
    - **Trending Now**: Displays trending items.
  - Each section includes horizontally scrolling items.

## Installation

1. **Open the Project**:

   Open the project in Android Studio.

2. **Sync Gradle**:

   Ensure that all dependencies are resolved by syncing Gradle.

3. **Run the Application**:

   Use Android Studio to run the application on an emulator or physical device.

## Usage

- Launch the app to see the splash screen.
- Navigate between login and sign-up activities.
- Use the home page to explore categories, popular items, and trending items.

## Notes

- The project includes two activities, as per the requirement to demonstrate multiple features.
- The Navigation Component is used to manage navigation between different fragments and activities.

## Contributing

Feel free to fork the repository and submit pull requests with improvements or bug fixes.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgements

- [Android Developers](https://developer.android.com/)
- [Retrofit](https://square.github.io/retrofit/)
- [Glide](https://bumptech.github.io/glide/)
- [Dagger - Hilt](https://dagger.dev/hilt/)
- [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
- [Navigation Component](https://developer.android.com/guide/navigation)
