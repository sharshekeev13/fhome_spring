package com.example.fhome.service;

import com.example.fhome.domain.entity.Favourites;
import com.example.fhome.domain.enums.AddDeleteStatus;

import java.util.List;

public interface FavouritesService {

    Favourites createFavourite(Long productId, Long userId);

    Favourites updateFavorite(Long favouriteId, AddDeleteStatus status);

    List<Favourites> findAll(Long userId);
}
