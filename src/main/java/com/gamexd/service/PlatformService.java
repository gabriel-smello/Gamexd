package com.gamexd.service;

import com.gamexd.domain.dto.PlatformDto;
import com.gamexd.domain.entity.Platforms;
import com.gamexd.mapper.PlatformMapper;
import com.gamexd.repository.PlatformRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlatformService {
    @Autowired
    PlatformRepository platformRepository;
    @Autowired
    PlatformMapper platformMapper;

    public List<PlatformDto> findAllPlatforms() {
        List<Platforms> platforms = platformRepository.findAll();
        return platformMapper.toDtoList(platforms);
    }
}
