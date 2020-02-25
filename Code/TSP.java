/******************************************************************************
*  A Teaching GA					  Developed by Hal Stringer & Annie Wu, UCF
*  Version 2, January 18, 2004
*******************************************************************************/

import java.io.*;
import java.util.*;
import java.text.*;

public class TSP extends FitnessFunction{

/*******************************************************************************
*                            INSTANCE VARIABLES                                *
*******************************************************************************/


/*******************************************************************************
*                            STATIC VARIABLES                                  *
*******************************************************************************/


/*******************************************************************************
*                              CONSTRUCTORS                                    *
*******************************************************************************/

	public TSP(){
		name = "TSP Problem";
	}

/*******************************************************************************
*                                MEMBER METHODS                                *
*******************************************************************************/

public long dis(Point a, Point b) {
	long retval =  (long)Math.sqrt(Math.pow((a.x - b.x), 2) + Math.pow((a.x - b.x), 2));
	return retval;
}

public ArrayList<Integer> ordmap(TSPChromo X) {
	ArrayList<Integer> retval = new ArrayList<>();
	HashSet<Integer> used = new HashSet<Integer>();

	for (int i = 0; i < Parameters.geneSize; i++) {
		int j = X.chromo.get(i);
		for (; used.contains(j % Parameters.geneSize); j++);
		retval.add(j % Parameters.geneSize);
		used.add(j);
	}
	return retval;
}

public void doRawFitness(TSPChromo X){
	if (Parameters.ordflag) {
		ArrayList<Integer> temp = ordmap(X);
		for (int i : temp) {
			System.out.print(i + " ");
		}
		System.out.println();
		for (int i = 0; i < Parameters.geneSize - 1; i++) {
			X.rawFitness += dis(Search.cities.get(temp.get(i)), Search.cities.get(temp.get(i + 1)));
		}
		X.rawFitness += dis(Search.cities.get(temp.get(Parameters.geneSize - 1)), Search.cities.get(temp.get(0)));
	}
	else {
		for (int i = 0; i < Parameters.geneSize - 1; i++) {
			X.rawFitness += dis(Search.cities.get(X.chromo.get(i)), Search.cities.get(X.chromo.get(i + 1)));
		}
		X.rawFitness += dis(Search.cities.get(X.chromo.get(Parameters.geneSize - 1)), Search.cities.get(X.chromo.get(0)));

	}
	//System.out.println("fitness is "  + X.rawFitness);
}
//  PRINT OUT AN INDIVIDUAL GENE TO THE SUMMARY FILE *********************************

public void doPrintGenes(TSPChromo X, FileWriter output) throws java.io.IOException{
	System.out.println("Executing FF Gene Output");
}

/*******************************************************************************
*                             STATIC METHODS                                   *
*******************************************************************************/

}   // End of OneMax.java ******************************************************