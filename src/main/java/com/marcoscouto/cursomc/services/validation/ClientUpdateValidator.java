package com.marcoscouto.cursomc.services.validation;

import com.marcoscouto.cursomc.domain.Client;
import com.marcoscouto.cursomc.dto.ClientDTO;
import com.marcoscouto.cursomc.repositories.ClientRepository;
import com.marcoscouto.cursomc.resources.exception.FieldMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;

public class ClientUpdateValidator implements ConstraintValidator<ClientUpdate, ClientDTO> {

    @Autowired
    HttpServletRequest request;

    @Autowired
    ClientRepository clientRepository;

    @Override
    public void initialize(ClientUpdate ann) {}

    @Override
    public boolean isValid(ClientDTO obj, ConstraintValidatorContext context) {

        //Get id for URI /clients/{id}
        Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Integer id = Integer.parseInt(map.get("id"));

        List<FieldMessage> list = new ArrayList<>();

        Client cli = clientRepository.findByEmail(obj.getEmail());

        if(cli != null && !cli.getId().equals(id))
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
