/******************************************************************************
*  A Teaching GA					  Developed by Hal Stringer & Annie Wu, UCF
*  Version 2, January 18, 2004
*******************************************************************************/

import java.io.*;
import java.util.*;
import java.text.*;

class FitnessFunction{

/*******************************************************************************
*                            INSTANCE VARIABLES                                *
*******************************************************************************/

	public String name;

/*******************************************************************************
*                            STATIC VARIABLES                                  *
*******************************************************************************/

/*******************************************************************************
*                              CONSTRUCTORS                                    *
*******************************************************************************/

	public FitnessFunction() {

		System.out.print("Setting up Fitness Function.....");

	}

/*******************************************************************************
*                                MEMBER METHODS                                *
*******************************************************************************/

//  COMPUTE A CHROMOSOME'S RAW FITNESS *************************************

	public long dis(Point a, Point b) {
		return Math.sqrt(Math.pow((a.x - b.x), 2) + Math.pow((a.x - b.x), w);
	}

	public TSPChromo ordmap(TSPChromo X) {
		TSPChromo retval = new TSPChromo();
		HashSet<Integer> used = new HashSet<Integer>();

		for (int i = 0; i < Parameters.geneSize; i++) {
			int j = X.chromo.get(i);
			for (; used.contains(j % Parameters.geneSize); j++);
			retval.chromo.add(i, j);
		}
		return retval;
	}

	public void doRawFitness(TSPChromo X){
		if (Parameters.ordFlag) {
			TSPChromo temp = ordmap(X);
			for (int i = 0; i < Parameters.geneSize - 1; i++) {
				X.rawFitness += dis(Search.cities.get(temp.chromo.get(i)), Search.cities.get(temp.chromo.get(i)));
			}
		}
		else {
			for (int i = 0; i < Parameters.geneSize - 1; i++) {
				X.rawFitness += dis(Search.cities.get(X.chromo.get(i)), Search.cities.get(X.chromo.get(i)));
			}
		}

//  PRINT OUT AN INDIVIDUAL GENE TO THE SUMMARY FILE *********************************

	public void doPrintGenes(TSPChromo X, FileWriter output) throws java.io.IOException{
		System.out.println("Executing FF Gene Output");
	}


/*******************************************************************************
*                             STATIC METHODS                                   *
*******************************************************************************/


}   // End of OneMax.java ******************************************************

