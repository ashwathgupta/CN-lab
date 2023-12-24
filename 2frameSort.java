import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

class Packet {
    int sequenceNumber;
    String data;

    Packet(int sequenceNumber, String data) {
        this.sequenceNumber = sequenceNumber;
        this.data = data;
    }
}

public class frameSort2 {
    private static final int FRAME_SIZE = 3;

    public static Packet[] divideMessageIntoPackets(String message) {
        int messageLength = message.length();
        int numberOfPackets = (int) Math.ceil((double) messageLength / FRAME_SIZE);
        Packet[] packets = new Packet[numberOfPackets];

        for (int i = 0, j = 0; i < numberOfPackets; i++) {
            int end = Math.min(j + FRAME_SIZE, messageLength);
            packets[i] = new Packet(i + 1, message.substring(j, end));
            j += FRAME_SIZE;
        }

        return packets;
    }

    public static Packet[] shufflePackets(Packet[] originalPackets) {
        Random random = new Random();
        Packet[] shuffledPackets = Arrays.copyOf(originalPackets, originalPackets.length);

        for (int i = 0; i < shuffledPackets.length; i++) {
            int randomIndex = random.nextInt(shuffledPackets.length);
            Packet temp = shuffledPackets[i];
            shuffledPackets[i] = shuffledPackets[randomIndex];
            shuffledPackets[randomIndex] = temp;
        }

        return shuffledPackets;
    }

   /*  public static void SortPackets(Packet[] packets) {
        int n = packets.length;
        boolean swapped;
        do {
            swapped = false;
            for (int i = 1; i < n; i++) {
                if (packets[i - 1].sequenceNumber > packets[i].sequenceNumber) {
                    Packet temp = packets[i - 1];
                    packets[i - 1] = packets[i];
                    packets[i] = temp;
                    swapped = true;
                }
            }
            n--;
        } while (swapped);
    }*/


    public static void SortPackets(Packet[] packets) {
        Arrays.sort(packets, Comparator.comparingInt(packet -> packet.sequenceNumber));
    }


    public static void receiveAndProcessPackets(Packet[] receivedPackets) {
        System.out.println("\nPackets received in the following order:");
        for (Packet packet : receivedPackets) {
            System.out.printf("Frame %d: %s%n", packet.sequenceNumber, packet.data);
        }

        SortPackets(receivedPackets);

        System.out.println("\n\nPackets in order after sorting:");
        for (Packet packet : receivedPackets) {
            System.out.printf("Frame %d: %s%n", packet.sequenceNumber, packet.data);
        }

        System.out.println("\n\nMessage received is :\n");
        StringBuilder reconstructedMessage = new StringBuilder();
        for (Packet packet : receivedPackets) {
            reconstructedMessage.append(packet.data);
        }
        System.out.println(reconstructedMessage);
    }

    public static void main(String[] args) {
        String message;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the message to be transmitted:");
        message = scanner.nextLine();

        System.out.println("\nMessage divided into frames:");
        Packet[] originalPackets = divideMessageIntoPackets(message);
        for (Packet packet : originalPackets) {
            System.out.printf("Frame %d: %s%n", packet.sequenceNumber, packet.data);
        }

        Packet[] shuffledPackets = shufflePackets(originalPackets);
        receiveAndProcessPackets(shuffledPackets);

        scanner.close();
    }
}


/*Enter The message to be Transmitted:
My name is Ashwath

Message divided into frames:
Frame 1: My
Frame 2: nam
Frame 3: e i
Frame 4: s A
Frame 5: shw
Frame 6: ath

Packets received in the following order:
Frame 3: e i
Frame 2: nam
Frame 4: s A
Frame 1: My
Frame 5: shw
Frame 6: ath


Packets in order after sorting:
Frame 1: My
Frame 2: nam
Frame 3: e i
Frame 4: s A
Frame 5: shw
Frame 6: ath


Message received is :

My name is Ashwath */
