MagicUtils
=========
An Android library project that provides various common utilities.  This project was developed in [Android Studio](http://developer.android.com/tools/studio/).

Adding MagicUtils to your project
--------------------------------
**1. Gradle dependency (Android Studio)**

 - 	Add the following to your `build.gradle`:
 ```gradle
repositories {
	    maven { url "https://jitpack.io" }
}

dependencies {
	    compile 'com.github.dream09:MagicUtils:v1.0'
}
```

**2. Maven**
- Add the following to your `pom.xml`:
 ```xml
<repository>
       	<id>jitpack.io</id>
	    <url>https://jitpack.io</url>
</repository>

<dependency>
	    <groupId>com.github.dream09</groupId>
	    <artifactId>MagicUtils</artifactId>
	    <version>v2.1</version>
</dependency>
```

**3. Jar file only**
 - Get the [**latest release .jar file**](https://github.com/dream09/MagicUtils/releases) from the releases area
 - Copy the **MagicUtils-X.X.jar** file into the `libs` folder of your Android project
 - Start using the library

Contributing to MagicUtils
---------------------------------

If you wish to contribute please create a feature branch from the *develop* branch and name *feature-yourfeature*.
