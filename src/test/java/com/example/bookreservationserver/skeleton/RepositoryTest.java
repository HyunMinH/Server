package com.example.bookreservationserver.skeleton;


import com.example.bookreservationserver.book.domain.repository.BookRepository;
import com.example.bookreservationserver.borrow.domain.repository.BorrowRepository;
import com.example.bookreservationserver.borrow.domain.repository.TestConfig;
import com.example.bookreservationserver.user.domain.repository.UserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(TestConfig.class)
@ActiveProfiles("test")
public class RepositoryTest {
    @Autowired
    protected BorrowRepository borrowRepository;

    @Autowired
    protected BookRepository bookRepository;

    @Autowired
    protected UserRepository userRepository;
}
