[![Build Status](https://travis-ci.org/siy/reactive-toolbox-core.svg?branch=master)](https://travis-ci.org/siy/reactive-toolbox-core)
[![Latest Release](https://jitpack.io/v/siy/reactive-toolbox-core.svg)](https://jitpack.io/#siy/reactive-toolbox-core)

## Reactive Toolbox Core

This library contains core classes for writing asynchronous processing in (more or less) functional style.

### Motivation
This library is an attempt to implement Promise-based asynchronous processing model. Being combined with FP-style error
handling, built-in scheduler and timeout processing it provides simple, expressive and convenient tool to write 
reactive services in Java.
Main focus of this library is convenience and performance.

### Prerequisites
Build is configured to use Java 11, although any version since Java 9 should suffice
to build and run code in the module.

### Asynchronous Processing

Asynchronous processing model implemented in this module is based on __Promise__-based asynchronous processing model. 
The core class for this purpose is (obviously) `Promise`. Beside usual triggering
actions upon resolution, this implementation contains built-in asynchronous task scheduler and timeout processor. 
Implementation is designed to be highly scalable, although careful benchmarking is still pending.  

The implementation does not use (nor based on) Java `CompletableFuture`. Instead it provides very simple
and lightweight implementation. 

### Functional Code Style Support

A number of useful classes and interfaces are provided to support functional 
programming in Java.
- `Option` - a replacement for `Optional` which is intended to be more convenient
and minimal reverences to imperative style (for example, `get` does not throw exceptions)
- `Tuples` - simple tuples for 1-9 elements
- `Either` - cornerstone of throw-less code
- `Functions` - functional interfaces for functions accepting from 0 to 9 parameters
- `Suppliers` - utility functions for `Supplier` functional interface

### How to use
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
    <version>reactive-toolbox-core-0.1.0</version>
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
    implementation 'com.github.siy:reactive-toolbox-core:reactive-toolbox-core-0.1.0'
}
```

#### Examples

Some examples are present in [Examples](https://github.com/siy/reactive-toolbox-core/tree/master/src/test/java/org/reactivetoolbox/core/examples).
More examples will be added soon.

### TODO
 - Rework naming, refactoring and better structure
 - More examples
 - Guide
