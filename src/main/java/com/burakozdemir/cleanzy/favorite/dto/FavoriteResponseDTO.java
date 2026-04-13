package com.burakozdemir.cleanzy.favorite.dto;

import com.burakozdemir.cleanzy.auth.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteResponseDTO {
    private Long id;
    private Long favoritedUserId;
    private String fullName;
    private String email;
    private Role role;
    private String profilePhotoURL;
    private Double rating;
    private LocalDateTime createdAt;
}
