package org.uv.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uv.security.entity.Clasificacion;

public interface ClasificacionRepository extends JpaRepository<Clasificacion, Long> {
}

