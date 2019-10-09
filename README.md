[![Build Status](https://travis-ci.org/siy/reactive-toolbox-core.svg?branch=master)](https://travis-ci.org/siy/reactive-toolbox-core)
[![Latest Release](https://jitpack.io/v/siy/reactive-toolbox-core.svg)](https://jitpack.io/#siy/reactive-toolbox-core)

## Reactive Toolbox Core

This library contains core classes for writing asynchronous processing in (more or less) functional style.

### Motivation
This library is an attempt to implement convenient and easy to use __Promise__-based asynchronous processing model in Java. 
Main focus of this library is convenience to use and performance.

### Asynchronous Processing

Asynchronous processing model implemented in this module is based on __Promise__-based asynchronous processing model (if you
re not familiar with this concept, see [Short Introduction](#short-introduction-to-promises) below).

Most famous and widely used implementation of __Promise__-bases processing is one found in ECMAScript 2015. 
While at first sight this implementation may look similar to one provided in ECMAScript 2015, there are few important
differences which should be kept in mind while comparing these two implementations.  

The main difference is that there is only two states of __Promise__ (pending and resolved) comparing to three states 
used by ECMAScript implementation (pending, resolved and rejected). This difference is caused by different approach 
to error handling - __reactive-toolbox-core__ uses __Either__-based error handling (see [Error Handling](#error-handling)) 
instead of explicit error state. Use of __Either__-based error handling is far less prone to __callback hell__ and much 
more clear and concise.
 
Another significant difference is the API. Despite identical naming, `then()` method attaches action to the instance for 
which it is invoked, it does not create sequence of __Promise__ instances waiting for previous instance to be resolved. 
This approach enables attachment of actions to same promise in different locations. For example, library heavily uses
attaching of internal actions to promises in order to implement necessary behavior. If chaining of the promises is 
necessary (i.e. promise which should wait for resolution of existing promise and then perform some action), then `map()` 
method. This method is close counterpart of ECMAScripts' `Promise.then()`.  

There are two versions of `Promise` implemented in the library. 

First version is plain `Promise` with content value defined by user. While this version is quite simple and straightforward 
to use, it lacks error handling and for this reason might not always be convenient. 
Second version operates with operation results wrapped into `Result` (see [Error Handling](#error-handling)) targeted real life use cases. 

Beside traditional methods for setting up processing when result arrives (`then()`, `onSuccess`, `onFailure`) 
implementations provide methods for asynchronous execution of tasks which accept `Promise` or `PromiseResult` 
(respectively) as a parameter. For this purpose library contains task scheduler tuned for running large amounts
of short non-blocking tasks. This scheduler also supports simple and convenient execution of tasks after specified delay.
Delayed task processing does not involve additional threads or blocking. One of the very convenient uses of this ability 
is implementation of timeouts for processing.

Along with operations on single promises, library implements operations on multiple instances at once, for example, 
creating `Promise` and `PromiseResult` instances which are resolved when any or all of the provided promises are resolved.

Also, library allows "chaining" of promises with transformation of the value as value is transferred from one promise to 
another. On top of that, `PromiseResult` supports even more complex scenarios, when results of promise resolution 
are conditionally passed to another processing returning `PromiseResult` and uses intermediate results as parameters.
See [`PromiseResult` Examples](#promiseresult-examples) below for more details. 

For testing purposes and some rare use cases blocking waiting is also implemented (with and without timeout).

#### Short Introduction to Promises
The __Promise__ is a (perhaps not yet available) result of execution of some asynchronous action. __Promise__ allows
to attach actions which will be executed when result will be available (i.e. __Promise__ will be resolved). If __Promise__
is already resolved, then attached action is executed immediately. One of the advantages of __Promise__ model is that
all user code is always executed in the context of single (although not necessarily the same) thread. 
This significantly simplifies code and eliminates need to synchronization in vast majority of use cases. Use of immutable
data eliminates remaining cases when synchronization might be necessary.    

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

Resolve promise:
```java
    final var promise = Promise.<Integer>give();
    ...
    promise.resolve(534);
```
Note that all attached actions will be executed in current thread.

Resolve promise and run attached actions asynchronously:
```java
    final var promise = Promise.<Integer>give();
    ...
    promise.resolveAsync(534);
```
Note that all attached actions will be executed in separate thread.

Promise can be configured before returning:
```java
    final Promise<Integer> promise = Promise.give(promise -> 
                                                    promise.then(value -> LOG.info("Received value {}", value)));    //Classic syntax
```

Wait for resolution of all promises:

```java
        final var promise1 = service1.requestString();
        final var promise2 = service2.requestInteger();
        final var promise3 = service3.requestUuid();
        final var allPromise = PromiseAll.allOf(promise1, promise2, promise3);

        allPromise.then(tuple -> tuple.map((int1, int2, int3) -> LOG.info("Values received: {}, {}, {}", int1, int2, int3)));
```
Note that result of resolution of all promises is returned as `Tuple` of corresponding size. 

Wait for resolution of any promise:
```java
        final var promise1 = service1.requestInteger();
        final var promise2 = service2.requestInteger();
        final var anyPromise = Promise.any(promise1, promise2);

        anyPromise.then(value -> LOG.info("Received value {}", value));
```
The the result of waiting for resolution of any promise is the value of first resolved promise, so the single value is
contained in resulting promise. Subsequent resolution of other promises is ignored. 

Note that `all` and `any` may wait indefinitely for resolution. If this is not desirable, then promise may be configured for 
resolution after specified timeout, if regular resolution is not triggered in timely manner.

Configure promise timeout:
```java
    promise.async(Timeout.of(30).seconds(), promise -> promise.resolve(timeoutValue));
```
The same method can be used to configure promise resolution after specified time and trigger actions waiting for promise resolutions.

#### `PromiseResult` Examples

### Functional Code Style Support

A number of useful classes and interfaces are provided to support functional 
programming in Java.
- `Option` - a replacement for `Optional` which is intended to be more convenient
and minimal reverences to imperative style (for example, `get` does not throw exceptions)
- `Tuples` - simple tuples for 1-9 elements
- `Either` - core interface for containers which hold one of two types of vales
- `Result` - based on `Either` exception-free error handling and execution flow control
- `Functions` - functional interfaces for functions accepting from 0 to 9 parameters
- `Suppliers` - utility functions for `Supplier` functional interface

#### Error Handling
The __reactive-toolbox-core__ uses __Either__-based error handling approach. The concept of __Either__ is borrowed from functional 
programming. __Either__ is a (lets call it) container which may hold one of the two possible types of vales. The __Either__ concept has many 
applications, but __reactive-toolbox-core__ focuses on use of this concept for representing result of operation execution. The result might be
one of two types - successful execution result or error (failure). The dedicated class __Result__ implements such a operation result container.
For convenience and conciseness all types of errors are collected under __Error__ interface umbrella. This allows to significantly reduce 
code verbosity and use only one type to specialize __Result__ for particular value type. Original __Either__ implementation is symmetric in 
regard to each value type (usually called `left` and `right`). This provides generality and flexibility. Specialization of __Result__ to 
narrower use case enables focusing its interface on convenient error handling, performing operations only when previous operation result is
successful and fast error propagation in case of failure at any step.

Since result of any operation can be either success or failure and result can be conveniently used for controlling execution flow, there is 
no need to use exceptions for error handling. This enables simple and straightforward handling of complex scenarios when several operations
need to be executed and any of them may return error.  

##### Simple Example

```java
    //Assume we have following method, which returns success if value is within specified range (inclusively)
    private Result<Integer> validateBetween(final int value, final int min, final int max) {
        return validateGE(value, min).flatMap(val -> validateLE(val, max));
    }

    private Result<Integer> validateGE(final int value, final int min) {
        return value < min ? failure(Error.with(WebErrorTypes.UNPROCESSABLE_ENTITY, "Input value below %d", min))
                           : success(value);
    }

    private Result<Integer> validateLE(final int value, final int max) {
        return value > max ? failure(Error.with(WebErrorTypes.UNPROCESSABLE_ENTITY, "Input value above %d", max))
                           : success(value);
    }

    ...
    //Use this method in the code:

    return validateBetween(input, 20, 100)
            .map(Objects::toString)
            .flatMap(val -> service.formatBannerResponse(val));
```
This code has no explicit error handling, although every subsequent step will be executed only if previous one succeeds
and error at every step will be properly propagated back to the caller. 

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
    <version>reactive-toolbox-core-0.2.0</version>
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
    implementation 'com.github.siy:reactive-toolbox-core:reactive-toolbox-core-0.2.0'
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
 