package kr.yerin.board2.controller;

import kr.yerin.board2.domain.entity.BoardEntity;
import kr.yerin.board2.dto.BoardDto;
import kr.yerin.board2.service.BoardService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
public class BoardController {

    private BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/")
    public String list(Model model, @RequestParam(required = false, value="page", defaultValue = "0") int page, @RequestParam(value="sort", defaultValue = "0")int sort) {
        if(sort == 0){
            Page<BoardEntity> listPage = boardService.list(page);
            int totalPage = listPage.getTotalPages();

            model.addAttribute("boardList", listPage.getContent());
            model.addAttribute("totalPage", totalPage);
            model.addAttribute("sort", sort);
        }else if(sort == 1){
            Page<BoardEntity> listPage = boardService.listup(page);
            int totalPage = listPage.getTotalPages();

            model.addAttribute("boardList", listPage.getContent());
            model.addAttribute("totalPage", totalPage);
            model.addAttribute("sort", sort);
        }
        return "board/list.html";
    }

//    @GetMapping("/")
//    public String list(Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum) {
//        List<BoardDto> boardList = boardService.getBoardList(pageNum);
//        Integer[] pageList = boardService.getPageList(pageNum);
//
//        model.addAttribute("boardList", boardList);
//        model.addAttribute("pageList", pageList);
//
//        return "board/list.html";
//    }

    @GetMapping("/post")
    public String write(){
        return "board/write.html";
    }

    @PostMapping("/post")
    public String write(BoardDto boardDto){
        boardService.savePost(boardDto);
        return "redirect:/";
    }

    @GetMapping("/post/{no}")
    public String detail(@PathVariable("no") Long no, Model model) {
        BoardDto boardDTO = boardService.getPost(no);

        model.addAttribute("boardDto", boardDTO);
        return "board/detail.html";
    }

    @GetMapping("/post/edit/{no}")
    public String edit(@PathVariable("no") Long no, Model model) {
        BoardDto boardDTO = boardService.getPost(no);

        model.addAttribute("boardDto", boardDTO);
        return "board/update.html";
    }

    @PutMapping("/post/edit/{no}")
    public String update(BoardDto boardDTO) {
        boardService.savePost(boardDTO);

        return "redirect:/";
    }

    @DeleteMapping("/post/{no}")
    public String delete(@PathVariable("no") Long no) {
        boardService.deletePost(no);

        return "redirect:/";
    }


}
