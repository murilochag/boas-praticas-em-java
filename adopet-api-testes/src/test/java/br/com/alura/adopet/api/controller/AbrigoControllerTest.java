package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.AbrigoDto;
import br.com.alura.adopet.api.dto.PetDto;
import br.com.alura.adopet.api.model.TipoPet;
import br.com.alura.adopet.api.service.AbrigoService;
import br.com.alura.adopet.api.service.PetService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class AbrigoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AbrigoService service;

    @MockBean
    private PetService petService;

    @Test
    void deveRetornarCodigo200QuandoListarAbrigos() throws Exception {

        AbrigoDto abrigo = new AbrigoDto(10L, "Abrigo Pet Feliz");

        BDDMockito.when(service.listar()).thenReturn(List.of(abrigo));

        mvc.perform(MockMvcRequestBuilders.get("/abrigos"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                            [{"id": 10, nome:"Abrigo Pet Feliz"}]
                        """));
    }

    @Test
    void deveRetornarCodigo200QuandoSolicitarCadastroDeUsuario() throws Exception {

        String json = """
                {
                    "nome":"abrigo qualquer",
                    "telefone":"(91)92222-8888",
                    "email":"qualquer@mail.com"
                }
                """;

        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.post("/abrigos")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();


        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveRetornarCodigo400QuandoSolicitarCadastroDeUsuarioComValoresInvalidos() throws Exception {

        // json com telefone no formáto inválido
        String json = """
                {
                    "nome":"abrigo qualquer",
                    "telefone":"22228888",
                    "email":"qualquer@mail.com"
                }
                """;

        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.post("/abrigos")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();


        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    void deveRetornarCodigo400QuandoSolicitarCadastroDeUsuarioComValoresNulos() throws Exception {

        String json = "{}";


        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.post("/abrigos")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    void deveRetornarCodigo200QuandoListarPetesDoAbrigoERetornarListaComPetes() throws Exception {

        String idOuNome = "10";
        PetDto petsNoAbrigo = new PetDto(10L, TipoPet.CACHORRO, "pretinho", "srd", 3);

        BDDMockito.when(service.listarPetsDoAbrigo(idOuNome)).thenReturn(List.of(petsNoAbrigo));

        mvc.perform(MockMvcRequestBuilders.get("/abrigos/"+idOuNome+"/pets"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [{
                            "id": 10,
                            "tipo": "CACHORRO",
                            "nome": "pretinho",
                            "raca": "srd",
                            "idade": 3
                        }]
                        """));
    }

    @Test
    void deveRetornarCodigo200QuandoSolicitarCadastrarPetNoAbrigo() throws Exception {

        String json = """
                    {
                        "tipo": "CACHORRO",
                        "nome": "sirius",
                        "raca": "srd",
                        "idade": 3,
                        "cor": "preto",
                        "peso": 4.35
                    }
                """;

        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.post("/abrigos/1/pets")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveRetornarCodigo400QuandoSolicitarCadastrarPetNoAbrigoComErro() throws Exception {

        String json = "{}";

        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.post("/abrigos/1/pets")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

}