package ar.edu.unlu.baraja;

import java.io.Serializable;

public interface ICarta extends Serializable{
	public int getNumero();
	
	public Palo getPalo();

	public String getPaloStr();
	
	public void setJodete0(boolean res);
	
	public boolean getJodete0();
}
