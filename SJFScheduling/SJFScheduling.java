import java.util.*;

class NumOfProcess {
    String name;
    int burstTime;

    NumOfProcess(String name, int burstTime) {
        this.name = name;
        this.burstTime = burstTime;
    }
}

public class SJFScheduling {
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        int numProcesses = 79;
        int transientEventTime = 5;
        List<NumOfProcess> numOfProcesses = generateProcesses(numProcesses);
        sjfScheduling(numOfProcesses, transientEventTime);
    }

    private static List<NumOfProcess> generateProcesses(int count) {
        List<NumOfProcess> numOfProcesses = new ArrayList<>(count);
        for (int i = 1; i <= count; i++) {
            numOfProcesses.add(new NumOfProcess("P" + i, RANDOM.nextInt(10) + 1));
        }
        return numOfProcesses;
    }

    public static void sjfScheduling(List<NumOfProcess> numOfProcesses, int transientEventTime) {
        // PriorityQueue for SJF scheduling
        PriorityQueue<NumOfProcess> processQueue = new PriorityQueue<>(Comparator.comparingInt(p -> p.burstTime));
        processQueue.addAll(numOfProcesses);

        int currentTime = 0;
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;
        boolean transientEventOccurred = false;

        StringBuilder output = new StringBuilder("Process\tBurst Time\tWaiting Time\tTurnaround Time\n");

        while (!processQueue.isEmpty()) {
            NumOfProcess numOfProcess = processQueue.poll();

            if (currentTime >= transientEventTime && !transientEventOccurred) {
                numOfProcess.burstTime += 3;
                output.append(String.format("Transient event: Process %s burst time adjusted by +3 units at time %d\n",
                        numOfProcess.name, currentTime));
                transientEventOccurred = true;

                // Reinsert the updated process into the priority queue
                processQueue.add(numOfProcess);
                // Re-sorting would be handled by the priority queue itself
            }

            int waitingTime = currentTime;
            int turnaroundTime = waitingTime + numOfProcess.burstTime;

            totalWaitingTime += waitingTime;
            totalTurnaroundTime += turnaroundTime;
            currentTime += numOfProcess.burstTime;

            output.append(String.format("%s\t\t%d\t\t\t%d\t\t\t\t%d\n",
                    numOfProcess.name, numOfProcess.burstTime, waitingTime, turnaroundTime));
        }

        System.out.print(output);
        System.out.printf("\nAverage waiting time = %.2f\n", (double) totalWaitingTime / numOfProcesses.size());
        System.out.printf("Average turnaround time = %.2f\n", (double) totalTurnaroundTime / numOfProcesses.size());
    }
}
