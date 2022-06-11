package rs.ac.singidunum.autoservis.mock;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import rs.ac.singidunum.autoservis.domain.AppUser;
import rs.ac.singidunum.autoservis.repository.AppUserRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class MockUserRepository implements AppUserRepository {

    /*
    Mock-ovan metod za pronalazanje korinika za email
    Vrednost je unpared uneta
    */
    @Override
    public Optional<AppUser> findByEmail(String email) {
        AppUser user = new AppUser();
        user.setId(1);
        user.setName("Petar Petrovic");
        user.setEmail("ppetrovic@gmail.com");

        if (!email.equals(user.getEmail()))
            return Optional.empty();

        // email se podudara
        return Optional.of(user);
    }

    @Override
    public List<AppUser> findAll() {
        return null;
    }

    @Override
    public List<AppUser> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<AppUser> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<AppUser> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void delete(AppUser entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends AppUser> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends AppUser> S save(S entity) {
        return null;
    }

    @Override
    public <S extends AppUser> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    /*
    Mock-ovan metod za pronalazanje korinika za ID
    Vrednost je unpared uneta
     */
    @Override
    public Optional<AppUser> findById(Integer integer) {
        AppUser user = new AppUser();
        user.setId(1);
        user.setName("Korisnik sa ID 1");

        // Ukoliko id nije 1 vrati prazno
        if (integer != 1)
            return Optional.empty();

        // Id je 1
        return Optional.of(user);
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends AppUser> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends AppUser> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<AppUser> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public AppUser getOne(Integer integer) {
        return null;
    }

    @Override
    public AppUser getById(Integer integer) {
        return null;
    }

    @Override
    public AppUser getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends AppUser> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends AppUser> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends AppUser> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends AppUser> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends AppUser> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends AppUser> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends AppUser, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
