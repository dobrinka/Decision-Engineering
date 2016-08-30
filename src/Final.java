import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class Final {
	
	private static int[][] data = new int[200][12];
	private static float[][] prefmat = new float[200][200];
	private static float[] flow = new float[200];
	private static float[] flowpositive = new float[200];
	private static float[] flownegative = new float[200];
	private static int n = 200;
	static float[]  w = new float[]{0.05f, 0.05f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.05f, 0.05f};
	int element;

	public static void main(String[] args) throws IOException 
		{
		
		// lit le fichier
		
			BufferedReader readFile = new BufferedReader(new FileReader("data.txt"));
			
				for(int i = 0; i < 200; i++){

	                try{
		                String myline = readFile.readLine();
		                StringTokenizer Tok = new StringTokenizer(myline);
	                    for(int k = 0; k < 12; k++){
	                    	data[i][k] = (int) Integer.parseInt(Tok.nextElement().toString());
	                    	
	                    }
	                }
	                catch(NumberFormatException e){
	                    System.out.println(e);
	                }
				}
				pref ();
				flow ();
				promethee();

		}
		//retourne le max des differences 
		public static int max(int k)
		{
			int ref = 0;
			for(int i = 0; i < n; i++){
				for(int j = 0; j < n; j++){
					if(i != j)
					{
						if(Math.abs(data[j][k] - data[i][k]) > ref)
						{
							ref = Math.abs(data[j][k] - data[i][k]);
						}
					}
				}
			}
			return ref;
		}
		// retourne le min des differences
		public static int min(int k)
		{
			int ref = 999999999;
			for(int i = 0; i < n; i++){
				for(int j = 0; j < n; j++){
					if(i != j)
					{
						if(Math.abs(data[j][k] - data[i][k]) < ref)
						{
							ref = Math.abs(data[j][k] - data[i][k]);
						}
					}
				}
			}
			return ref;
		}
		// retourne la somme des differences
		public static int sum(int k)
		{
			int s = 0;
			for(int i = 0; i < n; i++){
				for(int j = 0; j < n; j++){
					if(i != j)
					{
						s = s + Math.abs(data[j][k] - data[i][k]);
					}
				}
			}
			return s;
		}
		
		public static float p_k(int k)
		{
			return (sum(k)/n*(n-1))+(min(k)/2);
		}
		public static float q_k(int k)
		{
			return (sum(k)/n*(n-1))+(max(k)/2);
		}
		public static float funkPk (int k,float x)
		{
			if(x <= p_k(k))
			{
				return 0.0f;
			}
			else if((x > p_k(k))&&(x < q_k(k)))
			{
				return (x - p_k(k))/(p_k(k) - q_k(k));
			}
			else
			{
				return 1.0f;
			}
		}
		// calcule la matrice des preferences
		public static void pref ()
		{

			for (int i=0; i< n; i++){
				for (int j=0; j< n; j++){
					prefmat[i][j] = 0;
					for (int k=0; k< 12; k++){
						prefmat[i][j] = prefmat[i][j] + w[k]*funkPk(k, data[i][k] - data[j][k]);
					}
				}
			}
		}
		// calcule les trois "flows"
		public static void flow ()
		{
			for (int i=0; i< n; i++){
				// positive
				flowpositive[i] = 0;
				for (int j=0; j< n; j++){
					flowpositive[i] = flowpositive[i] + prefmat[i][j];
				}
				flowpositive[i] = flowpositive[i] / n;
				// negative
				flownegative[i] = 0;
				for (int j=0; j< n; j++){
					flownegative[i] = flownegative[i] + prefmat[j][i];
				}
				flownegative[i] = flownegative[i] / n;
				// flow
				flow[i] = flowpositive[i] - flownegative[i];
				}
			}
		//classer net low
		public static void net_flow ()
		{
			float element;
			for (int i=1; i<200; i++){
				for (int j=199; j>=i; j--){
					if (flow[j-1]>flow[j]){
						element= flow[j-1];
						flow[j-1]=flow[j];
						flow[j]= element;
					}
				}
			}
			}
        	// affiche le resultat
		public static void promethee ()
		{
			for (int i=0; i<n; i++){

						
						System.out.println(flow[i] + flowpositive[i] + flownegative[i]);
			}
		}
	}
	
