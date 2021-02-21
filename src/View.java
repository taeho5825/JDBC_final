import java.util.ArrayList;

public class View {
	
	// �Խù� ��� ���
	public void printArticleList(ArrayList<Article> articles) {
		for(int i = 0; i < articles.size(); i++) {
			
			Article a = articles.get(i);
			System.out.println("====================");
			System.out.println("��ȣ : " + a.getId());
			System.out.println("���� : " + a.getTitle());
			System.out.println("���� : " + a.getBody());
			System.out.println("�ۼ��� : " + a.getMid());
			System.out.println("��ȸ�� : " + a.getHit());
			System.out.println("���ƿ� : " + a.getLikeCnt());
			System.out.println("�ۼ��� : " + a.getRegDate());
			System.out.println("====================");
		}
		
	}
	
	//Ư�� �Խù� �󼼺���
	public void printArticle(Article article, ArrayList<Reply> replies) {
		System.out.println("====================");
		System.out.println("��ȣ : " + article.getId());
		System.out.println("���� : " + article.getTitle());
		System.out.println("���� : " + article.getBody());
		System.out.println("�ۼ��� : " + article.getRegDate());
		System.out.println("====================");
		System.out.println("========= ��� =========");
		for(int i = 0; i < replies.size(); i++) {
			Reply r = replies.get(i);
			System.out.println("�ۼ��� : " + r.getNickname());
			System.out.println("���� : " + r.getBody());
			System.out.println("�ۼ��� : " + r.getRegDate());
			System.out.println("======================");
		}
		System.out.println("========================");
	}
}
