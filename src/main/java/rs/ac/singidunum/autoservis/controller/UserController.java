package rs.ac.singidunum.autoservis.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import rs.ac.singidunum.autoservis.domain.AppRole;
import rs.ac.singidunum.autoservis.domain.AppUser;
import rs.ac.singidunum.autoservis.model.CreateUserModel;
import rs.ac.singidunum.autoservis.service.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping(path = "/api/user")
public class UserController {

    private final UserService service;

    @GetMapping(path = "/me")
    public AppUser getSelfUser(Principal principal) {
        return service.getUserFromPrincipal(principal);
    }

    @GetMapping(path = "/all")
    public List<AppUser> getAllUsers() {
        return service.getAllUsers();
    }

    @GetMapping(path = "/all/roles")
    public List<AppRole> getAllRoles() {
        return service.getAllRoles();
    }

    @PostMapping
    public AppUser createUserAccount(@RequestBody CreateUserModel model, @RequestParam(name = "secret") String secret) {
        return service.createUser(model, secret);
    }
}
