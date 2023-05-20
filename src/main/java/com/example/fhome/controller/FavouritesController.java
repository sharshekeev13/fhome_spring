package com.example.fhome.controller;


import com.example.fhome.domain.entity.Favourites;
import com.example.fhome.domain.enums.AddDeleteStatus;
import com.example.fhome.service.impl.FavouritesServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "FAVOURITES", description = "Конечные точки для работы с избранными товарами")
@RestController
@RequestMapping("/api/favourites")
public class FavouritesController {

    private final FavouritesServiceImpl favouritesService;

    public FavouritesController(FavouritesServiceImpl favouritesService) {
        this.favouritesService = favouritesService;
    }

    @Operation(summary = "Избранные товары пользователя")
    @GetMapping()
    public ResponseEntity<List<Favourites>> getAllFavourites(@RequestParam Long userId){
        return ResponseEntity.ok(favouritesService.findAll(userId));
    }

    @Operation(summary = "Создание избранного для пользователя")
    @PostMapping("/{userId}/{productId}")
    public ResponseEntity<Favourites> createFavourite(@PathVariable Long userId, @PathVariable Long productId){
        return ResponseEntity.ok(favouritesService.createFavourite(productId,userId));
    }

    @Operation(summary = "Обновление статуса избранного")
    @PostMapping("/{id}")
    public ResponseEntity<Favourites> updateFavourite(@PathVariable Long id, @RequestParam(defaultValue = "ADDED") AddDeleteStatus status){
        return ResponseEntity.ok(favouritesService.updateFavorite(id,status));
    }
}
