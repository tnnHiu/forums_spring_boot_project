package org.spring.mockprojectwebapp.services.implement;

import org.spring.mockprojectwebapp.dtos.admin.HashtagDTO;
import org.spring.mockprojectwebapp.entities.Hashtag;
import org.spring.mockprojectwebapp.repositories.HashtagRepository;
import org.spring.mockprojectwebapp.services.HashtagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class HashtagServiceImpl implements HashtagService {
    @Autowired
    private HashtagRepository hashtagRepository;
    @Override
    public Page<HashtagDTO> searchHashtags(String keyword, Pageable pageable) {
         Page<Hashtag> hashtagPage;
         hashtagPage = hashtagRepository.findByHashtagNameContainingIgnoreCase(keyword, pageable);
        return hashtagPage.map(this::mapToDTO);
    }
    private HashtagDTO mapToDTO(Hashtag hashtag) {
        return HashtagDTO.builder()
                .hashtagId(hashtag.getId())
                .hashtagName(hashtag.getHashtagName())
                .createdAt(hashtag.getCreatedAt())
                .build();
    }

    private Hashtag mapToEntity(HashtagDTO hashtagDTO) {
        return Hashtag.builder()
                .id(hashtagDTO.getHashtagId())
                .hashtagName(hashtagDTO.getHashtagName())
                .createdAt(hashtagDTO.getCreatedAt())
                .build();
    }
}
