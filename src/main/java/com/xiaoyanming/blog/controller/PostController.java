package com.xiaoyanming.blog.controller;

import com.xiaoyanming.blog.form.PostForm;
import com.xiaoyanming.blog.model.Post;
import com.xiaoyanming.blog.service.PostService;
import com.xiaoyanming.blog.vo.PostVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @ResponseBody
    @GetMapping("/api/posts")
    public List<PostVo> getPosts(@RequestParam("curPage") int curPage, @RequestParam("pageSize") int pageSize){
        return postService.getPostsPage(curPage, pageSize);
    }

    @GetMapping("/posts")
    public String viewPosts(Model model) {
        List<Post> posts = postService.findAll();
        model.addAttribute("posts", posts);

        return "post/posts";
    }

    // 增
    @GetMapping("/post/create")
    public String create(PostForm postForm) {
        return "post/create";
    }

    @RequestMapping(value = "/post/create", method = RequestMethod.POST)
    public String createPage(@Valid PostForm postForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "/post/create";
        }

        return postService.addNewPost(postForm);
    }

    // 删
    @GetMapping("/post/delete/{id}")
    public String getPostToDelete(@PathVariable("id") Long id, Model model) {
        return postService.confirmDeletePostById(id, model);
    }

    @DeleteMapping(value = "/post/delete/{id}")
    public String deletePost(@PathVariable Long id) {
        return postService.deletePostById(id);
    }

    // 改
    @GetMapping("/post/edit/{id}")
    public String getPostToEdit(@PathVariable("id") Long id, Model model) {
        return postService.findPostById(id, model);
    }

    @PutMapping("/post/edit")
    public String editPage(@Valid Post post, BindingResult bindingResult) {
        return postService.validateUserEditPermissions(post, bindingResult);
    }

    // 查
    @GetMapping("/post/{id}")
    public String getPost(@PathVariable("id") Long id,
                       Model model) {
        return postService.showPostById(id, model);
    }
}
