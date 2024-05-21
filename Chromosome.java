
import java.text.DecimalFormat;

public class Chromosome 
{
    Gene gene[];
    double accuracy;
    int num_hidden_nodes;
    int num_of_zero;
    double fitness;
    double c_fitness;
    String vote[];
    String target_output[];
    
    /*******************************************************************************************************
     *                This constructor is for generate the chromosome string                               *
     *******************************************************************************************************/
    Chromosome(Dataset training_DataSet,int initial_hidden_nodes)
    {
        
        DecimalFormat df2 = new DecimalFormat("#.##");
        gene=new Gene[(training_DataSet.noOfAttributes-1)* initial_hidden_nodes+ (initial_hidden_nodes*training_DataSet.noOfClasses)+initial_hidden_nodes+training_DataSet.noOfClasses];
        
        //System.out.println(gene.length);//for testing
        for (int counter=0;counter<=gene.length-1;counter++)
        {
            gene[counter]=new Gene();
            
        }
    }
    Chromosome(Dataset training_DataSet,int initial_hidden_nodes,String Dna1[])
    {
        gene=new Gene[(training_DataSet.noOfAttributes-1)* initial_hidden_nodes+ (initial_hidden_nodes*training_DataSet.noOfClasses)+initial_hidden_nodes+training_DataSet.noOfClasses];
        
        //System.out.println("length"+Dna1.length);
        for (int counter=0;counter<=Dna1.length-1;counter++)
        {
            //System.out.println("Dna1["+counter+"]="+Dna1[counter]);
            gene[counter]=new Gene(Dna1[counter]);
            
            //hidden_bais=hidden_bais1;
            //output_bais=output_bais1;
        }
        
    }
    /*******************************************************************************************************
     *                This method is for showing the chromosome string                                     *
     *******************************************************************************************************/
    public void Show_chromosome()
    {
        for (int counter=0;counter<=gene.length-1;counter++)
        {
            gene[counter].Show_gene();
            System.out.print("|");
        }
    }
}
