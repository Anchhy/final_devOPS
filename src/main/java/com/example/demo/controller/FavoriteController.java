package com.example.demo.controller;

import com.example.demo.model.Favorite;
import com.example.demo.repository.FavoriteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteRepository favoriteRepository;

    public FavoriteController(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    @GetMapping
    public ResponseEntity<List<Favorite>> getAll() {
        return ResponseEntity.ok(favoriteRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Favorite> getById(@PathVariable Long id) {
        return favoriteRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Favorite> create(@RequestBody Favorite favorite) {
        return ResponseEntity.ok(favoriteRepository.save(favorite));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Favorite> update(@PathVariable Long id, @RequestBody Favorite favorite) {
        return favoriteRepository.findById(id)
                .map(existing -> {
                    favorite.setId(id);
                    return ResponseEntity.ok(favoriteRepository.save(favorite));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return favoriteRepository.findById(id)
                .map(existing -> {
                    favoriteRepository.deleteById(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
