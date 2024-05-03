package org.hca.blogproject.utility;

import org.hca.blogproject.exception.BusinessException;
import org.hca.blogproject.exception.ErrorType;

import java.lang.reflect.Field;

public interface IBusinessRules<T,ID>{
    void checkIfExistsById(ID id,T t);
    void checkIfExistsById(ID id);
    boolean isExistsById(ID id);
    void checkIfNull(String field);
    void checkIfNull(Long field);
    void validateFieldLength(String value, Field field);
}
