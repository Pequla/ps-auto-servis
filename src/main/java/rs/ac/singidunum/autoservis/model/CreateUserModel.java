package rs.ac.singidunum.autoservis.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateUserModel {

    private String name;
    private String email;
    private String password;
    private List<String> roles;
}
