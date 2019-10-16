# PEPPERChart: Basic charting for Android
![](https://github.com/alexrussellbrookes/PEPPERChart/blob/master/images/PEPPER_Logo.jpg)

PEPPERChart is designed to make the construction of charts for Android as straight-forward as possible. It leverages the power of the 
[D3.js](https://d3js.org/) library for Android developers, allowing them to quickly create basic chart types.

## Resources
[API Wiki](https://github.com/alexrussellbrookes/PEPPERChart/wiki)

## Installing
Add the library to your Android project like this:

Add jitpack to your root build.gradle at the end of repositories:
```java
allprojects {
  repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```  

Add the dependency to your app gradle.
```java
dependencies {
    implementation 'com.github.alexrussellbrookes:PEPPERChart:v1.0'
}
```

## Examples

[![PEPPER Dashboard Demo](http://img.youtube.com/vi/_enwognHbwI/0.jpg)](https://youtu.be/_enwognHbwI)
