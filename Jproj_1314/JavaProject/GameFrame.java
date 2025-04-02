package JavaProject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.io.File;
import javax.sound.sampled.*;

//실제 게임 실행 JFrame
public class GameFrame extends JFrame {
	private JLabel playerInfoLabel;  // 플레이어 정보 표시 라벨
	private JLabel aiJL;  // AI 선택 표시 라벨
	private JLabel heartLabel;  // 하트 표시 라벨
	private Player player;
	private AI ai;
	private ImageIcon levelIM;// 레벨에 따른 기본 배경
	private JButton leftButton;
	private JButton centerButton;
	private JButton rightButton;
	private JButton resetButton;
	private JButton exitButton;
	private BackgroundPanel backgroundPanel; // 배경을 그릴 JPanel
	private JLayeredPane layeredPane; // 레이어드 판 추가
	private Clip BGM;
	
	public GameFrame(Player player, AI ai) {
		this.player=player;
		this.ai=ai;
		
		setTitle("참참참 게임");  // 창 제목 설정
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // 창 닫을 때 프로그램 종료
		setSize(600, 400);  // 창 크기 설정
		setLocationRelativeTo(null); //화면 가운데로 띄우기
		setLayout(null);  // null 레이아웃으로 설정
		ImageIcon icon=new ImageIcon("imgs/imgIcon.jpg");
		setIconImage(icon.getImage());
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image cursorimage=tk.getImage("imgs/mouse02.png");
		Point point=new Point(20,20);
		Cursor cursor=tk.createCustomCursor(cursorimage, point, "haha");
		setCursor(cursor); 
		layeredPane = new JLayeredPane();
		setContentPane(layeredPane);//여러컴포넌트를 올려 놓기
		// 배경을 그릴 패널 추가
		backgroundPanel = new BackgroundPanel();
		backgroundPanel.setBounds(0, 0, 600, 400);  // 배경이 패널 크기만큼 차지
		layeredPane.add(backgroundPanel, Integer.valueOf(0));// 배경은 레이어 0번에 추가
		// 버튼 설정
		leftButton = new JButton("왼쪽");
		leftButton.setBounds(100, 200, 100, 50); // 왼쪽 버튼 위치
		centerButton = new JButton("중앙");
		centerButton.setBounds(250, 200, 100, 50); // 중앙 버튼 위치
		rightButton = new JButton("오른쪽");
		rightButton.setBounds(400, 200, 100, 50); // 오른쪽 버튼 위치
		resetButton = new JButton("초기화");
		resetButton.setBounds(350, 320, 100, 25); // 초기화 버튼 위치
		exitButton = new JButton("게임 종료");
		exitButton.setBounds(450, 320, 100, 25); // 게임 종료 버튼 위치

		// 버튼 클릭 이벤트 처리
		leftButton.addActionListener(e -> PlayerChoice(1));
		centerButton.addActionListener(e -> PlayerChoice(2));
		rightButton.addActionListener(e -> PlayerChoice(3));
		exitButton.addActionListener(e -> {
			saveGame();
			System.exit(0);//게임종료
		});
		resetButton.addActionListener(e -> {
			resetGame();
			JOptionPane.showMessageDialog(this, "게임이 초기화되었습니다.");
		});

		// 플레이어 정보 라벨 설정
		playerInfoLabel = new JLabel("플레이어: " + player.getName() + " | 레벨: 1 | 콤보: 0 | 점수: 0", JLabel.LEFT);
		playerInfoLabel.setBounds(10, 10, 400, 40);
		playerInfoLabel.setForeground(Color.WHITE);

		aiJL= new JLabel();
		//aiJL.setBounds(200, 150, 200, 40);
		aiJL.setSize(600, 400);
		ImageIcon aidraw=new ImageIcon("imgs/center.png");
		aiJL.setIcon(aidraw);
		
		heartLabel = new JLabel("하트: " + player.prHeart(), JLabel.RIGHT);
		heartLabel.setBounds(350, 20, 200, 40);
		heartLabel.setForeground(Color.WHITE);
		
		
		//라벨 레이어 1번으로 배경위에 덮기
		layeredPane.add(playerInfoLabel, Integer.valueOf(1));
		layeredPane.add(aiJL, Integer.valueOf(1));
		layeredPane.add(heartLabel, Integer.valueOf(1));

		// 버튼들 레이어2번으로 배경이랑 라벨위에 띄우기
		layeredPane.add(leftButton, Integer.valueOf(2));
		layeredPane.add(centerButton, Integer.valueOf(2));
		layeredPane.add(rightButton, Integer.valueOf(2));
		layeredPane.add(exitButton, Integer.valueOf(2));
		layeredPane.add(resetButton, Integer.valueOf(2));
		
		loadPlayerData();
		updateBackground();
		levelBGM();
		
		revalidate();
		repaint();
	}
	private void PlayerChoice(int playerChoice) { //플레이어 선택 처리하는 메서드
		if(player.isGameOver()) { //게임이 종료된 상태인지 확인하기
			JOptionPane.showMessageDialog(this, "게임 오버! 초기화를 눌러 새로운 게임을 시작하세요~!");
			return;
		}
		ai.setDir(); // AI가 랜덤 방향 설정
		String aiIMG=ai.showDrawturn();

		// 이미지 경로가 비어 있거나 잘못된 경우 기본 이미지를 사용하도록 처리
		if (aiIMG == null || aiIMG.isEmpty()) {
			aiIMG = "imgs/center.png"; // 기본 이미지 경로로 설정
		}
		    
		ImageIcon aiIcon = new ImageIcon(aiIMG);
		// 이미지 크기가 너무 크면 크기를 조정하여 표시
		Image img = aiIcon.getImage();  // 이미지를 가져와서
		Image size = img.getScaledInstance(250, 250, Image.SCALE_SMOOTH);  // 크기 조정
		aiJL.setIcon(new ImageIcon(size));  // 크기 조정된 이미지를 JLabel에 설정
		aiJL.setHorizontalAlignment(JLabel.CENTER);  // 이미지의 수평 중앙 정렬
		aiJL.setVerticalAlignment(JLabel.CENTER);
		aiJL.setBounds(200, 50, 200, 200);
		revalidate();
	    repaint();
		
		boolean success= playerChoice != ai.getDir(); // 회피 성공 여부
		player.updateScore(success); // 점수 및 하트 업데이트
		
		String levelUpMessage=player.checkLevelUp(); //레벨업하고 메시지 출력
		if(! levelUpMessage.isEmpty()) {
			JOptionPane.showMessageDialog(this, levelUpMessage);//레벨업메시지 출력
			updateBackground(); //배경 업데이트
			levelBGM();//븍므업뎃
		}
		
		if (player.isGameOver()) { //게임오버확인
			JOptionPane.showMessageDialog(this, "게임 오버! 최종 점수: " + player.getScore());
			saveGame();//게임 진행 상황 저장하기
			disableGameControls(); // 창 닫기
		}else{
			playerInfoLabel.setText(updatePlayerInfo());//게임이 종료되지 않았으면 플레이어정보+하트 수 업데이트
			heartLabel.setText("하트: " + player.prHeart());
		}
	}
	private void saveGame() { //게임진행상황저장하기
		//게임 진행 정보를 파일에다가 저장하기
		try(BufferedWriter writer = new BufferedWriter(new FileWriter("gameSave.txt", true))){
			//플레이어 정보를 game_progress파일에다가 저장
			writer.write(player.getName() + "," + player.getScore() + "," + player.getLevel() + "," + player.getCombo() + "," + player.getHeart()+","+player.getLevelCount());
			writer.newLine();
		}catch (IOException e) {
			//저장실패시
			JOptionPane.showMessageDialog(this, "게임 진행 상황 저장에 실패했습니다.");
		}
	}
	private void disableGameControls() {//게임에 졌을 때 버튼 비활성화시키기
		leftButton.setEnabled(false);
		centerButton.setEnabled(false);
		rightButton.setEnabled(false);
	}
	private void resetGame() { //게임초기화!
		player.reset(); //플레이어 상태 초기화
		playerInfoLabel.setText(updatePlayerInfo());//플레이어 정보 업뎃
		heartLabel.setText("하트: "+player.prHeart());//하트 상태 다시 업데이트
		//오른쪽왼쪽중앙 버튼 활성화 시키기
		leftButton.setEnabled(true);
		centerButton.setEnabled(true);
		rightButton.setEnabled(true);
		updateBackground();//배경업뎃
		levelBGM();//브금업뎃
		//레이아웃을 다시 계산하고 화면을 갱신
		revalidate();
		repaint();
	}
	private String updatePlayerInfo() {//플레이어 정보 출력하기
		return "플레이어 이름 : "+player.getName()+" | 레벨 : "+player.getLevel()+" | 점수 : "+player.getScore()+" | 콤보 : "+player.getCombo()+" | 회피횟수 : "+player.getLevelCount();
	}
	private void loadPlayerData() {
		try (BufferedReader reader = new BufferedReader(new FileReader("gameSave.txt"))) {
			String line;
			boolean dataLoaded = false;
			while ((line = reader.readLine()) != null) {
				String[] data = line.split(",");
				if (data.length >= 5 && data[0].equals(player.getName())) {
					player.setName(data[0]);
					player.setScore(tryParseInt(data[1]));
					player.setLevel(tryParseInt(data[2]));
					player.setCombo(tryParseInt(data[3]));
					player.setHearts(tryParseInt(data[4]));
					player.setLevelCount(tryParseInt(data[5]));
					dataLoaded = true;
					break;
				}
			}
			if (!dataLoaded) {
				// 데이터가 없으면 새로 시작
				JOptionPane.showMessageDialog(this, "저장된 게임 데이터가 없습니다. 새로운 게임을 시작합니다.");
				player.reset();
			}
			playerInfoLabel.setText(updatePlayerInfo());
			heartLabel.setText("하트 : " + player.prHeart());  // 하트 상태 업데이트
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "저장된 게임 데이터가 없습니다 ");
			player.reset();
		}	
	}

	private int tryParseInt(String str) {
		try {
			return Integer.parseInt(str);
		}catch(NumberFormatException e) {
			return 1;
		}
	}
	private String getDirection(int dir) {
		return switch(dir) {
		case 1 -> "왼쪽";
		case 2 -> "중앙";
		case 3 -> "오른쪽";
		default -> "알 수 없음";
		};
	}
	public void updateBackground() {
	    // 레벨마다 배경화면 변경
	    switch (player.getLevel()) {
	        case 1:
	            levelIM = new ImageIcon("imgs/Level1.jpg");
	            break;
	        case 2:
	            levelIM = new ImageIcon("imgs/Level2.jpg");
	            break;
	        case 3:
	            levelIM = new ImageIcon("imgs/Level3.png");
	            break;
	        default:
	            levelIM = new ImageIcon("imgs/Level1.jpg");
	            break;
	    }
	    backgroundPanel.repaint();
	}
	// 배경을 그릴 JPanel 클래스
	class BackgroundPanel extends JPanel {
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (levelIM != null) {
				g.drawImage(levelIM.getImage(), 0, 0, getWidth(), getHeight(), null); // 배경 이미지를 패널에 맞게 그리기
			}
		}
	}
	public void levelBGM() {
		if(BGM!=null&&BGM.isRunning()) { //이전브금종료
			BGM.stop();
			BGM.close();
		}
		String bgmname="";
		switch (player.getLevel()) {
		case 1 : 
			bgmname="music/1Level.wav";break;
		case 2 :
			bgmname="music/2Level.wav";break;
		case 3 :
			bgmname="music/기본홈화면.wav";break;
			default :
				bgmname="music/1Level.wav";
				break;
		}
		try {
			File a=new File(bgmname);
			AudioInputStream b = AudioSystem.getAudioInputStream(a);
			BGM=AudioSystem.getClip();
			BGM.open(b);
			BGM.start();
			BGM.loop(Clip.LOOP_CONTINUOUSLY); //게속듣기
		}catch(IOException | UnsupportedAudioFileException | LineUnavailableException e) {
			
		}
	}
}
