package Repository.Interface;

import Entity.MainDœuvre;
import com.sun.tools.javac.Main;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MainDoeuvreRepositoryInterface {

    Optional<MainDœuvre> findById(UUID id) throws SQLException;
    List<MainDœuvre> findAll() throws SQLException;
    MainDœuvre save(MainDœuvre mainDœuvre);
    void deleteById(UUID id);
    void update(MainDœuvre mainDœuvre , String column , String value);
    List<MainDœuvre> findByProjectId(UUID id) throws SQLException;
}
