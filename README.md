# Album Postr
An app for generating album posters that include an author's signature (if available), the color palette of the album art, the names of the songs in the album, and the year the album was released

# To Use
To use Album Postr, you will need to download [IntelliJ Community Edition](https://www.jetbrains.com/idea/download/?section=windows) and [OpenJDK 17](https://openjdk.org/projects/jdk/17/). From there, [clone](https://docs.github.com/en/repositories/creating-and-managing-repositories/cloning-a-repository) the GitHub repo. Next, use IntelliJ to open build.gradle in the downloaded files as a project. Then, follow the steps in `For Developers` 

# For Developers
API keys are not included in the source code. To contribute to album-postr you will need to generate your own. Please visit [The Spotify Developers Page](https://developer.spotify.com/) to learn more

To add your keys, simply create a new class called `Keys.java` in the `dev.chrismharris.main` package and add the following code
```java
public static final String CLIENT_ID = "<YOUR_ID_HERE>";
public static final String CLIENT_SECRET = "<YOUR_SECRET_HERE>";
```

# External Libraries Used
- [Color Thief](https://github.com/SvenWoltmann/color-thief-java) by [Sven Woltmann](https://github.com/SvenWoltmann/)
- [Java Wrapper for Spotify API](https://github.com/spotify-web-api-java/spotify-web-api-java) by [Michael Thelin](https://github.com/thelinmichael)
