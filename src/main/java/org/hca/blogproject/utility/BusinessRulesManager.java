package org.hca.blogproject.utility;

import jakarta.persistence.Column;
import lombok.RequiredArgsConstructor;
import org.hca.blogproject.exception.BusinessException;
import org.hca.blogproject.exception.DataBaseException;
import org.hca.blogproject.exception.ErrorType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Field;

@RequiredArgsConstructor
public class BusinessRulesManager<T,ID> implements IBusinessRules<T,ID>{
    private final JpaRepository<T,ID> jpaRepository;
    @Override
    public void checkIfExistsById(ID id,T t) {
        if (!jpaRepository.existsById(id)) throw new BusinessException(ErrorType.NOT_FOUND,t.getClass().getSimpleName());
    }

    @Override
    public void checkIfExistsById(ID id) {
        if (!jpaRepository.existsById(id)) throw new BusinessException(ErrorType.NOT_FOUND);
    }

    @Override
    public boolean isExistsById(ID id) {
        return (jpaRepository.existsById(id));
    }
    public void checkIfNull(String field) {
        if(field == null) {
            throw new BusinessException(ErrorType.NULL_FIELD);
        }
    }

    @Override
    public void checkIfNull(Long field) {
        if(field == null) {
            throw new BusinessException(ErrorType.NULL_FIELD);
        }
    }
    private int getMaxLength(Field field) {
        Column column = field.getAnnotation(Column.class);
        if (column != null) {
            String columnDefinition = column.columnDefinition();
            String[] parts = columnDefinition.split("\\(");
            if (parts.length > 1) {
                String lengthPart = parts[1].replaceAll("\\)", "");
                return Integer.parseInt(lengthPart);
            }
        }
        // Default length if not found in column definition
        return -1;
    }

    private void validateFieldLength(String value, String fieldName, int maxLength) {
        if (value != null && maxLength != -1 && value.length() > maxLength) {
            throw new DataBaseException(ErrorType.DATABASE_ERROR,fieldName + " exceeds maximum length of " + maxLength + " characters.");
        }
    }

    public void validateFieldLength(String value, Field field) {
        int maxLength = getMaxLength(field);
        validateFieldLength(value, field.getName(), maxLength);
    }
}
