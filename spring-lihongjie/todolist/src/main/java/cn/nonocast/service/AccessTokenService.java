package cn.nonocast.service;

import cn.nonocast.model.AccessToken;
import cn.nonocast.model.User;
import cn.nonocast.repository.AccessTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccessTokenService {
	@Autowired
	private AccessTokenRepository accessTokenRepository;

	public AccessToken valid(String email, String token) {
		AccessToken result = null;

		AccessToken target = accessTokenRepository.get(email);
		if(target != null && target.getSecret().equals(token)) {
			result = target;
		}

		return result;
	}

	public AccessToken get(User user) {
		AccessToken token;
		AccessToken target = this.findByEmail(user.getEmail());
		if(target != null) {
			token = target;
			accessTokenRepository.invalidate(target);
		} else {
			token = new AccessToken(user);
			this.save(token);
		}
		return token;
	}

	public List<AccessToken> findAll() {
		return accessTokenRepository.findAll();
	}

	public Page<AccessToken> findAll(Pageable pageable) {
		return accessTokenRepository.findAll(pageable);
	}

	private AccessToken findByEmail(String email) {
		return accessTokenRepository.get(email);
	}

	private void save(AccessToken token) {
		accessTokenRepository.save(token);
		accessTokenRepository.invalidate(token);
	}
}
