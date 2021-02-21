// DTO 
public class Member {

	private String loginId;
	private String nickname;
	
	// 게터 세터 만드는 법 : alt + s + r -> 다 체크 후 generate
	
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
}
