package com.moashare.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moashare.dto.BoardDTO;
import com.moashare.dto.BookmarkDTO;
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
		int result=replyRepository.replySave(replyDTO.getMember_id(), replyDTO.getBoard_id(), replyDTO.getRcontent());
		System.out.println(result); // 오브젝트를 출력하면 자동으로 toString 호출
		Board board=boardRepository.findById(replyDTO.getBoard_id())
				.orElseThrow(() -> {
					return new IllegalArgumentException("아이디가 없어 댓글 개수 찾기를 실패하였씁니다.");
				}); // 영속화 완료
		board.increaseReplyCnt(board.getReplycnt());
	}

	// 댓글삭제
	@Transactional
	public void deleteReply(Long replyId) {
		Reply reply =replyRepository.findById(replyId)
				.orElseThrow(() -> {
					return new IllegalArgumentException("아이디가 없어 댓글 개수 찾기를 실패하였씁니다.");
				});
		replyRepository.deleteById(replyId);
		 // 영속화 완료
		log.info("<<<<<<<<<<<<<<<<<<<<<<< reply : " +reply.getBoard().getId());
		Board board=boardRepository.findById(reply.getBoard().getId())
				.orElseThrow(() -> {
					return new IllegalArgumentException("아이디가 없어 댓글 개수 찾기를 실패하였씁니다.");
				});
		board.decreaseReplyCnt(board.getReplycnt());
		
	}
	
	// 조회수 증가
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
    public String likeBoard(Long boardId, Long memberId ) {
    	Board board=boardRepository.findById(boardId).orElseThrow(() -> {
    		return new IllegalArgumentException("게시판이 제대로 확인되지않아 북마크에 실패하였씁니다.");
    	});
    	
    	Member member=memberRepository.findById(memberId).orElseThrow(() -> {
    		return new IllegalArgumentException("아이디가 제대로 확인되지않아 북마크에 실패하였씁니다.");
    	});
    	String bookmarkStatus="n"; 
    	if(bookmarkRepository.findByBoardAndMember(board, member)==null) {
    		// 북마크를 누른 적이 없다면 Bookmark 생성
    		Bookmark bookmark = Bookmark.builder()
    				.board(board)
    				.member(member)
    				.build();
    		bookmarkRepository.save(bookmark);
    		bookmarkStatus="y"; 
    	} else {
    		// 북마크 취소 후 테이블 삭제 
    		Bookmark bookmark = bookmarkRepository.findByBoardAndMember(board, member);
    		bookmark.unLikedBookmark(board);
    		bookmarkRepository.delete(bookmark);
    	}
    	log.info("<<<<<<<<<<<<<<<<<<<<<<bookmarkstatus : " +bookmarkStatus);
    	return bookmarkStatus;
    	
    }
    // 로그인 아이디에 대한 북마크 가져오기 
    @Cacheable(value = "translationCache", key ="#memberId")
    @Transactional(readOnly = true)
	public List<Bookmark> bookmarkList(Long memberId) {
		Member member=memberRepository.findById(memberId).orElseThrow(() -> {
    		return new IllegalArgumentException("아이디가 제대로 확인되지않아 북마크리스트를 가져오지못하였습니다.");
    	});
		
		List<Bookmark> bookmarkList=bookmarkRepository.findByMember(member);
		if (bookmarkList !=null) {
			return bookmarkList;
		}
		return null;
	}
    
    // 해당 boardId를 가진 board객체 List객체로 가져오기
    @Transactional(readOnly = true)
	public List<BoardDTO> inBoardId(List<Long> ids) {
    	BoardDTO boardDTO=null; 
    	List<BoardDTO> result= new ArrayList<>();
		List<Board> boardList=boardRepository.findByIdIn(ids);
		for (Board board : boardList ) {
			boardDTO = BoardDTO.builder()
					.board(board)
					.build();
			result.add(boardDTO);
		}
		return result;
	}
    // 북마크체크된 북마크리스트 가져오기
    @Transactional(readOnly = true)
	public Page<BookmarkDTO> bookmarkCkList(Long memberId, Pageable pageable) {
    	Member member=memberRepository.findById(memberId).orElseThrow(() -> {
    		return new IllegalArgumentException("아이디가 확인되지않아 북마크를 가져오지못하였습니다.");
    	});
		Page<Bookmark> bookmarks= bookmarkRepository.findByMember(member, pageable);
		int size=bookmarks.getSize();
		log.info("<<<<<<<<<<<<<<<<<< 여기 사이즈 : " +size);
		List<BookmarkDTO> bookmarkDTO=doBookmarkPaging(bookmarks);
		log.info("MMMMMMMMM");
		return new PageImpl<>(bookmarkDTO, pageable, bookmarks.getTotalElements());
		
	}
    // 북마크 페이징처리
	private List<BookmarkDTO> doBookmarkPaging(Page<Bookmark> bookmarks) {
		List<BookmarkDTO> bookmarkDTO=new ArrayList<>();
		for(Bookmark bookmark : bookmarks ) {
			 BookmarkDTO result = BookmarkDTO.builder()
					 .board(bookmark.getBoard())
					 .build();
			 bookmarkDTO.add(result);
		}
		return bookmarkDTO;
	}
    // 댓글 수정
    @Transactional
	public void updateReply(Long replyId, ReplyDTO replyDTO) {
		Reply reply=replyRepository.findById(replyId)
				.orElseThrow(() -> {
					return new IllegalArgumentException("아이디가 없어 글 찾기를 실패하였씁니다.");
				}); // 영속화 완료
		reply.update(replyDTO.getRcontent());
	}
    
    // 댓글 리스트 가져오기 
    @Transactional(readOnly = true)
	public List<Reply> getReplyList(Long board_id){
		log.info("<<<<<<<<<<<<<<<<서비스 : " +board_id);
		Board board=boardRepository.findById(board_id).orElseThrow(() -> {
    		return new IllegalArgumentException("게시글이 제대로 확인되지않아 댓글리스트를 가져오지못하였습니다.");
    	});
		List<Reply> replyList=new ArrayList<>();
		replyList=replyRepository.findAllByOrderByBoardDesc(board_id);
		if (replyList !=null) {
			return replyList;
		}
		return null;

	}
    // 인기글 가져오기
    @Transactional(readOnly = true)
	public List<Board> hotBoardList() {
		List<Board> hotBoardList=boardRepository.findAllByHotBoard();
		return hotBoardList;
	}
}
