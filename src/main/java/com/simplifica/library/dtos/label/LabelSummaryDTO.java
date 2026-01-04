package com.simplifica.library.dtos.label;

import com.simplifica.library.dtos.user.responses.UserSummaryDTO;
import com.simplifica.library.entities.Label;
import com.simplifica.library.entities.User;

public record LabelSummaryDTO(
        Long idLabel,
        String name
) {
    public static LabelSummaryDTO fromEntity (Label label) {
        return new LabelSummaryDTO(
                label.getIdLabel(),
                label.getName()
        );
    }
}