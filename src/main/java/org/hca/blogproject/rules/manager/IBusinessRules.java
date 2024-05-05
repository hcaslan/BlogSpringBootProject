package org.hca.blogproject.rules.manager;

import org.hca.blogproject.exception.BusinessException;
import org.hca.blogproject.exception.ErrorType;

import java.lang.reflect.Field;
import java.util.List;

public interface IBusinessRules<T,ID>{
    void checkIfExistsById(ID id,T t);
    void checkIfExistsById(ID id);
    boolean isExistsById(ID id);
    void checkIfNull(String value);
    void checkIfNull(Long value);
    void checkIfListEmpty(List<String> list);
    void validateFieldLength(String value, Field field);
}
