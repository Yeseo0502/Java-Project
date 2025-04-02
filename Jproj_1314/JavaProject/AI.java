package JavaProject;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
class AI {
	//AI방향 1->왼쪽 2->가운데 3->오른쪽
	private int dir;
	private int count;
	AI(){
		setDir(); //Ai가 시작할 떄 방향을 랜덤으로 설정
		this.count=3;
	}
	void setDir() {
		Random ra=new Random();
		this.dir=ra.nextInt(3)+1; // 0~2 사이의 랜덤 값 + 1 -> 1~3 사이의 값
	}
	int getDir() { //방향private
		return this.dir;
	}
	int getCount() {//목표 회피 횟수 반환
		return this.count;
	}
//	void showDir() { 
//		String direction = switch(dir) {
//		case 1 -> "왼쪽";
//		case 2 -> "중앙";
//		case 3 -> "오른쪽";
//		default -> " ";
//		}; 
//	}
	String showDrawturn() { //AI방향을 쉽게 볼 수 있는 이미지 설정!
		switch(dir) {
		case 1 : 
			return "imgs/left.png";
		case 2 : 
			return "imgs/center.png";
		case 3: 
			return "imgs/right.png";
			default : 
				return "imgs/center.png";
		}
	}
	void setLevel(int level) {
		switch(level) {
			case 1 -> this.count=3;
			case 2 -> this.count=5;
			case 3 -> this.count=10;
			default -> this.count=3; //기본 1레벨값
		}System.out.println("레벨 "+level+"\n목표 "+count+"번 피하기");
	}
	boolean isLevelPass(int Levelcount) {
		return Levelcount>=count;
	}
}

