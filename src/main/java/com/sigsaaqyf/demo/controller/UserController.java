package com.sigsaaqyf.demo.controller;

import com.sigsaaqyf.demo.service.UserService;

import javax.validation.Valid;

import com.sigsaaqyf.demo.entity.Role;
import com.sigsaaqyf.demo.entity.User;
import com.sigsaaqyf.demo.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/")
    public String index() {
        return "user-form/login";
    }

    @GetMapping("/login")
    public String login(){
        return "user-form/login";
    }

    @GetMapping("/activeUserList")
    public String activeUserList(Model model) {
        model.addAttribute("userList", userService.getAllActiveUsers());
        return "user-form/user-list";
    }

    @GetMapping("/userList")
    public String userList(Model model) {
        model.addAttribute("userList", userService.getAllUsers());
        return "user-form/user-list";
    }

    

    @GetMapping("/userRegister")
    public String userRegister(Model model) {
        model.addAttribute("userForm", new User());
        model.addAttribute("roleList", roleRepository.findAll());
        model.addAttribute("myRole", new Role());
        model.addAttribute("userRegisterMode", true);
        return "user-form/user-register";
    }

    @PostMapping("/userRegister")
    public String userRegister(@Valid   @ModelAttribute("userForm") User user, BindingResult result, ModelMap model, 
                                    @ModelAttribute("myRole")Role myRole) {
        
        if (result.hasErrors()) {
            model.addAttribute("userForm", user);
            model.addAttribute("roleList", roleRepository.findAll());
            model.addAttribute("myRole", new Role());
            model.addAttribute("userRegisterMode", true);
            return "user-form/user-register";
        } else {
            try {
                //user.setId(null);
                if (user.getRoles()==null) {
                    user.setLocked(false);
                    user.setRoles(roleRepository.findByNombre("estudiante") );
                    userService.createUser(user);
                } else {
                    user.setLocked(false);
                    user.setRoles(roleRepository.findById( user.getRoles().getId()) );
                    userService.createUser(user);
                }
                
            } catch (Exception e) {
                model.addAttribute("errorMessage",e.getMessage());
                model.addAttribute("userForm", user);
                model.addAttribute("roleList", roleRepository.findAll());
                model.addAttribute("myRole", new Role());
                model.addAttribute("userRegisterMode", true);
                return "user-form/user-register";
            }
        }
        return "user-form/login";
    }

    @GetMapping("/adminRegister")
    public String adminRegister(Model model) {
        model.addAttribute("userForm", new User());
        model.addAttribute("roleList", roleRepository.findAll());
        model.addAttribute("adminRegisterMode", true);
        return "user-form/user-register";
    }

    @PostMapping("/adminRegister")
    public String adminRegister(@Valid   @ModelAttribute("userForm")User user, BindingResult result, ModelMap model) {
        
        if (result.hasErrors()) {
            model.addAttribute("userForm", user);
            model.addAttribute("roleList", roleRepository.findAll());
            model.addAttribute("adminRegisterMode", true);
            return "user-form/user-register";
        } else {
            try {
                //user.setId(null);
                user.setLocked(false);
                user.setRoles(roleRepository.findById( user.getRoles().getId()) );
                userService.createUser(user);
            } catch (Exception e) {
                model.addAttribute("errorMessage",e.getMessage());
                model.addAttribute("userForm", user);
                model.addAttribute("roleList", roleRepository.findAll());
                model.addAttribute("adminRegisterMode", true);
                return "user-form/user-register";
            }
        }
        return "redirect:/userList";
    }

    @GetMapping("/userEdit/{id}")
    public String editUser(Model model, @PathVariable(name = "id")Long id) throws Exception {
        
        User userToEdit = userService.getUserById(id);
        model.addAttribute("userForm",userToEdit);
        model.addAttribute("userEditMode", true);
        model.addAttribute("roleList", roleRepository.findAll());
        model.addAttribute("myRole", new Role());
        model.addAttribute("adminRegisterMode", true);
        return "user-form/user-register";
    }

    @PostMapping("/userEdit")
    public String editUser(@Valid @ModelAttribute("userForm")User userFrom, BindingResult result, ModelMap model,
                                    @ModelAttribute("myRole")Role myRole) {
        if (result.hasErrors()) {
            model.addAttribute("userForm", userFrom);
            model.addAttribute("userEditMode", true);
            model.addAttribute("roleList", roleRepository.findAll());
            model.addAttribute("myRole", new Role());
            model.addAttribute("adminRegisterMode", true);
            return "user-form/user-register";
        }
        try {
            userFrom.setRoles(roleRepository.findById( userFrom.getRoles().getId()) );
            //userFrom.setRoles(roleRepository.findById( myRole.getId() ) );
            userService.updateUser(userFrom);
            model.addAttribute("userList", userService.getAllUsers());
            //return "user-form/user-list";
            return "redirect:/userList/";
        } catch (Exception e) {
            model.addAttribute("errorMessage",e.getMessage());
            model.addAttribute("userForm", userFrom);
            model.addAttribute("userEditMode", true);
            model.addAttribute("adminRegisterMode", true);
            model.addAttribute("roleList", roleRepository.findAll());
            return "user-form/user-register";
        }
    }

    @GetMapping("/userDelete/{id}")
    public String deleteUser(Model model, @PathVariable(name = "id")Long id) throws Exception{
        try {
            userService.deleteUser(id);
        } catch (Exception e) {
            model.addAttribute("listErrorMessage",e.getMessage());
        }
        
        //return userList(model);
        model.addAttribute("userList", userService.getAllUsers());
        return "redirect:/userList";
    }

    @GetMapping("/lockUser/{id}")
    public String lockUser(Model model, @PathVariable(name="id")Long id){
        try {
            User user = userService.getUserById(id);
            user.setLocked(true);
            userService.updateUser(user);
        } catch (Exception e) {
            model.addAttribute("listErrorMessage",e.getMessage());
        }
        //return userList(model);
        model.addAttribute("userList", userService.getAllUsers());
        return "redirect:/userList";
    }

    @GetMapping("/unlockUser/{id}")
    public String unlockUser(Model model, @PathVariable(name="id")Long id){
        try {
            User user = userService.getUserById(id);
            user.setLocked(false);
            userService.updateUser(user);
        } catch (Exception e) {
            model.addAttribute("listErrorMessage",e.getMessage());
        }
        //return userList(model);
        model.addAttribute("userList", userService.getAllUsers());
        return "redirect:/userList";
    }
}




















