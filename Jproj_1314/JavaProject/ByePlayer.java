package JavaProject;

import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ByePlayer extends JFrame{
	private JTextField usernameField;
	private JPasswordField passField;
	ByePlayer(){
		setTitle("회원탈퇴");
		setSize(300,200);
		setLayout(new GridLayout(3,2,10,10));
		setLocationRelativeTo(null); //화면 가운데로 띄우기
		ImageIcon icon=new ImageIcon("imgs/imgIcon.jpg");
		setIconImage(icon.getImage());
		add(new JLabel("사용자명 입력 : "));
		usernameField=new JTextField();
		add(usernameField);
		add(new JLabel("사용자 비밀번호 : "));
		passField=new JPasswordField();
		add(passField);
		
		JButton deleteJB=new JButton("완료");
		add(deleteJB);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image cursorimage=tk.getImage("imgs/mouse01.png");
		Point point=new Point(20,20);
		Cursor cursor=tk.createCustomCursor(cursorimage, point, "haha");
		setCursor(cursor);
		deleteJB.addActionListener(e -> {
			String username=usernameField.getText();
			String password = new String(passField.getPassword());
			if(deleteAccount(username,password)) {
				JOptionPane.showMessageDialog(this, "회원 탈퇴가 완료되었습니다.");
				dispose();
			}
			else {
				JOptionPane.showMessageDialog(this, "회원정보가 틀립니다. 다시 입력해주세요!");
			}
		});
	}
	private boolean deleteAccount(String username,String password) {
		try {
			File inputFile = new File("users.txt");
			File tempFile = new File("gameSave.txt");
			
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
			String line;
			boolean deleted= false;
			while((line = reader.readLine()) != null) {
				String[] credentials = line.split(",");
				if(credentials[0].equals(username)) {
					deleted=true;
				}
				else {
					writer.write(line);
					writer.newLine();
				}
			}
			reader.close();
			writer.close();
			if(deleted) {
				//기존 파일 삭제 후 임시 원본 파일처리의 파일로 변경함 
				if(inputFile.delete()) {
					tempFile.renameTo(inputFile);
				}
				return true;
			}
			else {
				tempFile.delete();
				return false;
			}
		}catch(IOException e) {
			JOptionPane.showMessageDialog(this, "파일 처리 실패");
		}
		return false;
	}
}

