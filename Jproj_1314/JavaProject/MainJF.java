package JavaProject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

//메인 메뉴 JFrame
class MainJF extends JFrame {
	MainJF() {
		setTitle("참참참 게임하기");  // 창 제목 설정
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // 창 닫을 때 프로그램 종료
		setSize(600, 450);  // 창 크기 설정
		setLayout(new BorderLayout());  // 레이아웃 설정
		setLocationRelativeTo(null); //화면 중앙에다가 설치
		//첫화면 배경화면
		ImageIcon ic=new ImageIcon("imgs/HomePage.png");
		
		ImageIcon icon=new ImageIcon("imgs/imgIcon.jpg");
		setIconImage(icon.getImage());
		
		//마우스
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image cursorimage=tk.getImage("imgs/mouse01.png");
		Point point=new Point(20,20);
		Cursor cursor=tk.createCustomCursor(cursorimage, point, "haha");
		setCursor(cursor); 
		
		JPanel backgroundPanel = new JPanel() { //배경을 그릴 새로운 JPanel생성
			protected void paintComponent(Graphics g) {//paintComponent 오버라이드하며 조정하거나 수정하기
				super.paintComponent(g); //JP의 기본적인 그리기 동작을 호출
				// 배경 이미지 그리기
				g.drawImage(ic.getImage(), 0, 0, getWidth(), getHeight(), null);
				// ic.getImage()는 이미지를 가져, 0, 0은 이미지가 그려질 시작 좌표
				//nullㅇㄴ 이미지에 필요한 설정 없어서 
				//getget들은 JP의 너비와 높이를 가져와서 이미지 크기 조절하기
			}
		};
		backgroundPanel.setLayout(null);  // 배경 패널의 레이아웃을 null로 설정하여 위치를 자유롭게 조정
		add(backgroundPanel, BorderLayout.CENTER);  // 배경 JPanel을 JFrame 중앙에 추가
     
		JLabel titleLabel = new JLabel("참참참 게임에 오신 것을 환영합니다!", JLabel.CENTER);
		titleLabel.setFont(new Font("Serif", Font.BOLD, 17));  // 타이틀 폰트 설정\
		titleLabel.setForeground(Color.WHITE); //참참참환영하얀색으로
		titleLabel.setBounds(135, 120, 300, 30); //크기좌표설정하기
		backgroundPanel.add(titleLabel); //참참참환영메세지추가
		
		// 게임 시작 버튼 이미지 설정
		JButton startButton = new JButton("게임 시작");  // 버튼에 텍스트와 이미지 함께 설정
		startButton.setBounds(140, 300, 140, 40);
		startButton.setPreferredSize(new Dimension(150, 40));  // 버튼 크기 설정
		backgroundPanel.add(startButton);//게임시작 버튼추가

		// 게임 설명 버튼 이미지 설정
		JButton helpButton = new JButton("게임 설명");  // 버튼에 텍스트와 이미지 함께 설정
		helpButton.setBounds(300, 300, 140, 40);  // 버튼의 위치와 크기 설정
		helpButton.setPreferredSize(new Dimension(150, 40));  // 버튼 크기 설정
		backgroundPanel.add(helpButton);//게임설명 버튼추가
     
		// 게임 시작 버튼 클릭 시 로그인 화면으로 이동
		startButton.addActionListener(e -> {
			new LoginJF().setVisible(true);  // 로그인 화면 생성
			dispose(); // 현재 메뉴 창 닫기
		});

		// 게임 설명 버튼 클릭 시 메시지 설명 띄우기
		helpButton.addActionListener(e -> JOptionPane.showMessageDialog(this,
				"게임 설명:\n1. AI의 선택을 피해 방향을 선택하세요.\n2. 레벨에 따라 목표 횟수 달성 시 레벨 업!\n3. 하트가 모두 소진되면 게임 종료!",
				"게임 설명", JOptionPane.INFORMATION_MESSAGE));		
	}
}
