package com.burakozdemir.cleanzy.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Compact user summary included in nested responses")
public class UserSummaryDTO {

    @Schema(description = "User ID", example = "42")
    private Long id;

    @Schema(description = "User ID (alias for id, kept for client compatibility)", example = "42")
    private Long userId;

    @Schema(example = "Ahmet Yılmaz")
    private String fullName;

    @Schema(example = "ahmet@example.com")
    private String email;
}
