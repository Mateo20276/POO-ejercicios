package ar.edu.unlu.prendas;

public class Camisa extends Prendas{
	private boolean mangaLarga;
	
	public Camisa(double preciolista, double porcentajedeganancia, boolean mangalarga)throws Exception{
		super(preciolista, porcentajedeganancia);
		this.setMangaLarga(mangalarga);
		
	}

	private void setMangaLarga(boolean mangaLarga) {
		this.mangaLarga = mangaLarga;
		
	}
	
	public double calcularPrecioDeVenta() {
		double precioVT = super.calcularPrecioDeVenta();
		if (this.mangaLarga) {
			return 
		}
	}

}
