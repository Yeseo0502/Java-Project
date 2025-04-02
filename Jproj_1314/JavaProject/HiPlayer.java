package JavaProject;

import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.*;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

//회원가입 화면
public class HiPlayer extends JFrame {
	private JTextField usernameField;  // 사용자명 입력 필드
	private JPasswordField passwordField;  // 비밀번호 입력 필드	

	public HiPlayer() {
		setTitle("회원가입");  // 창 제목 설정
		setSize(300, 200);  // 창 크기 설정
		setLayout(new GridLayout(3, 2, 10, 10));  // 그리드 레이아웃 설정
		setLocationRelativeTo(null); // 화면 가운데로 띄우기
		add(new JLabel("사용자명:"));  // 사용자명 라벨 추가
		usernameField = new JTextField();  // 사용자명 입력 필드
		add(usernameField);
		add(new JLabel("비밀번호:"));  // 비밀번호 라벨 추가
		passwordField = new JPasswordField();  // 비밀번호 입력 필드
		add(passwordField);
		JButton finish = new JButton("회원가입 완료");  // 회원가입 버튼
		add(finish);
		//배경이미지
		ImageIcon icon=new ImageIcon("imgs/imgIcon.jpg");
		setIconImage(icon.getImage());
		//마우스
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image cursorimage=tk.getImage("imgs/mouse01.png");
		Point point=new Point(20,20);
		Cursor cursor=tk.createCustomCursor(cursorimage, point, "haha");
		setCursor(cursor); 
		// 회원가입 버튼 클릭 시 입력된 사용자명과 비밀번호를 가져옴
		finish.addActionListener(e -> {
			String username = usernameField.getText();  // 입력된 사용자명
			String password = new String(passwordField.getPassword());  // 입력된 비밀번호
			// 이미 존재하는 사용자명인 경우
			if (isUserExistsInFile(username)) {
				// 존재하는 사용자가 있으면 메시지 출력
				JOptionPane.showMessageDialog(this, "이미 존재하는 사용자명입니다.");
			}
			// 사용자가 비밀번호를 입력하지 않은 경우
			else if (username.isEmpty() || password.isEmpty()) {
				// 입력되지 않은 필드가 있으면 메시지 출력
				JOptionPane.showMessageDialog(this, "아이디와 비밀번호를 입력하세요.");
			}
            // 올바르게 입력된 경우
			else {
				// 사용자명과 비밀번호를 파일로 저장하기
				try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true))) {
					writer.write(username + "," + password);
					writer.newLine(); // 새 줄에 기록하기
					JOptionPane.showMessageDialog(this, "회원가입 성공!");
					dispose(); // 창 닫기
				} catch (IOException ex) { // 파일 저장 오류 처리
					JOptionPane.showMessageDialog(this, "파일 저장 중 오류가 발생했습니다.");
				}
			}
		});
	}
	// 파일에서 사용자가 존재하는지 확인하는 메서드
	private boolean isUserExistsInFile(String username) {
		try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
        	String line;
        	while ((line = reader.readLine()) != null) {
        		String[] credentials = line.split(",");
        		if (credentials.length > 0 && credentials[0].equals(username)) {
                	return true; // 사용자명 존재
        		}
        	}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "파일 읽기 중 오류가 발생했습니다.");
		}
		return false; // 사용자명 없음
	}
}
