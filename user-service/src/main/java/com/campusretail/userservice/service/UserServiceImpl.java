package com.campusretail.userservice.service;

import com.campusretail.userservice.dto.CredentialsDto;
import com.campusretail.userservice.dto.ReadUserDto;
import com.campusretail.userservice.dto.UserLoginDto;
import com.campusretail.userservice.dto.WriteUserDto;
import com.campusretail.userservice.entity.User;
import com.campusretail.userservice.entity.UserDetails;
import com.campusretail.userservice.entity.UserRole;
import com.campusretail.userservice.repository.UserDetailsRepository;
import com.campusretail.userservice.repository.UserRepository;
import com.campusretail.userservice.repository.UserRoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Implementation of the service interface, using
 * dependency injection to use the repositorys to
 * develop the methods for the controller
 */

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final UserRoleRepository userRoleRepository;
	private final UserDetailsRepository userDetailsRepository;
	private final PasswordEncoder passwordEncoder;
	private final ModelMapper mapper;

	@Value("${secret-key}")
	private String secretKey;

	@Autowired
	public UserServiceImpl(UserRepository userRepository,
	                       UserRoleRepository userRoleRepository,
	                       PasswordEncoder passwordEncoder,
	                       UserDetailsRepository userDetailsRepository,
	                       ModelMapper mapper) {
		this.userRepository = userRepository;
		this.userRoleRepository = userRoleRepository;
		this.passwordEncoder = passwordEncoder;
		this.userDetailsRepository = userDetailsRepository;
		this.mapper = mapper;
	}

	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}

	@Override
	@Async("AsyncExecutor")
	public CompletableFuture<List<User>> getAllUsersAsync() {
		return CompletableFuture.completedFuture(userRepository.findAll());
	}

	@Override
	@Async("AsyncExecutor")
	public CompletableFuture<User> getUserByIdAsync(Long id) {
		return CompletableFuture.completedFuture(userRepository.findById(id).orElse(null));
	}

	@Override
	@Async("AsyncExecutor")
	public CompletableFuture<Optional<User>> getUserByNameAsync(String userName) {
		return CompletableFuture.completedFuture(userRepository.findByUserName(userName));
	}

	@Override
	@Async("AsyncExecutor")
	public CompletableFuture<ReadUserDto> saveUserAsync(WriteUserDto writeUserDto) {
		Optional<User> checkUser = userRepository.findByUserName(writeUserDto.getUserName());
		if (!checkUser.isPresent())
			throw new RuntimeException("User already exist");

		String password = passwordEncoder.encode(writeUserDto.getUserPassword());
		
		UserDetails details = new UserDetails();
		details.setEmail(writeUserDto.getEmail());
		details.setFirstName(writeUserDto.getFirstName());
		details.setLastName(writeUserDto.getLastName());
		details.setCountry(writeUserDto.getCountry());
		details.setLocality(writeUserDto.getLocality());
		details.setStreet(writeUserDto.getStreet());
		details.setZipCode(writeUserDto.getZipCode());
		details.setStreetNumber(writeUserDto.getStreetNumber());
		details.setPhoneNumber(writeUserDto.getPhoneNumber());
		
		UserRole role = userRoleRepository.findUserRoleByRoleName("User");
		User user = new User();
		user.setUserName(writeUserDto.getUserName());
		user.setUserPassword(password);
		user.setUserDetails(details);
		user.setRole(role);
		user.setActive(1);
		
		ReadUserDto readUserDto = mapper.map(userRepository.save(user), ReadUserDto.class);
		return CompletableFuture.completedFuture(readUserDto);
	}

	@Override
	public CompletableFuture<UserLoginDto> validateToken(String token) {
		String username = Jwts.parser()
				.setSigningKey(secretKey)
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
		Optional<User> optionalUser = userRepository.findByUserName(username);
		if (!optionalUser.isPresent())
			throw new RuntimeException("User not found");

		User user = optionalUser.get();
		UserLoginDto userLoginDto = new UserLoginDto(user.getUserName(), createToken(user));

		return CompletableFuture.completedFuture(userLoginDto);
	}

	@Override
	public CompletableFuture<UserLoginDto> signIn(CredentialsDto credentialsDto) {
		Optional<User> optionalUser = userRepository.findByUserName(credentialsDto.getUsername());
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			if (passwordEncoder.matches(credentialsDto.getPassword(), user.getUserPassword())) {
				String token = createToken(user);
				return CompletableFuture.completedFuture(new UserLoginDto(user.getUserName(), token));
			} else
				throw new RuntimeException("Incorrect password");
		}
		throw new RuntimeException("User not found");
	}

	private String createToken(User user) {
		Claims claims = Jwts.claims().setSubject(user.getUserName());

		Date now = new Date();
		Date validity = new Date(now.getTime() + 3600000); // 1 hour

		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(validity)
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();
	}
}
