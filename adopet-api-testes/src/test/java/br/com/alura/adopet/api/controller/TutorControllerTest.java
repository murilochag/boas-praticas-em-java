package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.service.TutorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class TutorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TutorService service;


    @Test
    void deveRetornarCodigo200QuandoSolicitarCadastroDeTutor() throws Exception {

        String json = """
                {
                  "nome": "João Silva",
                  "telefone": "(12)3456-7890",
                  "email": "joao.silva@example.com"
                }
                """;

        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.post("/tutores")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveRetornarCodigo400QuandoSolicitarCadastroDeTutorSemDados() throws Exception {

        String json = "{}";

        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.post("/tutores")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    void deveRetornarCodigo200QuandoSolicitarAtualizacaoDeTutor() throws Exception {

        String json = """
                {
                  "id": 10,
                  "nome": "João Silva da Costa",
                  "telefone": "(12)3456-7890",
                  "email": "joao.silva@example.com"
                }
                """;

        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.put("/tutores")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveRetornarCodigo400QuandoSolicitarAtualizacaoDeTutorComDadosErrados() throws Exception {

        String json = """
                {
                  "nome": "João Silva da Costa",
                  "telefone": "(12)3456-7890",
                  "email": "joao.silva@example.com"
                }
                """;

        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.put("/tutores")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

}