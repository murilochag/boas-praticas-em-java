package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ValidacaoTutorComAdocaoEmAndamentoTest {

    @InjectMocks
    private ValidacaoTutorComAdocaoEmAndamento validacaoTutorComAdocaoEmAndamento;

    @Mock
    private AdocaoRepository adocaoRepository;

    @Mock
    private TutorRepository tutorRepository;

    @Mock
    private Adocao adocao;

    @Mock
    private SolicitacaoAdocaoDto dto;

    @Spy
    private List<Adocao> adocoes = new ArrayList<>();

    @Mock
    private Tutor tutor;

    @Mock
    private Pet pet;


    @Test
    void devePemitirSocilictacaoDeAdocaComAdocaoReprovada() {

        BDDMockito.when(adocaoRepository.findAll()).thenReturn(adocoes);
        BDDMockito.when(tutorRepository.getReferenceById(dto.idTutor())).thenReturn(tutor);
        BDDMockito.when(adocao.getTutor()).thenReturn(tutor);
        adocao.marcarComoReprovada("justificativa qualquer");
        adocoes.add(adocao);

        Assertions.assertDoesNotThrow(() -> validacaoTutorComAdocaoEmAndamento.validar(dto));
    }

    @Test
    void devePemitirSocilictacaoDeAdocaComAdocaoAprovada() {

        BDDMockito.when(adocaoRepository.findAll()).thenReturn(adocoes);
        BDDMockito.when(tutorRepository.getReferenceById(dto.idTutor())).thenReturn(tutor);
        BDDMockito.when(adocao.getTutor()).thenReturn(tutor);
        adocao.marcarComoAprovada();
        adocoes.add(adocao);

        Assertions.assertDoesNotThrow(() -> validacaoTutorComAdocaoEmAndamento.validar(dto));
    }

    @Test
    void naoDevePemitirSocilictacaoDeAdoca() {

        BDDMockito.when(adocaoRepository.findAll()).thenReturn(adocoes);
        BDDMockito.when(tutorRepository.getReferenceById(dto.idTutor())).thenReturn(tutor);
        adocao = new Adocao(tutor, pet, "motivo qualquer");
        adocoes.add(adocao);

        Assertions.assertThrows(ValidacaoException.class,() -> validacaoTutorComAdocaoEmAndamento.validar(dto));
    }

}