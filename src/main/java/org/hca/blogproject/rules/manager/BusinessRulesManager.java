package org.hca.blogproject.rules.manager;

import lombok.RequiredArgsConstructor;
import org.hca.blogproject.exception.ValidationException;
import org.hca.blogproject.exception.LengthException;
import org.hca.blogproject.exception.ErrorType;
import org.hca.blogproject.utility.annotation.MaxLength;
import org.hca.blogproject.utility.annotation.MinLength;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Field;
import java.util.List;

@RequiredArgsConstructor
public class BusinessRulesManager<T,ID> implements IBusinessRules<T,ID>{
    private final JpaRepository<T,ID> jpaRepository;
    @Override
    public void checkIfExistsById(ID id,T t) {
        if (!jpaRepository.existsById(id)) throw new ValidationException(ErrorType.NOT_FOUND,t.getClass().getSimpleName());
    }

    @Override
    public void checkIfExistsById(ID id) {
        if (!jpaRepository.existsById(id)) throw new ValidationException(ErrorType.NOT_FOUND);
    }

    @Override
    public boolean isExistsById(ID id) {
        return (jpaRepository.existsById(id));
    }
    public void checkIfNull(String value) {
        if(value == null) {
            throw new ValidationException(ErrorType.EMPTY_FIELD);
        }
    }

    @Override
    public void checkIfNull(Long value) {
        if(value == null) {
            throw new ValidationException(ErrorType.EMPTY_FIELD);
        }
    }

    @Override
    public void checkIfListEmpty(List<String> list) {
        if(list == null) throw new ValidationException(ErrorType.EMPTY_FIELD);
        if (list.isEmpty()) throw new ValidationException(ErrorType.EMPTY_FIELD);
    }

    private int getMaxLength(Field field) {
        MaxLength maxLength = field.getAnnotation(MaxLength.class);
        if (maxLength != null) {
                return maxLength.value();
        }
        return Integer.MAX_VALUE;
    }
    private static int getMinLength(Field field) {
        MinLength minLength = field.getAnnotation(MinLength.class);
        if (minLength != null) {
            return minLength.value();
        }
        return Integer.MIN_VALUE;
    }

    private void validateFieldLength(String value, String fieldName, int maxLength, int minLength) {
        if(value != null){
            if ( maxLength != -1 && value.length() > maxLength) {
                throw new LengthException(ErrorType.DATABASE_ERROR,fieldName + " exceeds maximum length of " + maxLength + " characters.");
            }
            if (minLength != -1 && value.length() < minLength) {
                throw new LengthException(ErrorType.DATABASE_ERROR,fieldName + " must be at least " + minLength + " characters long.");
            }
        }else {
            throw new ValidationException(ErrorType.EMPTY_FIELD,fieldName);
        }
    }

    public void validateFieldLength(String value, Field field) {
        int maxLength = getMaxLength(field);
        int minLength = getMinLength(field);
        validateFieldLength(value, field.getName(), maxLength, minLength);
    }
}
