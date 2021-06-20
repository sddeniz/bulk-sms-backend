package UserAuth;

import com.behsa.smsgw.common.SmsGwException;
import entity.Token;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenConfirmRepository tokenConfirmRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private Map<String, User> loginUser = new HashMap<>();

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        final Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new UsernameNotFoundException(MessageFormat.format("User with email {0} cannot be found.", email));
        }
    }

    public void signup(User user) {

        final String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        userRepository.save(user);

        final Token token = new Token(user);

        tokenConfirmRepository.save(token);

        emailConfirm(user.getEmail(), token.getConfirm());
    }

    public String signIn(String username, String password) {
        Optional<User> user = userRepository.findByEmail(username);
        if (!user.isPresent()) {
            throw new SmsGwException("user not found");
        }
        String encryptedPassword = passwordEncoder.encode(password);
        if (!user.get().getPassword().equals(encryptedPassword)) {
            throw new SmsGwException("user not found");
        }
        String token = UUID.randomUUID().toString();
        loginUser.put(token, user.get());
        return token;
    }

    public void userconfirm(String tokenId) {
        Optional<Token> token = tokenConfirmRepository.findById(tokenId);
        if (!token.isPresent()) {
            throw new SmsGwException("token not found");
        }
        final User user = token.get().getUser();

        user.setEnable(true);

        userRepository.save(user);
        tokenConfirmRepository.delete(token.get());
    }


    private void emailConfirm(String userMail, String token) {
        final SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userMail);
        mailMessage.setSubject("Mail Confirmation Link!");
        mailMessage.setFrom("<MAIL>");
        mailMessage.setText(
                "Thank you for registering. Please click on the below link to activate your account." + "http://localhost:8002/test/sign-up/confirm?token="
                        + token);
        emailService.sendEmail(mailMessage);
    }

}

