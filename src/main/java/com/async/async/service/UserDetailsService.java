package com.async.async.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.async.async.entity.UserDetails;
import com.async.async.repo.UserDetailsRepository;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserDetailsService {
	
	Logger logger = LoggerFactory.getLogger(UserDetailsService.class);

	@Autowired
	private UserDetailsRepository userRepo;
	
	@Async
	public CompletableFuture<List<UserDetails>> getAllUsers(){
		return CompletableFuture.completedFuture(userRepo.findAll());
	}
	
	public ResponseEntity saveUserDetails(MultipartFile[] files) throws Exception{
		int count = 1;
		for(MultipartFile file: files) {
			System.out.println("File processing in progress: {}"+(count++));
			saveFileDetails(file);
		}
		System.out.println("Files are processed");
		return ResponseEntity.ok().build();
	}
	
	@Async
	private CompletableFuture<List<UserDetails>> saveFileDetails(MultipartFile file) throws Exception {
		long start = System.currentTimeMillis();
		List<UserDetails> users = parseCSVFile(file);
		logger.info("saving list of users of size {}", users.size(), "" + Thread.currentThread().getName());
        users = userRepo.saveAll(users);
        long end = System.currentTimeMillis();
        logger.info("Total time {}", (end - start));
        return CompletableFuture.completedFuture(users);
	}
	
	private List<UserDetails> parseCSVFile(final MultipartFile file) throws Exception {
        final List<UserDetails> users = new ArrayList<>();
        try {
            try (final BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    final String[] data = line.split(",");
                    final UserDetails user = new UserDetails();
                    user.setFirst_name(data[0]);
                    user.setEmail(data[1]);
                    user.setGender(data[2]);
                    users.add(user);
                }
                return users;
            }
        } catch (final IOException e) {
            logger.error("Failed to parse CSV file {}", e);
            throw new Exception("Failed to parse CSV file {}", e);
        }
    }
	
	
	public static List<UserDetails> findAllUsersFromFile() throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		List<UserDetails> users = mapper.readValue(new File("MOCK_DATA.json"), new TypeReference<List<UserDetails>>() {});
		return users;
	}
	
	
	
	
}
