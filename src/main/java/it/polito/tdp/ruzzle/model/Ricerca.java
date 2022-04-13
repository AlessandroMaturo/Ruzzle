package it.polito.tdp.ruzzle.model;

import java.util.ArrayList;
import java.util.List;

/* In questa classe andrò a mettere i metodi ricorsivi */

public class Ricerca {

	/* Dobbiamo ricevere anche la Board perchà siamo fuori dal Model 
	 * Decidiamo di ritornare una lista di posizioni così se c'è possiamo illuminarla sulla tastiera 
	 * Questo è il metodo che sistemala ricorsione e poi la lancia, non è ancora quello ricorsivo
	 * Qua dentro controlliamo ce c'è la lettera*/
	
	public List<Pos> trovaParola(String parola, Board board) {
		
		for(Pos p: board.getPositions()) {
			
			/* la lettera in pos è == alla prima lettera di parola 
			 * Devi aggiungere .get() alla fine perchè devi passareda una String Porperties a una Stringa
			 * .charAt(0) lo metti sulla board che tanto sarà una solo carattere
			 * se entri nell if potrai fare partire la ricorsione*/
			
			if(board.getCellValueProperty(p).get().charAt(0)==parola.charAt(0)) {
				List<Pos> parziale = new ArrayList<Pos>();
				parziale.add(p);
				
				/* qua livello sarà 1 perchè gli metti già la prima lettera
				 * Se lasci solo return parziale lì magari ti trova le prime due lettere e poi stop ma non una lista nulla quindi
				
					cerca(parola, parziale, 1, board);
					return parziale;*/
				
				if(cerca(parola, parziale, 1, board))
					return parziale;
				
			}
		}
		
		return null;
		
	}
	
	/* Ci basta la lista di posizione come parziale tanto la parola complet la sai già */
	
	public boolean cerca(String parola, List<Pos> percorso, int livello, Board board) {
		
		/* casi terminali */
		
		if(livello == parola.length()) {
			return true;
		}
		
		Pos ultima = percorso.get(percorso.size()-1);
		List<Pos> adiacenti = board.getAdjacencies(ultima);
		
		for(Pos a: adiacenti) {
			
			/* Dobbiamo anche controllare di non averla ancora usata quella lettera -> Reggola di RUZZLE
			 * Ricordati che per fare funzionare contains devi mettere hashCode ed equals */
			
			if(board.getCellValueProperty(a).get().charAt(0)==parola.charAt(livello) && !percorso.contains(a)) {
				percorso.add(a);
				cerca(parola, percorso, livello+1, board);
				
				/* Ricordati che fare la remove con delle liste è diverso rispetto che con un set 
				 * Aggiungiamo una ottimizzazione cioè se trovi la parola eviti di fare bascktracking
				 * cioè sarebbe questo if*/
				
				if(cerca(parola, percorso, livello+1, board))
					return true;
				
				percorso.remove(percorso.size()-1);
				
			}
			
		}
			
		/* Ritorno false perchè avrei visitato tutte le adicenti senza trovare le lettere */
		
		return false;
		
	}
	
}
