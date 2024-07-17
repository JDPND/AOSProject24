public class FirstComeFirstServe {
    public static void main(String[] args) {
        final int NUM_PROCESSES = 79;

        String[] processNames = new String[NUM_PROCESSES];
        int[] burstTimes = new int[NUM_PROCESSES];

        // Example burst times for 79 processes
        for (int i = 0; i < NUM_PROCESSES; i++) {
            processNames[i] = "P" + (i + 1);
            burstTimes[i] = (i % 10) + 1; // Assigning burst times as an example
        }

        int[] waitingTimes = new int[NUM_PROCESSES];
        int[] turnaroundTimes = new int[NUM_PROCESSES];

        waitingTimes[0] = 0;
        for (int i = 1; i < NUM_PROCESSES; i++) {
            waitingTimes[i] = burstTimes[i - 1] + waitingTimes[i - 1];
        }

        for (int i = 0; i < NUM_PROCESSES; i++) {
            turnaroundTimes[i] = waitingTimes[i] + burstTimes[i];
        }

        System.out.println("Process\tBurst Time\tWaiting Time\tTurnaround Time");
        for (int i = 0; i < NUM_PROCESSES; i++) {
            System.out.println(processNames[i] + "\t\t\t" + burstTimes[i] + "\t\t\t" + waitingTimes[i] + "\t\t\t\t" + turnaroundTimes[i]);
        }

        double totalWaitingTime = 0.0;
        double totalTurnaroundTime = 0.0;

        for (int i = 0; i < NUM_PROCESSES; i++) {
            totalWaitingTime += waitingTimes[i];
            totalTurnaroundTime += turnaroundTimes[i];
        }

        double averageWaitingTime = totalWaitingTime / NUM_PROCESSES;
        double averageTurnaroundTime = totalTurnaroundTime / NUM_PROCESSES;

        System.out.printf("\nAverage waiting time = %.2f\n", averageWaitingTime);
        System.out.printf("Average turnaround time = %.2f\n", averageTurnaroundTime);
    }
}


