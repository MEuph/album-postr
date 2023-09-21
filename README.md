# Album Postr
An app for generating album posters that include an author's signature (if available), the color palette of the album art, the names of the songs in the album, and the year the album was released

# To Use
To use Album Postr, make sure you are on the latest version of Java 8, and that your installation of Java includes JavaFX. It should by default. Then make sure you have a decent internet connection, and you're ready to roll! Check the releases folder for the latest version

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
