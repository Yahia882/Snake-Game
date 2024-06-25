
import javax.swing.JFrame;

public class GameFrame extends JFrame {
	GamePanel Game ;
	
	
	public GameFrame() {
		
		this.setSize(600,600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		Game= new GamePanel();
		this.add(Game);
		this.setTitle("Snake");
		
		
		
		
		this.setResizable(false);
		
		
		
		this.pack();
		
		
		
		
		this.setVisible(true);
		
		this.setLocationRelativeTo(null);
		
		
		}
	
		
	}
	 

		
	


