package rs.ac.singidunum.autoservis.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import rs.ac.singidunum.autoservis.AppUtils;
import rs.ac.singidunum.autoservis.domain.AppUser;
import rs.ac.singidunum.autoservis.mock.MockRoleRepository;
import rs.ac.singidunum.autoservis.mock.MockUserRepository;
import rs.ac.singidunum.autoservis.model.CreateUserModel;

class UserServiceTest {

    private final MockUserRepository mockUserRepo = new MockUserRepository();
    private final MockRoleRepository mockRoleRepo = new MockRoleRepository();
    private final BCryptPasswordEncoder mockPassEncoder = new BCryptPasswordEncoder();
    private final UserService service = new UserService(mockUserRepo, mockRoleRepo, mockPassEncoder);

    /*
    Test kreiranja naloga sa pogresnom formatom mail adrese
    Ulazni parametri: string koji nije email adresa
    Oceikvani izlaz: RuntimeException sa porukom "Email address not valid"
     */
    @Test
    void createUserInvalidEmail() {
        String input = "ABCDEFGH";
        String expected = "Email address not valid";
        RuntimeException thrown = Assertions.assertThrowsExactly(RuntimeException.class, () ->
                service.createUser(CreateUserModel.builder()
                        .email(input)
                        .password("12345678")
                        .build(), AppUtils.ACCOUNT_CREATION_SECRET));
        Assertions.assertEquals(expected, thrown.getMessage());
    }

    /*
    Test kreiranja naloga sa pogresnom duzinom lozinke
    Ulazni parametri: lozinka duzine manje od 8
    Oceikvani izlaz: RuntimeException sa porukom "Password must have at least 8 chars"
     */
    @Test
    void createUserInvalidPasswordLength() {
        String input = "12345";
        String expected = "Password must have at least 8 chars";
        RuntimeException thrown = Assertions.assertThrowsExactly(RuntimeException.class, () ->
                service.createUser(CreateUserModel.builder()
                        .email("username@domain.com")
                        .password(input)
                        .build(), AppUtils.ACCOUNT_CREATION_SECRET));
        Assertions.assertEquals(expected, thrown.getMessage());
    }

    /*
    Integracioni test pronalazenja korisnika iz baze podataka
    Napomena: Baza podataka je simulirana u MockUserRepository#findById
    Ulazni paramteri: ppetrovic@gmail.com
    Ocekivan izlaz: Petar Petrovic
     */
    @Test
    void findUserByIdFromDataBase() {
        String input = "ppetrovic@gmail.com";
        String expected = "Petar Petrovic";

        // Konstrukcija objekta interfejsa Principal
        // Principal je integrisan korisnik u spring okruzenju
        // Koji nastaje autentifkacijom modula Spring Security
        AppUser output = service.getUserFromPrincipal(() -> input);
        Assertions.assertEquals(expected, output.getName());
    }
}