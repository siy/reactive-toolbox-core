## Reactive Toolbox Core

This module contains core classes for writing asynchronous processing in (more or less) functional style.

### Prerequisites
Build is configured to use Java 11, although any version since Java 9 should suffice
to build and run code in the module.

### Asynchronous Processing

Asynchronous processing model implemented in this module is based on __Promise__-based
asynchronous processing model. 
The core class for this purpose is (obviously) `Promise`. Beside usual triggiring
actions upon resolution, this implementation contains built-in asynchronous 
task scheduler and timeout processor. Implementation is designed to be highly 
scalable, although careful benchmarking is still pending.  

The implementation does
not use (nor based on) Java `CompletableFuture`. Instead it provides very simple
and lightweight implementation. Beside general __Promise__ functionality is has 
built in timeout processing and asynchronous task pool. 

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
#### Configure Gradle Dependency
#### Examples
