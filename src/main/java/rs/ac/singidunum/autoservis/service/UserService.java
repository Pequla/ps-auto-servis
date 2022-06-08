package rs.ac.singidunum.autoservis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.autoservis.AppUtils;
import rs.ac.singidunum.autoservis.domain.AppRole;
import rs.ac.singidunum.autoservis.domain.AppUser;
import rs.ac.singidunum.autoservis.model.CreateUserModel;
import rs.ac.singidunum.autoservis.repository.AppRoleRepository;
import rs.ac.singidunum.autoservis.repository.AppUserRepository;

import java.security.Principal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final AppUserRepository userRepo;
    private final AppRoleRepository roleRepo;
    private final BCryptPasswordEncoder encoder;

    public AppUser getUser(String email) {
        Optional<AppUser> optional = userRepo.findByEmail(email);
        if (optional.isEmpty()) throw new RuntimeException("User not found");
        return optional.get();
    }

    public AppUser getUserFromPrincipal(Principal principal) {
        return getUser(principal.getName());
    }

    public AppUser createUser(CreateUserModel model, String secret) {
        if (!secret.equals(AppUtils.ACCOUNT_CREATION_SECRET)) {
            throw new RuntimeException("Bad secret");
        }

        AppUser user = new AppUser();
        user.setName(model.getName());
        user.setEmail(model.getEmail());
        user.setPassword(encoder.encode(model.getPassword()));

        Set<AppRole> roles = new HashSet<>();
        model.getRoles().forEach(role -> roles.add(getRoleByName(role)));
        user.setRoles(roles);

        return userRepo.save(user);
    }

    public AppRole getRoleByName(String roleName) {
        Optional<AppRole> optional = roleRepo.findAppRoleByName(roleName);
        if (optional.isEmpty()) throw new RuntimeException("Role not found");
        return optional.get();
    }

    public List<AppUser> getAllUsers() {
        return userRepo.findAll();
    }

    public List<AppRole> getAllRoles() {
        return roleRepo.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> optional = userRepo.findByEmail(username);
        if (optional.isEmpty()) throw new UsernameNotFoundException("User " + username + " was not found");
        AppUser user = optional.get();
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority((role.getName()))));
        return new User(user.getEmail(), user.getPassword(), authorities);
    }
}
