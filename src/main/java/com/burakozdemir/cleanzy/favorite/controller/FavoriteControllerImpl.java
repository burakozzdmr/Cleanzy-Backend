package com.burakozdemir.cleanzy.favorite.controller;

import com.burakozdemir.cleanzy.common.response.ApiSuccessResponse;
import com.burakozdemir.cleanzy.favorite.dto.FavoriteRequestDTO;
import com.burakozdemir.cleanzy.favorite.dto.FavoriteResponseDTO;
import com.burakozdemir.cleanzy.favorite.service.FavoriteService;
import org.apache.coyote.Response;
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

    @GetMapping("/")
    public ResponseEntity<ApiSuccessResponse<List<FavoriteResponseDTO>>> getFavoriteList() {
        return null;
    }

    @GetMapping("/{favoriteId}")
    public ResponseEntity<ApiSuccessResponse<FavoriteResponseDTO>> getFavoriteById(@PathVariable Long favoriteId) {
        return null;
    }

    @PostMapping("/")
    public ResponseEntity<ApiSuccessResponse<FavoriteResponseDTO>> addFavorite(
            @RequestBody FavoriteRequestDTO favoriteRequestDTO) {
        return null;
    }

    @DeleteMapping("/{favoriteId}")
    public ResponseEntity<ApiSuccessResponse<Boolean>> deleteFavoriteById(@PathVariable Long favoriteId) {
        return null;
    }
}
