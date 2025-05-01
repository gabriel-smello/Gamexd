package com.gamexd.repository;

import com.gamexd.domain.entity.Platforms;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlatformRepository extends JpaRepository<Platforms, Long> {
}
