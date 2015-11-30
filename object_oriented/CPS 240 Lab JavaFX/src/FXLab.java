import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.*;



public class FXLab extends Application{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
		launch(args);
		
	}

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		
		
		TextField tf1 = new TextField();		//call createScreen() method
		TextField tf2 = new TextField();
		VBox pane = new VBox(10, tf1, tf2);
		//test property binding
//		tf2.textProperty().bindBidirectional(tf1.textProperty());
		
		
		//test event handling
		tf1.setOnKeyReleased(e -> {
			
			tf2.setText(tf1.getText());
			
		});
		tf2.setOnKeyReleased(e -> {
			
			tf1.setText(tf2.getText());
			
		});
		
		Scene scene = new Scene(pane);

		
	    stage.setResizable(false);												//set resizing to false
	    stage.setScene(scene);				//setting scene with full functioning of screen and buttons
	    stage.show();	
	
	}

}
