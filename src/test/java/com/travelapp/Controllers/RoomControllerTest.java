package com.travelapp.Controllers;

import com.travelapp.Models.Room;
import com.travelapp.Services.RoomService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RoomControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RoomService roomService;

    @Test
    void getRooms_ok() throws Exception {
        Mockito.when(roomService.getRooms(Mockito.any(),
                                          Mockito.anyInt(),
                                          Mockito.any(),
                                          Mockito.any()))
                .thenReturn(new ArrayList<>());

        this.mvc.perform(get("/api/rooms")
                                 .param("city", "Paris")
                                 .param("guests", "2")
                                 .param("check_in_date", "2023-05-09")
                                 .param("check_out_date", "2023-05-10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void getRooms_bad_params() throws Exception {
        Mockito.when(roomService.getRooms(Mockito.any(),
                                          Mockito.anyInt(),
                                          Mockito.any(),
                                          Mockito.any()))
                .thenReturn(new ArrayList<>());

        this.mvc.perform(get("/api/rooms")
                                 .param("city", "Paris")
                                 .param("check_in_date", "2023-05-09")
                                 .param("check_out_date", "2023-05-10"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser("admin")
    void getMetadata() throws Exception {
        this.mvc.perform(get("/api/rooms/metadata"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser("admin")
    void getAllRooms() throws Exception {
        Mockito.when(roomService.getAllRooms())
                .thenReturn(new ArrayList<>());

        this.mvc.perform(get("/api/rooms/all")
                                 .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));;
    }

    @Test
    @WithMockUser("admin")
    void createRoom_ok() throws Exception {
        String body = "{\"beds\": 3}";

        Mockito.when(roomService.createRoom(Mockito.any(Long.class),
                                            Mockito.any(Room.class)))
                .thenReturn(true);

        this.mvc.perform(post("/api/rooms")
                                 .param("hotel_id", "1")
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .content(body))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser("admin")
    void createRoom_bad() throws Exception {
        String body = "{\"beds\": 3}";

        Mockito.when(roomService.createRoom(Mockito.any(Long.class),
                                            Mockito.any(Room.class)))
                .thenReturn(false);

        this.mvc.perform(post("/api/rooms")
                                 .param("hotel_id", "1")
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .content(body))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser("admin")
    void deleteRoom_ok() throws Exception {
        Mockito.when(roomService.deleteRoom(Mockito.any(Long.class)))
                .thenReturn(true);

        this.mvc.perform(delete("/api/rooms/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("success"));
    }

    @Test
    @WithMockUser("admin")
    void deleteRoom_bad() throws Exception {
        Mockito.when(roomService.deleteRoom(Mockito.any(Long.class)))
                .thenReturn(false);

        this.mvc.perform(delete("/api/rooms/{id}", 1L))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}