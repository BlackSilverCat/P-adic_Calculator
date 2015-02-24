import java.util.Scanner;
/**
 *
 * @author Matthew
 */
public class PAdic {

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
        int x=0;
        double numb = num;
        double de = denom;
        numb = numb/de;
        if(numb < 0 || numb != (int)numb) {
            System.out.println("enter number of terms to show in the expansion");
            x = scan.nextInt();
        }
        boolean neg = true;
        if(numb == (prime-1))
        {
            System.out.println("1 + "+prime+"^n+1 as n approaches infinity");
        }
        else
        {
            if(numb > 0) //checks if number is a negative
            {
                neg = false;
            }
            int chez = (int)numb;
            if(numb == chez) //determines if rational is also contained in the integers
            {
                basepower = 1; //since its a whole number we set power in the summation to 1
                if(neg)
                {
                        basepower = 1; //finds the power that elements will vary by
                        int multi = (topower(prime, basepower) - 1) / (prime - 1); // gets easy to express num
                        int facty = largestfactor(chez, prime, basepower);
                        int curr = facty * multi; // equals highest prime used in summation since neg rational is linear
                        cur = settohighest(curr, prime); //sets cur to highest exponent in the decomposition
                        int meow = chez / facty * -1;
                        int basepowers = getmpower(meow, prime);
                        topositive = fillfactors(meow, basepowers, prime);
                        factors = fillfactors(curr, cur, prime); //fills factors with the factor correspodning to the specfic exponent
                        factors = mergemulti(topositive, factors, prime, x);
                        printstring(basepower, prime, factors, x, true);
                }
                else
                {
                    int curr = chez;
                    //cur = settohighest(curr, prime); //sets cur to highest exponent in the decomposition
                    factors = fillfactors(curr, getmpower(chez,prime), prime); //fills factors with the factor correspodning to the specfic exponent
                    printstring(0, prime, factors, factors.length, neg);
                }
            }
            else
            {
                int deno = simpde(numb); //simplifies the number
                if(neg)
                {
                    if(deno%prime != 0) {
                        basepower = getpower(deno, prime); //finds the power that elements will vary by
                        int multi = (topower(prime, basepower) - 1) / deno; // gets easy to express num
                        num = num/(denom/deno);
                        int curr = num * multi * -1; // equals highest prime used in summation since neg rational is linear
                        cur = settohighest(curr, prime); //sets cur to highest exponent in the decomposition
                        factors = fillfactors(curr, cur, prime); //fills factors with the factor correspodning to the specfic exponent
                        printstring(basepower, prime, factors, x, true);
                    }
                    else
                    {
                        System.out.print("error p divides denominator");
                    }
                }
                else
                {
                    if(deno%prime != 0) {
                        int negc = getAddition(numb);
                        int newnum = (num/(denom/deno)) - (negc* deno);
                        int basepowers = getmpower(negc,prime);
                        topositive = fillfactors(negc,basepowers,prime);
                        basepower = getpower(deno, prime); //finds the power that elements will vary by
                        int multi = (topower(prime, basepower) - 1) / deno; // gets easy to express num
                        int curr = newnum *  multi *-1; // equals highest prime used in summation since neg rational is linear
                        cur = settohighest(curr, prime); //sets cur to highest exponent in the decomposition
                        factors = fillfactors(curr, cur, prime); //fills factors with the factor correspodning to the specfic exponent
                        factors = mergea(topositive, factors, prime ,x);
                        printstring(basepower, prime, factors, x, neg);
                    }
                }
            }
        }
    }
    public static int largestfactor( int num, int prime, int basepower)
    {
        int i;
        for(i = topower(prime,basepower)-2;i>1;i--)
            if(num % i == 0)
                break;
        return i;
    }
    public static int topower(int num, int power)
    {
        int a = 1;
        for(int i = 0; i<power; i++)
            a *= num;
        return a;
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
    public static int getAddition(double number)
    {
        int i=1;
        while(number-i > 0)
            i++;
        return i;
    }
    public static int getmpower(int numb, int prime) {
        {
            int i = 0;
            while((numb - topower(prime,i)) > 0)
            {
                i++;
            }
            if(numb !=0)
            i--;

            return i;
        }
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
        if(left < 0)
            left = left * -1;
        if(curt < 0)
            curt=curt*1;
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
    public static int[] mergea(int[] fact, int[] factor, int prime, int x) //allows for carrying to combine two lists useful for positives and ints
    {
        int[] merged;
        int size;
        int carry = 0;
        if(fact.length < factor.length)
        {
            size = fact.length;
        }
        else
        {
            size = factor.length;
        }
        merged = new int[x];
        for(int i = 0; i < size; i++)
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
        for(int je = size; je < merged.length; je++)
        {
            if(carry == 1 && factor[je % factor.length] + carry < prime)
            {
                merged[je] = factor[je %factor.length]+1;
            }
            else
            {
                merged[je] = factor[je %factor.length];
            }


        }
        return merged;
    }
    public static int[] mergemulti(int[] fact, int[] factor, int prime, int x) //allows for carrying to combine two lists useful for positives and ints
    {
        int[] merged;
        int size;
        int carry[] = new int[fact.length+factor.length];
        int carry2[];
            size = fact.length+factor.length-1;

        merged = new int[size];
            for(int i = 0; i < fact.length; i++)
            {
                for(int j = 0; j < factor.length; j++)
                {
                        merged[i+j] += fact[i]*factor[j];
                        if(merged[i+j] > prime)
                        {
                            merged[i+j] = merged[i+j]%prime;
                            carry[i+j+1]++;
                        }
                }
            }
        if(carry[carry.length-1] == 0) {
            carry2 = new int[carry.length -1];
            for (int o = 0; o < carry2.length;o++ )
                carry2[o]=carry[o];
        }
        else
        {
            carry2 = carry;
        }
        merged = mergea(carry2,merged,prime,x);
        return merged;
    }
    public static int simpde(double numb) // simplifies the denominator in case number is not in it simplest form
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
        if (isneg == true )
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
            for(int i = 0; i < x; i++)
            {
                if(factors[i % factors.length] != -1)
                {
                    System.out.print("" + factors[i] + "*" + prime + "^" + i + " + ");
                }
            }
            System.out.print("...");
        }

    }
}
