
import java.util.*;


public class Nodes 
{
    double input;
    double output;
    double bais;
    double error;
    int hidden_nodes;
    Nodes()
    {
    
    }
    /*******************************************************************************************************
     *                     Activation function(hyperbolic tangent function)                                *
     *******************************************************************************************************/
    double Hyperbolic_tangent(double x) 
    {   
        return (double)((1.0 - Math.exp(-(2*x)))/(1.0 + Math.exp(-(2*x))));
    }
    
    
    
}
