package com.example.demo.controller;

import com.example.demo.model.TerrainImage;
import com.example.demo.repository.TerrainImageRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/terrain-images")
public class TerrainImageController {

    private final TerrainImageRepository terrainImageRepository;

    public TerrainImageController(TerrainImageRepository terrainImageRepository) {
        this.terrainImageRepository = terrainImageRepository;
    }

    @GetMapping
    public ResponseEntity<List<TerrainImage>> getAll() {
        return ResponseEntity.ok(terrainImageRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TerrainImage> getById(@PathVariable Long id) {
        return terrainImageRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TerrainImage> create(@RequestBody TerrainImage terrainImage) {
        return ResponseEntity.ok(terrainImageRepository.save(terrainImage));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TerrainImage> update(@PathVariable Long id, @RequestBody TerrainImage terrainImage) {
        return terrainImageRepository.findById(id)
                .map(existing -> {
                    terrainImage.setId(id);
                    return ResponseEntity.ok(terrainImageRepository.save(terrainImage));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return terrainImageRepository.findById(id)
                .map(existing -> {
                    terrainImageRepository.deleteById(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
