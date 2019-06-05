# jomutils-android
An Android utilities library to help coding faster

## Use
```gradle
repositories {
    mavenLocal(); // LOL
}

dependencies {
    //...
    compile 'com.jommobile.android:jomutils:1.0.1'
}
```

# Features
## Google Endpoints
 
## Architectures
### Database ([ROOM][android-room])
### Repository

## UIs
### Activity
`AbstractToolbarActivity` using `Toolbar` as app action bar and provides the correct setting title for configuration changes.

### Fragment
`BaseFragment` set correct title to its own Activity. TODO need to be improved for any app that has only one Activity or implement Navigation lib from Android Jetpack.
### Widgets
* `RecyclerView` + Data Binding + Clickable Actions
* `app:recyclerConfig` with `RecyclerConfiguration` to configure `RecyclerView` using data binding lib
* 2 base classes of `PagerAdapter` - state & non state


## Others
### Application
We provide a base interface `JOMApp` to get some default methods like `getAppExecutors()` to get a single `AppExecutors` in the context of our application.
### DeviceUtils
An utilities class to use with device configurations, getting device information,... etc.

### SharePrefs
An utilities class working with `SharedPreferences`. TODO: currently Android team announce to deprecated SharedPreferences module.

### Logs
Logs with default prefix tag "jom_".

### AppExecutors
Contains 3 different `Executors` - `diskIO()` a single thread executor, `networkIO()` a fixed thread pool executor, and `mainThread()` a main thread executor.
It is a singleton class - even you create it or use `JOMApp`, it is the same instance.

[android-room]
[Apache 2.0]: https://opensource.org/licenses/Apache-2.0