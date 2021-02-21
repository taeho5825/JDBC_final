import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	static Connection conn = null;
	static Statement stmt = null;
	static ArticleDao adao = new ArticleDao();
	static View view = new View();

	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		// 입력 용도
		Scanner sc = new Scanner(System.in);
		Member loginedMember = null;

		while (true) {
			if(loginedMember == null) {
				System.out.println("명령어 : ");				
			} else {
				System.out.println("명령어 [" + loginedMember.getLoginId() + "(" + loginedMember.getNickname() + ")] : ");
			}
			String cmd = sc.nextLine();

			if (cmd.equals("add")) {

				System.out.println("제목을 입력해주세요 : ");
				String title = sc.nextLine();

				System.out.println("내용을 입력해주세요 : ");
				String body = sc.nextLine();

				adao.addArticle(title, body);

				// DB에 저장 - insert

			} else if (cmd.equals("list")) {
				// 저장했던 데이터 모두 출력
				// DB에 있는 데이터 가져와서 출력 - select

				ArrayList<Article> articles = adao.ArticleList();
				view.printArticleList(articles);

			} else if (cmd.equals("update")) {

				System.out.println("수정할 게시물 번호 입력 : ");
				// 게시물 번호로 게시물 찾기
				int aid = Integer.parseInt(sc.nextLine());

				if (isExistArticleById(aid)) {

					System.out.println("제목을 입력해주세요 : ");
					String title = sc.nextLine();

					System.out.println("내용을 입력해주세요 : ");
					String body = sc.nextLine();

					stmt = conn.createStatement();

					String sql2 = "UPDATE article \r\n" + "SET title = '" + title + "',\r\n" + "`body` = '" + body
							+ "'\r\n" + "WHERE id = " + aid;

					stmt.executeUpdate(sql2);

				} else {
					System.out.println("없는 게시물입니다.");
				}
			} else if (cmd.equals("delete")) {
				System.out.println("삭제할 게시물 번호 입력 : ");
				// 게시물 번호로 게시물 찾기
				int aid = Integer.parseInt(sc.nextLine());

				if (isExistArticleById(aid)) {
					String sql = "DELETE\r\n" + "FROM article\r\n" + "WHERE id = " + aid;
					stmt = conn.createStatement();
					stmt.executeUpdate(sql);

				} else {
					System.out.println("없는 게시물입니다.");
				}
			} else if (cmd.equals("read")) {
				System.out.println("상세보기 할 게시물 번호 입력 : ");
				// 게시물 번호로 게시물 찾기
				int aid = Integer.parseInt(sc.nextLine());
				Article article = adao.getArticleById(aid);
				
				if (article != null) {
					ArrayList<Reply> replies = adao.getReplies(aid);
					view.printArticle(article, replies);
					
				} else {
					System.out.println("없는 게시물입니다.");
				}
			} else if (cmd.equals("signup")) {
				System.out.println("아이디:");
				String id = sc.nextLine();
				System.out.println("비밀번호:");
				String pw = sc.nextLine();
				System.out.println("닉네임:");
				String nick = sc.nextLine();

				adao.addMember(id, pw, nick);

			} else if (cmd.equals("login")) {
				System.out.println("아이디:");
				String id = sc.nextLine();
				System.out.println("비밀번호:");
				String pw = sc.nextLine();

				loginedMember = adao.loginCheck(id, pw);

			}
		}
		// ================================================================================
	}

	private static boolean isExistArticleById(int aid) throws SQLException {

		String sql = "select * from article where id = " + aid;

		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);

		if (rs.next()) {
			return true;
		}

		return false;
	}
}
