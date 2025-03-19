package org.spring.mockprojectwebapp.controllers.home;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.spring.mockprojectwebapp.dtos.user.UpdatePasswordDTO;
import org.spring.mockprojectwebapp.dtos.user.UpdateUsernameDTO;
import org.spring.mockprojectwebapp.entities.User;
import org.spring.mockprojectwebapp.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.spring.mockprojectwebapp.dtos.admin.PostDTO;
import org.spring.mockprojectwebapp.dtos.admin.UserDTO;
import org.spring.mockprojectwebapp.services.PostService;
import org.spring.mockprojectwebapp.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/profile")
public class HomeProfileController {
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    @RequestMapping
    public String showProfilePage(HttpSession session, Model model) {
        int userId = (int) session.getAttribute("userId");
        UserDTO userDTO = userService.findUserById(userId);
        List<PostDTO> postDTOs = postService.getUserPosts(userId);
        model.addAttribute("userDTO", userDTO);
        model.addAttribute("postDTOs", postDTOs);
        return "user/profile";
    }

    @Autowired
    private ProfileService profileService;

    @GetMapping("/edit-profile")
    public String showEditProfilePage(Model model, HttpSession session) {
        String email = (String) session.getAttribute("userEmail");
        if (email == null) {
            return "redirect:/login";
        }

        User currentUser = profileService.getCurrentUser(email);

        UpdateUsernameDTO updateUsernameDTO = new UpdateUsernameDTO();
        updateUsernameDTO.setUsername(currentUser.getUsername());

        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTO();

        model.addAttribute("email", email);
        model.addAttribute("updateUsernameDTO", updateUsernameDTO);
        model.addAttribute("updatePasswordDTO", updatePasswordDTO);

        return "user/edit-profile";
    }

    @PostMapping("/update-username")
    public String updateUsername(Model model, HttpSession session,
                                 @Valid @ModelAttribute("updateUsernameDTO") UpdateUsernameDTO updateUsernameDTO,
                                 BindingResult result, RedirectAttributes redirectAttributes) {
        String email = (String) session.getAttribute("userEmail");
        if (email == null) {
            return "redirect:/login";
        }

        if (result.hasErrors()) {
            // Nếu có lỗi validation, trả về trang profile với thông báo lỗi
            User currentUser = profileService.getCurrentUser(email);
            updateUsernameDTO.setUsername(currentUser.getUsername()); // Điền lại username hiện tại
            model.addAttribute("email", email);
            model.addAttribute("updatePasswordDTO", new UpdatePasswordDTO()); // Đảm bảo đối tượng này tồn tại
            return "user/edit-profile";
        }

        profileService.updateUsername(email, updateUsernameDTO);
        session.setAttribute("userName", updateUsernameDTO.getUsername());

        // Thêm thông báo thành công vào redirectAttributes
        redirectAttributes.addFlashAttribute("usernameSuccess", true);

        return "redirect:/profile/edit-profile";
    }

    @PostMapping("/update-password")
    public String updatePassword(Model model, HttpSession session,
                                 @Valid @ModelAttribute("updatePasswordDTO") UpdatePasswordDTO updatePasswordDTO,
                                 BindingResult result, RedirectAttributes redirectAttributes) {
        String email = (String) session.getAttribute("userEmail");
        if (email == null) {
            return "redirect:/login";
        }

        if (result.hasErrors()) {
            // Nếu có lỗi validation, trả về trang profile với thông báo lỗi
            User currentUser = profileService.getCurrentUser(email);
            UpdateUsernameDTO updateUsernameDTO = new UpdateUsernameDTO();
            updateUsernameDTO.setUsername(currentUser.getUsername()); // Điền lại username hiện tại
            model.addAttribute("email", email);
            model.addAttribute("updateUsernameDTO", updateUsernameDTO); // Đảm bảo đối tượng này tồn tại
            return "user/edit-profile";
        }

        String errorMessage = profileService.updatePassword(email, updatePasswordDTO);

        if (errorMessage != null) {
            // Nếu có lỗi, thêm thông báo lỗi vào model
            User currentUser = profileService.getCurrentUser(email);
            UpdateUsernameDTO updateUsernameDTO = new UpdateUsernameDTO();
            updateUsernameDTO.setUsername(currentUser.getUsername()); // Điền lại username hiện tại
            model.addAttribute("email", email);
            model.addAttribute("oldPasswordError", errorMessage);
            model.addAttribute("updateUsernameDTO", updateUsernameDTO); // Đảm bảo đối tượng này tồn tại
            return "user/edit-profile";
        }

        // Xóa session để đảm bảo người dùng bị đăng xuất
        session.invalidate();

        // Redirect đến /logout để Spring Security xử lý đăng xuất
        return "redirect:/logout";
    }

}
