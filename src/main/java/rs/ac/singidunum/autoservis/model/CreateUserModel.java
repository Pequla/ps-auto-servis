package rs.ac.singidunum.autoservis.model;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CreateUserModel {

    private String name;
    private String email;
    private String password;
    private List<String> roles;
}
