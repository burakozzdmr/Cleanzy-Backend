package com.burakozdemir.cleanzy.favorite.service;

import com.burakozdemir.cleanzy.auth.entity.User;
import com.burakozdemir.cleanzy.auth.repository.AuthRepository;
import com.burakozdemir.cleanzy.cleaner.repository.CleanerRepository;
import com.burakozdemir.cleanzy.common.exception.BusinessException;
import com.burakozdemir.cleanzy.common.exception.ErrorType;
import com.burakozdemir.cleanzy.common.response.ApiSuccessResponse;
import com.burakozdemir.cleanzy.customer.repository.CustomerRepository;
import com.burakozdemir.cleanzy.favorite.dto.FavoriteRequestDTO;
import com.burakozdemir.cleanzy.favorite.dto.FavoriteResponseDTO;
import com.burakozdemir.cleanzy.favorite.entity.Favorite;
import com.burakozdemir.cleanzy.favorite.repository.FavoriteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final AuthRepository authRepository;
    private final CustomerRepository customerRepository;
    private final CleanerRepository cleanerRepository;

    FavoriteServiceImpl(
            FavoriteRepository favoriteRepository,
            AuthRepository authRepository,
            CustomerRepository customerRepository,
            CleanerRepository cleanerRepository
    ) {
        this.favoriteRepository = favoriteRepository;
        this.authRepository = authRepository;
        this.customerRepository = customerRepository;
        this.cleanerRepository = cleanerRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ApiSuccessResponse<List<FavoriteResponseDTO>> fetchFavoritesByUserId(Long userId) {
        User user = authRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorType.USER_NOT_FOUND));

        List<FavoriteResponseDTO> favorites = favoriteRepository.findAllByUser(user)
                .stream()
                .map(this::toFavoriteResponseDTO)
                .toList();

        return ApiSuccessResponse.of(favorites, favorites.size());
    }

    @Override
    @Transactional
    public ApiSuccessResponse<FavoriteResponseDTO> addFavorite(FavoriteRequestDTO request) {
        User user = authRepository.findById(request.getUserId())
                .orElseThrow(() -> new BusinessException(ErrorType.USER_NOT_FOUND));

        User favoritedUser = authRepository.findById(request.getFavoritedUserId())
                .orElseThrow(() -> new BusinessException(ErrorType.USER_NOT_FOUND));

        if (favoriteRepository.existsByUserAndFavoritedUser(user, favoritedUser)) {
            throw new BusinessException(ErrorType.ALREADY_FAVORITED);
        }

        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setFavoritedUser(favoritedUser);

        Favorite saved = favoriteRepository.save(favorite);
        return ApiSuccessResponse.of(toFavoriteResponseDTO(saved));
    }

    @Override
    @Transactional
    public ApiSuccessResponse<Boolean> removeFavorite(FavoriteRequestDTO request) {
        User user = authRepository.findById(request.getUserId())
                .orElseThrow(() -> new BusinessException(ErrorType.USER_NOT_FOUND));

        User favoritedUser = authRepository.findById(request.getFavoritedUserId())
                .orElseThrow(() -> new BusinessException(ErrorType.USER_NOT_FOUND));

        if (!favoriteRepository.existsByUserAndFavoritedUser(user, favoritedUser)) {
            throw new BusinessException(ErrorType.FAVORITE_NOT_FOUND);
        }

        favoriteRepository.deleteByUserAndFavoritedUser(user, favoritedUser);
        return ApiSuccessResponse.of(true);
    }

    // ── Mapping ───────────────────────────────────────────────────────────────

    private FavoriteResponseDTO toFavoriteResponseDTO(Favorite favorite) {
        User favUser = favorite.getFavoritedUser();

        FavoriteResponseDTO dto = new FavoriteResponseDTO();
        dto.setId(favorite.getId());
        dto.setFavoritedUserId(favUser.getId());
        dto.setFullName(favUser.getFullName());
        dto.setEmail(favUser.getEmail());
        dto.setRole(favUser.getRole());
        dto.setCreatedAt(favorite.getCreatedAt());

        switch (favUser.getRole()) {
            case CUSTOMER -> customerRepository.findByUser(favUser).ifPresent(customer -> {
                dto.setRating(customer.getRating());
                dto.setProfilePhotoURL(customer.getProfilePhotoURL());
            });
            case CLEANER -> cleanerRepository.findByUser(favUser).ifPresent(cleaner -> {
                dto.setRating(cleaner.getRating());
                dto.setProfilePhotoURL(cleaner.getProfilePhotoURL());
            });
        }

        return dto;
    }
}
