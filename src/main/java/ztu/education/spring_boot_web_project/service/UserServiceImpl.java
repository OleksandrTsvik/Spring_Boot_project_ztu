package ztu.education.spring_boot_web_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ztu.education.spring_boot_web_project.dto.UserLoginDTO;
import ztu.education.spring_boot_web_project.dto.UserRegisterDTO;
import ztu.education.spring_boot_web_project.entity.User;
import ztu.education.spring_boot_web_project.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public User register(UserRegisterDTO userRegisterDTO) {
        if (userRepository.findUserByEmail(userRegisterDTO.getEmail()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Електронна адреса уже використовується");
        }

        if (userRepository.findUserByPhoneNumber(userRegisterDTO.getPhoneNumber()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Номер телефону уже використовується");
        }

        User newUser = new User();

        newUser.setFullName(userRegisterDTO.getFullName());
        newUser.setPhoneNumber(userRegisterDTO.getPhoneNumber());
        newUser.setEmail(userRegisterDTO.getEmail());
        newUser.setPassword(bCryptPasswordEncoder.encode(userRegisterDTO.getPassword()));
        newUser.setEnabled(true);

        return userRepository.save(newUser);
    }

    @Override
    @Transactional
    public User login(UserLoginDTO userLoginDTO) {
        User user = userRepository.findUserByEmail(userLoginDTO.getEmail());

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Неправильна електронна адреса або пароль");
        }

        if (!user.isEnabled()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Користувач заблокований");
        }

        if (!bCryptPasswordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Неправильна електронна адреса або пароль");
        }

        return user;
    }

    @Override
    @Transactional
    public User login(String email, String password) {
        return this.login(new UserLoginDTO(email, password));
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public int deleteUser(int id) {
        return userRepository.deleteUserById(id);
    }

    @Override
    @Transactional
    public String toggleUser(int id) {
        User user = userRepository.findUserById(id);
        if (user == null) {
            return "Не вдалося знайти користувача з 'id'=" + id;
        }

        user.setEnabled(!user.isEnabled());
        userRepository.save(user);

        if (user.isEnabled()) {
            return "Користувача з 'id'=" + id + " було розблоковано";
        }

        return "Користувача з 'id'=" + id + " було заблоковано";
    }
}
