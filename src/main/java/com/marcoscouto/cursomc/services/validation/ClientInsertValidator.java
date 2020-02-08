package com.marcoscouto.cursomc.services.validation;

import com.marcoscouto.cursomc.domain.enums.TypeClient;
import com.marcoscouto.cursomc.dto.ClientInsertDTO;
import com.marcoscouto.cursomc.resources.exception.FieldMessage;
import com.marcoscouto.cursomc.services.validation.utils.BR;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClientInsertValidator implements ConstraintValidator<ClientInsert, ClientInsertDTO> {

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

        list.forEach(x -> {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(x.getMessage())
                    .addPropertyNode(x.getFieldName())
                    .addConstraintViolation();
        });

        return list.isEmpty();
    }
}
