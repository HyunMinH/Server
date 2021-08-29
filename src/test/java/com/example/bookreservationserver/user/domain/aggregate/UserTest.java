package com.example.bookreservationserver.user.domain.aggregate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class UserTest {
    @Test
    @DisplayName("비밀번호 변경 성공")
    public void testChangePasswordSuccess() {
        //given
        User user = User.builder().password("abcd1234").build();

        //when
        user.changePassword("abcd1234", "zxcv5678");

        //then
        assertEquals(user.getPassword(), "zxcv5678");
    }

    @Test
    @DisplayName("비밀번호 변경 실패")
    public void testChangePasswordFailed(){
        //given
        String oldPassword = "abcd1234";
        User user = User.builder().password(oldPassword).build();

        //when
        IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class, ()->user.changePassword(oldPassword+"a", oldPassword+"a"));
        NullPointerException exception2 = assertThrows(NullPointerException.class, ()->user.changePassword(oldPassword, null));
        IllegalArgumentException exception3 = assertThrows(IllegalArgumentException.class, ()->user.changePassword(oldPassword, oldPassword));


        //then
        assertEquals(exception1.getMessage(), "기존 비밀번호와 일치하지 않습니다.");
        assertEquals(exception2.getMessage(), "새 비밀번호가 null입니다.");
        assertEquals(exception3.getMessage(), "기존 비밀번호와 새 비밀번호가 일치합니다.");
    }
}