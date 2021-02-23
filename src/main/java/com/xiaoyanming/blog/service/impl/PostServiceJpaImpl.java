package com.xiaoyanming.blog.service.impl;

import com.xiaoyanming.blog.form.PostForm;
import com.xiaoyanming.blog.model.Post;
import com.xiaoyanming.blog.model.User;
import com.xiaoyanming.blog.repository.PostRepository;
import com.xiaoyanming.blog.service.NotificationService;
import com.xiaoyanming.blog.service.PostService;
import com.xiaoyanming.blog.service.UserService;
import com.xiaoyanming.blog.vo.PostVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceJpaImpl implements PostService {

    String nameLoggedInUser;

    private final NotificationService notificationService;
    private final PostRepository postRepository;
    private final UserService userService;

    @Override
    public List<PostVo> getPostsPage(int curPage, int pageSize) {
        List<Post> postsPage = postRepository.findPostsPage(PageRequest.of(curPage, pageSize));
        return postsPage.stream().map(p -> {
            PostVo postVo = new PostVo();
            postVo.setTitle(p.getTitle());
            postVo.setDate(p.getDate());
            postVo.setAuthor(p.getAuthor().getUsername());
            postVo.setHtml(markdownToHTML(p.getBody()));
            return postVo;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public String addNewPost(@Valid PostForm postForm) {
        String nameLoggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(nameLoggedInUser);

        Post post = new Post();
        post.setTitle(postForm.getTitle());
        post.setBody(postForm.getBody());
        post.setAuthor(user);
        post.setDate(new Date());
        postRepository.save(post);

        notificationService.addInfoMessage("Post created.");
        return "redirect:/posts";
    }

    private String markdownToHTML(String markdown) {
        Parser parser = Parser.builder()
                .build();

        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder()
                .build();

        return renderer.render(document);
    }

    @Override
    public String confirmDeletePostById(Long id, Model model) {
        Post post = postRepository.findById(id).orElse(null);
        if (post == null) {
            notificationService.addErrorMessage("Cannot find post: " + id);
            return "redirect:/posts";
        }
        model.addAttribute("post", post);
        return "/post/delete";
    }

    @Override
    public String deletePostById(Long id) {
        nameLoggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Post post = postRepository.findById(id).orElse(null);

        if (post.getAuthor().getUsername().equals(nameLoggedInUser)) {
            postRepository.deleteById(id);
            notificationService.addInfoMessage("Delete successful");
            return "redirect:/posts";
        }
        notificationService.addErrorMessage("Delete without successful! It's not your post.");

        return "redirect:/posts";
    }

    @Override
    public String findPostById(Long id, Model model) {
        Post post = postRepository.findById(id).orElse(null);

        if (post == null) {
            notificationService.addErrorMessage("Cannot find post: " + id);
            return "redirect:/posts";
        }

        model.addAttribute("post", post);
        return "/post/edit";
    }

    @Override
    public String validateUserEditPermissions(@Valid Post post, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            notificationService.addInfoMessage("Edit without successful");
            return "/post/edit";
        }

        if (post.getTitle().isEmpty() || post.getBody().isEmpty()) {
            notificationService.addErrorMessage("All fields are required!");
            return "/post/edit";
        }

        nameLoggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();

        if (post.getAuthor().getUsername().equals(nameLoggedInUser)) {
            postRepository.save(post);
            notificationService.addInfoMessage("Edit successful");
            return "redirect:/posts";
        }
        notificationService.addErrorMessage("Edit without successful! It's not your post.");

        return "redirect:/posts";
    }

    @Override
    public String showPostById(Long id, Model model) {
        Post post = postRepository.findById(id).orElse(null);

        if (post == null) {
            notificationService.addErrorMessage("Cannot find post: " + id);
            return "redirect:/";
        }

        PostVo postVo = new PostVo();
        postVo.setTitle(post.getTitle());
        postVo.setDate(post.getDate());
        postVo.setAuthor(post.getAuthor().getUsername());
        postVo.setHtml(markdownToHTML(post.getBody()));

        // BeanUtils.copyProperties(post, postVo);

        model.addAttribute("postVo", postVo);
        return "/post/view";
    }
}
