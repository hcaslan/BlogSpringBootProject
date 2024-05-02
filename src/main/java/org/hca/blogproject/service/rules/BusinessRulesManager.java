package org.hca.blogproject.service.rules;

import lombok.RequiredArgsConstructor;
import org.hca.blogproject.exception.BusinessException;
import org.hca.blogproject.exception.ErrorType;
import org.springframework.data.jpa.repository.JpaRepository;
@RequiredArgsConstructor
public class BusinessRulesManager<T,ID> implements IBusinessRules<T,ID>{
    private final JpaRepository<T,ID> jpaRepository;
    @Override
    public void checkIfExistsById(ID id) {
        if (!jpaRepository.existsById(id)) throw new BusinessException(ErrorType.NOT_FOUND);
    }

}
