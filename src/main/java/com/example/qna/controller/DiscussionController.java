package com.example.qna.controller;

import com.example.qna.entity.Discussion;
import com.example.qna.entity.Reply;
import com.example.qna.entity.User;
import com.example.qna.service.DiscussionService;
import com.example.qna.service.ReplyService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.List;

@Controller
public class DiscussionController {

    @Autowired
    private DiscussionService discussionService;

    @Autowired
    private ReplyService replyService;

    @GetMapping("/discussions")
    public String listDiscussions(Model model) {
        List<Discussion> list = discussionService.listAll();
        model.addAttribute("discussions", list);
        return "discussion_list";
    }

    @GetMapping("/discussion/new")
    public String newDiscussionPage() {
        return "discussion_new";
    }

    @PostMapping("/discussion/new")
    public String createDiscussion(HttpServletRequest request,
                                   @RequestParam String title,
                                   @RequestParam String content,
                                   Model model) {

        User user = (User) request.getAttribute("currentUser");
        if (user == null) return "redirect:/login";

        if (title == null || title.trim().isEmpty() || content == null || content.trim().isEmpty()) {
            model.addAttribute("error", "标题和内容不能为空");
            return "discussion_new";
        }

        Discussion discussion = new Discussion();
        discussion.setTitle(title.trim());
        discussion.setContent(content.trim());
        discussion.setAuthor(user.getUsername());
        discussion.setCreateTime(LocalDateTime.now());

        discussionService.create(discussion);
        return "redirect:/discussions";
    }

    @GetMapping("/discussion/detail")
    public String discussionDetail(@RequestParam(required = true) Integer id, Model model) {
        Discussion discussion = discussionService.findById(id);
        if (discussion == null) return "redirect:/discussions";

        List<Reply> replies = replyService.findByDiscussionId(id);
        model.addAttribute("discussion", discussion);
        model.addAttribute("replies", replies);
        return "discussion_detail";
    }

    @PostMapping("/discussion/detail")
    public String postReply(HttpServletRequest request,
                            @RequestParam Integer discussionId,
                            @RequestParam String content) {

        User user = (User) request.getAttribute("currentUser");
        if (user == null) return "redirect:/login";

        if (discussionId == null || content == null || content.trim().isEmpty()) {
            return "redirect:/discussions";
        }

        Discussion discussion = discussionService.findById(discussionId);
        if (discussion == null) return "redirect:/discussions";

        Reply reply = new Reply();
        reply.setDiscussionId(discussionId);
        reply.setContent(content.trim());
        reply.setAuthor(user.getUsername());
        reply.setCreateTime(LocalDateTime.now());
        replyService.create(reply);

        return "redirect:/discussion/detail?id=" + discussionId;
    }
}
