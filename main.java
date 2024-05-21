import java.util.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class main {
    public static void main(String args[]) throws Exception {
        // for(int counter0=0;counter0<10;counter0++)
        // {

        // buildInitialPopulation DRFCR=new buildInitialPopulation();
        /***********************************
         * all dataset
         ******************* gg
         *****************/

        //String dataset = "BREAST";
        //String dataset="CREDIT"; //
        //String dataset="DIABETES";
        //String dataset="GLASS"; //
        //String dataset="HEART";
        String dataset = "IRIS";
        //String dataset="LABOR"; // ERROR
        //String dataset="SONAR"; // ERROR
        //String dataset="WINE"; 
        //String dataset="ZOO"; //ERROR
        //String dataset="LETTER"; // ERROR
        //String dataset="YEAST";
        //String dataset="AUTO";
        //String dataset="ANNEAL";

        System.out.println(dataset);// for testing

        int population_size = 20;
        int fold = 9;
        double avg_training_accuracy = 0;
        double avg_testing_accuracy = 0;
        double voting_acc = 0.00;
        double avg_voting_acc = 0;
        double acc = 0;
        System.out.println(dataset);// for testing
        // dataset path and attribute path
        for (int counter = 0; counter <= fold; counter++) {
            System.out.println(counter + "fold-----------------------------------------------------------");
            String path_Training_DataSet = "src/files/" + dataset + "training"
                    + counter + ".data";
            // System.out.println(dataset);
            String path_Testing_DataSet = "src/files/" + dataset + "test" + counter
                    + ".data";
            String path_Attribute_Information = "src/files/" + dataset
                    + "Attribute.data";
            String dlimiter = ",";
            // invoke DataSet constructor ---> class DataSet
            Dataset training_DataSet = new Dataset(path_Training_DataSet, dlimiter, path_Attribute_Information,
                    dlimiter);
            Dataset testing_DataSet = new Dataset(path_Testing_DataSet, dlimiter, path_Attribute_Information, dlimiter);
            // Dataset training_DataSet= new
            // Dataset(path_Training_DataSet,dlimiter,path_Attribute_Information,dlimiter);
            // invoke BackPropagation constructor ---> class BackPropagation
            // BackPropagation bpn= new BackPropagation(training_DataSet);
            // calling train_bpn method -----> class BackPropagation
            // bpn.train_bpn(training_DataSet);
            float crossover_probability = (float) 0.8;
            float mutation_probability = (float) 0.1;
            int number_of_generation = 300;
            int number_of_hidden_nodes = (training_DataSet.noOfAttributes - 1) * (training_DataSet.noOfClasses);
            MOGA_ANN moga_ann = new MOGA_ANN(training_DataSet, testing_DataSet, population_size, crossover_probability,
                    mutation_probability, number_of_generation, number_of_hidden_nodes);
            // System.out.println("population size="+moga_ann.pareto_pop.pop_size);
            // System.out.println("for
            // testing-----------------------------------------------------------------");
            // moga_ann.pareto_pop.Show_population();
            double accuracy = moga_ann.pop_ANN.bpn[0].Classification(training_DataSet, testing_DataSet,
                    moga_ann.pareto_pop.chromo[moga_ann.pareto_pop.fit_chromo],
                    (training_DataSet.noOfAttributes - 1) * (training_DataSet.noOfClasses));
            for (int counter1 = 0; counter1 < moga_ann.pareto_pop.pop_size - 1; counter1++) {
                moga_ann.pop_ANN.bpn[counter1].pareto_voting(training_DataSet, testing_DataSet,
                        moga_ann.pareto_pop.chromo[counter1],
                        (training_DataSet.noOfAttributes - 1) * (training_DataSet.noOfClasses));
            } // end of for
            acc = 0.0;
            int vote_count[] = new int[training_DataSet.noOfClasses];
            String fired_nodes[] = new String[testing_DataSet.noOfRecords];
            for (int counter2 = 0; counter2 < testing_DataSet.noOfRecords - 1; counter2++) {
                for (int counter3 = 0; counter3 < training_DataSet.noOfClasses - 1; counter3++) {
                    int count = 0;
                    for (int counter4 = 0; counter4 < moga_ann.pareto_pop.pop_size - 1; counter4++) {
                        if (moga_ann.pareto_pop.chromo[counter4].vote[counter2]
                                .equals(training_DataSet.classLevels[counter3])) {
                            count++;
                        } // end of if
                    } // end of 3rd for loop
                    vote_count[counter3] = count;
                } // end of 2nd for loop
                int max = 0;
                for (int counter5 = 0; counter5 < training_DataSet.noOfClasses; counter5++) {
                    if (vote_count[max] < vote_count[counter5]) {
                        max = counter5;
                    } // end of if statement
                } // end of for loop
                fired_nodes[counter2] = training_DataSet.classLevels[max];
            } // end of 1'st for loop
            for (int counter6 = 0; counter6 < testing_DataSet.noOfRecords - 1; counter6++) {
                if (fired_nodes[counter6]
                        .equals(testing_DataSet.dataSet[counter6][training_DataSet.noOfAttributes - 1])) {
                    acc++;
                }
            } // end of for loop
            voting_acc = (acc / testing_DataSet.noOfRecords) * 100;
            avg_voting_acc += voting_acc;
            avg_testing_accuracy += accuracy;
            avg_training_accuracy += moga_ann.pareto_pop.chromo[moga_ann.pareto_pop.fit_chromo].fitness;
            System.out.print("training accuracy=" + moga_ann.pareto_pop.chromo[moga_ann.pareto_pop.fit_chromo].fitness);
            System.out.print("\ttesting accuracy=" + accuracy);
            System.out.print(
                    "\tno of zeros=" + moga_ann.pareto_pop.chromo[moga_ann.pareto_pop.max_zero_chromo].num_of_zero);
            System.out.println();
            // System.out.println("\nThe average training accuracy"+avg_training_accuracy);
            // System.out.println("\nThe average testing accuracy"+avg_testing_accuracy);
        } // end of final for loop
        System.out.println("avg testing accuracy" + avg_testing_accuracy / 10);
        System.out.println("avg voting accuracy" + avg_voting_acc / 10);
    }

    public static double Calculate_testing_accuracy(Dataset training_DataSet, Dataset testing_DataSet,
            Chromosome chromo) {
        double accuracy = 0.0;
        return accuracy;
    }

}