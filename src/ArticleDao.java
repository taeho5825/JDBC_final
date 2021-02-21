import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

// DB ���� ���� ó��
public class ArticleDao {

	Connection conn = null;

	// JDBC ���� ����
	// ====================== DB ���� ����============================================
	String url = "jdbc:mysql://127.0.0.1:3306/t1?serverTimezone=UTC"; // ������ DBMS �ּ�
	String id = "root";
	String pw = "";
	// ==============================================================================

	// ===============================���� �õ�======================================
	private Connection getConnection() throws ClassNotFoundException, SQLException {
		if (conn == null) {
			Class.forName("com.mysql.cj.jdbc.Driver"); // mysqlDriver �� ã�ƾ��մϴ�.
			conn = DriverManager.getConnection(url, id, pw); // Ư�� DBMS�� ����
		}

		return conn;
		// ==============================================================================
	}

	// �Խù� ���
	public void addArticle(String title, String body) throws SQLException, ClassNotFoundException {

		Statement stmt = getConnection().createStatement();

		String sql = "INSERT INTO article \r\n" + "SET title = '" + title + "',\r\n" + "`body` = '" + body + "',\r\n" + "`mid` = 1,\r\n"
				+ "hit = 0,\r\n" + "regDate = NOW()";
		// ��ȸ ����� �ִ� ��� : select -> executeQuery() - ResultSet���� ����
		// ��ȸ ����� ���� ��� : update, delete, insert -> executeUpdate() - ���� X
		stmt.executeUpdate(sql);

		if (stmt != null) {
			stmt.close();
		}

	}

	// �Խù� ��� ��ȸ
	public ArrayList<Article> ArticleList() throws SQLException, ClassNotFoundException {

		Statement stmt = getConnection().createStatement();
		String sql = "select * from article";
		ResultSet rs = stmt.executeQuery(sql);

		ArrayList<Article> articles = new ArrayList<>();
		
		while (rs.next()) {
			// Ŀ���� �̵����� �� ��� ������ true, false
			
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

	// ȸ������
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
	
	// �α���
	public Member loginCheck(String id, String pw) throws ClassNotFoundException, SQLException {
		Statement stmt = getConnection().createStatement();
		String sql = "SELECT * FROM `member` WHERE loginId = '" + id + "' AND loginPw = '" + pw + "'";
		
		ResultSet rs = stmt.executeQuery(sql);
		// �ܰ��� if�� next ó��
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
	
	// Ư�� �Խù� ��������
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
	
	// ��� ��� ��������
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
			// Ŀ���� �̵����� �� ��� ������ true, false
			
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

