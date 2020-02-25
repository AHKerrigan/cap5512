/******************************************************************************
*  A Teaching GA					  Developed by Hal Stringer & Annie Wu, UCF
*  Version 2, January 18, 2004
*******************************************************************************/

import java.io.*;
import java.util.*;
import java.text.*;

public class TSPChromo
{
/*******************************************************************************
*                            INSTANCE VARIABLES                                *
*******************************************************************************/

	public ArrayList<Integer> chromo;
	public double rawFitness;
	public double sclFitness;
	public double proFitness;
	public boolean ordflag;

/*******************************************************************************
*                            INSTANCE VARIABLES                                *
*******************************************************************************/

	private static double randnum;

/*******************************************************************************
*                              CONSTRUCTORS                                    *
*******************************************************************************/

	public TSPChromo(){

		//  Set gene values to a randum sequence of 1's and 0's
		char geneBit;
		this.ordflag = Parameters.ordflag;
		this.chromo = new ArrayList<Integer>();
		Random rand = new Random();
		if (ordflag) {
			for (int i = 0; i < Parameters.geneSize; i++) {
				chromo.add(1);
			}
		}	
		else {
			for (int i = 0; i < Parameters.geneSize; i++) {
				chromo.add(i);
			}
		}

		this.rawFitness = -1;   //  Fitness not yet evaluated
		this.sclFitness = -1;   //  Fitness not yet scaled
		this.proFitness = -1;   //  Fitness not yet proportionalized
	}


/*******************************************************************************
*                                MEMBER METHODS                                *
*******************************************************************************/

	//  Get Alpha Represenation of a Gene **************************************

	/**
	public String getGeneAlpha(int geneID){
		int start = geneID * Parameters.geneSize;
		int end = (geneID+1) * Parameters.geneSize;
		String geneAlpha = this.chromo.substring(start, end);
		return (geneAlpha);
	}
	**/

	//  Get Integer Value of a Gene (Positive or Negative, 2's Compliment) ****

	/** 
	public int getIntGeneValue(int geneID){
		String geneAlpha = "";
		int geneValue;
		char geneSign;
		char geneBit;
		geneValue = 0;
		geneAlpha = getGeneAlpha(geneID);
		for (int i=Parameters.geneSize-1; i>=1; i--){
			geneBit = geneAlpha.charAt(i);
			if (geneBit == '1') geneValue = geneValue + (int) Math.pow(2.0, Parameters.geneSize-i-1);
		}
		geneSign = geneAlpha.charAt(0);
		if (geneSign == '1') geneValue = geneValue - (int)Math.pow(2.0, Parameters.geneSize-1);
		return (geneValue);
	}
	**/

	//  Get Integer Value of a Gene (Positive only) ****************************

	/**
	public int getPosIntGeneValue(int geneID){
		String geneAlpha = "";
		int geneValue;
		char geneBit;
		geneValue = 0;
		geneAlpha = getGeneAlpha(geneID);
		for (int i=Parameters.geneSize-1; i>=0; i--){
			geneBit = geneAlpha.charAt(i);
			if (geneBit == '1') geneValue = geneValue + (int) Math.pow(2.0, Parameters.geneSize-i-1);
		}
		return (geneValue);
	}
	**/

	//  Mutate a Chromosome Based on Mutation Type *****************************

	public void doMutation() {

		// Simple swap mutation, valid for both path and ordinal representaiton
		for (int i = 0; i < this.chromo.size(); i++) {
			randnum = Search.r.nextDouble();
			if (randnum < Parameters.mutationRate) {
				int j = Search.r.nextInt(this.chromo.size());
				Collections.swap(this.chromo, i, j);
			}
		}
	}

/*******************************************************************************
*                             STATIC METHODS                                   *
*******************************************************************************/

	//  Select a parent for crossover ******************************************

	public static int selectParent(){

		double rWheel = 0;
		int j = 0;
		int k = 0;

		switch (Parameters.selectType){

		case 1:     // Proportional Selection
			randnum = Search.r.nextDouble();
			for (j=0; j<Parameters.popSize; j++){
				rWheel = rWheel + Search.member[j].proFitness;
				if (randnum < rWheel) return(j);
			}
			break;

		case 3:     // Random Selection
			randnum = Search.r.nextDouble();
			j = (int) (randnum * Parameters.popSize);
			return(j);

		case 2:     //  Tournament Selection

		default:
			System.out.println("ERROR - No selection method selected");
		}
	return(-1);
	}

	//  Produce a new child from two parents  **********************************

	public static void mateParents(int pnum1, int pnum2, TSPChromo parent1, TSPChromo parent2, TSPChromo child1, TSPChromo child2){

		int xoverPoint1;
		int xoverPoint2;

		xoverPoint1 = Search.r.nextInt(Parameters.geneSize);
		xoverPoint2 = Search.r.nextInt((Parameters.geneSize - xoverPoint1) + 1) + xoverPoint1 - 1;
		
		if (Parameters.ordflag) {
			

			for (int i = 0; i <= xoverPoint1; i++) {
				child1.chromo.add(i, parent2.chromo.get(i));
				child2.chromo.add(i, parent1.chromo.get(i));
			}
			for (int i = xoverPoint1 + 1; i < Parameters.geneSize - 1; i++) {
				child1.chromo.add(i, parent1.chromo.get(i));
				child2.chromo.add(i, parent2.chromo.get(i));
			}

		}
		else {
			HashSet<Integer> temp1 = new HashSet<Integer>();
			//System.out.println(xoverPoint1 + " " + xoverPoint2);
			HashSet<Integer> temp2 = new HashSet<Integer>();

			// Copy over to the children
			for (int i = xoverPoint1; i <= xoverPoint2; i++) {
				child1.chromo.add(i, parent1.chromo.get(i));
				child2.chromo.add(i, parent2.chromo.get(i));
				temp1.add(parent1.chromo.get(i));
				temp2.add(parent2.chromo.get(i));
			}
			// Do child 1
			for (int i = 0, j = 0; i < Parameters.geneSize; i++) {
				if (i >= xoverPoint1 && i <= xoverPoint2) {
					continue;
				}
				while (temp1.contains(parent2.chromo.get(j))) {
					j++;
				}
				child1.chromo.add(i, parent2.chromo.get(j));
				temp1.add(parent2.chromo.get(j));
			}
			// Do child 2
			for (int i = 0, j = 0; i < Parameters.geneSize; i++) {
				if (i >= xoverPoint1 && i <= xoverPoint2) {
					continue;
				}
				while (temp2.contains(parent1.chromo.get(j))) {
					j++;
				}
				child2.chromo.add(i, parent1.chromo.get(j));
				temp2.add(parent1.chromo.get(j));
			}
		}

		//  Set fitness values back to zero
		child1.rawFitness = -1;   //  Fitness not yet evaluated
		child1.sclFitness = -1;   //  Fitness not yet scaled
		child1.proFitness = -1;   //  Fitness not yet proportionalized
		child2.rawFitness = -1;   //  Fitness not yet evaluated
		child2.sclFitness = -1;   //  Fitness not yet scaled
		child2.proFitness = -1;   //  Fitness not yet proportionalized
	}

	//  Produce a new child from a single parent  ******************************

	public static void mateParents(int pnum, TSPChromo parent, TSPChromo child){

		//  Create child chromosome from parental material
		child.chromo = parent.chromo;

		//  Set fitness values back to zero
		child.rawFitness = -1;   //  Fitness not yet evaluated
		child.sclFitness = -1;   //  Fitness not yet scaled
		child.proFitness = -1;   //  Fitness not yet proportionalized
	}

	//  Copy one chromosome to another  ***************************************

	public static void copyB2A (TSPChromo targetA, TSPChromo sourceB){

		targetA.chromo = sourceB.chromo;

		targetA.rawFitness = sourceB.rawFitness;
		targetA.sclFitness = sourceB.sclFitness;
		targetA.proFitness = sourceB.proFitness;
		return;
	}

}   // End of Chromo.java ******************************************************