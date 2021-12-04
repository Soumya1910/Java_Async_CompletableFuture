package com.async.async;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.async.async.entity.UserDetails;
import com.async.async.service.UserDetailsService;

public class SupplyAsyncDemo {
	
	public static List<UserDetails> getAllUsers() throws InterruptedException, ExecutionException{
		CompletableFuture<List<UserDetails>> userDetails = CompletableFuture.supplyAsync(()-> {
			System.out.println("Executed by : "+Thread.currentThread().getName());
			try {
				return UserDetailsService.findAllUsersFromFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		});
		return userDetails.get();
	}
	
	public static List<UserDetails> getAllUsersWithCustomExecutor() throws InterruptedException, ExecutionException{
		Executor executor = Executors.newCachedThreadPool();
		CompletableFuture<List<UserDetails>> userDetails = CompletableFuture.supplyAsync(()-> {
			System.out.println("Executed by : "+Thread.currentThread().getName());
			try {
				return UserDetailsService.findAllUsersFromFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}, executor);
		return userDetails.get();
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		//getAllUsers().forEach(System.out::println);
		getAllUsersWithCustomExecutor().forEach(System.out::println);

	}

}
