package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.ListarPetDto;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    public List<ListarPetDto> listraTodosOsPetsDisponiveis() {
        List<Pet> pets = petRepository.findAll();
        List<ListarPetDto> disponiveis = new ArrayList<>();
        for (Pet pet : pets) {
            if (!pet.getAdotado()) {
                disponiveis.add(new ListarPetDto(pet.getId(),pet.getTipo(),pet.getNome(), pet.getRaca(), pet.getIdade(), pet.getCor(), pet.getPeso(), pet.getAdotado()));
            }
        }
        return disponiveis;
    }
}
