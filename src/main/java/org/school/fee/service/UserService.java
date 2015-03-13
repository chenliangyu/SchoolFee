package org.school.fee.service;

import org.school.fee.models.User;

public interface UserService {
	public User login(String username, String password);
	public void saveUser(User user);
	public User findAdmin();
}
