package com.moashare.model;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Index;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Board {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message="제목은 필수입력값입니다.")
	@Size(min = 1, max = 100)
	private String title;
	
	@NotBlank(message="내용은 필수입력값입니다.")
	@Column(columnDefinition = "LONGTEXT")
	private String content;
	
	@ColumnDefault("0")
	private int hits;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "member_id")
	private Member member;
	
	@ColumnDefault("0")
	private int replycnt;
	
	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE) 
	@JsonIgnoreProperties({"board"})
	private List<Reply> replys;
	
	@CreationTimestamp
	private Timestamp reg_dt;
	
	@Builder
	public Board(Long id, String title, String content, int hits, int replycnt, Member member , Timestamp reg_dt ) {
		this.id=id;
		this.title=title;
		this.content=content;
		this.hits=hits;
		this.member=member;
		this.reg_dt=reg_dt;
		
	}
	
	public BoardBuilder toBuilder() {
        return Board.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .hits(this.hits)
                .member(this.member)
                .replycnt(this.replycnt)
                .reg_dt(this.reg_dt);
    }
	
	// 게시판 수정
	public void update(String title, String content) {
		this.title=title;
		this.content=content;
	}

	// 리뷰개수 수정
	public void increaseReplyCnt(int replycnt ) {
		this.replycnt=replycnt+1;
	}

	public void decreaseReplyCnt(int replycnt) {
		this.replycnt=replycnt-1;
		
	}
}
