/**
 * 
 */
package com.pratik.userapp;

/**
 * @author Pratik Das
 *
 */
public class RegisterUserService {
	
	private UserRepository userRepository;
	
	private NotificationService notificationService;
	
	public RegisterUserService(UserRepository userRepository, NotificationService notificationService) {
		super();
		this.userRepository = userRepository;
		this.notificationService = notificationService;
	}



	public boolean register(final User user) {
		userRepository.addUser(user);
		notificationService.notify("User is created");
		return true;
	}

}
