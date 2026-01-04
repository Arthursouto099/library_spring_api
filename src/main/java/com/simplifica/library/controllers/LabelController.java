package com.simplifica.library.controllers;

import com.simplifica.library.dtos.label.LabelCreateRequestDTO;
import com.simplifica.library.dtos.label.LabelResponseDTO;
import com.simplifica.library.entities.Label;
import com.simplifica.library.entities.User;
import com.simplifica.library.services.LabelService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/label/features")
public class LabelController {
    LabelService labelService;

    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    @PostMapping("/create")
    public ResponseEntity<LabelResponseDTO> createLabel(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody LabelCreateRequestDTO labelCreateRequestDTO
            ) {
        Label label = labelService.createLabel(labelCreateRequestDTO.toEntity(), user);
        return  ResponseEntity.status(HttpStatus.CREATED).body(LabelResponseDTO.fromEntity(label));
    }

    @GetMapping("/all")
    public  ResponseEntity<List<LabelResponseDTO>> findAllByIdUser(
        @AuthenticationPrincipal User user
    ) {
        List<Label> labels = labelService.findLabelsByIdUser(user.getIdUser());
        return  ResponseEntity.ok(labels.stream().map(LabelResponseDTO::fromEntity).toList());
    }

    @PatchMapping("/edit/{idLabel}")
    public ResponseEntity<LabelResponseDTO> editLabel(
            @PathVariable Long idLabel,
            @Valid @RequestBody LabelCreateRequestDTO labelCreateRequestDTO
    ) {
        Label label = labelService.editLabel(labelCreateRequestDTO, idLabel );
        return  ResponseEntity.status(HttpStatus.OK).body(LabelResponseDTO.fromEntity(label));
    }

    @DeleteMapping("/delete/{idLabel}")
    public ResponseEntity<?> deleteLabel(
            @PathVariable  Long idLabel
    ) {
        labelService.deleteLabel(idLabel);
        return  ResponseEntity.noContent().build();
    }
}
