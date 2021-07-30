package com.smalaca.rentalapplication.infrastructure.rest.api.hotel;

import com.smalaca.rentalapplication.infrastructure.json.JsonFactory;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HotelRestControllerSystemTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String NAME = "Forum";
    private static final String STREET = "Florianska";
    private static final String POSTAL_CODE = "12-345";
    private static final String HOUSE_NUMBER = "1";
    private static final String BUILDING_NUMBER = "13";
    private static final String CITY = "Cracow";
    private static final String COUNTRY = "Poland";

    @Test
    void shouldAddHotel() throws Exception {

        HotelDto hotelDto = new HotelDto(NAME,STREET,POSTAL_CODE, BUILDING_NUMBER,CITY,COUNTRY);

        mockMvc.perform(post("/hotel")
           .contentType(MediaType.APPLICATION_JSON)
                .content(asJson(hotelDto)))
                .andExpect(status().isCreated());

    }

    @Test
    void shouldReturnExistingHotels() throws Exception {
        HotelDto hotel1 = new HotelDto("Forum","Lewa","12-345","12","Cracow","Poland");
        HotelDto hotel2 = new HotelDto("Forum22","Lewa2","12-345","12","Cracow","Poland");

        addHotel(hotel1);
        addHotel(hotel2);

        final ResultActions actual = mockMvc.perform(get("/hotel"));

        actual
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*]", Matchers.hasSize(2)))
                //.andExpect(jsonPath("$.[0].name").value("Forum"))
                .andExpect(jsonPath("$.[0].street").value("Lewa"))
                //.andExpect(jsonPath("$.[1].name").value("Forum22"))
                .andExpect(jsonPath("$.[1].street").value("Lewa2"));
    }

    private void addHotel(final HotelDto hotelDto) throws Exception {
        mockMvc.perform(post("/hotel")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJson(hotelDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturnAllHotels() throws Exception {
        final ResultActions actual = mockMvc.perform(get("/hotel"));

        actual
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnNothingWhenThereWasNoHotelCreated() throws Exception {
        final ResultActions actual = mockMvc.perform(get("/hotel"));

        actual
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*]", Matchers.hasSize(0)));
    }

    private String asJson(final HotelDto hotelDto) {
        return new JsonFactory().createJson(hotelDto);
    }

}