import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

// DB 관련 로직 처리
public class ArticleDao {

	Connection conn = null;

	// JDBC 접속 정보
	// ====================== DB 접속 정보============================================
	String url = "jdbc:mysql://127.0.0.1:3306/t1?serverTimezone=UTC"; // 접속할 DBMS 주소
	String id = "root";
	String pw = "";
	// ==============================================================================

	// ===============================접속 시도======================================
	private Connection getConnection() throws ClassNotFoundException, SQLException {
		if (conn == null) {
			Class.forName("com.mysql.cj.jdbc.Driver"); // mysqlDriver 를 찾아야합니다.
			conn = DriverManager.getConnection(url, id, pw); // 특정 DBMS에 접속
		}

		return conn;
		// ==============================================================================
	}

	// 게시물 등록
	public void addArticle(String title, String body) throws SQLException, ClassNotFoundException {

		Statement stmt = getConnection().createStatement();

		String sql = "INSERT INTO article \r\n" + "SET title = '" + title + "',\r\n" + "`body` = '" + body + "',\r\n" + "`mid` = 1,\r\n"
				+ "hit = 0,\r\n" + "regDate = NOW()";
		// 조회 결과가 있는 경우 : select -> executeQuery() - ResultSet으로 리턴
		// 조회 결과가 없는 경우 : update, delete, insert -> executeUpdate() - 리턴 X
		stmt.executeUpdate(sql);

		if (stmt != null) {
			stmt.close();
		}

	}

	// 게시물 목록 조회
	public ArrayList<Article> ArticleList() throws SQLException, ClassNotFoundException {

		Statement stmt = getConnection().createStatement();
		String sql = "select * from article";
		ResultSet rs = stmt.executeQuery(sql);

		ArrayList<Article> articles = new ArrayList<>();
		
		while (rs.next()) {
			// 커서를 이동했을 때 결과 있으면 true, false
			
			Article a = new Article();
			
			a.setId(rs.getInt("id"));
			a.setTitle(rs.getString("title"));
			a.setBody(rs.getString("body"));
			a.setMid(rs.getInt("mid"));
			a.setHit(rs.getInt("hit"));
			a.setRegDate(rs.getString("regDate"));
			
			articles.add(a);

		}

		if (rs != null) {
			rs.close();
		}
		if (stmt != null) {
			stmt.close();
		}
		
		return articles;
	}

	// 회원가입
	public void addMember(String id, String pw, String nick) throws ClassNotFoundException, SQLException {
		Statement stmt = getConnection().createStatement();
		String sql = "INSERT INTO `member`\r\n" + 
				"SET loginId = '" + id + "',\r\n" + 
				"loginPw = '" + pw + "',\r\n" + 
				"nickname = '" + nick + "',\r\n" + 
				"regDate = NOW();";
		
		stmt.executeUpdate(sql);
		if (stmt != null) {
			stmt.close();
		}
	}
	
	// 로그인
	public Member loginCheck(String id, String pw) throws ClassNotFoundException, SQLException {
		Statement stmt = getConnection().createStatement();
		String sql = "SELECT * FROM `member` WHERE loginId = '" + id + "' AND loginPw = '" + pw + "'";
		
		ResultSet rs = stmt.executeQuery(sql);
		// 단건은 if로 next 처리
		Member m = new Member();
		
		if(rs.next()) {	
			//m.loginId = rs.getString("loginId");
			m.setLoginId(rs.getString("loginId"));
			m.setNickname(rs.getString("nickname"));
		}
		
		if (stmt != null) {
			stmt.close();
		}
		
		return m;
	}
	
	// 특정 게시물 가져오기
	public Article getArticleById(int aid) throws ClassNotFoundException, SQLException {
		
		Statement stmt = getConnection().createStatement();
		String sql = "SELECT * \r\n" + "FROM article \r\n" + "WHERE id = " + aid;
		ResultSet rs = stmt.executeQuery(sql);

		Article a = new Article();
		
		if (rs.next()) {
			int targetId = rs.getInt("id");
			String title = rs.getString("title");
			String body = rs.getString("body");
			String regDate = rs.getString("regDate");
			
			a.setId(targetId);
			a.setTitle(title);
			a.setBody(body);
			a.setRegDate(regDate);
			
		}
		
		return a;
	}
	
	// 댓글 목록 가져오기
	public ArrayList<Reply> getReplies(int aid) throws ClassNotFoundException, SQLException {
		Statement stmt = getConnection().createStatement();
		String sql = "SELECT r.*, m.nickname \r\n" + 
				"FROM reply r\r\n" + 
				"INNER JOIN `member` m\r\n" + 
				"ON r.mid = m.id\r\n" + 
				"WHERE r.aid = " + aid;
		
		ResultSet rs = stmt.executeQuery(sql);

		ArrayList<Reply> replies = new ArrayList<>();
		
		while (rs.next()) {
			// 커서를 이동했을 때 결과 있으면 true, false
			
			Reply r = new Reply();
			
			r.setId(rs.getInt("id"));
			r.setAid(rs.getInt("aid"));
			r.setBody(rs.getString("body"));
			r.setMid(rs.getInt("mid"));
			r.setRegDate(rs.getString("regDate"));
			r.setNickname(rs.getString("nickname"));
			
			replies.add(r);

		}

		if (rs != null) {
			rs.close();
		}
		if (stmt != null) {
			stmt.close();
		}
		
		return replies;
	}
}

