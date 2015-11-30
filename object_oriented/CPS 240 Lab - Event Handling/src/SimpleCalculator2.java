import java.swing.*;

public class SimpleCalculator2{


	public Calc2(){
	
			panel = new JPanel();
			label = new JLabel();		
			button = new JButton("=");			text1 = new JTextField();
			text2 = new JTextField();
			text3 = new JTextField();
		
		
			panel.setLayout(new GridLayout(1, 5))
			
			panel.add(text1);
			panel.add(text2);
			panel.add(text3);
		
			panel.add(button);	
		
			panel.add(label);
		
			button.addActionListener(new MyListener());	
			setTitle("CPS240 Lab Calculator");
			pack();
			setVisible();
	}

	public static void main(String[] args){
	
		Calc2 calc = new Calc2();
	
	}


}