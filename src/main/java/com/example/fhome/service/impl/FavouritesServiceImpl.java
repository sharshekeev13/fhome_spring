package com.example.fhome.service.impl;

import com.example.fhome.domain.entity.Favourites;
import com.example.fhome.domain.entity.Product;
import com.example.fhome.domain.entity.User;
import com.example.fhome.domain.enums.AddDeleteStatus;
import com.example.fhome.domain.enums.Status;
import com.example.fhome.exception.ApiRequestException;
import com.example.fhome.repository.FavouritesRepository;
import com.example.fhome.repository.ProductRepository;
import com.example.fhome.repository.UserRepository;
import com.example.fhome.service.FavouritesService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FavouritesServiceImpl implements FavouritesService {

    private final FavouritesRepository favouritesRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public FavouritesServiceImpl(FavouritesRepository favouritesRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.favouritesRepository = favouritesRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }


    @Override
    public Favourites createFavourite(Long productId, Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if(user == null || user.getStatus() != Status.CONFIRM){
            throw new ApiRequestException("User Not Found");
        }
        Product product = productRepository.findById(productId).orElse(null);
        if(product == null || product.getStatus() != Status.CONFIRM){
            throw new ApiRequestException("Product Not Found");
        }
        Favourites favourites = Favourites.builder()
                .product(product)
                .user(user)
                .status(AddDeleteStatus.ADDED)
                .build();
        return favouritesRepository.save(favourites);
    }

    @Override
    public Favourites updateFavorite(Long favouriteId, AddDeleteStatus status) {
        Favourites favourites = favouritesRepository.findById(favouriteId).orElse(null);
        if(favourites == null || favourites.getStatus() == AddDeleteStatus.DELETED){
            throw new ApiRequestException("Favourite Product Not Found");
        }
        favourites.setStatus(status);
        return favouritesRepository.save(favourites);
    }

    @Override
    public List<Favourites> findAll(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if(user == null){
            throw new ApiRequestException("User Not Found");
        }
        return favouritesRepository.findAllByUser(user);
    }
}
