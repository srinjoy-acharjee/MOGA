public class Accuracy_Calculate_only_GA 
{
	InitialNodes initial_nodes[];
    Nodes hidden_nodes[];
    Nodes output_nodes[];
    double hidden_nodes_weight[][];
    double output_nodes_weight[][];
    double output_node_error[];
    double hidden_node_error[];
    double output_node_bais[];
    double hidden_node_bais[];
    double testing_hidden_bias[];
    double testing_output_bais[];
    double po_delta=0,pob_delta=0,ph_delta=0,phb_delta=0;    
    Population population_after_bpn_fainal=null;
    double number_of_update_count;    	
    int pop_size;
    public Accuracy_Calculate_only_GA(Dataset training_DataSet, Dataset testing_DataSet, Chromosome chromo, int initial_hidden_nodes,Population combination_population_after_remove_unnecessary_weight)
	{
    	pop_size= combination_population_after_remove_unnecessary_weight.pop_size;
    	initial_nodes =create_initial_node(training_DataSet);
    	hidden_nodes =create_hidden_node(training_DataSet,initial_hidden_nodes);
    	output_nodes =create_output_node(training_DataSet);
    	create_input_hidden_weight_matrix(training_DataSet,chromo,initial_hidden_nodes);
    	create_hidden_output_weight_matrix(training_DataSet,chromo,initial_hidden_nodes);
    	bais(training_DataSet,chromo,initial_hidden_nodes);    	
    	chromo.fitness=testing(training_DataSet,initial_hidden_nodes,chromo);
     	chromo.num_of_zero=calculate_number_of_zero( chromo,  pop_size,  initial_hidden_nodes);
     	
    	//System.out.print("accuracy="+accuracy+"    Number of zero"+number_of_zero+"\n");
     	//System.out.println();
     	//chromo.Show_chromosome();
	}
    
	/*****************************************************************************************************                                                                                                   *
     *      This method is used to create number of input layer nodes                                     *                              *
     ******************************************************************************************************/      
     public InitialNodes[] create_initial_node(Dataset training_DataSet)
     {
         //allocating input nodes
         InitialNodes nodes[] = new InitialNodes[training_DataSet.noOfAttributes-1]; 
         //creating input nodes
         
         for (int i=0; i<training_DataSet.noOfAttributes-1; i++ )
         {
             nodes[i]=new InitialNodes();
         }//end of loop
         return nodes;
     }//end InitialNodes[] create_initial_node               
     /*******************************************************************************************************
      *                 This method is used to create number of hidden layer nodes                          *
      *******************************************************************************************************/            
      public Nodes[] create_hidden_node(Dataset training_DataSet,int initial_hidden_nodes)
     {
         //allocating hidden nodes
         Nodes nodes[] = new Nodes[initial_hidden_nodes];
         //creating hidden nodes
         for (int i=0;i<initial_hidden_nodes;i++ )
         {
             nodes[i]=new Nodes();
         } //end of loop  
         return nodes;           
     }//end of Nodes[] create_hidden_node method
     /*******************************************************************************************************
      *                          This method is to create no of output layer nodes                          *
      *******************************************************************************************************/                            
     public  Nodes[] create_output_node(Dataset training_DataSet)
     {
         //allocating output nodes
         Nodes nodes[] = new Nodes[training_DataSet.noOfClasses];
         //creating output nodes
         for (int i=0;i<=training_DataSet.noOfClasses-1;i++)
         {
             nodes[i]=new Nodes();
         }//end of loop
         return nodes;
     }//end Nodes[] create_output_node method
     /*******************************************************************************************************
      *                          This method is to initialize all nodes                                     *
      *******************************************************************************************************/
     void node_initialization(Dataset training_DataSet,int initial_hidden_nodes)
     {
         //initialization of input nodes
         for (int i=0; i < training_DataSet.noOfAttributes-1; i++ )
         {
             initial_nodes[i].input=0.0;
             initial_nodes[i].output=0.0;
         }//end of loop
         //initialization of hidden nodes
         for (int i=0;i<initial_hidden_nodes;i++ )
         {
        	 //System.out.println( hidden_nodes[i].input+"m1");
             hidden_nodes[i].input=0.0;
             hidden_nodes[i].output=0.0;
             hidden_nodes[i].error=0.0;             
         }//end of loop
         //initialization of output nodes        
         for (int i=0; i <= training_DataSet.noOfClasses-1; i++)
         {
        	 output_nodes[i].input=0.0;
         	 output_nodes[i].output=0.0;
             output_nodes[i].error=0.0;
         }//end of loop        
     }//end of node_initialization method
     /*******************************************************************************************************
      *               This method is to create input to hidden layer weight matrix                          *
      *******************************************************************************************************/            
     public  void create_input_hidden_weight_matrix(Dataset training_DataSet,Chromosome chromo,int initial_hidden_nodes)
     {
         //allocating input to hidden layer weight matrix
     	//System.out.print("\nThe hidden layer node\n");
         hidden_nodes_weight = new double[training_DataSet.noOfAttributes-1][initial_hidden_nodes]; 
         for(int i=0; i< training_DataSet.noOfAttributes-1 ; i++)
         {
             for(int j =0; j< initial_hidden_nodes; j++)
             {
                 //creating and storing random weight between -0.1 to 0.1 into weight matrix 
                 hidden_nodes_weight[i][j]= chromo.gene[i*initial_hidden_nodes+j].gene_value;
                 //System.out.print("|*"+hidden_nodes_weight[i][j]+"|");
             }//end of inner loop            
         }//end of outer loop	
         //System.out.println();
     }//end of create_input_hidden_weight_matrix method
     
     /*******************************************************************************************************
      *                This method is to create hidden to output layer weight matrix                        *
      *******************************************************************************************************/        
     public void create_hidden_output_weight_matrix(Dataset training_DataSet,Chromosome chromo,int initial_hidden_nodes)
     {
         //allocating hidden to output layer weight matrix
     	//System.out.print("\nThe output layer node\n");
         output_nodes_weight=new double[initial_hidden_nodes][training_DataSet.noOfClasses];  
         int counter = (initial_hidden_nodes*(training_DataSet.noOfAttributes-1));
         for(int i=0;i<initial_hidden_nodes;i++)
         {
             for(int j=0; j< training_DataSet.noOfClasses;j++)
             {
                 output_nodes_weight[i][j]= chromo.gene[counter].gene_value;
                 //System.out.print("|-"+output_nodes_weight[i][j]+"-|");
                 counter++;
             }//end of inner loop	
         }//end of outer loop
     }//end of create_hidden_output_weight_matrix method
    /*******************************************************************************************************
      *                This method is to create hidden to output node bais                                 *
      *******************************************************************************************************/
    public void bais(Dataset training_DataSet,Chromosome chromo,int initial_hidden_nodes)
    {
         output_node_bais=new double[training_DataSet.noOfClasses];
         hidden_node_bais=new double[initial_hidden_nodes];
         for(int counter=0;counter<initial_hidden_nodes;counter++)
         {
             hidden_node_bais[counter]=0.0;
         }//end of loop
         for(int counter=0;counter<training_DataSet.noOfClasses;counter++)
         {
             output_node_bais[counter]=0.0;
         }//end of loop
         int hidden_bais=0;    
         for(int counter=(training_DataSet.noOfAttributes-1)* initial_hidden_nodes+ (initial_hidden_nodes*training_DataSet.noOfClasses);counter<(training_DataSet.noOfAttributes-1)* initial_hidden_nodes+ (initial_hidden_nodes*training_DataSet.noOfClasses)+initial_hidden_nodes;counter++)
         {     
             hidden_node_bais[hidden_bais]=chromo.gene[counter].gene_value;
             hidden_bais++;
         }//end of loop
         int output_bais=0;
         for(int counter=(training_DataSet.noOfAttributes-1)* initial_hidden_nodes+ (initial_hidden_nodes*training_DataSet.noOfClasses)+initial_hidden_nodes; counter< (training_DataSet.noOfAttributes-1)* initial_hidden_nodes+ (initial_hidden_nodes*training_DataSet.noOfClasses)+initial_hidden_nodes+training_DataSet.noOfClasses;counter++)
         {    
             output_node_bais[output_bais]=chromo.gene[counter].gene_value;
             output_bais++;            
         }//end of loop
     }//end of bais method 
    /******************************************************************************************************   
     *       This method is for testing the network using training dataset (use k-fold classification)     *
     *******************************************************************************************************/
    public double testing(Dataset training_DataSet,int initial_hidden_nodes,Chromosome chromo)
    {
    	create_input_hidden_weight_matrix(training_DataSet,chromo,initial_hidden_nodes);    	
        create_hidden_output_weight_matrix(training_DataSet,chromo,initial_hidden_nodes);        
        bais(training_DataSet,chromo,initial_hidden_nodes);           
        int fired_output_node_number=0;
        double counter=0;
        //System.out.println("shaw"+training_DataSet.noOfRecords);
        for(int loop_counter=0;loop_counter<training_DataSet.noOfRecords;loop_counter++)
        {        	
        	node_initialization(training_DataSet,initial_hidden_nodes);            
            calculating_output_of_hidden_layer(training_DataSet,loop_counter,initial_hidden_nodes);
            calculating_output_of_output_layer(training_DataSet,initial_hidden_nodes);
            fired_output_node_number=calculated_output(training_DataSet);
          //  System.out.println(fired_output_node_number);
            //System.out.println(training_DataSet.classLevels[fired_output_node_number]+""+testing_DataSet.dataSet[loop_counter][testing_DataSet.noOfAttributes-1]);
            if(training_DataSet.classLevels[fired_output_node_number].equals(training_DataSet.dataSet[loop_counter][training_DataSet.noOfAttributes-1]))
            {
                counter++;
            }
        }
        //System.out.println(counter+""+testing_DataSet.noOfRecords);
        double accuracy=((counter/training_DataSet.noOfRecords)*100);        
        //System.out.print("\naccuracy="+accuracy+"%\t\t");//for testing
        return accuracy;
    }
    /*******************************************************************************************************
     *                This method is to calculate the output of hidden layer nodes                        *
     *******************************************************************************************************/
    public void calculating_output_of_hidden_layer(Dataset training_DataSet,int i,int initial_hidden_nodes)
    {
    	int p=0;        
        for(int q=0;q<training_DataSet.noOfAttributes-1;q++)
        {      
            if(Double.parseDouble(training_DataSet.maxValues[p])<Double.parseDouble(training_DataSet.maxValues[q]))
            {
                p=q;
            }//end of if            
        }//end of loop        
         int max=Integer.toString((int)Double.parseDouble(training_DataSet.maxValues[p])).length();
         //System.out.println(max);
        if( max > 2)
        {            
             for(int k=0; k<initial_hidden_nodes;k++)
             {
                for(int j=0;j< training_DataSet.noOfAttributes-1;j++)
                {
                	//initializing hidden nodes
                	initial_nodes[j].input=Double.parseDouble(training_DataSet.normalizedDataSet[i][j]);
                	//System.out.println(i+"="+training_DataSet.dataSet[i][j]);//for training
                	initial_nodes[j].output=initial_nodes[j].input;            
                	hidden_nodes[k].input+=(initial_nodes[j].output*hidden_nodes_weight[j][k]);
                 }//end of inner loop
                hidden_nodes[k].input+=hidden_node_bais[k];
                //System.out.println(hidden_nodes[k].input);//for training
                hidden_nodes[k].output=hidden_nodes[k].Hyperbolic_tangent(hidden_nodes[k].input);//calculating output of hidden nodes
            }//end of outer loop
        }//end of if loop
       else
        {
            //System.out.println("hello");
           for(int k=0; k<initial_hidden_nodes;k++)
           {
            for(int j=0;j< training_DataSet.noOfAttributes-1;j++)
            {
                //initializing hidden nodes
                initial_nodes[j].input=Double.parseDouble(training_DataSet.dataSet[i][j]);
                //System.out.println(i+"="+training_DataSet.dataSet[i][j]);//for training
                initial_nodes[j].output=initial_nodes[j].input;            
                hidden_nodes[k].input+=(initial_nodes[j].output*hidden_nodes_weight[j][k]);
            }//end of inner loop
            hidden_nodes[k].input+=hidden_node_bais[k];
            //System.out.println(hidden_nodes[k].input);//for training
            hidden_nodes[k].output=hidden_nodes[k].Hyperbolic_tangent(hidden_nodes[k].input);//calculating output of hidden nodes
           } //end of outer loop
        }//end of else
    }//end of calculating_output_of_hidden_layer method
    /*******************************************************************************************************
     *                This method is to calculate the output of output layer nodes                         *
     *******************************************************************************************************/
    public void calculating_output_of_output_layer(Dataset training_DataSet,int initial_hidden_nodes)
    {        
    	for(int l=0;l<training_DataSet.noOfClasses;l++)
        {    		
            for(int k =0; k <initial_hidden_nodes; k++)
            {
            	//System.out.println("output_nodes[l].input:"+output_nodes[l].input+"hidden_nodes[k].output:"+hidden_nodes[k].output+"output_nodes_weight:"+output_nodes_weight[k][l]);
            	output_nodes[l].input+=(hidden_nodes[k].output*output_nodes_weight[k][l]);            	
            }//end of loop
            output_nodes[l].input+=output_node_bais[l];
            //calculating output of output nodes
            output_nodes[l].output=output_nodes[l].Hyperbolic_tangent(output_nodes[l].input);
        } //end of outer loop
    }// end of calculating_output_of_output_layer method
	
    /*******************************************************************************************************
     *                This method is to find the fired node                                                *
     *******************************************************************************************************/
    public int calculated_output(Dataset training_DataSet)
    {
        int max=0;
        int present_node=1;
        while( present_node < training_DataSet.noOfClasses)
        {
            if(output_nodes[max].output<output_nodes[present_node].output)
            {
                max=present_node;
            }//end of if
            present_node++;
        }//end while loop
        return max;       
    }//end of calculated_output method
     /*************************************************************************************************************************************
     *                                This method count the total number of zero                                                         *
     *************************************************************************************************************************************/
    private int calculate_number_of_zero(Chromosome chromo, int pop_size, int initial_hidden_nodes)
    {

    	int numberofzero=0;
    	for(int counter1=0;counter1<chromo.gene.length;counter1++)
            {
                if(chromo.gene[counter1].gene_value==0)
                {
                	numberofzero=numberofzero+1;
                }//end else                 
            }//end outer for loop  
    	return (numberofzero);
	}
 

}
