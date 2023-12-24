import java.util.Random;
import java.util.Scanner;

public class RED
{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the max no of packets");// no of pkts to be sent
        int maxPackets= scanner.nextInt();

        System.out.println("Enter queue size"); // size of queue pkt can be stored

        int queueSize = scanner.nextInt();
        System.out.println("Enter the max probability");

        double maxProbability=scanner.nextDouble();
        System.out.println("Enter the min probability");
        double minProbability=scanner.nextDouble();

        System.out.println("Enter the threshold value");

        int threshold=scanner.nextInt();

        simulateCongestion(maxPackets,queueSize,maxProbability,minProbability,threshold);


    }
    private static void simulateCongestion(int maxPackets , int queueSize, double maxProbability,double minProbability, int threshold)
    {
        Random rand = new Random (System.currentTimeMillis());
        int queueLength=0;

        for(int i=0; i<maxPackets;i++)
        {

            double dropProbability = calculateDropProbability(queueLength,queueSize,maxProbability,minProbability,threshold);

            if(queueLength>=threshold && rand.nextDouble() <dropProbability)
            {
                System.out.println("Packet dropped (Congestion avoidance)");
                //checking the threshold value and the probabbility to check whether to accept or reject the packet
            }
            else{
                System.out.println("packet accepted"+(i+1));
                queueLength++;
            }
        }
    }
    private static double calculateDropProbability(int currentQueueLength, int queueSize,double maxProbability,double minProbability,int threshold)
    {
        double slope = (maxProbability-minProbability)/(queueSize-threshold);

        return minProbability+slope*(currentQueueLength - threshold);
    }
}