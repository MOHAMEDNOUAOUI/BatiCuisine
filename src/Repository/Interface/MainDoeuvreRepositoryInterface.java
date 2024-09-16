package Repository.Interface;

import Entity.MainDœuvre;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface MainDoeuvreRepositoryInterface {

    Optional<MainDœuvre> findById(int id) throws SQLException;
    List<MainDœuvre> findAll() throws SQLException;
    MainDœuvre save(MainDœuvre mainDœuvre);
    void deleteById(int id);
    void update(MainDœuvre mainDœuvre , String column , String value);
}
