package org.hca.blogproject.rules.manager;

import java.lang.reflect.Field;
import java.util.List;

/**
 * These classes provide a structured approach to validate entities and perform custom validation checks specific to the project's requirements
 */
public interface IBusinessRules<T,ID>{
    void checkIfExistsById(ID id,T t);
    void checkIfExistsById(ID id);
    boolean isExistsById(ID id);
    void checkIfNull(String value);
    void checkIfNull(Long value);
    void checkIfListEmpty(List<String> list);
    void validateFieldLength(String value, Field field);
}
