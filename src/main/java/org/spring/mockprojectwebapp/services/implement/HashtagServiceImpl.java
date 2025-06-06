package org.spring.mockprojectwebapp.services.implement;

import org.spring.mockprojectwebapp.dtos.admin.HashtagDTO;
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
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<HashtagDTO> getAllHashtags(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return hashtagRepository.findAll(pageable).map(this::mapToDTO);
    }

    @Override
    public HashtagDTO getHashtagById(int id) {
        Hashtag hashtag = hashtagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hashtag not found"));
        return mapToDTO(hashtag);
    }

    @Override
    public void saveHashtag(HashtagDTO hashtagDTO) {
        Hashtag hashtag = mapToEntity(hashtagDTO);
        String hashtagName = hashtag.getHashtagName();

        if (hashtag.getId() == 0) {
            // Trường hợp tạo hashtag mới
            if (hashtagRepository.existsByHashtagNameIgnoreCase(hashtagName)) {
                throw new RuntimeException("Hashtag đã tồn tại, vui lòng thử lại.");
            }
            hashtag.setCreatedAt(LocalDateTime.now());
        } else {
            // Trường hợp cập nhật hashtag
            if (hashtagRepository.existsByHashtagNameIgnoreCaseAndIdNot(hashtagName, hashtag.getId())) {
                throw new RuntimeException("Hashtag đã tồn tại, vui lòng thử lại.");
            }
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
                .map(this::mapToDTO);
    }

    public Page<HashtagDTO> searchHashtags(String keyword, Pageable pageable) {
        Page<Hashtag> hashtagPage;
        hashtagPage = hashtagRepository.findByHashtagNameContainingIgnoreCase(keyword, pageable);
        return hashtagPage.map(this::mapToDTO);
    }

    private HashtagDTO mapToDTO(Hashtag hashtag) {
//        HashtagDTO hashtagDTO = new HashtagDTO();
//        hashtagDTO.setHashtagId(hashtag.getId());
//        hashtagDTO.setHashtagName(hashtag.getHashtagName());
//        hashtagDTO.setCreatedAt(hashtag.getCreatedAt());
//        return hashtagDTO;
        return HashtagDTO.builder().hashtagId(hashtag.getId()).hashtagName(hashtag.getHashtagName()).createdAt(hashtag.getCreatedAt()).build();
    }

    private Hashtag mapToEntity(HashtagDTO hashtagDTO) {
        Hashtag hashtag = new Hashtag();
        hashtag.setId(hashtagDTO.getHashtagId());

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