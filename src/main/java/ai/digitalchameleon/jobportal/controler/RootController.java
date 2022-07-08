package ai.digitalchameleon.jobportal.controler;

import ai.digitalchameleon.jobportal.enums.AuthorisedUserType;
import ai.digitalchameleon.jobportal.model.AuthorizedUser;
import ai.digitalchameleon.jobportal.payload.JwtResponse;
import ai.digitalchameleon.jobportal.repository.AuthorizedUserRepository;
import ai.digitalchameleon.jobportal.security.jwt.JwtUtils;
import ai.digitalchameleon.jobportal.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("login")
public class RootController {

	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	JwtUtils jwtUtils;
	@Autowired
	private AuthorizedUserRepository authorizedUserRepository;

	// @GetMapping(params = "id")
	// public Optional<AuthorizedUser> getRoot(@RequestParam("id") String id) {
	// 	return authorizedUserRepository.findById(id);
	// }

	// @GetMapping
	// public List<AuthorizedUser> getAll() {
	// 	return (List<AuthorizedUser>) authorizedUserRepository.findAllByRole(AuthorisedUserType.PLATFORM_ADMIN);
	// }

	@EventListener
	public void appReady(ApplicationReadyEvent event) {
		String userId = " ";
		String userName = "Platform Admin";
		String password = "1234";
		authorizedUserRepository.save(new AuthorizedUser(userId, userName, encoder.encode(password), AuthorisedUserType.PLATFORM_ADMIN));

		authorizedUserRepository.save(new AuthorizedUser("admin_org", "Org Admin", encoder.encode("1234"), AuthorisedUserType.ORG_ADMIN));

		authorizedUserRepository.save(new AuthorizedUser("recruter1", "Recruiter One", encoder.encode("1234"), AuthorisedUserType.RECRUITER));

		authorizedUserRepository.save(new AuthorizedUser("jobseeker1", "Job Seeker One", encoder.encode("1234"), AuthorisedUserType.JOB_SEEKER));
	}

	@PostMapping
	public ResponseEntity<?> login(@RequestBody AuthorizedUser authorizedUser) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authorizedUser.getId(), authorizedUser.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		Optional<AuthorizedUser> optionalUser = authorizedUserRepository.findById(authorizedUser.getId());
		if (optionalUser.isPresent())
			return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), authorizedUser.getName()));
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Userid Not found!");
	}

	// @PostMapping("/register")
	// public ResponseEntity<?> add(@RequestBody AuthorizedUser admin) {
	// 	if (authorizedUserRepository.existsByAdminId(admin.getAdminId())) {
	// 		return ResponseEntity
	// 				.badRequest()
	// 				.body(new MessageResponse("Error: Username is already taken!"));
	// 	}

	// 	admin.setPassword(encoder.encode(admin.getPassword()));
	// 	admin.setRole(AuthorisedUserType.PLATFORM_ADMIN);
	// 	authorizedUserRepository.save(admin);

	// 	return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	// }
}
