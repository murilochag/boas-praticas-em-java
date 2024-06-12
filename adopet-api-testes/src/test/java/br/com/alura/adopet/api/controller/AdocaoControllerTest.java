package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.service.AdocaoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
class AdocaoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AdocaoService service;

    @Test
    void deveriaDevolverCodigo200ParaSolicitacaoDeAdocaoSemErro() throws Exception {

        String json = """
                {   "idPet": 1,
                    "idTutor": 1,
                    "motivo": "motivo qualquer"
                }
                """;

        MockHttpServletResponse response = mvc.perform(post("/adocoes")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo400ParaSolicitacaoDeAdocaoComErros() throws Exception {

        String json = "{}";

        MockHttpServletResponse response = mvc.perform(post("/adocoes")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    void deveAprovarAdocaoSemErroRetornandoCodigo200() throws Exception {

        String json = """
                    {
                    "idAdocao":10
                    }
                    """;

        MockHttpServletResponse response = mvc.perform(put("/adocoes/aprovar")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveAprovarAdocaoComErroRetornandoCodigo400() throws Exception {

        String json = "{}";

        MockHttpServletResponse response = mvc.perform(put("/adocoes/aprovar")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    void deveReprovarAdocaoSemErroRetornandoCodigo200() throws Exception {

        String json = """
                    {
                    "idAdocao": 10,
                    "justificativa": "justificativa qualquer"
                    }
                    """;

        MockHttpServletResponse response = mvc.perform(put("/adocoes/reprovar")
                .content(json).contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveReprovarAdocaoComErroRetornandoCodigo400() throws Exception {

        String json = "{}";

        MockHttpServletResponse response = mvc.perform(put("/adocoes/reprovar")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

}