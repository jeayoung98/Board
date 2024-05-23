package org.example.board.controller;


import org.example.board.domain.Board;
import org.example.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<Board> boards = boardService.findAll(PageRequest.of(page, 10));
        model.addAttribute("boards", boards.getContent());
        model.addAttribute("totalPages", boards.getTotalPages());
        model.addAttribute("currentPage", page);
        return "board/list";
    }

    @GetMapping("/view")
    public String view(Model model, @RequestParam Long id) {
        Board board = boardService.findById(id);
        model.addAttribute("board", board);
        return "board/view";
    }

    @GetMapping("/writeform")
    public String writeForm(Model model) {
        model.addAttribute("board", new Board());
        return "board/writeform";
    }

    @PostMapping("/write")
    public String write(@ModelAttribute Board board) {
        boardService.save(board);
        return "redirect:/board/list";
    }

    @GetMapping("/updateform")
    public String updateForm(Model model, @RequestParam Long id) {
        model.addAttribute("board", boardService.findById(id));
        System.out.println(model);
        return "board/updateform";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Board board, @RequestParam String password) {
        Board existingBoard = boardService.findById(board.getId());

        if (!existingBoard.getPassword().equals(password)) {
            return "redirect:/board/view?id=" + board.getId();
        }


        existingBoard.setName(board.getName());
        existingBoard.setTitle(board.getTitle());
        existingBoard.setContent(board.getContent());

        boardService.update(existingBoard);

        return "redirect:/board/view?id=" + board.getId();
    }


    @GetMapping("/deleteform")
    public String deleteForm(Model model, @RequestParam Long id) {
        model.addAttribute("id", id);
        return "board/deleteform";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long id, @RequestParam String password) {
        Board board = boardService.findById(id);
        if (board.getPassword().equals(password)) {
            boardService.deleteById(id);
        }
        return "redirect:/board/list";
    }
}
