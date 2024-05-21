
import java.text.DecimalFormat;


public class Gene 
{
    double gene_value;
   // DecimalFormat df2 = new DecimalFormat("#.##");
    /*******************************************************************************************************
     *                This constructor is to generate random numbers range between(-0.1 to 0.1)            *
     *******************************************************************************************************/
    Gene()
    {
        gene_value=(double)(((Math.random()*2.0)-1.0)*0.1);
        gene_value=Math.round(100*gene_value)/100.0;
    }
    Gene(String gene_value1)
    {
        //System.out.println(gene_value1);
        gene_value=(double)Double.parseDouble(gene_value1);
        System.out.println("sks"+gene_value);
    }
    /*******************************************************************************************************
     *                This method shows the gene value                                                     *
     *******************************************************************************************************/
    public void Show_gene()
    {
        
        System.out.print((gene_value));
    }
}
