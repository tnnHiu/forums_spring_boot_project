package org.spring.mockprojectwebapp.services.implement;

import org.spring.mockprojectwebapp.dtos.HashtagDTO;
import org.spring.mockprojectwebapp.entities.Hashtag;
import org.spring.mockprojectwebapp.repositories.HashtagRepository;
import org.spring.mockprojectwebapp.services.HashtagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HashtagServiceImpl implements HashtagService {

    @Autowired
    private HashtagRepository hashtagRepository;

    @Override
    public List<HashtagDTO> getAllHashtags() {
        return hashtagRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<HashtagDTO> getAllHashtags(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return hashtagRepository.findAll(pageable).map(this::convertToDTO);
    }

    @Override
    public HashtagDTO getHashtagById(int id) {
        Hashtag hashtag = hashtagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hashtag not found"));
        return convertToDTO(hashtag);
    }

    @Override
    public void saveHashtag(HashtagDTO hashtagDTO) {
        Hashtag hashtag = convertToEntity(hashtagDTO);
        if (hashtag.getId() == 0) {
            hashtag.setCreatedAt(LocalDateTime.now());
        } else {
            Hashtag existingHashtag = hashtagRepository.findById(hashtag.getId())
                    .orElseThrow(() -> new RuntimeException("Hashtag not found"));
            hashtag.setCreatedAt(existingHashtag.getCreatedAt());
        }
        hashtagRepository.save(hashtag);
    }

    @Override
    public void deleteHashtag(int id) {
        if (!hashtagRepository.existsById(id)) {
            throw new RuntimeException("Hashtag not found");
        }
        hashtagRepository.deleteById(id);
    }

    @Override
    public Page<HashtagDTO> searchHashtags(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return hashtagRepository.findByHashtagNameContainingIgnoreCase(keyword, pageable)
                .map(this::convertToDTO);
    }

    private HashtagDTO convertToDTO(Hashtag hashtag) {
        HashtagDTO hashtagDTO = new HashtagDTO();
        hashtagDTO.setId(hashtag.getId());
        hashtagDTO.setHashtagName(hashtag.getHashtagName());
        hashtagDTO.setCreatedAt(hashtag.getCreatedAt());
        return hashtagDTO;
    }

    private Hashtag convertToEntity(HashtagDTO hashtagDTO) {
        Hashtag hashtag = new Hashtag();
        hashtag.setId(hashtagDTO.getId());

        // Thêm ký tự "#" vào đầu hashtag nếu thiếu
        String hashtagName = hashtagDTO.getHashtagName();
        if (hashtagName != null && !hashtagName.startsWith("#")) {
            hashtagName = "#" + hashtagName;
        }
        hashtag.setHashtagName(hashtagName);

        hashtag.setCreatedAt(hashtagDTO.getCreatedAt());
        return hashtag;
    }
}