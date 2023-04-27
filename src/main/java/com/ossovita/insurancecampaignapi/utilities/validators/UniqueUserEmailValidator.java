package com.ossovita.insurancecampaignapi.utilities.validators;

import com.ossovita.insurancecampaignapi.entity.User;
import com.ossovita.insurancecampaignapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UniqueUserEmailValidator implements ConstraintValidator<UniqueUserEmail, String> {

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean isValid(String userEmail, ConstraintValidatorContext context) {
        Optional<User> user = userRepository.findByUserEmail(userEmail);
        return user.isEmpty();
    }
}