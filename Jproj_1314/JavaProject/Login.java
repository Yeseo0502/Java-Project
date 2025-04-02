package JavaProject;

import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import JavaProject.*;

//로그인 화면
public class Login extends JFrame {
	private JTextField usernameField;  // 사용자명 입력 필드
	private JPasswordField passwordField;  // 비밀번호 입력 필드
	public Login() {
		setTitle("로그인");  // 창 제목 설정
		setSize(300, 200);  // 창 크기 설정
		setLocationRelativeTo(null); //화면 가운데로 띄우기
		setLayout(new GridLayout(3, 2, 10, 10));  // (행, 열, 가로 간격, 세로 간격)

		add(new JLabel("사용자명:"));  // 사용자명 라벨 추가
		usernameField = new JTextField();  // 사용자명 입력 필드
		add(usernameField);

		add(new JLabel("비밀번호:"));  // 비밀번호 라벨 추가
		passwordField = new JPasswordField();  // 비밀번호 입력 필드
		add(passwordField);
		ImageIcon icon=new ImageIcon("imgs/imgIcon.jpg");
		setIconImage(icon.getImage());
		JButton loginButton = new JButton("완료");  // 로그인 버튼
		add(loginButton);
		//마우스
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image cursorimage=tk.getImage("imgs/mouse01.png");
		Point point=new Point(20,20);
		Cursor cursor=tk.createCustomCursor(cursorimage, point, "haha");
		setCursor(cursor); 
		// 로그인 버튼 클릭 시
		loginButton.addActionListener(e -> {
			String username = usernameField.getText();  // 입력된 사용자명
			String password = new String(passwordField.getPassword());  // 입력된 비밀번호
			//파일처리
			if(authenticateUser(username,password)) {
				JOptionPane.showMessageDialog(this, "로그인 성공!");
				Player player = loadPlayerData(username);
				new GameFrame(player, new AI()).setVisible(true);
				dispose();
			}
			else {
				JOptionPane.showMessageDialog(this, "로그인 실패\n아이디 또는 비밀번호를 확인하세요!");
			}
		});
	}
	private boolean authenticateUser (String username,String password) {
		try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))){
			String line;
			while ((line = reader.readLine()) != null) {
				String[] credentials = line.split(",");
				if (credentials.length == 2) {
					if (credentials[0].equals(username) && credentials[1].equals(password)) {
						return true;
					}
				}
			}
		}catch(IOException e) {
			JOptionPane.showMessageDialog(this, "파일 읽는데 오류가 발생했습니다");
		}
		return false;
	}
	private Player loadPlayerData(String username) {
		try(BufferedReader read=new BufferedReader(new FileReader("gameSave.txt"))){
			String line;
			while((line = read.readLine()) != null) {
				String[] gameDatea=line.split(",");
				if(gameDatea.length==2 && gameDatea[0].equals(username)) {
					return new Player(username,Integer.parseInt(gameDatea[1]));
				}
			}
		}catch(IOException e) {
			JOptionPane.showMessageDialog(this, "게임 진행 상황 불러오기 실패 죄송합니다.");
		}
		return new Player(username,0);
	}
}
