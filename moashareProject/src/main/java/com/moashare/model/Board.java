package com.moashare.model;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

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
import jakarta.persistence.OrderBy;
import jakarta.validation.constraints.NotNull;
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
	
	@NotNull(message="제목은 필수입력값입니다.")
	@Size(min = 1, max = 100)
	private String title;
	
	@NotNull(message="내용은 필수입력값입니다.")
	@Column(columnDefinition = "LONGTEXT")
	private String content;
	
	@ColumnDefault("0")
	private int hits;
	
	// Many=Board , One=Member
	@ManyToOne(fetch = FetchType.EAGER) // FetchType.EAGER 무조건 들고옴
	@JoinColumn(name = "member_id")
	private Member member;// DB는 오브젝트 저장할 수 없다.  
	
	// mappedBy 연관관계 주인아니다 (foreign key 아님), 데이터베이스에 컬럼을 만들지 말자 
	// 만약 댓글을 펼치기버튼을 눌러 볼 경우 LAZY
	// cascade = CascadeType.REMOVE 게시판 삭제시 댓글 들 같이 삭제 
	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE) 
	@JsonIgnoreProperties({"board"})
//	@OrderBy("id desc")
	private List<Reply> replys;
	
	@CreationTimestamp
	private Timestamp reg_dt;
	
	@Builder
	public Board(Long id, String title, String content, int hits, Member member , Timestamp reg_dt ) {
		this.id=id;
		this.title=title;
		this.content=content;
		this.hits=hits;
		this.member=member;
		this.reg_dt=reg_dt;
		
	}
	
	// 게시판 수정
	public void update(String title, String content) {
		this.title=title;
		this.content=content;
	}

}
