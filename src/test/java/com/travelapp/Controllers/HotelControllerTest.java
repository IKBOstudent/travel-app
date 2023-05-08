package com.travelapp.Controllers;

import com.travelapp.Models.Hotel;
import com.travelapp.Services.HotelService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HotelControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private HotelService hotelService;


    @Test
    @WithMockUser("admin")
    void getMetadata() throws Exception {
        this.mvc.perform(get("/api/hotels/metadata"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser("admin")
    void getAllHotels() throws Exception {
        Mockito.when(hotelService.getAllHotels())
                .thenReturn(new ArrayList<>());

        this.mvc.perform(get("/api/hotels/all")
                                 .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));;
    }

    @Test
    @WithMockUser("admin")
    void createHotel_ok() throws Exception {
        String body = "{\"city\": \"a\"}";

        Mockito.when(hotelService.createHotel(Mockito.any(Hotel.class)))
                .thenReturn(true);

        this.mvc.perform(post("/api/hotels")
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .content(body))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));;
    }

    @Test
    @WithMockUser("admin")
    void createHotel_bad() throws Exception {
        String body = "{\"city\": \"a\"}";

        Mockito.when(hotelService.createHotel(Mockito.any(Hotel.class)))
                .thenReturn(false);

        this.mvc.perform(post("/api/hotels")
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .content(body))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser("admin")
    void deleteHotel_ok() throws Exception {
        Mockito.when(hotelService.deleteHotel(Mockito.any(Long.class)))
                .thenReturn(true);

        this.mvc.perform(delete("/api/hotels/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("success"));
    }

    @Test
    @WithMockUser("admin")
    void deleteHotel_bad() throws Exception {
        Mockito.when(hotelService.deleteHotel(Mockito.any(Long.class)))
                .thenReturn(false);

        this.mvc.perform(delete("/api/hotels/{id}", 1L))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}