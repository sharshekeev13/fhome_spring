package com.example.fhome.repository;


import com.example.fhome.domain.entity.Favourites;
import com.example.fhome.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavouritesRepository extends JpaRepository<Favourites,Long> {
    List<Favourites> findAllByUser(User user);
}
