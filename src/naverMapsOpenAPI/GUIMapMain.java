package naverMapsOpenAPI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUIMapMain {
	JTextField address;
	JLabel resAddress, resX, resY, jibunAddress;
	JLabel imageLabel;
	public void initGUI() {
		JFrame frm = new JFrame("Map View");
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container c = frm.getContentPane();
		
		imageLabel = new JLabel("지도보기");
		
		JPanel northPan = new JPanel();
		JLabel addressLbl = new JLabel("주소입력");
		address = new JTextField(50);
		JButton btn = new JButton("클릭");
		northPan.add(addressLbl);
		northPan.add(address);
		northPan.add(btn);
		btn.addActionListener(new NaverMap(this));
		
		JPanel southPan = new JPanel();
		southPan.setLayout(new GridLayout(4, 1));
		resAddress = new JLabel("도로명");
		jibunAddress = new JLabel("지번주소");
		resX = new JLabel("경도");
		resY = new JLabel("위도");
		southPan.add(resAddress);
		southPan.add(jibunAddress);
		southPan.add(resX);
		southPan.add(resY);
		
		c.add(BorderLayout.NORTH, northPan);
		c.add(BorderLayout.CENTER, imageLabel);
		c.add(BorderLayout.SOUTH, southPan);
		frm.setSize(730, 660);
		frm.setVisible(true);
	}
	
	public static void main(String[] args) {
		new GUIMapMain().initGUI();
	}
}
