package org.hca.blogproject.service.rules;

import org.hca.blogproject.exception.BusinessException;
import org.hca.blogproject.exception.ErrorType;

public interface IBusinessRules<T,ID>{
    void checkIfExistsById(ID id);

}
