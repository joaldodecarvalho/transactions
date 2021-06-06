package pismo.io.transactions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pismo.io.transactions.domain.OperationType;

@Repository
public interface OperationTypeRepository extends JpaRepository<OperationType, Long> {

}
