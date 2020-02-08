package com.marcoscouto.cursomc.services.validation;

import com.marcoscouto.cursomc.domain.Client;
import com.marcoscouto.cursomc.domain.enums.TypeClient;
import com.marcoscouto.cursomc.dto.ClientInsertDTO;
import com.marcoscouto.cursomc.repositories.ClientRepository;
import com.marcoscouto.cursomc.resources.exception.FieldMessage;
import com.marcoscouto.cursomc.services.validation.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClientInsertValidator implements ConstraintValidator<ClientInsert, ClientInsertDTO> {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public void initialize(ClientInsert ann) {}

    @Override
    public boolean isValid(ClientInsertDTO obj, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        if(obj.getTypeClient().equals(TypeClient.PHYSICAL_PERSON.getCode())
                && !BR.isValidCPF(obj.getDocument()))
                    list.add(new FieldMessage("document", "Invalid CPF"));

        if(obj.getTypeClient().equals(TypeClient.LEGAL_PERSON.getCode())
                && !BR.isValidCNPJ(obj.getDocument()))
                    list.add(new FieldMessage("document", "Invalid CNPJ"));

        Client cli = clientRepository.findByEmail(obj.getEmail());
        if(cli != null)
            list.add(new FieldMessage("email", "E-mail already registred"));

        list.forEach(x -> {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(x.getMessage())
                    .addPropertyNode(x.getFieldName())
                    .addConstraintViolation();
        });

        return list.isEmpty();
    }
}
