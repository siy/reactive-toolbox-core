## Reactive Toolbox Core

This module contains core classes for writing asynchronous processing in (more or less) functional style.

### Prerequisites
Build is configured to use Java 13, although any version since Java 9 should suffice
to build and run code in the module.

### Asynchronous Processing

Asynchronous processing model implemented in this module is based on __Promises__. 
The core class for this purpose is (obviously) `Promise`. The implementation does
not use (nor based on) Java `CompletableFuture`. Instead it provides very simple
and lightweight implementation. Beside general __Promise__ functionality is has 
built in timeout processing and asynchronous task pool. 

### Functional Code Style Support

A number of userful classes and interfaces are provided to support functional 
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
