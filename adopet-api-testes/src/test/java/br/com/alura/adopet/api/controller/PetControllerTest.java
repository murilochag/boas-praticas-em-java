package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.PetDto;
import br.com.alura.adopet.api.model.TipoPet;
import br.com.alura.adopet.api.service.PetService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc
class PetControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PetService petService;

    @Test
    void deveRetornar200QuandoSolicitarParaListarPets() throws Exception {

        PetDto pet1 = new PetDto(10L, TipoPet.CACHORRO, "mauricio", "srd", 5);
        PetDto pet2 = new PetDto(11L, TipoPet.GATO, "lourenzo", "srd", 2);

        BDDMockito.when(petService.buscarPetsDisponiveis()).thenReturn(List.of(pet1, pet2));

        MockHttpServletResponse response =
                mvc.perform(MockMvcRequestBuilders.get("/pets"))
                .andReturn()
                .getResponse();

        Assertions.assertEquals(200, response.getStatus());

        String json = "[{\"id\":10,\"tipo\":\"CACHORRO\",\"nome\":\"mauricio\",\"raca\":\"srd\",\"idade\":5},{\"id\":11,\"tipo\":\"GATO\",\"nome\":\"lourenzo\",\"raca\":\"srd\",\"idade\":2}]";

        Assertions.assertEquals(json, response.getContentAsString());
    }

}