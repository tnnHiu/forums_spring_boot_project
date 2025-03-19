package org.spring.mockprojectwebapp.services;

//import org.spring.mockprojectwebapp.dtos.HashtagDTO;
import org.spring.mockprojectwebapp.dtos.admin.HashtagDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HashtagService {
    List<HashtagDTO> getAllHashtags();
    Page<HashtagDTO> getAllHashtags(int page, int size);
    HashtagDTO getHashtagById(int id);
    void saveHashtag(HashtagDTO hashtagDTO);
    void deleteHashtag(int id);
    Page<HashtagDTO> searchHashtags(String keyword, int page, int size);
    Page<HashtagDTO> searchHashtags(String keyword, Pageable pageable);
}
