package com.sigsaaqyf.demo.service;

import java.util.Optional;

import com.sigsaaqyf.demo.entity.User;
import com.sigsaaqyf.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    UserRepository repository;

    @Override
    public Iterable<User> getAllUsers() {
        return repository.findAll();
    }
    
    private boolean checkCarnetAvailble(User user) throws Exception {
        Optional <User> carnetFound = repository.findByCarnet(user.getCarnet());
        if (carnetFound.isPresent()) {
            throw new Exception("Carnet ya registrado");
        } 
        return true;
    }

    private boolean checkPasswordMatch(User user) throws Exception {
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            throw new Exception("contraseñas no coinciden");
        }
        return true;
    }

    @Override
    public User createUser(User user) throws Exception {
        if (checkCarnetAvailble(user) && checkPasswordMatch(user)) {
            user = repository.save(user);
        }
        return user;
    }

    @Override
    public User getUserById(Long id) throws Exception {
        
        return repository.findById(id).orElseThrow( () -> new Exception("usuario no encontrado"));
    }

    @Override
    public User updateUser(User user) throws Exception {
        User userFound = repository.findById(user.getId()).orElseThrow(() -> new Exception("no lo encontré man"));
        //User userFound = getUserById(user.getId());
        //userFound.setId(user.getId());
        userFound.setPrimerNombre(user.getPrimerNombre());
        userFound.setSegundoNombre(user.getSegundoNombre());
        userFound.setPrimerApellido(user.getPrimerApellido());
        userFound.setSegundoApellido(user.getSegundoApellido());
        userFound.setCarnet(user.getCarnet());
        userFound.setEmail(user.getEmail());
        userFound.setRoles(user.getRoles());

        return repository.save(userFound);
    }

    @Override
    public void deleteUser(Long id) throws Exception {
        User userFound = getUserById(id);
        repository.delete(userFound);

    }

    @Override
    public Iterable<User> getAllActiveUsers() {
        return repository.findAllByLocked(false);
        
    }


}