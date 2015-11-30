//John deGuise, CPS 240, Qi Liao, Homework 7 JavaFX Calculator, 11/25/15

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

// a simple JavaFX calculator.
public class Calculator extends Application {
	
  private static final String[][] buttonSymbols = {		//set buttonSymbols array for buttons
      { "1", "2", "3", "+" },
      { "4", "5", "6", "-" },
      { "7", "8", "9", "*" },
      { "C", "0", "=", "/" }
  };

  private final Map<String, Button> keyPressMap = new HashMap<>();

  private DoubleProperty stackValue = new SimpleDoubleProperty();
  private DoubleProperty value = new SimpleDoubleProperty();			//instantiating objects as doubleproperty to preserve Op logic on stack/input_vals
  																		
  private enum Op { NOOP, ADD, SUBTRACT, MULTIPLY, DIVIDE }				//instantiate enumeration of Op (=, +, -, *, /) 

  private Op curOp   = Op.NOOP;		//can be op, add, sub, mult, or div
  private Op stackOp = Op.NOOP;		//instantiating O

  public static void main(String[] args) { 
	  
	  launch(args); 
	  
  }

  @Override 
  public void start(Stage stage) {
    
	final TextField screen  = makeScreen();		//call createScreen() method
    final TilePane  buttons = formatButtons();		//call formatButtons() method

    stage.setTitle("CPS 240 Calculator");							//set stage/calculator title
    stage.setResizable(false);												//set resizing to false
    stage.setScene(new Scene(createLayout(screen, buttons)));				//setting scene with full functioning of screen and buttons
    stage.show();															//set stage to visible
    																		//void return type
  }

  private VBox createLayout(TextField screen, TilePane buttons) {
    
	final VBox layout = new VBox(20);										//VBox as a vertical column with 20 spacing
    layout.setAlignment(Pos.CENTER);										//set the alignment to center
    layout.setStyle("-fx-background-color: grey; -fx-padding: 20; -fx-font-size: 20;");
    layout.getChildren().setAll(screen, buttons);							//getChildren() and setAll 'panes' in the column to their respective screen or button
    
    handleKeyPress(layout);												//call methods for event handling on the VBox pieces, sets up logic for event handles
    screen.prefWidthProperty().bind(buttons.widthProperty());				//binds the event handling for the buttons to the output of the screen
    
    return layout;															//returns VBox layout
  
  }

  private void handleKeyPress(VBox layout) {								/*inputs VBox layout, adds eventfilters for buttons based on if button is pressed
	  																			if so, creates a new Button "activated" that has the information for button when pressed
	  																			if that information is not null, update it to the button handler with .fire()*/
    layout.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
    	
      @Override
      public void handle(KeyEvent keyEvent) {
        Button activated = keyPressMap.get(keyEvent.getText());
        if (activated != null) {
          activated.fire();
        }
      }
    
    });																	//void return type
  }	

  private TextField makeScreen() {
    
	final TextField screen = new TextField();		//instantiate screen for output
    
    screen.setAlignment(Pos.CENTER_RIGHT);			//align screen
    screen.setEditable(false);						//set editing condition to false
    screen.textProperty().bind(Bindings.format("%.0f", value));		//bind values of text that come in to 
    
    return screen;									//return TextField screen
  }

  private TilePane formatButtons() {
    
	TilePane buttons = new TilePane();				//instantiate buttons based on the TilePane
    buttons.setVgap(7);								//set spacing for both HGap and VGap to 7
    buttons.setHgap(7);
    
    buttons.setPrefColumns(buttonSymbols[0].length);		//set preferred columns = to buttonSymbols[0].length (should yield 1)
    
    for (String[] r: buttonSymbols) {					/*for each String array r in template: for each String in the array r, 
    												get the children of the buttons, add button made with createButton(s)*/
      
      for (String s: r) {
        
    	  buttons.getChildren().add(createButton(s));
     
      }
    }
    
    return buttons;									//return TilePane type formatted
  }

  
  private Button createButton(final String s) {		//method for creating all buttons - mapping the button's symbol to its method of functionality
    
	Button button = valueStandardButton(s);			//make a new standard button out of input string S

    if (s.matches("[0-9]")) {						//if s matches a number, makeNumericButton
      valueNumericButton(s, button);
    } 
    else {											//else it doesn't, instantiate final based on ObjectProperty<Op> as determineOp() method
      final ObjectProperty<Op> triggerOp = determineOperand(s);
      
      if (triggerOp.get() != Op.NOOP) {				//if the triggerOperation != equal, logically it's an OperandButton (based button and trigOp), +-*/
        valueOperandButton(button, triggerOp);
      } 
      else if ("C".equals(s)) {						//else if it matches the C, it's a clear button
        valueClearButton(button);
      } 
      else if ("=".equals(s)) {						//else if it matches the =, it's an equals button
        valueEqualsButton(button);
      }
    
    }

    return button;									//return Button type
  }

  private ObjectProperty<Op> determineOperand(String s) {					//determine which operation button it is (+ - * /)
    
	final ObjectProperty<Op> triggerOp = new SimpleObjectProperty<Op>(Op.NOOP);		//set final based on ObjectProperty<Op> to new ObjectProperty of Op.NOOP
    switch (s) {
      case "+": triggerOp.set(Op.ADD);      break;							//switch statement for whatever property of the 4 is identified
      case "-": triggerOp.set(Op.SUBTRACT); break;
      case "*": triggerOp.set(Op.MULTIPLY); break;
      case "/": triggerOp.set(Op.DIVIDE);   break;
    }
    return triggerOp;														//return the operand specified as ObjectProperty<Op>
  }

  private void valueOperandButton(Button button, final ObjectProperty<Op> triggerOp) {		//create the operations buttons function
    
	button.setOnAction(new EventHandler<ActionEvent>() {							//button sets new event handler																		
																					//handle = the currentOperation, (triggerOp.get(), where triggerOp has our new operand
      @Override
      public void handle(ActionEvent actionEvent) {
        curOp = triggerOp.get();												//void return
      }
    });
  }

  private Button valueStandardButton(String s) {									//setting standard function for buttons
	  																			//adding them to our accelerator event handling
    Button button = new Button(s);
    																			//new button based on input string
    keyPressMap.put(s, button);
    button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);						//setting button's max size as what the max of the inputted number is
    
    return button;																//return Button type
  }

  private void valueNumericButton(String s, Button button) {			//method for handling creation of number buttons functionality
    
	button.setOnAction(new EventHandler<ActionEvent>() {					//set event handlers for button calls
		
      @Override			
      public void handle(ActionEvent actionEvent) {							
        
    	if (curOp == Op.NOOP) {												//if operation is a Number Operation
          value.set((value.get()) * 10 + Double.parseDouble(s));				//set value as value * 10, plus s parsed as Int
        } 
    	else {																//else we have a value to save for an operation, so push it to the stack
          stackValue.set(value.get());
          value.set(Double.parseDouble(s));									//set a new value as an Integer parse of the string argument that came in
          stackOp = curOp;													//stackOperation re-set to currentOperation
          curOp = Op.NOOP;													//currentOperation is the new pushed Operation
        
    	}
      }
    });
  }

  private void valueClearButton(Button button) {							//method for clearButton functionality
    
	button.setOnAction(new EventHandler<ActionEvent>() {
      
      @Override
      public void handle(ActionEvent actionEvent) {		//event handle sets a 0
        value.set(0);
      }
    
    });
  }

  private void valueEqualsButton(Button button) {				//method for equalButton functionality
    
	button.setOnAction(new EventHandler<ActionEvent>() {
      
      @Override
      public void handle(ActionEvent actionEvent) {		//event handle uses a switch statement based on stack operations
    	  												//determines what operation to perform on stacked values
        switch (stackOp) {
          case ADD:      value.set(stackValue.get() + value.get()); break;
          case SUBTRACT: value.set(stackValue.get() - value.get()); break;
          case MULTIPLY: value.set(stackValue.get() * value.get()); break;
          case DIVIDE:   value.set(stackValue.get() / value.get()); break;
          case NOOP:     break;
        }
      }
    });
  }
}