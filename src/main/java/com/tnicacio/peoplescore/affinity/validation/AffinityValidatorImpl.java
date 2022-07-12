package com.tnicacio.peoplescore.affinity.validation;

import com.tnicacio.peoplescore.affinity.dto.AffinityDTO;
import com.tnicacio.peoplescore.affinity.service.AffinityService;
import com.tnicacio.peoplescore.exception.dto.FieldMessage;
import com.tnicacio.peoplescore.state.service.StateService;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Setter(onMethod_ = @Autowired)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AffinityValidatorImpl implements ConstraintValidator<AffinityValidator, AffinityDTO> {

    AffinityService affinityService;
    StateService stateService;

    @Override
    public boolean isValid(AffinityDTO dto, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();
        validateRegion(list, dto.getRegion());
        validateStates(list, dto.getStates());

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }

    private void validateRegion(List<FieldMessage> errorList, String region) {
        if (!affinityService.validateRegion(region)) {
            errorList.add(new FieldMessage("region", "Região já cadastrada"));
        }
    }

    private void validateStates(List<FieldMessage> errorList, Set<String> states) {
        if (!CollectionUtils.isEmpty(states) && !stateService.stateAbbreviationsValid(states)) {
            String repeatedStates = states.stream()
                    .filter(state -> stateService.existsByAbbreviation(state))
                    .collect(Collectors.joining(","));
            errorList.add(new FieldMessage("states", "Estados já cadastrados: [" + repeatedStates + "]"));
        }
    }
}
