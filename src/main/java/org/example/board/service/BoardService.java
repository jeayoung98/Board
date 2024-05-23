package org.example.board.service;


import org.example.board.domain.Board;
import org.example.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    public Page<Board> findAll(Pageable pageable) {
        Pageable sortedByDescId = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "createdAt"));

        return boardRepository.findAll(sortedByDescId);
    }

    public Board findById(Long id) {
        return boardRepository.findById(id).orElse(null);
    }

    public Board save(Board board) {
        board.setCreatedAt(LocalDateTime.now());
        return boardRepository.save(board);
    }

    public Board update(Board board) {
        board.setUpdatedAt(LocalDateTime.now());
        return boardRepository.save(board);
    }

    public void deleteById(Long id) {
        boardRepository.deleteById(id);
    }
}
