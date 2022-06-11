package rs.ac.singidunum.autoservis.mock;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import rs.ac.singidunum.autoservis.domain.AppRole;
import rs.ac.singidunum.autoservis.repository.AppRoleRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class MockRoleRepository implements AppRoleRepository {
    @Override
    public Optional<AppRole> findAppRoleByName(String name) {
        return Optional.empty();
    }

    @Override
    public List<AppRole> findAll() {
        return null;
    }

    @Override
    public List<AppRole> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<AppRole> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<AppRole> findAllById(Iterable<Integer> integers) {
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
    public void delete(AppRole entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends AppRole> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends AppRole> S save(S entity) {
        return null;
    }

    @Override
    public <S extends AppRole> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<AppRole> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends AppRole> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends AppRole> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<AppRole> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public AppRole getOne(Integer integer) {
        return null;
    }

    @Override
    public AppRole getById(Integer integer) {
        return null;
    }

    @Override
    public AppRole getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends AppRole> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends AppRole> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends AppRole> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends AppRole> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends AppRole> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends AppRole> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends AppRole, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
