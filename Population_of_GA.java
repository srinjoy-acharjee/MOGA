
public class Population_of_GA
{
	int population_size_of_GA;
	Accuracy_Calculate_only_GA ga[];
	public int pop_size;	
    //BackPropagation1 bpn[];    
	Population_of_GA(Population combination_population_after_remove_unnecessary_weight,Dataset training_DataSet,Dataset testing_DataSet,int initial_hidden_nodes)
    {
	   population_size_of_GA=combination_population_after_remove_unnecessary_weight.pop_size;
       ga=new Accuracy_Calculate_only_GA[combination_population_after_remove_unnecessary_weight.pop_size];
       //invoking backpropagation class
       //System.out.println("entering bpn");
       for (int counter=0;counter<=combination_population_after_remove_unnecessary_weight.pop_size-1;counter++)
       {
    	   ga[counter]=new Accuracy_Calculate_only_GA(training_DataSet,testing_DataSet,combination_population_after_remove_unnecessary_weight.chromo[counter],initial_hidden_nodes,combination_population_after_remove_unnecessary_weight); 	 
       }
       
       //System.out.println("change in chomosome"+ga); 
    } 
   
   
}
