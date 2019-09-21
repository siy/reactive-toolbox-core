## Reactive Toolbox Core

This module contains core classes for writing asynchronous processing in (more or less) functional style.

### Asynchronous Processing

Asynchronous processing model implemented in this module is based on __Promises__. 
The core class for this purpose is (obviously) `Promise`. The implementation does
not use (nor based on) Java `CompletableFuture`. Instead it provides very simple
and lightweight implementation. Beside general __Promise__ functionality is has 
built in timeout processing and asynchronous task pool. 

