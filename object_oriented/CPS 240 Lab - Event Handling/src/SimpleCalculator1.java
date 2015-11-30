import javax.swing.*;  // jFrame

public class SimpleCalculator1 extends JFrame{

	private JLabel;
	private JButton;
	private JTextField;

	public static void main(String args[]){
	
		Calc1 calc = new Calc1();	
	
	}
	
	public Calc1(){
	
		panel = new JPanel();
		label = new JLabel();		
		button = new JButton("=");		text1 = new JTextField();
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
	class MyListener implements ActionListener{
	
		@Override
		public void actionPerformed(ActionEvent e){
		
			switch(operator){
				case "+":
					result = n1 + n2 + "";
					break;
				case "-":
					result = n1 - n2 + "";
					break;
				case "*":
					result = n1 * n2 + "";
					break;
				case "/":
					result = n1 / n2 + "";
					break;
				case "ˆ":
					result = Math.pow(n1, n2) + "";
					break;
				case "%":
					result = n1 % n2 + "";
					break;
			}
			label.setText(result);
		
		
		}
	
	
	}
}
