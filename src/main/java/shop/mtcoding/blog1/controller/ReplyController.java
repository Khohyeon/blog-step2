package shop.mtcoding.blog1.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blog1.dto.ResponseDto;
import shop.mtcoding.blog1.dto.reply.ReplyReq.ReplySaveReqDto;
import shop.mtcoding.blog1.handler.ex.CustomApiException;
import shop.mtcoding.blog1.handler.ex.CustomException;
import shop.mtcoding.blog1.model.User;
import shop.mtcoding.blog1.service.ReplyService;

@Controller
public class ReplyController {

    @Autowired
    private HttpSession session;
    @Autowired
    private ReplyService replyService;

    @DeleteMapping("/reply/{id}")
    public @ResponseBody ResponseEntity<?> delete(@PathVariable int id) { // responsebody를 붙이면 데이터를 응답한다.
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomApiException("인증이 되지 않았습니다", HttpStatus.UNAUTHORIZED);
        }
        // db가 없어서 여기서 권한 검사를 할 수 가없다.
        replyService.댓글삭제(id, principal.getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "삭제성공", null), HttpStatus.OK);

    }

    @PostMapping("/reply")
    public String save(ReplySaveReqDto replySaveReqDto) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomException("인증이 되지 않았습니다", HttpStatus.UNAUTHORIZED);
        }
        if (replySaveReqDto.getComment() == null || replySaveReqDto.getComment().isEmpty()) {
            throw new CustomException("comment을 작성해주세요");
        }
        if (replySaveReqDto.getBoardId() == null) {
            throw new CustomException("boardId가 필요합니다");
        }
        replyService.댓글쓰기(replySaveReqDto, principal.getId());
        // 서비스 호출 (replySaveReqDto, principal.getId())
        return "redirect:/board/" + replySaveReqDto.getBoardId();
    }

}
