package org.spring.mockprojectwebapp.controllers.home;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.spring.mockprojectwebapp.dtos.admin.CategoryDTO;
import org.spring.mockprojectwebapp.dtos.admin.HashtagDTO;
import org.spring.mockprojectwebapp.dtos.admin.PostDTO;
import org.spring.mockprojectwebapp.dtos.admin.UserDTO;
import org.spring.mockprojectwebapp.dtos.user.UserSearchDTO;
import org.spring.mockprojectwebapp.services.HashtagService;
import org.spring.mockprojectwebapp.services.PostService;
import org.spring.mockprojectwebapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private HashtagService hashtagService;

    @GetMapping
    public String showHomePage(Model model, HttpSession session) {
        List<PostDTO> recentPostDTOs = postService.getRecentPosts();
        List<UserDTO> receUserDTOS = userService.findAllUsers();
        model.addAttribute("recentPostDTOs", recentPostDTOs);
        model.addAttribute("receUserDTOS", receUserDTOS);
        model.addAttribute("userName", session.getAttribute("userName"));
        return "index";
    }

    @GetMapping("/search")
    public String search(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "8") int size,
            Model model,
            HttpServletRequest request) {

        Pageable pageable = PageRequest.of(page, size);

        if (keyword != null && !keyword.isEmpty()) {
            Page<PostDTO> postDTOPage = postService.searchPosts(keyword, pageable);
            Page<UserSearchDTO> userSearchDTOPage = userService.searchUsersWithPageable(keyword, pageable);
            Page<HashtagDTO> hashtagDTOPage = hashtagService.searchHashtags(keyword, pageable);

            model.addAttribute("postDTOs", postDTOPage.getContent());
            model.addAttribute("totalPostPages", postDTOPage.getTotalPages());

            model.addAttribute("userSearchDTOs", userSearchDTOPage.getContent());
            model.addAttribute("totalUserPages", userSearchDTOPage.getTotalPages());

            model.addAttribute("hashtagDTOs", hashtagDTOPage.getContent());
            model.addAttribute("totalHashtagPages", hashtagDTOPage.getTotalPages());

            model.addAttribute("currentPage", page);
            model.addAttribute("keyword", keyword);
            model.addAttribute("currentUri", request.getRequestURI());

            model.addAttribute("categoryDTO", new CategoryDTO());
        }

        return "user/search";
    }

}