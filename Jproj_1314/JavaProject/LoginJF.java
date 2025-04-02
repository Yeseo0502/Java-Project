package JavaProject;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import JavaProject.*;

//로그인 및 회원가입 JFrame
class LoginJF extends JFrame {
	private static final HashMap<String, String> userDatabase = new HashMap<>();  // 사용자 데이터 저장 (username, password)

	public LoginJF() {
		setTitle("로그인 / 회원가입");  // 창 제목 설정
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // 창 닫을 때 프로그램 종료
		setSize(500, 300);  // 창 크기 설정
		setLocationRelativeTo(null); //화면 가운데로 띄우기
		setLayout(null);
		ImageIcon LoginIM=new ImageIcon("imgs/Login.png");

		JPanel backgroundLog = new JPanel() {//배경을 그릴 새로운 JPanel생성
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);//JP의 기본적인 그리기 동작을 호출
				g.drawImage(LoginIM.getImage(), 0, 0, getWidth(), getHeight(), this);  // 배경 이미지 그리기
			}
		};
		ImageIcon icon=new ImageIcon("imgs/imgIcon.jpg");
		setIconImage(icon.getImage());
		backgroundLog.setLayout(null);  // 배경 패널의 레이아웃을 null로 설정하여 자유롭게 위치 조정
		backgroundLog.setBounds(0, 0, getWidth(), getHeight());
		add(backgroundLog);  // JFrame에 배경 패널 추가
		//마우스
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image cursorimage=tk.getImage("imgs/mouse01.png");
		Point point=new Point(20,20);
		Cursor cursor=tk.createCustomCursor(cursorimage, point, "haha");
		setCursor(cursor); 
		
		JButton loginButton = new JButton("로그인");
		loginButton.setBounds(130, 170, 100, 30); //좌표크기설정하기
		backgroundLog.add(loginButton); //JFrame에 로그인 버튼 추가
		
		JButton registerButton = new JButton("회원가입");
		registerButton.setBounds(250, 170, 100, 30);
		backgroundLog.add(registerButton);
		
		JButton deleteAccountButton = new JButton("회원 탈퇴");
		deleteAccountButton.setBounds(380, 220, 100, 30);
		backgroundLog.add(deleteAccountButton);

		// 로그인 버튼 클릭 시 로그인 화면으로 이동
		loginButton.addActionListener(e -> new Login().setVisible(true));
		// 회원가입 버튼 클릭 시 회원가입 화면으로 이동
		registerButton.addActionListener(e -> new HiPlayer().setVisible(true));
		//회원퇄퇴 버튼 클릭 시 회원탈퇴 화면으로 이동
		deleteAccountButton.addActionListener(e -> new ByePlayer().setVisible(true));
	}
}
