package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastrarPetNoAbrigoDto;
import br.com.alura.adopet.api.dto.ListarAbrigoDto;
import br.com.alura.adopet.api.dto.SolicitarCadastroAbrigoDto;
import br.com.alura.adopet.api.excpetion.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AbrigoService {

    @Autowired
    private AbrigoRepository abrigoRepository;

    public List<ListarAbrigoDto> listarTodosOsAbrigos() {
        return abrigoRepository.findAll().stream()
                .map(abrigo -> new ListarAbrigoDto(abrigo.getId(), abrigo.getNome(), abrigo.getTelefone(), abrigo.getEmail()))
                .collect(Collectors.toList());
    }

    public void solicitarCadastro(SolicitarCadastroAbrigoDto dto) {
        boolean nomeJaCadastrado = abrigoRepository.existsByNome(dto.nome());
        boolean telefoneJaCadastrado = abrigoRepository.existsByTelefone(dto.telefone());
        boolean emailJaCadastrado = abrigoRepository.existsByEmail(dto.telefone());

        if (nomeJaCadastrado || telefoneJaCadastrado || emailJaCadastrado) {
            throw new ValidacaoException("\"Dados já cadastrados para outro abrigo!\"");
        } else {
            Abrigo abrigo = new Abrigo(dto.nome(), dto.telefone(), dto.email());
            abrigoRepository.save(abrigo);
        }
    }

    public List<Pet> listarPetsDoAbrigoPorId(String idRecebido) {
        Long id = Long.parseLong(idRecebido);
        return abrigoRepository.getReferenceById(id).getPets();
    }

    public List<Pet> listarPetsDoAbrigoPorNome(String nome) {
        Abrigo abrigo = abrigoRepository.findByNome(nome);
        if (abrigo == null) {
            throw new EntityNotFoundException();
        }
        return abrigo.getPets();
    }

    @Transactional
    public void cadastrarPetsNoAbrigoPorId(String idRecebido, CadastrarPetNoAbrigoDto dto) {
        Long id = Long.parseLong(idRecebido);
        Abrigo abrigo = abrigoRepository.getReferenceById(id);
        Pet pet = new Pet(dto.tipo(), dto.nome(), dto.raca(), dto.idade(), dto.cor(), dto.peso());
        pet.setAbrigo(abrigo);
        pet.setAdotado(false);
        abrigo.getPets().add(pet);
    }

    @Transactional
    public void cadastrarPetsNoAbrigoPorNome(String nome, CadastrarPetNoAbrigoDto dto) {
        Abrigo abrigo = abrigoRepository.findByNome(nome);
        Pet pet = new Pet(dto.tipo(), dto.nome(), dto.raca(), dto.idade(), dto.cor(), dto.peso());
        pet.setAbrigo(abrigo);
        pet.setAdotado(false);
        abrigo.getPets().add(pet);
    }
}
