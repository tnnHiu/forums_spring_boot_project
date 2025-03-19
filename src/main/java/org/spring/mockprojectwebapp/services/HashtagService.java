package org.spring.mockprojectwebapp.services;

import org.spring.mockprojectwebapp.dtos.admin.HashtagDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HashtagService {
   Page<HashtagDTO> searchHashtags(String keyword, Pageable pageable);
}
