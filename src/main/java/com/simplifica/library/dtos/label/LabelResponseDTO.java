package com.simplifica.library.dtos.label;

import com.simplifica.library.entities.Label;

public record LabelResponseDTO(
        Long idLabel,
        String name
) {
    public static LabelResponseDTO fromEntity (Label label) {
        return new LabelResponseDTO(
                label.getIdLabel(),
                label.getName()
        );
    }
}