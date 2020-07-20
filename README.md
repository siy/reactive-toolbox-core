[![Build Status](https://travis-ci.org/siy/reactive-toolbox-core.svg?branch=master)](https://travis-ci.org/siy/reactive-toolbox-core)
[![Latest Release](https://jitpack.io/v/siy/reactive-toolbox-core.svg)](https://jitpack.io/#siy/reactive-toolbox-core)

# Project is closed, all development is moved to [Reactive Toolbox](https://github.com/siy/reactive-toolbox)

## Reactive Toolbox Core

This library contains core classes for writing Java apps in FP-style along with __Promise__-based asynchronous processing.

### Description
 - [Consistent null values handling in Java](https://dev.to/siy/consistent-null-values-handling-in-java-3c5e)
 - [Consistent error propagation and handling in Java](https://dev.to/siy/consistent-error-propagation-and-handling-in-java-158j)
 - [Monads for Java programmers in simple terms](https://dev.to/siy/monads-for-java-programmers-in-simple-terms-1959)
 - [The power of Tuples](https://dev.to/siy/the-power-of-tuples-2cf4)
 - [Asynchronous Processing in Java with Promises](https://dev.to/siy/asynchronous-processing-in-java-with-promises-2oj0)

### Prerequisites
Build is configured to use Java 11, although any version since Java 9 should suffice
to build and run code in the module.

### How to use
The __reactive-toolbox-core__ Library is lightweight and has no external dependencies. In order to use it in your code
it is enough to add it as dependency to your Maven or Gradle build configuration and configure imports in module-info.java. 
 
#### Configure Maven Dependency

Add repository:
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
Add dependency:
```xml
<dependency>
    <groupId>com.github.siy</groupId>
    <artifactId>reactive-toolbox-core</artifactId>
    <version>reactive-toolbox-core-0.3.0</version>
</dependency>
```
#### Configure Gradle Dependency

Add repository:
```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
Add dependency:
```groovy
dependencies {
    implementation 'com.github.siy:reactive-toolbox-core:reactive-toolbox-core-0.3.0'
}
```
#### Configure module-info.java
TODO:
### Examples

Some examples are present in [Examples](https://github.com/siy/reactive-toolbox-core/tree/master/src/test/java/org/reactivetoolbox/core/examples).

### TODO
 - Guide
 - More examples
 - Better test coverage
 
