
public class Population_of_ANN 
{
    int population_size_of_Ann;
    BackPropagation bpn[];
    //BackPropagation1 bpn[];
   Population_of_ANN(Population combination_population_after_remove_unnecessary_weight,Dataset training_DataSet,Dataset testing_DataSet,int initial_hidden_nodes)
   {
       
       population_size_of_Ann=combination_population_after_remove_unnecessary_weight.pop_size;
       bpn=new BackPropagation[combination_population_after_remove_unnecessary_weight.pop_size];
       
       //invoking backpropogation class
       //System.out.println("entering bpn");
       for (int counter=0;counter<=combination_population_after_remove_unnecessary_weight.pop_size-1;counter++)
        {
            bpn[counter]=new BackPropagation(training_DataSet,testing_DataSet,combination_population_after_remove_unnecessary_weight.chromo[counter],initial_hidden_nodes);
        }       
    double ann_max = combination_population_after_remove_unnecessary_weight.max_fitness();
    System.out.println("ANN_max accuracy= "+ann_max);
    }
  /* Population_of_ANN(Population combination_population_after_remove_unnecessary_weight,Dataset training_DataSet,int initial_hidden_nodes)
   {
       
       population_size_of_Ann=combination_population_after_remove_unnecessary_weight.pop_size;
       bpn=new BackPropagation[combination_population_after_remove_unnecessary_weight.pop_size];
       
       //invoking backpropogation class
       //System.out.println("entering bpn");
       for (int counter=0;counter<=combination_population_after_remove_unnecessary_weight.pop_size-1;counter++)
        {
            bpn[counter]=new BackPropagation(training_DataSet,combination_population_after_remove_unnecessary_weight.chromo[counter],initial_hidden_nodes,combination_population_after_remove_unnecessary_weight);
            //System.out.println("accuracy of GA"+combination_population.chromo[counter].fitness);
        }
       double ga_max=combination_population_after_remove_unnecessary_weight.max_fitness();
       System.out.println("GA_max accuracy="+ga_max);
       
    
    }
  
   */
}

