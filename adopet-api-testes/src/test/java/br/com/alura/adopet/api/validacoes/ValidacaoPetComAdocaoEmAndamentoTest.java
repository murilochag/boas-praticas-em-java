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
class ValidacaoPetComAdocaoEmAndamentoTest {

    @InjectMocks
    private ValidacaoPetComAdocaoEmAndamento validacaoPetComAdocaoEmAndamento;

    @Mock
    private AdocaoRepository adocaoRepository;

    @Mock
    private SolicitacaoAdocaoDto dto;


    @Test
    void devePermitirSolicitacaoDeAdocao() {

        BDDMockito.when(adocaoRepository.existsByPetIdAndStatus(dto.idPet(), StatusAdocao.AGUARDANDO_AVALIACAO))
                .thenReturn(false);

        Assertions.assertDoesNotThrow(() -> validacaoPetComAdocaoEmAndamento.validar(dto));
    }

    @Test
    void naoDevePermitirSolicitacaoDeAdocao() {

        BDDMockito.when(adocaoRepository.existsByPetIdAndStatus(dto.idPet(), StatusAdocao.AGUARDANDO_AVALIACAO))
                .thenReturn(true);

        Assertions.assertThrows(ValidacaoException.class, () -> validacaoPetComAdocaoEmAndamento.validar(dto));
    }

}