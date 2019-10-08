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

Asynchronous processing model implemented in this module is based on __Promise__-based asynchronous processing model,
although API is significantly different from similar model implemented in ECMAScript 2015. 

There are two versions of `Promise` implemented in the library. 
First version is plain `Promise` with content value defined
by user. While this version is quite simple and straightforward to use, it lacks error handling and for this reason
might be not very convenient in real life use cases. 
Second version operates with results wrapped into `Result` (see below) and tuned to fit more complex use cases. 
It provides very convenient and natural way for handling errors during processing.

Beside traditional methods for setting up processing when result arrives (`then()`, `onSuccess`, `onFailure`) 
implementations provide methods for asynchronous execution of tasks which accept `Promise` or `PromiseResult` 
(respectively) as a parameter. For this purpose library contains task scheduler tuned for running large amounts
of short non-blocking tasks. This scheduler also supports simple and convenient execution of tasks after specified delay.
Delayed task processing does not involve additional threads or blocking. One of the very convenient uses of this ability 
is implementation of timeouts for processing.

Along with operations on single promises, library implements operations on multiple instances at once, for example, 
creating `Promise` and `PromiseResult` instances which are resolved when any or all of the provided promises are resolved.
Also, library allows "chaining" of promises with transformation of the value when it is transferred from one promise to 
another. On top of that `PromiseResult` supports even more complex scenarios, when results of promise resolution 
are conditionally passed to another function which also returns `PromiseResult` and uses intermediate results as parameters.
See some examples below for more details. 

For testing purposes and some rare use cases blocking waiting is also implemented (with and without timeout).

#### Plain `Promise` examples

Create empty (unresolved) instance:
```java
    final Promise<Integer> promise = Promise.give();    //Classic syntax
    final var promise = Promise.<Integer>give();      //Java 11 syntax
```
Create resolved promise: 
```java
    final Promise<Integer> promise = Promise.funfilled(123);    //Classic syntax 
    final var promise = Promise.funfilled(123);                 //Java 11 syntax
```
Promise can be configured before returning:
```java
    
```


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
