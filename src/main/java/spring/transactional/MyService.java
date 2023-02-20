package spring.transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.transactional.entities.MyEntity;
import spring.transactional.repositories.MyRepo;

@Service
@RequiredArgsConstructor
@Transactional
public class MyService {

    private final ApplicationContext applicationContext;

    private final MyRepo myRepo;

    @Transactional
    public void test1() {

        long count = myRepo.count();
        myRepo.save(new MyEntity());
        myRepo.count();
        test2();
    }

    @Transactional
    public void test2() {
        long count = myRepo.count();
        myRepo.save(new MyEntity());
        myRepo.count();
    }
}


