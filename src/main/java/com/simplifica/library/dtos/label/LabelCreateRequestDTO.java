package com.simplifica.library.dtos.label;

import com.simplifica.library.entities.Label;
import jakarta.validation.constraints.NotBlank;

public record LabelCreateRequestDTO(
        @NotBlank()
        String name
) {
    public Label toEntity() {
        Label label = new Label();
        label.setName(this.name);
        return  label;
    }
}
