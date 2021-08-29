package com.example.bookreservationserver.profile;

import com.example.bookreservationserver.skeleton.ControllerTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class WebRestControllerTest extends ControllerTest {
    @InjectMocks
    private WebRestController controller;

    @Mock
    private Environment environment;

    @Test
    public void checkProfile() throws Exception {
        //given
        doReturn(new String[]{"UP"}).when(environment).getActiveProfiles();

        //when
        ResultActions resultActions =  mockMvc.perform(
                MockMvcRequestBuilders.get("/profile")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        MvcResult response = resultActions.andExpect(status().isOk()).andReturn();
        assertEquals(response.getResponse().getContentAsString(), "UP");
    }

    @Override
    protected Object getController() {
        return controller;
    }
}