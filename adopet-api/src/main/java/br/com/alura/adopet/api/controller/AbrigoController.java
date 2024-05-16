package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.CadastrarPetNoAbrigoDto;
import br.com.alura.adopet.api.dto.ListarAbrigoDto;
import br.com.alura.adopet.api.dto.SolicitarCadastroAbrigoDto;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.service.AbrigoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/abrigos")
public class AbrigoController {

    @Autowired
    AbrigoService service;

    @GetMapping
    public ResponseEntity<List<ListarAbrigoDto>> listar() {
        return ResponseEntity.ok(service.listarTodosOsAbrigos());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastrar(@RequestBody @Valid SolicitarCadastroAbrigoDto dto) {
        try {
            service.solicitarCadastro(dto);
            return ResponseEntity.ok().body("Abrigo cadastrado com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{idOuNome}/pets")
    public ResponseEntity<List<Pet>> listarPets(@PathVariable String idOuNome) {
        try {
            return ResponseEntity.ok(service.listarPetsDoAbrigoPorId(idOuNome));
        } catch (EntityNotFoundException enfe) {
            return ResponseEntity.notFound().build();
        } catch (NumberFormatException e) {
            try {
                List<Pet> pets = service.listarPetsDoAbrigoPorNome(idOuNome);
                return ResponseEntity.ok(pets);
            } catch (EntityNotFoundException enfe) {
                return ResponseEntity.notFound().build();
            }
        }
    }

    @PostMapping("/{idOuNome}/pets")
    public ResponseEntity<String> cadastrarPet(@PathVariable String idOuNome, @RequestBody @Valid CadastrarPetNoAbrigoDto dto) {
        try {
            service.cadastrarPetsNoAbrigoPorId(idOuNome, dto);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException enfe) {
            return ResponseEntity.notFound().build();
        } catch (NumberFormatException nfe) {
            try {
                service.cadastrarPetsNoAbrigoPorNome(idOuNome, dto);
                return ResponseEntity.ok().build();
            } catch (EntityNotFoundException enfe) {
                return ResponseEntity.notFound().build();
            }
        }
    }

}
