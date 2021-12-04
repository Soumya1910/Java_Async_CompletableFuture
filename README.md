# Asynchronous Programming in Java
## CompletableFuture Interafce
CompletetableFuture is a building block and framework with different methods for composing, combining, executing in async process and handling errors.
```Java
public class CompletableFuture<T> implements Future<T>, CompletionStage<T>
```
* **Future** interface was implemented in Java 5 with very limited functions. It doesn't have any methods to combine these execution and handling possible errors.
* **CompleteStage** is implemented in Java 8 with lot of advanced functions.

These are the main disadvantages of Future Interface.
* It cannot be manually completed.
* Multiple Futures cannot be chained together and proceed further.
* No proper exception handling mechanism.

### runAsync():
If we want to run some background task but doesn't want to return anything, then we should use runAsync() method. It takes runnable Object and returns CompletableFuture<void>. 
```Java
CompletableFuture.runAsync(Runnable);
CompletableFuture.runAsync(Runnable, Executor);
```

### supplyAsync()
if we want to run some task in background but need to return some value to main thread then we should use supplyAsync(). It takes Supplier<T> and returns CompletableFuture<T>.
```Java
CompletableFuture.supplyAsync(Supplier<T>);
CompletableFuture.supplyAsync(Supplier<T>, Executor);
```
If we are not passing any custom executor, then thread will be taken from forkJoin global Thread pool.
