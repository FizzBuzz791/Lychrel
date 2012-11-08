/*********************************************************
 * Author: Trent Jones                                   *
 * Version: 1.0 - Basic functionality                    *
 * Summary: Test a number for Lychel candidate validity. *
 *********************************************************/

import java.util.Scanner;
import java.math.BigInteger;

public class palinBig
{
	public static void main(String[] args)
	{
		BigInteger inNumber = BigInteger.ZERO;
		int count = 0; // loop counter, used to avoid infinite loop caused by Lychrel candidates.
		boolean verbose = false; // Verbose output on/off
		int LOOP_MAX = 100; // default amount of iterations to try

		// Command line options tests
		if (args.length == 0)
		{
			System.out.println("Proper usage is: java palinBig [-v] [-i ...]");
			System.out.println("Try: java palinBig -help for more information");
			System.exit(0);
		}
		else
		{
			if (args.length == 1) // verbose or help
			{
				if (args[0].equals("-v"))
				{
					verbose = true;
				}
				else if (args[0].equals("-help"))
				{
					System.out.println("Use [-v] for verbose output.");
					System.out.println("Use [-i ...] to change the max steps.");
					System.exit(0);
				}
				else
				{
					System.out.println("Unrecognised command.");
					System.out.println("Proper usage is: java palinBig [-v] [-i ...]");
					System.exit(0);
				}
			}
			else if (args.length == 2) // iteration change
			{
				if (args[0].equals("-i"))
				{
					LOOP_MAX = Integer.parseInt(args[1]);
				}
				else
				{
					System.out.println("Unrecognised command.");
					System.out.println("Proper usage is: java palinBig [-v] [-i ...]");
					System.exit(0);
				}
			}
			else if (args.length == 3) // verbose and iteration
			{
				if (args[0].equals("-v"))
				{
					verbose = true;
					if (args[1].equals("-i"))
					{
						LOOP_MAX = Integer.parseInt(args[2]);
					}
					else
					{
						System.out.println("Unrecognised command.");
						System.out.println("Proper usage is: java palinBig [-v] [-i ...]");
						System.exit(0);
					}
				}
				else if (args[0].equals("-i"))
				{
					LOOP_MAX = Integer.parseInt(args[1]);
					if (args[2].equals("-v"))
					{
						verbose = true;
					}
					else
					{
						System.out.println("Unrecognised command.");
						System.out.println("Proper usage is: java palinBig [-v] [-i ...]");
						System.exit(0);
					}
				}
				else
				{
					System.out.println("Unrecognised command.");
					System.out.println("Proper usage is: java palinBig [v] [-i ...]");
					System.exit(0);
				}
			}
			else
			{
				System.out.println("Too many options.");
				System.out.println("Proper usage is: java palinBig [v] [-i ...]");
				System.exit(0);
			}
		}

		System.out.println("Please input the number to palidrome:");

		inNumber = new Scanner(System.in).nextBigInteger();

		// Only print if verbose is on (-v)
		if (verbose)
		{
			System.out.println("\nVerbose output:\n");
		}

		// Loop through and check for palindrome.
		while (inNumber.compareTo(reverseNum(inNumber)) != 0)
		{
			// Only print if verbose is on.
			if (verbose)
			{
				System.out.println(inNumber); 
				System.out.println(reverseNum(inNumber));
				System.out.println(line(inNumber.add(reverseNum(inNumber)).toString().length())); // just for prettyness.
				System.out.println(inNumber.add(reverseNum(inNumber)) + "\n");
			}
			
			// Permanently add number and reversed number (for next iteration).
			inNumber = inNumber.add(reverseNum(inNumber));
			// Increment loop counter.
			count++;

			// Prevent infinite loop and/or buffer overflow from Lychrel candidates.
			// Could include in loop tests, but then it makes output/feedback less simple.
			if (count > LOOP_MAX)
			{
				System.out.println("No dice after " + LOOP_MAX + " tries. Got to " + inNumber + " if you want to keep trying.");
				System.exit(0);
			}
		}

		System.out.println("Palidrome found in " + count + " steps;\n" + inNumber);
	}

	/*************************************************
	 * Recursively reverse the digits of an integer. *
	 *************************************************/
	public static BigInteger reverseNum(BigInteger inNum)
	{
		BigInteger reversed = BigInteger.ZERO;

		if (inNum.compareTo(BigInteger.ZERO) == 1) // compareTo() returns 1 if inNum > 0 
		{
			int length = inNum.toString().length();
			
			// this is a dirty hack due to the limits of long & BigInteger.valueOf()
			String mult = "1";
			for (int i = 1; i < length; i++)
			{
				mult += "0";
			}

			// Remove the last number and multiply to it's opposite position.
			reversed = (inNum.mod(BigInteger.TEN)).multiply(new BigInteger(mult));
			
			BigInteger tempNum = reverseNum(inNum.divide(BigInteger.TEN));
			reversed = reversed.add(tempNum);
		}
		
		return reversed;
	}

	/*************************************
	 * Prints out a line, eg '---------' *
	 * Just included for aesthetics.     *
	 *************************************/
	public static String line(int inLength)
	{
		String line = "";
		for (int i = 0; i < inLength; i++)
		{
			line += "-";
		}

		return line;
	}
}
