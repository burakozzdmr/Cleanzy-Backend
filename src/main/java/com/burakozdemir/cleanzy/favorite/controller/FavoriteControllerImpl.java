package com.burakozdemir.cleanzy.favorite.controller;

import com.burakozdemir.cleanzy.common.response.ApiSuccessResponse;
import com.burakozdemir.cleanzy.favorite.dto.FavoriteRequestDTO;
import com.burakozdemir.cleanzy.favorite.dto.FavoriteResponseDTO;
import com.burakozdemir.cleanzy.favorite.service.FavoriteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api/v1/favorites")
public class FavoriteControllerImpl implements FavoriteController {

    private final FavoriteService favoriteService;

    FavoriteControllerImpl(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @GetMapping("/{userId}")
    @Override
    public ResponseEntity<ApiSuccessResponse<List<FavoriteResponseDTO>>> getFavoritesByUserId(
            @PathVariable Long userId
    ) {
        return ResponseEntity
                .ok(favoriteService.fetchFavoritesByUserId(userId));
    }

    @PostMapping
    @Override
    public ResponseEntity<ApiSuccessResponse<FavoriteResponseDTO>> addFavorite(
            @RequestBody FavoriteRequestDTO request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(favoriteService.addFavorite(request));
    }

    @DeleteMapping
    @Override
    public ResponseEntity<ApiSuccessResponse<Boolean>> removeFavorite(
            @RequestBody FavoriteRequestDTO request
    ) {
        return ResponseEntity
                .ok(favoriteService.removeFavorite(request));
    }
}
