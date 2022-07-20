package kr.yerin.board2.service;


import kr.yerin.board2.domain.entity.BoardEntity;
import kr.yerin.board2.domain.repository.BoardRepository;
import kr.yerin.board2.dto.BoardDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BoardService {
    private BoardRepository boardRepository;

    @Transactional
    public Long savePost(BoardDto boardDto){
        return boardRepository.save(boardDto.toEntity()).getId();
    }

    public Page<BoardEntity> list(int page){
        return boardRepository.findAll(PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC,"id")));
    }

    public Page<BoardEntity> listup(int page){
        return boardRepository.findAll(PageRequest.of(page, 5, Sort.by(Sort.Direction.ASC,"id")));
    }

    @Transactional
    public BoardDto getPost(Long id) {
        Optional<BoardEntity> boardEntityWrapper = boardRepository.findById(id);
        BoardEntity boardEntity = boardEntityWrapper.get();

        BoardDto boardDTO = BoardDto.builder()
                .id(boardEntity.getId())
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .writer(boardEntity.getWriter())
                .createdDate(boardEntity.getCreatedDate())
                .build();

        return boardDTO;
    }

    @Transactional
    public void deletePost(Long id) {
        boardRepository.deleteById(id);
    }

}
