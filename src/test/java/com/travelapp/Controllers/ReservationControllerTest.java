package com.travelapp.Controllers;

import com.travelapp.Models.Reservation;
import com.travelapp.Services.ReservationService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ReservationControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ReservationService reservationService;

    @Test
    @WithMockUser("admin")
    void getMetadata() throws Exception {
        this.mvc.perform(get("/api/reservations/metadata"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser("admin")
    void getAllReservations() throws Exception {
        Mockito.when(reservationService.getAllReservations())
                .thenReturn(new ArrayList<>());

        this.mvc.perform(get("/api/reservations/all")
                                 .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));;
    }

    @Test
    @WithMockUser("admin")
    void createReservation_ok() throws Exception {
        String body = "{\"checkInDate\": \"2023-05-09\", \"checkOutDate\": \"2023-05-12\"}";

        Mockito.when(reservationService.createReservation(Mockito.any(Long.class),
                                                          Mockito.any(Reservation.class)))
                .thenReturn(true);

        this.mvc.perform(post("/api/reservations")
                                 .param("room_id", "1")
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .content(body))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser("admin")
    void createReservation_bad() throws Exception {
        String body = "{\"checkInDate\": \"2023-05-09\", \"checkOutDate\": \"2023-05-12\"}";

        Mockito.when(reservationService.createReservation(Mockito.any(Long.class),
                                                          Mockito.any(Reservation.class)))
                .thenReturn(false);

        this.mvc.perform(post("/api/reservations")
                                 .param("room_id", "1")
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .content(body))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser("admin")
    void deleteReservation_ok() throws Exception {
        Mockito.when(reservationService.deleteReservation(Mockito.any(Long.class)))
                .thenReturn(true);

        this.mvc.perform(delete("/api/reservations/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("success"));
    }

    @Test
    @WithMockUser("admin")
    void deleteReservation_bad() throws Exception {
        Mockito.when(reservationService.deleteReservation(Mockito.any(Long.class)))
                .thenReturn(false);

        this.mvc.perform(delete("/api/reservations/{id}", 1L))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}