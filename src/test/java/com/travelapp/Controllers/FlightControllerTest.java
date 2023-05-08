package com.travelapp.Controllers;

import com.travelapp.Models.Flight;
import com.travelapp.Services.FlightService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FlightControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FlightService flightService;

    @Test
    void getFlights_ok() throws Exception {
        Mockito.when(flightService.getFlights(Mockito.any(),
                                              Mockito.any(),
                                              Mockito.any()))
                .thenReturn(new ArrayList<>());

        this.mvc.perform(get("/api/flights")
                                 .param("origin", "Moscow")
                                 .param("destination", "Paris")
                                 .param("date", "2023-05-09"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void getFlights_bad_params() throws Exception {
        Mockito.when(flightService.getFlights(Mockito.any(),
                                              Mockito.any(),
                                              Mockito.any()))
                .thenReturn(new ArrayList<>());

        this.mvc.perform(get("/api/flights")
                                 .param("destination", "Paris")
                                 .param("date", "2023-05-09"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser("admin")
    void getMetadata() throws Exception {
        this.mvc.perform(get("/api/flights/metadata"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser("admin")
    void getAllFlights() throws Exception {
        Mockito.when(flightService.getAllFlights())
                .thenReturn(new ArrayList<>());

        this.mvc.perform(get("/api/flights/all")
                                 .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));;
    }

    @Test
    @WithMockUser("admin")
    void createFlight_ok() throws Exception {
        String body = "{\"origin\": \"a\",\"destination\": \"b\", \"date\": \"2023-05-21\"}";

        Mockito.when(flightService.createFlight(Mockito.any(Flight.class)))
                .thenReturn(true);

        this.mvc.perform(post("/api/flights")
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .content(body))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));;
    }

    @Test
    @WithMockUser("admin")
    void createFlight_bad() throws Exception {
        String body = "{\"origin\": \"a\", \"destination\": \"b\", \"date\": \"2023-05-21\"}";

        Mockito.when(flightService.createFlight(Mockito.any(Flight.class)))
                .thenReturn(false);

        this.mvc.perform(post("/api/flights")
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .content(body))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void bookFlight_ok() throws Exception {
        Mockito.when(flightService.getFlightById(Mockito.any(Long.class)))
                .thenReturn(new Flight(1L,
                                       "a",
                                       "b",
                                       LocalDate.of(2023, 5, 10),
                                       LocalTime.of(9, 10),
                                       LocalTime.of(12, 20),
                                       1000));

        this.mvc.perform(post("/api/flights/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void bookFlight_bad() throws Exception {
        Mockito.when(flightService.getFlightById(Mockito.any(Long.class)))
                .thenReturn(null);

        this.mvc.perform(post("/api/flights/{id}", 1L))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser("admin")
    void deleteFlight_ok() throws Exception {
        Mockito.when(flightService.deleteFlight(Mockito.any(Long.class)))
                .thenReturn(true);

        this.mvc.perform(delete("/api/flights/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("success"));
    }

    @Test
    @WithMockUser("admin")
    void deleteFlight_bad() throws Exception {
        Mockito.when(flightService.deleteFlight(Mockito.any(Long.class)))
                .thenReturn(false);

        this.mvc.perform(delete("/api/flights/{id}", 1L))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}