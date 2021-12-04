package com.async.async;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import com.async.async.entity.UserDetails;
import com.async.async.service.UserDetailsService;

public class ThenApplyAccept {

	public static CompletableFuture<Void> sendReminderEmail() {
		Executor executor = Executors.newCachedThreadPool();
		CompletableFuture<Void> users = CompletableFuture.supplyAsync(() -> {
			System.out.println("Fetch records from JSON : " + Thread.currentThread().getName());
			try {
				return UserDetailsService.findAllUsersFromFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}).thenApplyAsync((listUsers) -> {
			System.out.println("\nGroup users based on gender : " + Thread.currentThread().getName());
			// return users.stream().collect(Collectors.groupingBy(UserDetails::getGender));
			return listUsers.stream().filter(user -> user.getGender().equalsIgnoreCase("Male")).collect(Collectors.toList());
		},executor).thenApplyAsync((filterusers) -> {
			System.out.println("\nfilter users based on email length : " + Thread.currentThread().getName());
			return filterusers.stream().filter(u-> u.getEmail().length() - u.getEmail().indexOf('@') >= 5).collect(Collectors.toList());
		}).thenApplyAsync((filteredUsers)-> {
			System.out.println("\nGet only email details : " + Thread.currentThread().getName());
			return filteredUsers.stream()
					.map(UserDetails::getEmail)
					.collect(Collectors.toList());
		}, executor).thenAcceptAsync((emails)-> {
			System.out.println("\nBefore sending email : " + Thread.currentThread().getName());
			emails.forEach(email-> sendEmail(email));
		});
		return users;

	}
	
	private static void sendEmail(String email) {
		//System.out.println("sending email thread: " + Thread.currentThread().getName());
		System.out.println("Email sent for "+email);
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		/*
		 * Usecase : 1. Fetch all the records from JSON 2. Group all the users based on
		 * gender 3. Find the users whose domain is less than or equal to 10 characters
		 * 4. Get the list of email 5. Pass it to a function which generate Email (dummy
		 * function)
		 */
		sendReminderEmail().get();
	}

}
