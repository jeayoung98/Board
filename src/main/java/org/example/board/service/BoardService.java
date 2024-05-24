package org.example.board.service;


import lombok.RequiredArgsConstructor;
import org.example.board.domain.Board;
import org.example.board.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

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

    public Board update(Board board, String password) {
        Board existingBoard = boardRepository.findById(board.getId()).orElse(null);
        if (!existingBoard.getPassword().equals(password)) {
            return null;
        }
        board.setUpdatedAt(LocalDateTime.now());

        return boardRepository.save(board);
    }

    public void deleteById(Long id) {
        boardRepository.deleteById(id);
    }
}
