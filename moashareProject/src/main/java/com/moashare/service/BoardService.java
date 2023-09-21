package com.moashare.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import com.moashare.dto.BoardDTO;
import com.moashare.dto.ReplyDTO;
import com.moashare.model.Board;
import com.moashare.model.Bookmark;
import com.moashare.model.Member;
import com.moashare.model.Reply;
import com.moashare.repository.BoardRepository;
import com.moashare.repository.BookmarkRepository;
import com.moashare.repository.MemberRepository;
import com.moashare.repository.ReplyRepository;
import com.nimbusds.jose.proc.SecurityContext;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

	 private final BoardRepository boardRepository;
	 private final ReplyRepository replyRepository;
	 private final MemberRepository memberRepository;
	 private final String VIEWCOOKIENAME = "alreadyViewCookie";
	 private final BookmarkRepository bookmarkRepository;
	 
	 // 게시판 글 저장
	 @Transactional // 함수 종료 시 자동 commit
	 public void saveBoard(Board board) {
		 boardRepository.save(board);
	 }
	 
	// 게시판 목록 가져오기
	@Transactional(readOnly = true)
	public Page<BoardDTO> boardList(Pageable pageable) {
		Page<Board> boards= boardRepository.findAll(pageable);
		List<BoardDTO> boardDTO=doPaging(boards);
		return new PageImpl<>(boardDTO, pageable, boards.getTotalElements());
		
	}

	// 게시판 목록(검색 시)가져오기
	@Transactional(readOnly = true)
	public Page<BoardDTO> boardSearchList(String searchKeyWord, Pageable pageable) {
		Page<Board> boards= boardRepository.findByTitleContaining(searchKeyWord,pageable);
		List<BoardDTO> boardDTO=doPaging(boards);
		return new PageImpl<>(boardDTO, pageable, boards.getTotalElements());
	}
	
	// 공통 페이징 처리
	private List<BoardDTO> doPaging(Page<Board> boards) {
		List<BoardDTO> boardDTO=new ArrayList<>();
		for(Board board : boards ) {
			 BoardDTO result = BoardDTO.builder()
					 .board(board)
					 .build();
			 boardDTO.add(result);
		}
		return boardDTO;
	}

	// 게시판 상세페이지
	@Transactional
	public Board detailView(Long id) {
		Board board=boardRepository.findById(id)
				.orElseThrow(() -> {
					return new IllegalArgumentException("아이디를 찾을 수 없어 글을 볼 수 없습니다.");
				});
		return board;
	}
	
	// 게시판 삭제
	@Transactional
	public void deleteBoard(Long id) {
		boardRepository.deleteById(id);
	}

	// 게시판 수정
	@Transactional
	public void updateBoard(Long id, Board requestBoard) {
		Board board=boardRepository.findById(id)
				.orElseThrow(() -> {
					return new IllegalArgumentException("아이디가 없어 글 찾기를 실패하였씁니다.");
				}); // 영속화 완료
		board.update(requestBoard.getTitle(),requestBoard.getContent());

		// 해당 함수 종료시(Service가 종료될 때) 트랜잭션 종료, 이 때 더티체킹함(자동 업데이트)
	}

	// 댓글 작성
	@Transactional
	public void saveReply(ReplyDTO replyDTO) {
		
//		Member member=memberRepository.findById(replyDTO.getMemberId()).orElseThrow(() -> {
//			return new IllegalArgumentException("댓글 작성에 실패하였씁니다.회원 아이디를 찾을 수 없습니다.");
//		});
//		Board board=boardRepository.findById(replyDTO.getBoardId())
//				.orElseThrow(() -> {
//					return new IllegalArgumentException("댓글 작성에 실패하였씁니다.게시글번호를 찾을 수 없습니다.");
//				}); // 영속화
//		Reply reply=new Reply();
//		reply.update(member, board, replyDTO.getRcontent());
		int result=replyRepository.replySave(replyDTO.getMember_id(), replyDTO.getBoard_id(), replyDTO.getRcontent());
		System.out.println(result); // 오브젝트를 출력하면 자동으로 toString 호출
	}

	@Transactional
	public void deleteReply(Long replyId) {
		replyRepository.deleteById(replyId);
		
	}

	@Transactional
	public int updateHits(Long id) {
		return boardRepository.updateHits(id);
	}
	
	public Map<String, String> validateHandling(Errors errors) {
		Map<String, String> validatorResult = new HashMap<>();
		for(FieldError error : errors.getFieldErrors()) {
			String validKeyName=String.format("valid_%s", error.getField());
			validatorResult.put(validKeyName, error.getDefaultMessage());
		}
		return validatorResult;
	}
	// 조회수
	public int updateView(Long id, HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
        boolean checkCookie = false;
        int result = 0;
        if(cookies != null){
            for (Cookie cookie : cookies)
            {
                // 이미 조회를 한 경우 체크
                if (cookie.getName().equals(VIEWCOOKIENAME+id)) checkCookie = true;

            }
            if(!checkCookie){
                Cookie newCookie = createCookieForForNotOverlap(id);
                response.addCookie(newCookie);
                result = boardRepository.updateHits(id);
            }
        } else {
            Cookie newCookie = createCookieForForNotOverlap(id);
            response.addCookie(newCookie);
            result = boardRepository.updateHits(id);
        }
        return result;
	}

	/*
    * 조회수 중복 방지를 위한 쿠키 생성 메소드
    * @param cookie
    * @return
    * 
	 */
	private Cookie createCookieForForNotOverlap(Long id) {
		 Cookie cookie = new Cookie(VIEWCOOKIENAME+id, String.valueOf(id));
	        cookie.setComment("조회수 중복 증가 방지 쿠키");	// 쿠키 용도 설명 기재
	        cookie.setMaxAge(getRemainSecondForTommorow()); 	// 하루를 준다.
	        cookie.setHttpOnly(true);				// 서버에서만 조작 가능
	        return cookie;
	}

	// 다음 날 정각까지 남은 시간(초)
    private int getRemainSecondForTommorow() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tommorow = LocalDateTime.now().plusDays(1L).truncatedTo(ChronoUnit.DAYS);
        return (int) now.until(tommorow, ChronoUnit.SECONDS);
    }
    
    // 북마크 기능
    @Transactional
    public void likeBoard(Long boardId, Long memberId ) {
    	Board board=boardRepository.findById(boardId).orElseThrow(() -> {
    		return new IllegalArgumentException("게시판이 제대로 확인되지않아 북마크에 실패하였씁니다.");
    	});
    	
    	Member member=memberRepository.findById(memberId).orElseThrow(() -> {
    		return new IllegalArgumentException("아이디가 제대로 확인되지않아 북마크에 실패하였씁니다.");
    	});
    	
    	if(bookmarkRepository.findByBoardAndMember(board, member)==null) {
    		// 북마크를 누른 적이 없다면 Bookmark 생성
    		Bookmark bookmark = Bookmark.builder()
    				.board(board)
    				.member(member)
    				.build();
    		bookmarkRepository.save(bookmark);
    	} else {
    		// 북마크 취소 후 테이블 삭제 
    		Bookmark bookmark = bookmarkRepository.findByBoardAndMember(board, member);
    		bookmark.unLikedBookmark(board);
    		bookmarkRepository.delete(bookmark);
    	}
    	
    }
}
