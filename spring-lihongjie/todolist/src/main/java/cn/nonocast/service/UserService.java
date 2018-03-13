package cn.nonocast.service;

import cn.nonocast.model.User;
import cn.nonocast.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public Page<User> findAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	public User findOne(Long id) {
		return userRepository.findOne(id);
	}

	@Cacheable(cacheNames="user", key="#p0")
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public List<User> findByIds(List<Long> selected) {
		return userRepository.findByIds(selected);
	}

	public User findByName(String name) {
		return userRepository.findByName(name);
	}

	public User findByWechatid(String wechatid) {
		return userRepository.findByWechatid(wechatid);
	}

	public Page<User> findByRole(User.Role role, Pageable pageable) {
		return userRepository.findByRole(role, pageable);
	}

	public Page<User> findByKeyword(String q, Pageable pageable) {
		return userRepository.findByKeyword(q, pageable);
	}

	public Page<User> findByKeyword(Long q, Pageable pageable) {
		return userRepository.findByKeyword(q, pageable);
	}

	public Boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	public User findByEmailOrName(String q) {
		return userRepository.findByEmailOrName(q);
	}

	@CachePut(cacheNames="user", key="#user.email")
	public User save(User user) {
		return userRepository.save(user);
	}

	public boolean exists(Long id) {
		return userRepository.exists(id);
	}

	@CacheEvict(cacheNames="user", key="#user.email")
	public void delete(User user) {
		userRepository.delete(user);
	}

	@CacheEvict(cacheNames="user", allEntries=true)
	public void delete(Iterable<User> users) {
		userRepository.delete(users);
	}
}
