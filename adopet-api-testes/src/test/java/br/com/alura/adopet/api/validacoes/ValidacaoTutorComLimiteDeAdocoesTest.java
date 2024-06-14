package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class ValidacaoTutorComLimiteDeAdocoesTest {

    @InjectMocks
    private ValidacaoTutorComLimiteDeAdocoes validacaoTutorComLimiteDeAdocoes;

    @Mock
    private AdocaoRepository adocaoRepository;

    @Mock
    private SolicitacaoAdocaoDto dto;


    @Test
    void naoDevePermitirSolicitacaoDeAdocao() {

        BDDMockito.when(adocaoRepository.countByTutorIdAndStatus(dto.idTutor(), StatusAdocao.APROVADO))
                        .thenReturn(5);

        Assertions.assertThrows(ValidacaoException.class, () -> validacaoTutorComLimiteDeAdocoes.validar(dto));
    }

    @Test
    void devePermitirSolicitacaoDeAdocao() {

        BDDMockito.when(adocaoRepository.countByTutorIdAndStatus(dto.idTutor(), StatusAdocao.APROVADO))
                        .thenReturn(4);

        Assertions.assertDoesNotThrow(() -> validacaoTutorComLimiteDeAdocoes.validar(dto));
    }

}