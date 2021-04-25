package com.example.bookreservationserver.user.domain.aggregate;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private static User user1;

    static {
        user1 = new User(1L, "hiname", "1234-1234", "abcd@email.com", "1234");
    }

    @Test
    public void testChangePassword(){
        // 비밀번호가 다름.
        assertThrows(IllegalArgumentException.class, () -> {
            user1.changePassword("12345", "123456");
        });

        // 새로운 비밀번호가 null임
        assertThrows(NullPointerException.class, ()->{
            user1.changePassword("1234", null);
        });

        // 원래 비밀번호랑 같음.
        assertThrows(IllegalArgumentException.class, () -> {
            user1.changePassword("1234", "1234");
        });

        // 패스워드 포맷도 해야함. 이건 dto에서 하자
    }
}