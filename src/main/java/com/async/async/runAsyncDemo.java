package com.async.async;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.async.async.entity.UserDetails;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class runAsyncDemo {
	
	public static void saveUsers(File jsonFile) throws InterruptedException, ExecutionException {
		ObjectMapper mapper = new ObjectMapper();
		CompletableFuture<Void> response = CompletableFuture.runAsync(new Runnable() {

			@Override
			public void run() {
				try {
					List<UserDetails> users = mapper.readValue(jsonFile, new TypeReference<List<UserDetails>>() {});
					System.out.println("Thread name: "+Thread.currentThread().getName());
					System.out.println("Thread is picked from global thread forkJoin thread pool");
					System.out.println("User List size : "+users.size());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		});
		response.get();
	}

	public static void saveUsersWithCustomExecutorService(File jsonFile) throws InterruptedException, ExecutionException {
		ObjectMapper mapper = new ObjectMapper();
		Executor executor = Executors.newFixedThreadPool(5); // created a custom Thread Pool
		CompletableFuture<Void> response = CompletableFuture.runAsync(
				() -> {
				try {
					List<UserDetails> users = mapper.readValue(jsonFile, new TypeReference<List<UserDetails>>() {});
					System.out.println("Thread name: "+Thread.currentThread().getName());
					System.out.println("Thread is picked from custom defined thread pool");
					System.out.println("User List size : "+users.size());
				} catch (IOException e) {
					e.printStackTrace();
				}
		}, executor);
		response.get();
	}

	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		String fileName = "MOCK_DATA.json";
		File file = new File(fileName);
		saveUsers(file);
		saveUsersWithCustomExecutorService(file);
	}

}
