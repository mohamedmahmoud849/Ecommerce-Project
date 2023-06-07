package com.vodafone.ecommerce.repo;

import com.vodafone.ecommerce.relation.relationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelationRepo extends JpaRepository<relationEntity,Long> {
}
