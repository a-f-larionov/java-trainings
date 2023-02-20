package spring.transactional.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import spring.transactional.entities.MyEntity;

@Repository
public interface MyRepo extends CrudRepository<MyEntity, Long> {

    @Override
    Iterable<MyEntity> findAll();
}
