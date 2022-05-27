import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class main2 extends JFrame{
	JLabel la1 = new JLabel(""); // 윗 라벨
	JLabel la2 = new JLabel("0"); // 아래 라벨
	boolean change = false;
	boolean freeze = false;
	
	public main2() {
		setTitle("계산기");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container c = getContentPane();
		
		UpPanel p1 = new UpPanel();
		DownPanel p2 = new DownPanel();
		
		c.add(p1, BorderLayout.EAST);
		c.add(p2, BorderLayout.SOUTH);
		
		setSize(350, 600);
		setVisible(true);
	}
	
	class UpPanel extends JPanel{
		public UpPanel() {
			setLayout(new GridLayout(2, 1));
			
			la1.setFont(new Font("맑은 고딕", 0, 30));
			la1.setHorizontalAlignment(SwingConstants.RIGHT);
			la2.setFont(new Font("맑은 고딕", Font.BOLD, 40));
			la2.setHorizontalAlignment(SwingConstants.RIGHT);
			
			add(la1);
			add(la2);
			
		}
	}
	
	class DownPanel extends JPanel{
		public DownPanel() {
			JButton[] btn = new JButton[20];
			btn[0] = new JButton("<-");
			btn[1] = new JButton("C");
			btn[2] = new JButton("^2");
			btn[3] = new JButton("+");
			
			btn[4] = new JButton("1");
			btn[5] = new JButton("2");
			btn[6] = new JButton("3");
			btn[7] = new JButton("-");
			
			btn[8] = new JButton("4");
			btn[9] = new JButton("5");
			btn[10] = new JButton("6");
			btn[11] = new JButton("X");
			
			btn[12] = new JButton("7");
			btn[13] = new JButton("8");
			btn[14] = new JButton("9");
			btn[15] = new JButton("%");
			
			btn[16] = new JButton("");
			btn[17] = new JButton("0");
			btn[18] = new JButton(".");
			btn[19] = new JButton("=");
			
			setLayout(new GridLayout(6, 4, 2, 2));
			
			for (int i = 0; i < btn.length; i++) {
				btn[i].setPreferredSize(new Dimension(0, 65));
				btn[i].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						findFreeze(la2);
					}
				});
				
				if (i >= 4 & i <= 18 & i != 7 & i != 11 & i != 15 & i != 16) {
					btn[i].setBackground(Color.white);	
					btn[i].addMouseListener(new MouseAdapter() {
					public void mousePressed(MouseEvent e) {
						if (freeze == false) {
						JButton b = (JButton) e.getSource();
						if (change == false) {
							if (la2.getText() == "0")
								la2.setText(b.getText());
							else
								la2.setText(la2.getText() + b.getText());
						}
						else {
							la2.setText(b.getText());
							change = false;
						}
						}
					}
					});
				}
				else if (i != 19) {
					btn[i].setBackground(Color.LIGHT_GRAY);
					btn[i].addMouseListener(new MouseAdapter() {
							public void mousePressed(MouseEvent e) {
								JButton b = (JButton) e.getSource();
								if (b.getText() == "C") {
									la1.setText("");
									la2.setText("0");
									change = false;
									freeze = false;
								}
								else if (b.getText() == "<-") {
									if (change == false) { // change가 false면 지우기 가능
										if (la2.getText().length() == 1)
											la2.setText("0");
										else
											la2.setText(la2.getText().substring(0, la2.getText().length() - 1));
									}
								}
								else if (b.getText() == "^2") {
									if (la1.getText().startsWith("sqr") & change == true)
										la1.setText("sqr(" + la1.getText() + ")");
									else
										la1.setText("sqr(" + la2.getText() + ")");
									setLabel(la2, Math.pow(Double.parseDouble(la2.getText()), 2));
									change = true;
								}
								else if (b.getText() == "+") {
									if (la1.getText().endsWith("-") | la1.getText().endsWith("x") | la1.getText().endsWith("/"))
										if (change == false) {
											setLabel(la2, acc(la1.getText(), la2.getText()));
											la1.setText(la2.getText() + " +");
										}
										else
											la1.setText(la1.getText().substring(0, la1.getText().length() - 1) + "+");
									else if (la1.getText().endsWith("=")) 
										la1.setText(la2.getText() + " +");					
									else if (la1.getText().endsWith("+")) {
										if (change == false) {
											setLabel(la1, Double.parseDouble(la1.getText().substring(0, la1.getText().length() - 1)) + Double.parseDouble(la2.getText()));
											la1.setText(la1.getText() + " +");
											la2.setText(la1.getText().substring(0, la1.getText().length() - 2));
											change = true;
										}
									}
									else
										la1.setText(la2.getText() + " +");
										change = true;
								}
								else if (b.getText() == "-") {
									if (la1.getText().endsWith("+") | la1.getText().endsWith("x") | la1.getText().endsWith("/"))
										if (change == false) {
											setLabel(la2, acc(la1.getText(), la2.getText()));
											la1.setText(la2.getText() + " -");
										}
										else
											la1.setText(la1.getText().substring(0, la1.getText().length() - 1) + "-");
									else if (la1.getText().endsWith("=")) 
										la1.setText(la2.getText() + " -");	
									else if (la1.getText().endsWith("-")) {
										if (change == false) {
											setLabel(la1, Double.parseDouble(la1.getText().substring(0, la1.getText().length() - 1)) - Double.parseDouble(la2.getText()));
											la1.setText(la1.getText() + " -");
											la2.setText(la1.getText().substring(0, la1.getText().length() - 2));
											change = true;
										}
									}
									else
										la1.setText(la2.getText() + " -");
										change = true;
								}
								else if (b.getText() == "X") {
									if (la1.getText().endsWith("+") | la1.getText().endsWith("-") | la1.getText().endsWith("/"))
										if (change == false) {
											setLabel(la2, acc(la1.getText(), la2.getText()));
											la1.setText(la2.getText() + " x");
										}
										else
											la1.setText(la1.getText().substring(0, la1.getText().length() - 1) + "x");
									else if (la1.getText().endsWith("=")) 
										la1.setText(la2.getText() + " x");	
									else if (la1.getText().endsWith("x")) {
										if (change == false) {
											setLabel(la1, Double.parseDouble(la1.getText().substring(0, la1.getText().length() - 1)) * Double.parseDouble(la2.getText()));
											la1.setText(la1.getText() + " x");
											la2.setText(la1.getText().substring(0, la1.getText().length() - 2));
											change = true;
										}
									}
									else
										la1.setText(la2.getText() + " x");
										change = true;
								}
								else if (b.getText() == "%") {
									if (la1.getText().endsWith("+") | la1.getText().endsWith("-") | la1.getText().endsWith("x"))
										if (change == false) {
											setLabel(la2, acc(la1.getText(), la2.getText()));
											la1.setText(la2.getText() + " /");
										}
										else
											la1.setText(la1.getText().substring(0, la1.getText().length() - 1) + "/");
									else if (la1.getText().endsWith("=")) 
										la1.setText(la2.getText() + " /");	
									else if (la1.getText().endsWith("/")) {
										if (change == false) {
											setLabel(la1, Double.parseDouble(la1.getText().substring(0, la1.getText().length() - 1)) / Double.parseDouble(la2.getText()));
											la1.setText(la1.getText() + " /");
											la2.setText(la1.getText().substring(0, la1.getText().length() - 2));
											change = true;
										}
									}
									else
										la1.setText(la2.getText() + " /");
										change = true;
								}
								
							}
								
						});
				}
				else {
					btn[i].setBackground(Color.CYAN);
					btn[i].addMouseListener(new MouseAdapter() {
						public void mousePressed(MouseEvent e) {
							String input = la2.getText();
							if (la1.getText().endsWith("+")) 
								setLabel(la2, Double.parseDouble(la1.getText().substring(0, la1.getText().length() - 1)) + Double.parseDouble(la2.getText()));
							else if (la1.getText().endsWith("-")) 
								setLabel(la2, Double.parseDouble(la1.getText().substring(0, la1.getText().length() - 1)) - Double.parseDouble(la2.getText()));			
							else if (la1.getText().endsWith("x")) 
								setLabel(la2, Double.parseDouble(la1.getText().substring(0, la1.getText().length() - 1)) * Double.parseDouble(la2.getText()));			
							else if (la1.getText().endsWith("/")) 
								setLabel(la2, Double.parseDouble(la1.getText().substring(0, la1.getText().length() - 1)) / Double.parseDouble(la2.getText()));			
							la1.setText(la1.getText() + " " + input + " =");
						}
					});
				}
				
				add(btn[i]);
			}
			
		}
	}
	
	double acc(String dis, String sor) {
		double one = Double.parseDouble(dis.substring(0, dis.length() - 1));
		double two = Double.parseDouble(sor);
		if (dis.endsWith("+")) 
			return one + two;
		else if (dis.endsWith("-"))
			return one - two;
		else if (dis.endsWith("x"))
			return one * two;
		else
			return one / two;
	}
	
	boolean findFreeze(JLabel la) {
		if (la.getText().length() >= 15) {
			la.setText("오버플로");
			return true;
		}
		return false;
	}
	
	void setLabel(JLabel la, double num) {
		if ((int)num == Math.ceil(num))
			la.setText(Integer.toString((int)num));
		else
			la.setText(Double.toString(Math.round(num * 100) / 100.0));
	}
	
	public static void main(String[] args) {
		new main2();
	}

}
