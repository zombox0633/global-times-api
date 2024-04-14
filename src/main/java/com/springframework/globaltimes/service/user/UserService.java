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
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    public final UserRepository userRepository;

    //GET BY ID
    public User getUserById(String id){
        log.info("Get user by id:{}",id);
        return userRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new NotFoundException(ErrorMessage.NOT_FOUND.formatted("User")));
    }

    //Get Register
    public User getRegister(UseRegisterRequest request){
        try {
            var checkEmail = userRepository.findByEmail(request.email());

            if (checkEmail.isEmpty()){
                throw new NotFoundException(ErrorMessage.NOT_FOUND.formatted("email"));
            }

            var user = checkEmail.get();
            var key = Jwts.SIG.HS256.key().build();
            var checkJwt = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(request.jwt())
                    .getPayload()
                    .getSubject()
                    .equals(user.getId().toString());

            if (checkJwt){
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
            byte[] key = Base64.getDecoder().decode("EMk4cWOwmaaGSKKycg0GO3jCUe1Naufk");
            var secretKey = new SecretKeySpec(key, 0, key.length, "HS256");
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

    //CREATE ID
    public User createUser(UserRequest request){
        try {
            log.info("Create user with username:{}", request.email());

            //Check if email is already in use
            userRepository.findByEmail(request.email()).ifPresent(user -> {
                throw new InvalidException("Email is already in use");
            });

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
