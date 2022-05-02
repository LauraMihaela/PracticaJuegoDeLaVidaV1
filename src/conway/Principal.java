package conway;



public class Principal {

	public static void main(String[] args) {
		JuegoVida juegoVida = new JuegoVida();
	        try{
	        	juegoVida.runGame();
	        } catch (Exception e){
	            System.out.println(e.getMessage());
	        }

	}

}
