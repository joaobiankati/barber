package com.cesul.barber.controllers;

import com.cesul.barber.dtos.UpdateAvatarDTO;
import com.cesul.barber.dtos.UserCreationDTO;
import com.cesul.barber.dtos.UserResponseDTO;
import com.cesul.barber.services.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@Tag(name = "Usuários", description = "Gerenciamento de usuários da barbearia")
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping
    @Operation(summary = "Listar todos", description = "Retorna a lista de todos os usuários cadastrados.")
    public ResponseEntity<List<UserResponseDTO>> getAll() {
        return ResponseEntity.ok(this.usersService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar por ID", description = "Retorna o utilizador correspondente ao ID.")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable UUID id) {
        return this.usersService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Criar utilizador", description = "Gera um ID automático, valida os dados (nome e e-mail) e salva.")
    public ResponseEntity<UserResponseDTO> create(@RequestBody UserCreationDTO user) {
        UserResponseDTO newUser = this.usersService.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar utilizador", description = "Substitui os dados do utilizador correspondente ao ID.")
    public ResponseEntity<UserResponseDTO> update(@PathVariable UUID id, @RequestBody UserCreationDTO updatedUser) {
        return this.usersService.update(id, updatedUser)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/avatar")
    @Operation(
            summary = "Atualizar Avatar",
            description = "Atualiza exclusivamente a imagem de perfil do utilizador. Ignora outros campos."
    )
    public ResponseEntity<UserResponseDTO> patchUser(@PathVariable UUID id, @RequestBody UpdateAvatarDTO avatar) {
        return this.usersService.updateAvatar(id, avatar)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/clear")
    @Operation(summary = "Limpar utilizadores", description = "Limpa todos os utilizadores (útil para reiniciar os testes).")
    public ResponseEntity<Void> clearUsers() {
        this.usersService.clear();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar utilizador", description = "Remove o utilizador correspondente ao ID.")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        boolean removed = this.usersService.delete(id);

        if (removed) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
