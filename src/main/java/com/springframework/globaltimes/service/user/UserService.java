package com.springframework.globaltimes.service.user;

import com.springframework.globaltimes.constants.ErrorMessage;
import com.springframework.globaltimes.dto.user.UseRegisterRequest;
import com.springframework.globaltimes.dto.user.UserChangePasswordRequest;
import com.springframework.globaltimes.dto.user.UserRequest;
import com.springframework.globaltimes.dto.user.UserUpdateRequest;
import com.springframework.globaltimes.entity.user.User;
import com.springframework.globaltimes.exception.InvalidException;
import com.springframework.globaltimes.exception.NotFoundException;
import com.springframework.globaltimes.repository.user.UserRepository;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    public final UserRepository userRepository;

    private final byte[] key = Base64.getDecoder().decode("3GNoee4B1nUtT4Us04gyowhmffg0NKj6PacDo++cM9k=");

    //GET BY ID
    public User getUserById(String id){
        log.info("Get user by id:{}",id);
        return userRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new NotFoundException(ErrorMessage.NOT_FOUND.formatted("User")));
    }

    //Get Authentication
    public String authenticateUser(UserRequest request){
        try {
            var checkEmail = userRepository.findByEmail(request.email());
            if (checkEmail.isEmpty()){
                throw new NotFoundException(ErrorMessage.NOT_FOUND.formatted("email"));
            }

            var user = checkEmail.get();
            var checkPassword = BCrypt.checkpw(request.password(), user.getPassword());
            if(!checkPassword){
                throw new InvalidException("Invalid password.");
            }

            //jwt
            var secretKey = new SecretKeySpec(key, 0, key.length, "HmacSHA256");

            var nowTimeMillis = System.currentTimeMillis();
            var expTimeMillis = nowTimeMillis + 3600000;
            var exp = new Date(expTimeMillis);

            return Jwts.builder()
                    .header()
                    .keyId("userGlobalTime")
                    .and()
                    .subject(user.getId().toString())
                    .signWith(secretKey)
                    .issuedAt(new Date())
                    .expiration(exp)
                    .compact();

        }catch (InvalidException e){
            log.error(ErrorMessage.INVALID_REQUEST_LOG.formatted(e.getMessage()), e);
            throw e;
        }catch (Exception e){
            log.error(ErrorMessage.EXCEPTION_REQUEST_LOG.formatted(e.getMessage()),e);
            throw new InvalidException(ErrorMessage.FAILED_LOG.formatted("authentication user"));
        }
    }

    //Get UserProfile
    public User getUserProfile(UseRegisterRequest request){
        try {
            var checkEmail = userRepository.findByEmail(request.email());

            if (checkEmail.isEmpty()){
                throw new NotFoundException(ErrorMessage.NOT_FOUND.formatted("email"));
            }

            var user = checkEmail.get();

            //ทำการหาค่า key
//            var key = Jwts.SIG.HS256.key().build();
//            var encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
//            log.info(encodedKey);

            var secretKey = new SecretKeySpec(key, 0, key.length, "HmacSHA256");
            var isCheckJwt = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(request.jwt())
                    .getPayload()
                    .getSubject()
                    .equals(user.getId().toString());

            if (!isCheckJwt){
                throw new InvalidException("Invalid Jwt.");
            }

            return user;

        }catch (InvalidException e){
            log.error(ErrorMessage.INVALID_REQUEST_LOG.formatted(e.getMessage()), e);
            throw e;
        }catch (Exception e){
            log.error(ErrorMessage.EXCEPTION_REQUEST_LOG.formatted(e.getMessage()),e);
            throw new InvalidException(ErrorMessage.FAILED_LOG.formatted("register"));
        }
    }

    //CREATE ID
    public User createUser(UserRequest request){
        try {
            log.info("Create user with username:{}", request.email());

            //Check if email is already in use
            Optional<User> userOptional = userRepository.findByEmail(request.email());
            if(userOptional.isEmpty()){
                throw new InvalidException("Email is already in use");
            }

            //hashPassword
            var hashPassword = BCrypt.hashpw(request.password(), BCrypt.gensalt());

            //Create user
            var user = new User();
            user.setEmail(request.email());
            user.setPassword(hashPassword);
            user.setName(request.email());
            user.setBirthday(null);
            user.setRole("user");

            return  userRepository.save(user);

        }catch (InvalidException e){
            log.error(ErrorMessage.INVALID_REQUEST_LOG.formatted(e.getMessage()), e);
            throw e;
        }catch (Exception e){
            log.error(ErrorMessage.EXCEPTION_REQUEST_LOG.formatted(e.getMessage()),e);
            throw new InvalidException(ErrorMessage.FAILED_LOG.formatted("create user"));
        }
    }

    //Update Profile
    public User updateProfile(String id, UserUpdateRequest request){
        try {
            if(request.name() == null && request.birthday() == null){
                throw new InvalidException("No update information provided.");
            }

            var existingUser = getUserById(id);

            if(request.name() != null){
                existingUser.setName(request.name());
            }
            if(request.birthday() != null){
                existingUser.setBirthday(request.birthday());
            }

            userRepository.save(existingUser);
            return existingUser;

        }catch (InvalidException e){
            log.error(ErrorMessage.INVALID_REQUEST_LOG.formatted(e.getMessage()), e);
            throw e;
        }catch (Exception e){
            log.error(ErrorMessage.EXCEPTION_REQUEST_LOG.formatted(e.getMessage()),e);
            throw new InvalidException(ErrorMessage.FAILED_LOG.formatted("update user"));
        }
    }

    //Change Password
    public User changePassword(String id, UserChangePasswordRequest request){
        try {
            var existingUser = getUserById(id);
            var checkOldPassword = BCrypt.checkpw(request.oldPassword(), existingUser.getPassword());

            if(!checkOldPassword){
                throw new InvalidException("Invalid old password.");
            }
            if (!request.newPassword().equals(request.confirmNewPassword())){
                throw new InvalidException("New passwords do not match.");
            }

            //hashPassword
            var hashNewPassword = BCrypt.hashpw(request.newPassword(), BCrypt.gensalt());

            existingUser.setPassword(hashNewPassword);
            return existingUser;
        }catch (InvalidException e){
            log.error(ErrorMessage.INVALID_REQUEST_LOG.formatted(e.getMessage()), e);
            throw e;
        }catch (Exception e){
            log.error(ErrorMessage.EXCEPTION_REQUEST_LOG.formatted(e.getMessage()),e);
            throw new InvalidException(ErrorMessage.FAILED_LOG.formatted("change password"));
        }
    }

}
