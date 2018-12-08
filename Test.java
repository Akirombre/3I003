import java.lang.*;
import java.util.*;
import java.io.FileNotFoundException;
import java.io.File;

public class Test {

	static int k;
	static int s;
	static int[] v;

	static int algo;

	public static void main(String[] args) {

		
		try {
			Scanner scanner = new Scanner(new File("instance.txt"));
			s = scanner.nextInt();
			k = scanner.nextInt();
			v = new int[k+1];
			v[0] = 0;
			for(int i = 1; i <= k; i++){
				v[i] = scanner.nextInt();
			}
		} catch (FileNotFoundException e) {
			System.out.println("Fichier de données incorrect.");
			return;	
		}

		try {
			algo = Integer.parseInt(args[0]);

			switch(algo) {
				case 1:
					System.out.println("Le résultat de rechercheExhaustive est " + rechercheExhaustive(k,v,s));
					break;
				case 2:
					System.out.println("Le résultat de algoProgDyn est " + algoProgDyn(k,v,s));
					break;
				case 3:
					System.out.println("Le résultat de algoGlouton est " + algoGloutonR(k,v,s));
					break;
				case 4:
					System.out.println("Le résultat de testGloutonCompatible est " + testGloutonCompatible(k,v));
					break;
				default:
				System.out.println("Algorithme non sélectionné.");
			}

		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("Veuillez indiquer l'algorithme voulu SVP :");
			System.err.println("Numéro 1 pour rechercheExhaustive");
			System.err.println("Numéro 2 pour algoProgDyn");
			System.err.println("Numéro 3 pour algoGlouton");
			System.err.println("Numéro 4 pour testGloutonCompatible");
			return;
		}

	}

	// Algorithme I

	static int rechercheExhaustive(int k,int[] tab,int s) {

		int NbCont;
		int x;

		if(s<0) {
			return 9999999;
		}
		else {
			if(s==0) {
				return 0;
			} else {
				NbCont = s;
				for(int i=1;i<=k;i++) {
					x = rechercheExhaustive(k,tab,s-tab[i]);
					if((x+1) < NbCont) {
						NbCont = (x+1);
					} // fin si
				} // fin pour
			}
			return NbCont;
		}
	}

	// Algorithme III

	static int algoGloutonR(int k,int[] tab,int s) {
		if (k==0) {
			return 0;
		}
		else {
			int res = (int)Math.floor(s/tab[k]);
			return res+algoGloutonR(k-1,tab,(s-res*tab[k]));
		}
	}

	// Algorithme II

	
	static int algoProgDyn(int k, int[] tab, int s) {

		int a=0;
		int b=0;
		int[][] tabM = new int[s+1][k+1];

		for(int i=0;i <= k;i++) {
			for(int j=0;j<=s;j++) {
				if(j==0) {
					tabM[j][i]=0;
				}else{
					if(i==0) {
						tabM[j][i]=99999;
					}else {
						if(j - tab[i] >= 0){
							a = 1+tabM[j-tab[i]][i];
						}else{
							a = 99999;
						}
						if(i >= 1){
							b = tabM[j][i-1];
						}else{
							b = 99999;
						}
						tabM[j][i] = Math.min(a,b);
					
					}

				}		
			}	
		}	
		System.out.println(tabM[s][k]);
		return tabM[s][k];
	}

	// Algorithme TestGloutonCompatible

	static boolean testGloutonCompatible(int k,int[] tab) {

		if(k>=3) {
			for(int s=(tab[3]+2);s<(tab[k-1]+v[k]-1);s++) {
				for(int j=1;j<=k;j++) {
					if(tab[j]<=s && (algoGloutonR(k,tab,s)>1+algoGloutonR(k,tab,s-tab[j]))) {
						return false;
					}
				}
			}
		}
		return true;
	}
}
