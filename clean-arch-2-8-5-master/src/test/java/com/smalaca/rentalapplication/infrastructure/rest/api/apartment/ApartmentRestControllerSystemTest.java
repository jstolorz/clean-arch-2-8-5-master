package com.smalaca.rentalapplication.infrastructure.rest.api.apartment;

import com.google.common.collect.ImmutableMap;
import com.smalaca.rentalapplication.infrastructure.json.JsonFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Map;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
class ApartmentRestControllerSystemTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String OWNER_ID = "1234";
    private static final String STREET = "Florianska";
    private static final String POSTAL_CODE = "12-345";
    private static final String HOUSE_NUMBER = "1";
    private static final String APARTMENT_NUMBER = "13";
    private static final String CITY = "Cracow";
    private static final String COUNTRY = "Poland";
    private static final String DESCRIPTION = "Nice place to stay";
    private static final Map<String, Double> ROOMS_DEFINITION = ImmutableMap.of("Toilet", 10.0, "Bedroom", 30.0);

    @Test
    void shouldReturnNothingWhenApartmentDoesNotExist() throws Exception {
        String notExistId = UUID.randomUUID().toString();

        final ResultActions actual = mockMvc.perform(MockMvcRequestBuilders.get("/apartment/" + notExistId));

        actual
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.apartment").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.bookingHistory").isEmpty());

    }

    @Test
    void shouldReturnExistingApartment() throws Exception {

        final ApartmentDto apartmentDto = new ApartmentDto(OWNER_ID, STREET, POSTAL_CODE, HOUSE_NUMBER, APARTMENT_NUMBER, CITY, COUNTRY,
                DESCRIPTION, ROOMS_DEFINITION);

        final MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/apartment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJson(apartmentDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();


        final String existId;
        final ResultActions actual = mockMvc.perform(MockMvcRequestBuilders.get(mvcResult.getResponse().getRedirectedUrl()));

        actual
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.apartment.ownerId").value(OWNER_ID))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apartment.street").value(STREET))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bookingHistory").isEmpty());
    }

    private String asJson(final ApartmentDto apartmentDto) {
        return new JsonFactory().createJson(apartmentDto);
    }

}