package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AtualizarTutorDto;
import br.com.alura.adopet.api.dto.SolicitarCadastroTutorDto;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TutorService {

    @Autowired
    TutorRepository tutorRepository;

    @Transactional
    public void solicitarCadastro(SolicitarCadastroTutorDto dto) {

        boolean telefoneJaCadastrado = tutorRepository.existsByTelefone(dto.telefone());
        boolean emailJaCadastrado = tutorRepository.existsByEmail(dto.email());

        if (telefoneJaCadastrado) {
            throw new RuntimeException("telefone já cadastrado ");
        } else if (emailJaCadastrado) {
            throw new RuntimeException("email já cadastrado ");
        } else {
            tutorRepository.save(new Tutor(dto.nome(), dto.telefone(), dto.email()));
        }

    }


    @Transactional
    public void solicitarAtualizacao(AtualizarTutorDto dto) {
        Tutor tutor = new Tutor(dto.id(), dto.nome(), dto.telefone(), dto.email());
        tutorRepository.save(tutor);
    }
}
