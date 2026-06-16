package com.example.demo.controller;

import com.example.demo.model.Terrain;
import com.example.demo.repository.TerrainRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/terrains")
public class TerrainController {

    private final TerrainRepository terrainRepository;

    public TerrainController(TerrainRepository terrainRepository) {
        this.terrainRepository = terrainRepository;
    }

    @GetMapping
    public ResponseEntity<List<Terrain>> getAll() {
        return ResponseEntity.ok(terrainRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Terrain> getById(@PathVariable Long id) {
        return terrainRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Terrain> create(@RequestBody Terrain terrain) {
        return ResponseEntity.ok(terrainRepository.save(terrain));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Terrain> update(@PathVariable Long id, @RequestBody Terrain terrain) {
        return terrainRepository.findById(id)
                .map(existing -> {
                    terrain.setId(id);
                    return ResponseEntity.ok(terrainRepository.save(terrain));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return terrainRepository.findById(id)
                .map(existing -> {
                    terrainRepository.deleteById(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
