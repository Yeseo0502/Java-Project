package JavaProject;
import javax.swing.JOptionPane;

class Player {
	private String name;
	private int score; //점수
	private int level; //레벨
	private int combo;
	private int heart; //하트(할 수 있는 기회횟수)
	private int levelCount;
	
	private int rank; //랭킹
	private boolean gameOver;
	
	Player(String name,int score){
		this.name=name;
		this.score=score;
		this.level=1; //기본시작 1레벨
		this.heart=3; //심장은 3개
		this.combo=0;
		this.gameOver=false;
	}
	
	String getName() {
		return name;
	}
	int getScore() {
		return score;
	}
	int getLevel() {
		return level;
	}
	int getHeart() {
		return heart;
	}
	int getLevelCount() {
		return levelCount;
	}
	int getCombo() {
		return combo;
	}
	void setName(String name) {
		this.name=name;
	}
	void setScore(int score) {
		this.score=score;
	}
	void setLevel(int level) {
		this.level=level;
	}
	void setCombo(int combo) {
		this.combo=combo;
	}
	void setLevelCount(int levelCount) {
		this.levelCount=levelCount;
	}
	void setHearts(int heart) {
		this.heart=heart;
	}
	String prHeart() {
		if(heart==2) {
			return "❤❤💔";
		}
		else if(heart==1) {
			return "❤💔💔";
		}
		else if(heart==3) {
			return "❤❤❤";
		}
		return "💔💔💔";
	}
	boolean isGameOver() {
		return heart <= 0;
	}
	void setScore() {
		score++;
	}
	void updateScore(boolean success) {
        if (success) {
            combo++;
            score += combo;  // 콤보가 높을수록 점수가 더 많이 올라감
            levelCount++;
        }
        else {
        	combo=0;
        	heart--;
        }
	}
	private void levelUp() {
		level++;
		levelCount=0;
		heart=3;
		JOptionPane.showMessageDialog(null, "레벨 상승! 현재 레벨 : "+level);
	}
	void resetCombo() {
        combo = 0;
        System.out.println("콤보가 초기화되었습니다.");
    }
	
	boolean useHeart() {//하트사용
		if(heart>0) {
			heart--;
			return true; //기회(하트)가 있으면 사용
		}
		return false; //기회 없으면 사용 불가
	}
	void nowprint() {
		System.out.println(name+"님 현재상태\n점수 :"+score+"  회피횟수 :"+levelCount);
		//"  레벨 :"+level+"\n남은 하트 :"+prHeart()
	}
	String checkLevelUp() {
		if(level==1 && levelCount>=3) {
			level++;
			levelCount=0;
			heart=3;
			return "레벨업! 현재 레벨 : "+level;
		}
		else if(level ==2 && levelCount>=5) {
			level++;
			levelCount=0;
			heart=3;
			return "레벨업! 현재레벨: "+level;
		}
		return "";
	}
	void reset() { //초기화하면 이 상태로 변환!
		this.score=0;
		this.level=1;
		this.combo=0;
		this.heart=3;
		this.levelCount=0;
	}
}
