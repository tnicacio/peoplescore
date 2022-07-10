package com.tnicacio.peoplescore.affinity.repository;

import com.tnicacio.peoplescore.affinity.model.AffinityModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AffinityRepository extends JpaRepository<AffinityModel, Long> {
}
