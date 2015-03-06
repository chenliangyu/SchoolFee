package org.school.fee.service.impl;

import java.util.List;

import org.school.fee.dao.UserDao;
import org.school.fee.models.User;
import org.school.fee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;

	public User login(String username, String password) {
		// TODO Auto-generated method stub
		List<User> users = userDao.findByUsernameAndPassword(username,password);
		if(users!=null && users.size()>0){
			return users.get(0);
		}
		return null;
	}
	
	public void saveUser(User user) {
		// TODO Auto-generated method stub
		userDao.save(user);
	}	
}
