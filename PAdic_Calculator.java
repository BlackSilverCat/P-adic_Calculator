/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package p.adic_calculator;
import java.util.Scanner;
/**
 *
 * @author Matthew
 */
public class PAdic_Calculator {

    /**
     * @param args the command line arguments
     */
        
    public static void main(String [] args)
    {       
        Scanner scan = new Scanner(System.in);
        System.out.println("enter numerator");
        int num = scan.nextInt();
        System.out.println("enter denominator");
        int denom = scan.nextInt();
        System.out.println("enter prime for piadic expansion");
        int prime = scan.nextInt();
        int basepower; //base power of the summation
        int[] factors;
        int[] topositive;
        int cur;
        int x = 50;
	double numb = num;
        double de = denom;
        numb = numb/de;
        boolean neg = true;
        if(numb == 1 && prime == 2)
        {
            System.out.println("1 + 2^n+1 as n approaches infinity");
        }
        else
        {
	if(numb > 0) //checks if number is a negative
	{
		neg = false;
	}
        int chez = (int)numb;
	if(numb == chez) //determines if rational is also contained in the intergers
	{	
            basepower = 1; //since its a whole number we set power in the summation to 1
            if(neg)
            {
            int curr = (int)numb*(prime - 1) * -1;
            cur = settohighest(curr, prime); //sets cur to highest exponent in the decomposition
            factors = fillfactors(curr, cur, prime); //fills factors with the factor correspodning to the specfic exponent
            printstring(basepower, prime, factors, x, neg); 
            }
            else
            {
              int curr = num;
              cur = settohighest(curr, prime); //sets cur to highest exponent in the decomposition
              factors = fillfactors(curr, cur, prime); //fills factors with the factor correspodning to the specfic exponent
              int[] addon;
              addon = new int[factors.length];
              addon[1] = 1;
              factors = merge(addon, factors, prime);
              printstring(basepower, prime, factors, x, neg); 
            }
        }
	else
	{
            int deno = simpde(numb);
            if(neg)
            {
              basepower = getpower(deno, prime);
              int multi = (prime^basepower -1)/denom;
              int curr = num * multi * -1;
              cur = settohighest(curr, prime); //sets cur to highest exponent in the decomposition
              factors = fillfactors(curr, cur, prime); //fills factors with the factor correspodning to the specfic exponent
              printstring(basepower, prime, factors, x, neg); 
            }
            else
            {
                
            }
	}
        }
    }
    public static int settohighest(int number, int prime)
    {
         boolean contin = true;
         int curt = 0;
         int current = 1;
         while(contin) //determines highest power in decomposing the string will be used similarly in decomposing the numorator for rationals
                    {
                        int check = number - current;
                        if(check < 0)
                        {
                            contin = false;
                            curt--;
                        }
                        else
                        {
                            curt++;
                            current = current * prime;
                        }
                    }
          return curt;
    }
    public static int getpower(int denom, int prime)
    {
        int ret = 1;
       for(int i = 1; i < denom; i++)
              {
                  int checker = (int)Math.pow(prime, i);
                  int check = checker % denom;
                  if(check == 1)
                  {
                      ret = i;
                      i = denom;
                  }
              }
       return ret;
    }
    public static int[] fillfactors(int number, int curt, int prime) //sets cur to highest exponent of prime decomposition used for initial
    {
        int[] fact;
        fact = new int[curt+1];
        int left = number;        
        for(int i = curt; i > -1; i--) //determines factor for each power in the decomposition
            {
                int base = 1;
                for(int k = 0; k < i; k++)
                {
                    base = base * prime;
                }
                
                for(int j = 1; j <= prime; j++)
                {
                    int check = left - (j*base);
                    if(check < 0)
                    {
                        fact[i] = j-1;
                        j = prime;
                        left = left - fact[i]*base;
                    }
                }
             
            }
        return fact;
    }
    public static int[] merge(int[] fact, int[] factor, int prime) //allows for carrying to combine two lists
    {
        int[] merged;
        int size;
        int carry = 0;
        if(fact.length > factor.length)
        {
            size = fact.length;
        }
        else
        {
            size = factor.length;
        }
        merged = new int[size + 1];
        {
            for(int i = 0; i < merged.length - 1; i++)
            {
                int sum = fact[i] + factor[i] + carry;
                merged[i] = sum % prime;
                if(sum > prime)
                {
                    carry = 1;
                }
                else
                {
                    carry = 0;
                }
            }
            if(carry == 1)
            {
                merged[merged.length - 1] = 1;
            }
            else
            {
                merged[merged.length - 1] = -1;
            }
        }
       return merged;
    }
    public static int simpde(double numb)
    {
        int denom = 0;
        double check;
        int icheck; 
        do
        {
            denom ++;
            check = numb * denom;
            icheck = (int)check;
        } while(check != icheck);
        return denom;
    }
    public static void printstring(int power, int prime,  int[] factors, int x, boolean isneg) //prints in terms of summation
    {
        System.out.println("");
        if (isneg == true)       
        {
        for(int i = 0; i < x; i++)
        {
            if(factors[i % factors.length] != -1)
            {
            System.out.print("" + factors[i % factors.length] + "*" + prime + "^" + i + " + ");
            }
        }
        }
        else
        {
            
        }
    
    }
}
