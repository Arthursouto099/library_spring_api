package com.simplifica.library.services;

import com.simplifica.library.dtos.label.LabelCreateRequestDTO;
import com.simplifica.library.entities.Label;
import com.simplifica.library.entities.User;
import com.simplifica.library.exceptions.ResourceNotFoundException;
import com.simplifica.library.repositories.LabelRepository;
import com.simplifica.library.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LabelService {

    LabelRepository labelRepository;
    UserRepository userRepository;

    public LabelService(LabelRepository labelRepository, UserRepository userRepository) {
        this.labelRepository = labelRepository;
        this.userRepository = userRepository;
    }


    @Transactional
    public Label createLabel(Label label, User user) {
        User managedUser = userRepository.getReferenceById(user.getIdUser());
        managedUser.addLabelToLabelList(label);
        userRepository.save(managedUser);
        return  label;
    }

    @Transactional
    public  Label editLabel(LabelCreateRequestDTO labelCreateRequestDTO, Long idLabel) {
        Label label = labelRepository.findById(idLabel).orElseThrow(() -> new ResourceNotFoundException("Label não encontrado"));
        label.setName(labelCreateRequestDTO.name());
        labelRepository.save(label);
        return  label;
    }

    @Transactional
    public void  deleteLabel(Long idLabel) {
        labelRepository.deleteById(idLabel);
    }

    public List<Label> findLabelsByIdUser(Long idUser) {
        return labelRepository.findByUserIdUser(idUser);
    }


}
