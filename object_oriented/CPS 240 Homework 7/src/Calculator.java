//John deGuise, Calculator.java, CPS 240, 11/13/15 5PM

//MAKE SURE YOU CLEAR THE CALCULATOR BEFORE TESTING A DIFFERENT CHAIN OF COMMANDS
//IT'S EASY FOR VALUES TO ACCIDENTALLY CARRY OVER AND PRODUCE LOGIC ERRORS.  THANK YOU!



import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Calculator extends JFrame implements ActionListener{
	
	JPanel[] row = new JPanel[5];	
	private JButton button;
	
	//for clearing calculator
	double variable1 = 0;
	double variable2 = 0;
	char operand = ' ';
	
	//display dimension initialization
	int dimW = 300;
	int dimH = 35;
	Dimension displayDimension = new Dimension(dimW, dimH);
	JTextArea display = new JTextArea(1, 20);

	//math functions
	boolean[] function = new boolean[4];
	
	//booleans for actionListeners
	boolean isActive;
	boolean resultShown;
	
	
	double[] temporary = {0, 0};
	Font font = new Font("Times new Roman", Font.BOLD, 14);
	
	Calculator(){
		//set size, title, and resizable = false
		setSize(450, 450);
		setTitle("CPS 240 Calculator");
		setResizable(false);

		
		//center window, exit program on close
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//initialize grid for calculator and set
		GridLayout grid = new GridLayout(5, 5);
		setLayout(grid);
		
		//initialize data structure for holding math functions
		for(int i = 0; i < 4; i++)
			function[i] = false;
		
		
		FlowLayout f1 = new FlowLayout(FlowLayout.CENTER);
		FlowLayout f2 = new FlowLayout(FlowLayout.CENTER, 20, 20);
		
		//creating panels for buttons
		for(int i = 0; i < 5; i++){
			row[i] = new JPanel();
		}
		
		row[0].setLayout(f1);
		for(int i = 1; i < 5; i++){
			row[i].setLayout(f2);
		}
		
		row[0].add(display); //add display row
		add(row[0]);
		
		//16 action listeners for the 0-9 + - / * C and = buttons
		JButton btn1 = new JButton("1");
		btn1.addActionListener(new ActionListener() { 		

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(isActive){ // 1
					display.setText(" ");
					isActive = false;
				}
				display.setText(display.getText() + "1");
				
			}
			
			
		});
		row[1].add(btn1); 

		
		JButton btn2 = new JButton("2");
		btn2.addActionListener(new ActionListener() {	

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(isActive){ // 1
					display.setText(" ");
					isActive = false;
				}
				display.setText(display.getText() + "2");
			}
			
			
		});
		row[1].add(btn2);

		
		JButton btn3 = new JButton("3");
		btn3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(isActive){ // 1
					display.setText(" ");
					isActive = false;
				}
				display.setText(display.getText() + "3");
			}
			
			
		});
		row[1].add(btn3);

		
		JButton plus = new JButton("+");
		plus.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				equalsFunction();
				if (variable1 != 0) {
					if (checkIfMultiple()) {
						variable1 = variable1 + variable2;
						variable2 = 0;
					} else
						variable2 = Double.parseDouble(display.getText());
				} else {
					variable1 = Double.parseDouble(display.getText());
				}
				operand = '+';
				isActive = true;
				resultShown = false;
			}
			
			
		});
		row[1].add(plus);		
		add(row[1]);
		
		JButton btn4 = new JButton("4");

		btn4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(isActive){ // 1
					display.setText(" ");
					isActive = false;
				}
				display.setText(display.getText() + "4");
			}
			
			
		});
		row[2].add(btn4);

		
		JButton btn5 = new JButton("5");
		btn5.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(isActive){ // 1
					display.setText(" ");
					isActive = false;
				}
				display.setText(display.getText() + "5");
			}
			
			
		});
		row[2].add(btn5);

		
		JButton btn6 = new JButton("6");
		btn6.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(isActive){ // 1
					display.setText(" ");
					isActive = false;
				}
				display.setText(display.getText() + "6");
			}
			
			
		});
		row[2].add(btn6);

		
		JButton minus = new JButton("-");
		minus.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				equalsFunction();
				if (variable1 != 0) {
					if (checkIfMultiple()) {
						variable1 = variable1 - variable2;
					} else
						variable2 = Double.parseDouble(display.getText());
				} else {
					variable1 = Double.parseDouble(display.getText());
				}
				operand = '-';
				isActive = true;
				resultShown = false;
			}
			
			
		});
		row[2].add(minus);

		add(row[2]);
		
		JButton btn7 = new JButton("7");
		btn7.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(isActive){ // 1
					display.setText(" ");
					isActive = false;
				}
				display.setText(display.getText() + "7");
			}
			
			
		});
		row[3].add(btn7);

		
		JButton btn8 = new JButton("8");
		btn8.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(isActive){ // 1
					display.setText(" ");
					isActive = false;
				}
				display.setText(display.getText() + "8");
			}
			
			
		});
		row[3].add(btn8);

		
		JButton btn9 = new JButton("9");
		btn9.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(isActive){ // 1
					display.setText(" ");
					isActive = false;
				}
				display.setText(display.getText() + "9");
			}
			
			
		});
		row[3].add(btn9);

		
		JButton mult = new JButton("*");
		mult.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				equalsFunction();
				if (variable1 != 0) {
					if (checkIfMultiple()) {
						variable1 = variable1 * variable2;
					} else
						variable2 = Double.parseDouble(display.getText());
				} else {
					variable1 = Double.parseDouble(display.getText());
				}
				operand = '*';
				isActive = true;
				resultShown = false;
			}
			
			
		});
		row[3].add(mult);

		add(row[3]);
		
		
		JButton c = new JButton("C");
		c.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				variable1 = 0;
				variable2 = 0;
				operand = ' ';
				display.setText("");
				isActive = false;

			}
			
			
		});
		row[4].add(c);

		
		JButton btn0 = new JButton("0");
		btn0.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(isActive){ // 1
					display.setText(" ");
					isActive = false;
				}
				display.setText(display.getText() + "0");
			}
			
			
		});
		row[4].add(btn0);

		
		JButton equals = new JButton("=");
		equals.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
					equalsFunction();
				
			}
			
			
		});
		row[4].add(equals);

		
		JButton div = new JButton("/");
		div.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				equalsFunction();
				if (variable1 != 0) {
					if (checkIfMultiple()) {
						variable1 = variable1 / variable2;
					} else
						variable2 = Double.parseDouble(display.getText());
				} else {
					variable1 = Double.parseDouble(display.getText());
				}
				operand = '/';
				isActive = true;
				resultShown = false;
			}
			
			
		});
		row[4].add(div);

		add(row[4]);

		//set display font and stop editing
		display.setFont(font);
		display.setEditable(false);
		
		//set component orientation and size
		display.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		display.setPreferredSize(displayDimension);
		
		//allow window to be seen
		setVisible(true);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Calculator c = new Calculator();
	}
	
	public void equalsFunction(){
		if (!checkForWormholes()) { //if not divide by 0
			if (!resultShown) { //if result not shown
				variable2 = Double.parseDouble(display.getText());
				double result = 0;
				if (operand == '+') 
					result = variable1 + variable2;
				if (operand == '-')
					result = variable1 - variable2;
				if (operand == '/')
					result = variable1 / variable2;
				if (operand == '*')
					result = variable1 * variable2;

				if(checkIfMultiple()){
					display.setText(Double.toString(result));
					variable1 = Double.parseDouble(display.getText());
				}
				isActive = true;
				resultShown = true;
			}
		}
		else{
			display.setText("Err. divide by 0");
			resultShown = true;
			isActive = true;
		}
		variable2 = 0;
	}
	
	//checks if dividing by zero
	public boolean checkForWormholes(){
		if(variable2 == 0 && operand  == '/')
			return true;
		else
			return false;
	}
	
	//checks if input is to be # + # + #...
	public boolean checkIfMultiple() {
		if (variable1 != 0 && variable2 != 0) {
			return true;
		} else
			return false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
