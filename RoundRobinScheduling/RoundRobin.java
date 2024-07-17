public class RoundRobin {
    public static void main(String[] args) {
        final int NUM_PROCESSES = 79;
        final int TIME_QUANTUM = 2;

        String[] processNames = new String[NUM_PROCESSES];
        int[] burstTimes = new int[NUM_PROCESSES];
        int[] waitingTimes = new int[NUM_PROCESSES];
        int[] remainingBurstTimes = new int[NUM_PROCESSES];
        int currentTime = 0;

        // Initialize process names and burst times
        for (int i = 0; i < NUM_PROCESSES; i++) {
            processNames[i] = "P" + (i + 1);
            burstTimes[i] = (i % 10) + 1;
            remainingBurstTimes[i] = burstTimes[i];
        }

        boolean allProcessesCompleted;
        do {
            allProcessesCompleted = true;

            for (int i = 0; i < NUM_PROCESSES; i++) {
                if (remainingBurstTimes[i] > 0) {
                    allProcessesCompleted = false;
                    if (remainingBurstTimes[i] > TIME_QUANTUM) {
                        currentTime += TIME_QUANTUM;
                        remainingBurstTimes[i] -= TIME_QUANTUM;
                    } else {
                        currentTime += remainingBurstTimes[i];
                        waitingTimes[i] = currentTime - burstTimes[i];
                        remainingBurstTimes[i] = 0;
                    }
                }
            }
        } while (!allProcessesCompleted);

        int[] turnaroundTimes = new int[NUM_PROCESSES];
        for (int i = 0; i < NUM_PROCESSES; i++) {
            turnaroundTimes[i] = waitingTimes[i] + burstTimes[i];
        }

        System.out.println("Process\tBurst Time\tWaiting Time\tTurnaround Time");
        for (int i = 0; i < NUM_PROCESSES; i++) {
            System.out.printf("%s\t\t%d\t\t\t%d\t\t\t\t%d\n", processNames[i], burstTimes[i], waitingTimes[i], turnaroundTimes[i]);
        }

        double totalWaitingTime = 0;
        double totalTurnaroundTime = 0;
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


