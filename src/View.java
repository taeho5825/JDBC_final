import java.util.ArrayList;

public class View {
	
	// 게시물 목록 출력
	public void printArticleList(ArrayList<Article> articles) {
		for(int i = 0; i < articles.size(); i++) {
			
			Article a = articles.get(i);
			System.out.println("====================");
			System.out.println("번호 : " + a.getId());
			System.out.println("제목 : " + a.getTitle());
			System.out.println("내용 : " + a.getBody());
			System.out.println("작성자 : " + a.getMid());
			System.out.println("조회수 : " + a.getHit());
			System.out.println("좋아요 : " + a.getLikeCnt());
			System.out.println("작성일 : " + a.getRegDate());
			System.out.println("====================");
		}
		
	}
	
	//특정 게시물 상세보기
	public void printArticle(Article article, ArrayList<Reply> replies) {
		System.out.println("====================");
		System.out.println("번호 : " + article.getId());
		System.out.println("제목 : " + article.getTitle());
		System.out.println("내용 : " + article.getBody());
		System.out.println("작성일 : " + article.getRegDate());
		System.out.println("====================");
		System.out.println("========= 댓글 =========");
		for(int i = 0; i < replies.size(); i++) {
			Reply r = replies.get(i);
			System.out.println("작성자 : " + r.getNickname());
			System.out.println("내용 : " + r.getBody());
			System.out.println("작성일 : " + r.getRegDate());
			System.out.println("======================");
		}
		System.out.println("========================");
	}
}
